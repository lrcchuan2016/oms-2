package cn.broadin.oms.action;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;

import cn.broadin.oms.model.TerminalBean;
import cn.broadin.oms.model.TerminalTypeBean;
import cn.broadin.oms.service.ClubService;
import cn.broadin.oms.service.DataStatisticService;
import cn.broadin.oms.service.TerminalService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.DataStatisticsUtil;
import cn.broadin.oms.util.Tools;


/**
 * 数据统计
 * @author hch
 *
 */
@Controller("dataStatisticsAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DataStatisticsAction {
	
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private Map<String, String> paramMap = new HashMap<String, String>();
	private List<ConditionBean> clist = new ArrayList<ConditionBean>();
	@Resource
	private DataStatisticService dataStaService;
	@Resource
	private TerminalService terminalService;
	@Resource
	private ClubService clubService;
	
	public Map<String, Object> getResultJson() {
		return resultJson;
	}

	public void setResultJson(Map<String, Object> resultJson) {
		this.resultJson = resultJson;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 用户分布统计(省份)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String userDistributionSta(){
		String sql = "select u.province,count(*) from (select mb_attr,substr(mb_attr,1,locate('-',mb_attr)-1) province from user) u group by province";
		List<Object[]> usersData = (List<Object[]>) dataStaService.findUserDistribution(sql);
		if(null != usersData && 0 != usersData.size()){
			resultJson.put("result", "00000000");
			resultJson.put("data", usersData);
			return Action.SUCCESS;
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 查看地区下的用户城市分布s
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String userCityDistribution(){
		if(null != paramMap){
			String province = paramMap.get("province");
			String sql = null;
			if(null != province && !province.isEmpty() && !"未知".equals(province))
			sql = "select u.city,count(*) from (select mb_attr,substr(mb_attr,locate('-',mb_attr)+1) city from user where mb_attr like '"+province+"%') u group by city";
			else sql = "select u.city,count(*) from (select mb_attr,substr(mb_attr,locate('-',mb_attr)+1) city from user where mb_attr='' or mb_attr like '"+province+"%') u group by city";
			List<Object[]> usersData = (List<Object[]>) dataStaService.findUserDistribution(sql);
			
			if(null != usersData && 0 != usersData.size()){
				resultJson.put("result", "00000000");
				resultJson.put("data", usersData);
				return Action.SUCCESS;
			}
			
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 终端用户登录（每天）显示
	 * @return
	 */
	public String TerminalLoginShow(){
		List<TerminalTypeBean> terminalTypes = terminalService.findTerminalTypes();
		if(null != terminalTypes && 0 != terminalTypes.size()){
			int len = terminalTypes.size();
			JSONArray ja = new JSONArray();
			long startDate = 0;
			long endDate = 0;
			if(null == paramMap || null == paramMap.get("date")){
				Calendar cal = Calendar.getInstance();
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),0,0,0);
				startDate = cal.getTimeInMillis();
			}else{
				String dateParam = paramMap.get("date");
				startDate = Tools.string2utc(dateParam, 8, "yyyy-MM-dd");
			}
			endDate = startDate+24*60*60*1000;
			clist.add(new ConditionBean("login_utc", startDate, ConditionBean.GE));
			clist.add(new ConditionBean("login_utc", endDate, ConditionBean.LT));
			for(int i=0;i<len;i++){
				int type = terminalTypes.get(i).getId();
				clist.add(new ConditionBean("type", type, ConditionBean.EQ));
				List<TerminalBean> terminals = terminalService.findDayOfData(clist,null,false,null,null);
				clist.remove(2);
				int[] nums = new int[24];
				if(null != terminals && 0 != terminals.size()){
					int size = terminals.size();
					for(int j=0;j<size;j++){
						long loginUtc = terminals.get(j).getLogin_utc();
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(loginUtc);
						int hour = calendar.get(Calendar.HOUR_OF_DAY);
						nums[hour] += 1;
					}
				}
				JSONObject jo = new JSONObject();
				jo.element("name", terminalTypes.get(i).getDescription());
				jo.element("data", nums);
				ja.add(jo);
			}
			resultJson.put("result", "00000000");
			resultJson.put("data", ja);
		}else resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 条件查找终端登录状况（时间）统计
	 * @return
	 */
	public String QueryTeminalLogin(){
		List<TerminalTypeBean> terminalTypes = terminalService.findTerminalTypes();
		if(null != terminalTypes && 0 != terminalTypes.size()){
			int len = terminalTypes.size();
			JSONArray ja = new JSONArray();
			if(null != paramMap && null != paramMap.get("date")){
				String sDate = paramMap.get("date");
				String[] date = sDate.split("-");
				int length = date.length;

				if(length>0 && length<4){

					
					long[] l = DataStatisticsUtil.QueryStartAndEndUtc(sDate); 
					
					clist.add(new ConditionBean("login_utc", l[0], ConditionBean.GE));
					clist.add(new ConditionBean("login_utc", l[1], ConditionBean.LT));
					for(int i=0;i<len;i++){
						int type = terminalTypes.get(i).getId();
						clist.add(new ConditionBean("type", type, ConditionBean.EQ));
						List<TerminalBean> terminals = terminalService.findDayOfData(clist,null,false,null,null);
						clist.remove(2);   //移除上次的type条件
						if(null != terminals){
							JSONArray jsonArr = new JSONArray();
							jsonArr.add(terminalTypes.get(i).getDescription());
							jsonArr.add(terminals.size());
							ja.add(jsonArr);
						}
					}
					resultJson.put("result", "00000000");
					resultJson.put("data", ja);
					return Action.SUCCESS;
				}
			}	
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 查找各个家庭成员数量所占
	 * @return
	 */
	public String QueryClubMemebrNums(){
		
		String sql = "select uc.c,count(*) from (select count(*) c from user_club group by club_id) uc group by uc.c";
		List<Object[]> objs = dataStaService.executeSql(sql);
		if(null != objs){
			resultJson.put("result", "00000000");
			resultJson.put("data", objs);
		}else resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 查询家庭成员归属地
	 * @return
	 */
	public String QueryClubMemberVest(){
		if(paramMap.containsKey("clubId")){
			String clubId = paramMap.get("clubId");
			if(null != clubService.selectClubById(clubId)){
				String sql = "select c.mb_attr,count(*) from (select u.mb_attr from user_club uc,user u where uc.club_id = '"+clubId+"' and uc.user_id = u.id) c group by c.mb_attr";
				List<Object[]> objs = dataStaService.executeSql(sql);
				if(null != objs){
					resultJson.put("result", "00000000");
					resultJson.put("data", objs);
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
}
