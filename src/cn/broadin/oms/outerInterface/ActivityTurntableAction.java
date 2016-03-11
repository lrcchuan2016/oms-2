package cn.broadin.oms.outerInterface;

import it.sauronsoftware.base64.Base64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import cn.broadin.oms.model.ActivityGiftBean;
import cn.broadin.oms.model.ActivityLotteryRecordBean;
import cn.broadin.oms.model.ActivityWinningRecordBean;
import cn.broadin.oms.model.AdResourceBean;
import cn.broadin.oms.model.ClubBean;
import cn.broadin.oms.model.TerminalBean;
import cn.broadin.oms.model.UserAddrBean;
import cn.broadin.oms.model.UserBean;
import cn.broadin.oms.model.UserClubBean;
import cn.broadin.oms.outerUtil.TurntableHandler;
import cn.broadin.oms.service.ActivityTurntableService;
import cn.broadin.oms.service.AdService;
import cn.broadin.oms.service.ClubService;
import cn.broadin.oms.service.TerminalService;
import cn.broadin.oms.service.UserAddrService;
import cn.broadin.oms.service.UserService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.Tools;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 转盘活动接口类
 * @author Administrator
 *
 */
@Controller("activityTurntableAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActivityTurntableAction extends ActionSupport implements ServletResponseAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static int NUM = 2;   //定义转盘次数为2 
	private final static long SEC = 30*1000l;  //30s
	public final static int lotteryBaseNum = 1000000; //抽奖基数
	private HttpServletResponse response;
	private Map<String, String> paramMap = new HashMap<String, String>();
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private List<ConditionBean> conditions = new ArrayList<ConditionBean>();
	
	@Resource
	private TurntableHandler handler;
	@Resource
	private ActivityTurntableService turnSerice;
	@Resource
	private ClubService clubService;
	@Resource
	private UserService userService;
	@Resource
	private AdService adService;
	@Resource
	private UserAddrService userAddrService;
	@Resource
	private TerminalService terminalService;
	
	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	
	public Map<String, Object> getResultJson() {
		return resultJson;
	}

	public void setResultJson(Map<String, Object> resultJson) {
		this.resultJson = resultJson;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse resp) {
		this.response = resp;
	}
	
	/**
	 * 1、查询所有家庭成员的抽奖次数
	 * @return
	 */
	public String queryMemberLotteryNum(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(!validateToken(paramMap.get("token"))){
			resultJson.put("result", "00000005");
			resultJson.put("tip", "token错误！");
			return Action.SUCCESS;
		}
		if(null != paramMap.get("familyId") && null != paramMap.get("activityId")){
			ClubBean club = clubService.selectClubById(paramMap.get("familyId"));
			AdResourceBean adResource = adService.selectResourceById(AdResourceBean.class, paramMap.get("activityId"));
			if(null != club && null != adResource){
				conditions.add(new ConditionBean("club", club, ConditionBean.EQ));
				List<UserClubBean> userClubs = clubService.selectUserClub(UserClubBean.class, conditions, null, false, null, null);
				
				if(null != userClubs){
					JSONObject jo = new JSONObject();
					JSONArray ja = new JSONArray();
					for (UserClubBean uc : userClubs) {
						JSONObject json = new JSONObject();
						 conditions.clear();
						 long startUtc = adResource.getStart_utc();
						 long endUtc = adResource.getEnd_utc();
						 UserBean user = uc.getUser();
						 conditions.add(new ConditionBean("user", user, ConditionBean.EQ));
						 conditions.add(new ConditionBean("club", club, ConditionBean.EQ));
						 conditions.add(new ConditionBean("startUtc", startUtc, ConditionBean.GE));
						 conditions.add(new ConditionBean("startUtc", endUtc, ConditionBean.LT));
						 List<ActivityLotteryRecordBean> lotteryRecords = turnSerice.findLotteryRecord(conditions,null,false,null,null);
						//封装数据
						 json.element("userId", user.getId());
						 json.element("account", user.getAccount());
						 json.element("nickname", uc.getUser_nickname());
						 json.element("portrait", user.getPortrait());
						 if(null==lotteryRecords || lotteryRecords.isEmpty()) json.element("number", NUM);
						 else json.element("number", lotteryRecords.get(0).getLotteryNumber());
						ja.add(json);
					}
					jo.element("users", ja);
					resultJson.put("result", "00000000");
					resultJson.put("data", jo);
					return Action.SUCCESS;
				}else{
					resultJson.put("result", "00000006");
					resultJson.put("tip", "用户不在此家庭中！");
					return Action.SUCCESS;
				}
			}else{
				resultJson.put("result", "00000007");    //参数不正确
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000008");    //缺少参数
		return Action.SUCCESS;
	}
	
	/**
	 * 2、查询抽奖权限（手机端扫描二维码-手机端）
	 * @return
	 */
	public String queryLotteryAuth(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(!validateToken(paramMap.get("token"))){
			resultJson.put("result", "00000005");
			resultJson.put("tip", "token错误！");
			return Action.SUCCESS;
		}
		if(null != paramMap.get("familyId") && null != paramMap.get("userId") && null != paramMap.get("activityId")
				&& null != paramMap.get("terminalAddr")){
			String familyId = paramMap.get("familyId");
			String userId = paramMap.get("userId");
			String activityId = paramMap.get("activityId");
			String terminalAddr = paramMap.get("terminalAddr");
			ClubBean club = clubService.selectClubById(familyId);
			UserBean user = userService.selectById(UserBean.class, userId);
			AdResourceBean activity = adService.selectResourceById(AdResourceBean.class, activityId);
			//添加条件
			//先看活动是否有效
			//1.查看用户是否在此家庭中
			if(Tools.getNowUTC()>activity.getStart_utc() && Tools.getNowUTC()<=activity.getEnd_utc()){
				conditions.add(new ConditionBean("club", club, ConditionBean.EQ));
				conditions.add(new ConditionBean("user", user, ConditionBean.EQ));
				List<UserClubBean> uc = clubService.selectUserClub(UserClubBean.class, conditions, null, false, null, null);
				if(null != uc && !uc.isEmpty()){
					//2、此家庭是否有人抽奖
					ActivityLotteryRecordBean lotteryRecord = findLottery(activity,club);
					//3、lotteryRecord为空或者未抽奖或者超时可继续
					if(null == lotteryRecord || handler.validateSec(lotteryRecord.getStartUtc(),SEC)){
						//4、抽奖次数
						if(null == lotteryRecord){
							//NUM-置为抽奖次数，旋转置1  抽奖状态置1-正在抽奖
							ActivityLotteryRecordBean lr = turnSerice.findLottery(activity, club, user);
							
								if(lr == null){
									ActivityLotteryRecordBean lottery = new ActivityLotteryRecordBean(Tools.getMD5AndUUID(16), club, user, activity, NUM, 0,1, Tools.getNowUTC(), Tools.getNowUTC(),terminalAddr);
									if(null != turnSerice.addLotteryRecord(lottery)){
										resultJson.put("result", "00000000");
										resultJson.put("num", lottery.getLotteryNumber());
										
									}else resultJson.put("result", "00000001");
								}else{
									if(lr.getLotteryNumber()>0){
										lr.setIsLotteryNow(0);
										lr.setIsRotate(1);
										lr.setTerminalAddr(terminalAddr);  //哪个终端在抽奖
										lr.setStartUtc(Tools.getNowUTC());
										turnSerice.updateLotteryRecord(lr);
									}
									resultJson.put("result", "00000000");
									resultJson.put("num", lr.getLotteryNumber());
								}
							
							
						}else{
							//注意抽奖次数不在此更新
							lotteryRecord.setIsLotteryNow(0);
							lotteryRecord.setIsRotate(1);
							lotteryRecord.setTerminalAddr(terminalAddr);
							lotteryRecord.setStartUtc(Tools.getNowUTC());
	//						lotteryRecord.setEndUtc(Tools.getNowUTC());
							if(null != turnSerice.updateLotteryRecord(lotteryRecord)){
								resultJson.put("result", "00000000");
								resultJson.put("num", lotteryRecord.getLotteryNumber());
							}else resultJson.put("result", "00000001");
						}
						return Action.SUCCESS;
					}else {
						UserBean userBean = lotteryRecord.getUser();
						JSONObject jsUser = new JSONObject();
						jsUser.element("id", userBean.getId());
						jsUser.element("account", userBean.getAccount());
						jsUser.element("nickname", userBean.getNickname());
						jsUser.element("portrait", userBean.getPortrait());
						resultJson.put("result", "00000006"); //正在抽奖
						resultJson.put("tip", "正在抽奖");
						resultJson.put("user", jsUser);
					}
				}else resultJson.put("result", "00000007");	//用户不在此家庭中
			}else resultJson.put("result", "00000009");
		}else resultJson.put("result", "00000008");		//未传入参数
		return Action.SUCCESS;
	}
	
	/**
	 * 3、是否抽奖（是否转动转盘-TV端）
	 * @return
	 */
	public String isLottery(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(!validateToken(paramMap.get("token"))){
			resultJson.put("result", "00000005");
			resultJson.put("tip", "token错误！");
			return Action.SUCCESS;
		}
		if(null != paramMap.get("activityId") && null != paramMap.get("familyId")
				&& null != paramMap.get("terminalAddr")){
			ClubBean club = clubService.selectClubById(paramMap.get("familyId"));
			String terminalAddr = paramMap.get("terminalAddr");
			//UserBean user = userService.selectById(UserBean.class, paramMap.get("userId"));
			AdResourceBean activity = adService.selectResourceById(AdResourceBean.class, paramMap.get("activityId"));
			if(null != activity && null != club){
				ActivityLotteryRecordBean lotteryRecord = findLottery(activity, club);
				if(null != lotteryRecord){
					if(0 == lotteryRecord.getIsLotteryNow()){
					if(lotteryRecord.getLotteryNumber()>0
							&& lotteryRecord.getLotteryNumber()<=NUM){
						if(terminalAddr.equals(lotteryRecord.getTerminalAddr())){
							JSONObject jo = new JSONObject();
							UserBean user = lotteryRecord.getUser();
							jo.element("id", user.getId());
							jo.element("account", user.getAccount());
							jo.element("portrait", user.getPortrait());
							jo.element("nickname", user.getNickname());
							
							resultJson.put("result", "00000000");
							resultJson.put("user", jo);
						}else resultJson.put("result", "00000001");   //终端地址不正确
					}else{
						resultJson.put("result", "00000004");
						resultJson.put("tip", "抽奖次数无效");
					}
					}else{
						resultJson.put("result", "00000002");
						resultJson.put("tip", "正在抽奖");
					}
				}else{
					resultJson.put("result", "00000003");
					resultJson.put("tip", "未扫描二维码");
				}
			}else resultJson.put("result", "00000007");		//参数不正确
		}else resultJson.put("result", "00000008");  //未输入参数
		return Action.SUCCESS;
	}
	
	/**
	 * 4、获取抽奖记录
	 * @return
	 */
	public String getWinningRecord(){
			response.setHeader("Access-Control-Allow-Origin","*");
			if(!validateToken(paramMap.get("token"))){
				resultJson.put("result", "00000001");
				resultJson.put("tip", "token错误！");
				return Action.SUCCESS;
			}
			if(null != paramMap.get("activityId") 
					&& null != paramMap.get("userId") 
					&& null != paramMap.get("familyId")){
				AdResourceBean activity = adService.selectResourceById(AdResourceBean.class, paramMap.get("activityId"));
				UserBean user = userService.selectById(UserBean.class, paramMap.get("userId"));
				ClubBean club = clubService.selectClubById(paramMap.get("familyId"));
				if(null != activity 
						&& null != user 
						&& null != club){
					ActivityLotteryRecordBean lotteryRecord = turnSerice.findLottery(activity, club, user);
					if(null != lotteryRecord 
							&& lotteryRecord.getIsRotate() == 1
							&& lotteryRecord.getLotteryNumber()>0 
							&& lotteryRecord.getLotteryNumber()<=NUM){
						//抽奖超时
						if(!handler.validateSec(lotteryRecord.getStartUtc(), SEC)){
							//正在抽奖(改变抽奖状态)
							lotteryRecord.setIsLotteryNow(1);
							//lotteryRecord.setStartUtc(Tools.getNowUTC());
							lotteryRecord.setEndUtc(Tools.getNowUTC());
							
							turnSerice.updateLotteryRecord(lotteryRecord);
							conditions.clear();
							conditions.add(new ConditionBean("giftNumber", 0, ConditionBean.GT));
							List<ActivityGiftBean> gifts = turnSerice.findGiftByConditions(conditions,null,false,"modifyUtc",null);
							ActivityGiftBean gift = randomLottery(gifts);  
							ActivityWinningRecordBean winningRecord = null;
							//抽到奖品后保存
							if(null != gift){
								gift.setGiftNumber(gift.getGiftNumber()-1);  //抽中后更新奖品数量
								winningRecord = new ActivityWinningRecordBean(Tools.getMD5AndUUID(16), gift.getName(), "", lotteryRecord.getId(), gift.getId(), "", "",0, "", 0, Tools.getNowUTC(), 0l,"","",0l);
								turnSerice.updateGift(gift);
								turnSerice.addWinningRecord(winningRecord);
							}else{
								winningRecord = new ActivityWinningRecordBean(Tools.getMD5AndUUID(16), "谢谢参与", "", lotteryRecord.getId(), "0", "", "",0, "", 0, Tools.getNowUTC(), 0l,"","",0l);
								turnSerice.addWinningRecord(winningRecord);
							}
							
							//2、更新抽奖记录
							lotteryRecord.setIsLotteryNow(0);
							lotteryRecord.setIsRotate(0);
							lotteryRecord.setLotteryNumber(lotteryRecord.getLotteryNumber()-1);   //抽奖次数减1
							lotteryRecord.setEndUtc(Tools.getNowUTC());  //抽奖结束时间
							
							//更新
							if(null != turnSerice.updateLotteryRecord(lotteryRecord)) {
								resultJson.put("result", "00000000");
								resultJson.put("winRecord", winningRecord);
								resultJson.put("club", club);
								resultJson.put("prize", gift);
							}else resultJson.put("result", "00000001");
							return Action.SUCCESS;
						}else{
							resultJson.put("result", "00000006");
							resultJson.put("tip", "抽奖已超时");
						}
					}else {
						resultJson.put("result", "00000004");   //中奖记录无效
						resultJson.put("tip", "无抽奖次数或未扫描家庭二维码");
					}
				}else resultJson.put("result", "00000007");
			}else resultJson.put("result", "0000008");   //未传入参数
			return Action.SUCCESS;
	}

	
//	/**
//	 * 4、保存中奖记录
//	 * @return
//	 */
//	public String saveWinningRecord(){
//		response.setHeader("Access-Control-Allow-Origin","*");
//		if(!validateToken(paramMap.get("token"))){
//			resultJson.put("result", "00000001");
//			resultJson.put("tip", "token错误！");
//			return Action.SUCCESS;
//		}
//		if(null != paramMap.get("activityId") 
//				&& null != paramMap.get("userId") 
//				&& null != paramMap.get("familyId")){
//			AdResourceBean activity = adService.selectResourceById(AdResourceBean.class, paramMap.get("activityId"));
//			UserBean user = userService.selectById(UserBean.class, paramMap.get("userId"));
//			ClubBean club = clubService.selectClubById(paramMap.get("familyId"));
//			if(null != activity 
//					&& null != user 
//					&& null != club){
//				ActivityLotteryRecordBean lotteryRecord = turnSerice.findLottery(activity, club, user);
//				if(null != lotteryRecord){
//					//是否中奖（有没有giftId）
//					if(null != paramMap.get("giftId") && null != paramMap.get("name")){
//						//1、新增中奖记录。 
//						ActivityWinningRecordBean winningRecord = new ActivityWinningRecordBean(Tools.getMD5AndUUID(16), paramMap.get("name"), "", lotteryRecord.getId(), paramMap.get("giftId"), "", "",0, "", 0, Tools.getNowUTC(), 0l,"","",0l);
//						if(null == turnSerice.addWinningRecord(winningRecord)){
//							resultJson.put("result", "00000001");
//							return Action.SUCCESS;
//						}
//					}
//					//2、更新抽奖记录
//					lotteryRecord.setIsLotteryNow(0);
//					lotteryRecord.setIsRotate(0);
//					lotteryRecord.setLotteryNumber(lotteryRecord.getLotteryNumber()-1);   //抽奖次数减1
//					lotteryRecord.setEndUtc(Tools.getNowUTC());  //抽奖结束时间
//					
//					//更新
//					if(null != turnSerice.updateLotteryRecord(lotteryRecord)) resultJson.put("result", "00000000");
//					else resultJson.put("result", "00000001");
//					return Action.SUCCESS;
//				}else{
//					resultJson.put("result", "00000006");
//					resultJson.put("tip", "找不到中奖相关的抽奖信息");
//				}
//			}else resultJson.put("result", "00000007");
//		}else resultJson.put("result", "0000008");   //未传入参数
//		return Action.SUCCESS;
//	}
	
	/**
	 * 5、返回抽奖记录
	 * @return
	 */
	public String queryLotteryRecord(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(!validateToken(paramMap.get("token"))){
			resultJson.put("result", "00000005");
			resultJson.put("tip", "token错误！");
			return Action.SUCCESS;
		}
		if(null != paramMap.get("activityId") 
				&& null != paramMap.get("familyId")
				&& null != paramMap.get("userId")){
			AdResourceBean activity = adService.selectResourceById(AdResourceBean.class, paramMap.get("activityId"));
			UserBean user = userService.selectById(UserBean.class, paramMap.get("userId"));
			ClubBean club = clubService.selectClubById(paramMap.get("familyId"));
			if(null != activity && null != user && null != club){
				ActivityLotteryRecordBean lotteryRecord = turnSerice.findLottery(activity, club, user);
				if(null != lotteryRecord && 0 == lotteryRecord.getIsLotteryNow()){
					//conditions.add(new ConditionBean("giftId", "0", ConditionBean.NOT_EQ));
					PaginationBean page = turnSerice.findWinningRecord(conditions,null,false,"winningUtc",null);
					if(null != page && !page.getRecords().isEmpty()){
						ActivityWinningRecordBean wr = (ActivityWinningRecordBean) page.getRecords().get(0);
						resultJson.put("winningRecord", wr);
						resultJson.put("result", "00000000");
						return Action.SUCCESS;
					}else{
						resultJson.put("result", "00000004");    //没有抽奖记录
						resultJson.put("tip", "没有中奖记录");
					}
				}else resultJson.put("result", "00000006");     //
			}else resultJson.put("result", "00000007");
		}else resultJson.put("result", "00000008");   //未传入参数
		return Action.SUCCESS;
	}
	
	/**
	 * 6、保存领奖信息
	 * @return
	 */
	public String saveAcceptRecord(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(!validateToken(paramMap.get("token"))){
			resultJson.put("result", "00000005");
			resultJson.put("tip", "token错误！");
			return Action.SUCCESS;
		}
		if(null != paramMap.get("prizeId")
				&& null != paramMap.get("name")
				&& null != paramMap.get("phone")
				&& null != paramMap.get("addr")
				){
			ActivityWinningRecordBean wr = turnSerice.findWinningRecordById(paramMap.get("prizeId"));
				if(null != wr){
					if(wr.getIsDeliver() == 0){
						if(null != paramMap.get("content")) wr.setContent(StringEscapeUtils.unescapeXml(paramMap.get("content")));
						if(null != paramMap.get("addrId"))	wr.setAddrId(Integer.valueOf(paramMap.get("addrId")));
						
						wr.setIsDeliver(1);
						wr.setReceiver(paramMap.get("name"));
						wr.setReceiverPhone(paramMap.get("phone"));
						wr.setReceiverAddr(paramMap.get("addr"));
						wr.setAcceptPrizeUtc(Tools.getNowUTC());
						if(null != turnSerice.updateWinningRecord(wr)){
							resultJson.put("result", "00000000");
							return Action.SUCCESS;
						}else resultJson.put("result", "00000001");
					}else resultJson.put("result", "00000006");
				}else resultJson.put("result", "00000007"); //参数有误
		}else resultJson.put("result", "00000008");   //未输入参数
		return Action.SUCCESS;
	}
	
	/**
	 * 7、获取领奖记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryAcceptRecord(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(!validateToken(paramMap.get("token"))){
			resultJson.put("result", "00000005");
			resultJson.put("tip", "token错误！");
			return Action.SUCCESS;
		}
		if(null != paramMap.get("activityId") 
				&& (null != paramMap.get("userId") || null != paramMap.get("familyId"))){
			AdResourceBean activity = adService.selectResourceById(AdResourceBean.class, paramMap.get("activityId"));
			if(null != activity){
				if(null != paramMap.get("userId")){
					UserBean user = userService.selectById(UserBean.class, paramMap.get("userId"));
					if(null != user){
						conditions.add(new ConditionBean("user", user, ConditionBean.EQ));
						conditions.add(new ConditionBean("startUtc", activity.getStart_utc(), ConditionBean.GE));
						conditions.add(new ConditionBean("startUtc", activity.getEnd_utc(), ConditionBean.LT));
						List<ActivityLotteryRecordBean> lotteryRecords = turnSerice.findLotteryRecord(conditions, null, false, "endUtc", null);
						if(null != lotteryRecords && !lotteryRecords.isEmpty()){
							conditions.clear();
							SimpleExpression[] criterions = new SimpleExpression[lotteryRecords.size()];
							for (int i=0;i<lotteryRecords.size();i++) {
								criterions[i] = Restrictions.eq("lotteryId", lotteryRecords.get(i).getId());
							}
							conditions.add(new ConditionBean(null, criterions, ConditionBean.OR));
							//conditions.add(new ConditionBean("lotteryId", lotteryRecords.get(0).getId(), ConditionBean.EQ));
							conditions.add(new ConditionBean("giftId", "0", ConditionBean.NOT_EQ));
							PaginationBean page = turnSerice.findWinningRecord(conditions, null, false, "winningUtc", null);
							if(null != page && !page.getRecords().isEmpty()){
								resultJson.put("result", "00000000");
								resultJson.put("winningRecord", page.getRecords());
							}else resultJson.put("result", "00000002");    //未中奖
							return Action.SUCCESS;	
						}else{
							resultJson.put("result", "00000003");
							resultJson.put("tip", "没有抽奖信息！");
						}
					}else{
						resultJson.put("result", "00000004");
						resultJson.put("tip", "用户不存在");
						return Action.SUCCESS;
					}
//					List<ActivityLotteryRecordBean> lotteryRecords = turnSerice
				}else{
					ClubBean club = clubService.selectClubById(paramMap.get("familyId"));
					if(null != club){
						conditions.add(new ConditionBean("club", club, ConditionBean.EQ));
						conditions.add(new ConditionBean("startUtc", activity.getStart_utc(), ConditionBean.GE));
						conditions.add(new ConditionBean("startUtc", activity.getEnd_utc(), ConditionBean.LT));
						List<ActivityLotteryRecordBean> lotteryRecords =  turnSerice.findLotteryRecord(conditions, null, false, "endUtc", null);
						List<ActivityWinningRecordBean> wrs = new ArrayList<ActivityWinningRecordBean>();
						if(null != lotteryRecords){
							for (ActivityLotteryRecordBean lr : lotteryRecords) {
								conditions.clear();
								conditions.add(new ConditionBean("lotteryId", lr.getId(), ConditionBean.EQ));
								PaginationBean page = turnSerice.findWinningRecord(conditions, null, false, "winningUtc", null);
								if(null != page && !page.getRecords().isEmpty()){
									wrs.addAll((List<ActivityWinningRecordBean>)page.getRecords());
								}
							}
							resultJson.put("result", "00000000");
							resultJson.put("winningRecords", wrs);
							resultJson.put("club", club);
						}else resultJson.put("result", "00000002");  //家庭未有中奖信息
						return Action.SUCCESS;	
					}else{
						resultJson.put("result", "00000004");
						resultJson.put("tip", "家庭不存在");
					}
				}
			}else resultJson.put("result", "00000007"); //参数有误
		}else resultJson.put("result", "00000008");   //未输入参数
		return Action.SUCCESS;
	}
	
	/**
	 * 8、获取中奖信息
	 * @return
	 */
	public String queryWinningRecord(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(!validateToken(paramMap.get("token"))){
			resultJson.put("result", "00000005");
			resultJson.put("tip", "token错误！");
			return Action.SUCCESS;
		}
		if(null != paramMap.get("prizeId")){
			ActivityWinningRecordBean wr = turnSerice.findWinningRecordById(paramMap.get("prizeId"));
			if(null != wr){
				resultJson.put("result", "00000000");
				resultJson.put("winningRecord", wr);
				return Action.SUCCESS;
			}else resultJson.put("result", "00000001");
		}else resultJson.put("result", "00000008");
		return Action.SUCCESS;
	}
	
	/**
	 * 9、获取用户地址列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryUserAddr(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(!validateToken(paramMap.get("token"))){
			resultJson.put("result", "00000005");
			resultJson.put("tip", "token错误！");
			return Action.SUCCESS;
		}
		if(null != paramMap.get("userId")){
			conditions.add(new ConditionBean("userId", paramMap.get("userId"), ConditionBean.EQ));
			PaginationBean page = userAddrService.pageList(conditions,null,false,null,null);
			if(null != page && null != page.getRecords()){
				List<UserAddrBean> userAddrs = (List<UserAddrBean>) page.getRecords();
				resultJson.put("result", "00000000");
				resultJson.put("userAddrs", userAddrs);
				return Action.SUCCESS;
			}else resultJson.put("result", "00000001");
		}else resultJson.put("result", "00000008");
		return Action.SUCCESS;
	}
	
	
	//=====================新增=====

	/**
	 * 10、查询奖品列表
	 * @return
	 */
	public String queryActivityGift(){
		response.setHeader("Access-Control-Allow-Origin","*");

		if(!validateToken(paramMap.get("token"))){
			resultJson.put("result", "00000005");
			resultJson.put("tip", "token错误！");
			return Action.SUCCESS;
		}
		List<ActivityGiftBean> gifts = turnSerice.findAllGift();
		if(null != gifts){
			resultJson.put("result", "00000000");
			resultJson.put("gifts", gifts);
		}else resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 11、修改领奖信息
	 * @return
	 */
	public String editWinningRecord(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(!validateToken(paramMap.get("token"))){
			resultJson.put("result", "00000005");
			resultJson.put("tip", "token错误！");
			return Action.SUCCESS;
		}
		if(null != paramMap.get("prizeId")){
			ActivityWinningRecordBean wr = turnSerice.findWinningRecordById(paramMap.get("prizeId"));
			if(null != wr){
				if(wr.getIsDeliver() == 1){
					if(null != paramMap.get("content")) wr.setContent(StringEscapeUtils.unescapeXml(paramMap.get("content")));
					if(null != paramMap.get("addrId")) wr.setAddrId(Integer.valueOf(paramMap.get("addrId")));
				
					if(null != paramMap.get("name")) wr.setReceiver(paramMap.get("name"));
					if(null != paramMap.get("phone")) wr.setReceiverPhone(paramMap.get("phone"));
					if(null != paramMap.get("addr")) wr.setReceiverAddr(paramMap.get("addr"));
					wr.setAcceptPrizeUtc(Tools.getNowUTC());
					if(null != turnSerice.updateWinningRecord(wr)){
						resultJson.put("result", "00000000");
						return Action.SUCCESS;
					}else resultJson.put("result", "00000001");
				}else if(wr.getIsDeliver() == 0){
					resultJson.put("result", "00000003");
				}else if(wr.getIsDeliver() == 2){
					resultJson.put("result", "00000004");
				}else resultJson.put("result", "00000006");
			}else resultJson.put("result", "00000007");	
		}else resultJson.put("result", "00000008");   //未输入参数
		
		return Action.SUCCESS;
	}
	
	/**
	 * 返回活动信息
	 * @return
	 */
	public String getActivityInfo(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(!validateToken(paramMap.get("token"))){
			resultJson.put("result", "00000005");
			resultJson.put("tip", "token错误！");
			return Action.SUCCESS;
		}
		
		if(paramMap.containsKey("activityId")){
			AdResourceBean activity = adService.selectResourceById(AdResourceBean.class, paramMap.get("activityId"));
			if(null != activity){
				resultJson.put("result", "00000000");
				resultJson.put("activity", activity);
			}else resultJson.put("result", "00000007");
		}else resultJson.put("result", "00000008");
		return Action.SUCCESS;
	}
	
	/**
	 * 产生token
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getToken(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(null != paramMap.get("account") && null != paramMap.get("password")){
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			conditions.add(new ConditionBean("account", paramMap.get("account"), ConditionBean.EQ));
			conditions.add(new ConditionBean("password", paramMap.get("password"), ConditionBean.EQ));
			List<UserBean> users = userService.selectByAccount(UserBean.class, conditions, null, false, null, null);
			if(null != users && !users.isEmpty() && 1 == users.size()){
				conditions.clear();
				conditions.add(new ConditionBean("user_id", users.get(0).getId(), ConditionBean.EQ));
				conditions.add(new 	ConditionBean("online", "1", ConditionBean.EQ));
				List<TerminalBean> terminals = terminalService.findDayOfData(conditions,null,false,"login_utc",null);
				if(null != terminals && !terminals.isEmpty()){
					TerminalBean terminal = terminals.get(0);
					String token = terminal.getUser_id().concat("Z"+Long.toString(terminal.getLogin_utc()));
					Base64 base = new Base64();
					resultJson.put("result", "00000000");
					resultJson.put("token", base.encode(token, "UTF-8"));
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 验证token的算法是否正确
	 * @param token
	 * @return
	 */
	private boolean validateToken(String token){
//		if(null != token){
//			Base64 base = new Base64();
//			String[] strs = base.decode(token, "UTF-8").split("Z");
//			if(strs.length == 2){
//				List<ConditionBean> clist = new ArrayList<ConditionBean>();
//				clist.add(new ConditionBean("user_id", strs[0], ConditionBean.EQ));
//				clist.add(new ConditionBean("login_utc", Long.valueOf(strs[1]), ConditionBean.EQ));
//				clist.add(new ConditionBean("online", "1", ConditionBean.EQ));
//				List<TerminalBean> terminals = terminalService.findDayOfData(clist, null, false, null, null);
//				if(null != terminals && !terminals.isEmpty()){
//					return true;
//				}
//			}
//		}
		return true;
	}
	
	//产生随机数，根据随机数范围得出中奖物品
	private ActivityGiftBean randomLottery(List<ActivityGiftBean> gifts) {
		if(null != gifts && !gifts.isEmpty()){
			Random random = new Random();
			int r = random.nextInt(lotteryBaseNum);
			int sum = 0;
			
			for(int i=0;i<gifts.size();i++){
				ActivityGiftBean gift = gifts.get(i);
				sum += gift.getProbability();
				if(r<sum)
					return gift;
			}
		}
		return null;
	}
	
	//验证家庭里是否有人抽奖
	private ActivityLotteryRecordBean findLottery(AdResourceBean activity,
			ClubBean club) {
		if(null != activity && null != club){
			conditions.clear();
			conditions.add(new ConditionBean("club", club, ConditionBean.EQ));
			conditions.add(new ConditionBean("activity", activity, ConditionBean.EQ));
			conditions.add(new ConditionBean("startUtc", activity.getStart_utc(), ConditionBean.GE));
			conditions.add(new ConditionBean("startUtc", activity.getEnd_utc(), ConditionBean.LT));
			conditions.add(new ConditionBean("isRotate", 1, ConditionBean.EQ));
			List<ActivityLotteryRecordBean> lotteryRecords = (List<ActivityLotteryRecordBean>) turnSerice.findLotteryRecord(conditions, null, false, "startUtc", null);
			if(null != lotteryRecords && !lotteryRecords.isEmpty())
			{
				return lotteryRecords.get(0);
			}
			
		}
		return null;
	}

//	public String test(){
//		response.setHeader("Access-Control-Allow-Origin","*");
//		conditions.add(new ConditionBean("giftNumber", 0, ConditionBean.GT));
//		List<ActivityGiftBean> gifts = turnSerice.findGiftByConditions(conditions,null,false,null,null);
//		ActivityGiftBean gift = randomLottery(gifts);
//		
//		
//		resultJson.put("gift", gift);
//		
//		return Action.SUCCESS;
//	}
}
