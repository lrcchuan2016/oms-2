package cn.broadin.oms.action;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.ManagerBean;
import cn.broadin.oms.model.PushDeviceBean;
//import cn.broadin.oms.model.SysNotifyBean;
import cn.broadin.oms.model.UserBean;
import cn.broadin.oms.service.PushDeviceService;
//import cn.broadin.oms.service.SysNotifyService;
import cn.broadin.oms.service.UserService;
import cn.broadin.oms.service.impl.PushMsgServiceImpl;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
//import cn.broadin.oms.util.Tools;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;

/**
 * 推送服务消息
 * @author huchanghuan
 *
 */
@Controller("pushDeviceAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PushDeviceAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accessId;
	private String accessKey;
	private String secretKey;
	private String MtaAppKey;
	private String type;
	private Map<String, String> paramMap = new HashMap<String, String>();
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private List<ConditionBean> clist = new ArrayList<ConditionBean>();
	private PaginationBean page;
	@Resource
	private Map<String, String> xingeConfig;
	@Resource
	private PushDeviceService pushDeviceService;
	@Resource
	private PushMsgServiceImpl pushMsgService;
	@Resource
	private UserService userService;
//	@Resource
//	private SysNotifyService sysNotifyService;
	
	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getMtaAppKey() {
		return MtaAppKey;
	}

	public void setMtaAppKey(String mtaAppKey) {
		MtaAppKey = mtaAppKey;
	}
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public List<ConditionBean> getClist() {
		return clist;
	}

	public void setClist(List<ConditionBean> clist) {
		this.clist = clist;
	}

	public PaginationBean getPage() {
		return page;
	}

	public void setPage(PaginationBean page) {
		this.page = page;
	}

	/**
	 * 保存信鸽配置信息
	 * @return
	 */
	public String saveXingeConfig(){
		if(null != accessId && null != accessKey && null != secretKey && null != MtaAppKey && null != type){
			InputStream is = null;
			try {
				is = this.getClass().getResourceAsStream("/config/xinge.properties");
				Properties p = new Properties();
				p.load(is);
				//设置key-value，并保存
				if(type.equals("0")){
					p.setProperty("iosAccessId", accessId);
					p.setProperty("iosAccessKey", accessKey);
					p.setProperty("iosSecretKey", secretKey);
					p.setProperty("iosMtaAppKey", MtaAppKey);
					xingeConfig.put("iosAccessId", accessId);
					xingeConfig.put("iosAccessKey", accessKey);
					xingeConfig.put("iosSecretKey", secretKey);
					xingeConfig.put("iosMtaAppKey", MtaAppKey);
				}else{
					p.setProperty("androidAccessId", accessId);
					p.setProperty("androidAccessKey", accessKey);
					p.setProperty("androidSecretKey", secretKey);
					p.setProperty("androidMtaAppKey", MtaAppKey);
					xingeConfig.put("androidAccessId", accessId);
					xingeConfig.put("androidAccessKey", accessKey);
					xingeConfig.put("androidSecretKey", secretKey);
					xingeConfig.put("androidMtaAppKey", MtaAppKey);
				}
				p.store(new FileOutputStream(new File(this.getClass().getResource("/config/xinge.properties").toURI())), null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}finally{
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			resultJson.put("result", "00000000");
			return Action.SUCCESS;
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	
	/**
	 * 发送给所有设备
	 * @return
	 */
	public String pushAllDevice(){
		if(paramMap.containsKey("psw") && paramMap.containsKey("title")
				&& paramMap.containsKey("content") && paramMap.containsKey("sendTime")){
			String psw = paramMap.get("psw");
			String title = paramMap.get("title");
			String content = paramMap.get("content");
			String sendTime = StringUtils.replace(paramMap.get("sendTime"), "T", " ").concat(":00");
			String password = ((ManagerBean)(ActionContext.getContext().getSession().get("admin"))).getPassword();
			if(psw.equals(password)){
					Message message = new Message();
					message.setTitle(title);
					message.setContent(content);
					message.setSendTime(sendTime);
					message.setType(Message.TYPE_NOTIFICATION);
					//IOS
					MessageIOS messageIOS = new MessageIOS();
					messageIOS.setAlert(content);
					messageIOS.setSendTime(sendTime);
					messageIOS.setBadge(1);
					messageIOS.setSound("beep.wav");
					if(0 == pushMsgService.pushAllAndroidDevice(message)
						&& 0 == pushMsgService.pushIosAllDevice(messageIOS)){
						resultJson.put("result", "00000000");
					}else resultJson.put("result", "00000002");
			}else resultJson.put("result", "00000001");
		}else resultJson.put("result", "00000002");
		return Action.SUCCESS;
	}
	
	/**
	 * 分页查找所有用户
	 * @return
	 */
	public String findUserPage(){
		if(null != paramMap){
			String type = paramMap.get("searchType");
			String keyWords = paramMap.get("keyWords");
			if(null != type && !type.isEmpty()){
				if("2".equals(type)){
					List<String> sType = new ArrayList<String>();
					sType.add("0");
					sType.add("1");
					clist.add(new ConditionBean("status", sType, ConditionBean.IN));
				}else clist.add(new ConditionBean("status", type, ConditionBean.EQ));
			}
			if(null != keyWords && !keyWords.isEmpty()){
				Criterion[] cris = {Restrictions.like("account", "%"+keyWords+"%"),
						Restrictions.like("nickname", "%"+keyWords+"%")};
				clist.add(new ConditionBean(null, cris, ConditionBean.OR));
			}
			page = userService.pageList(UserBean.class, clist, null, false, null, page);
			resultJson.put("list", page);
		}else resultJson.put("list", userService.pageList(UserBean.class, null, null, false, null, page));
		return Action.SUCCESS;
	}
	
	/**
	 * 推送消息给单个设备
	 * @return
	 */
	public String pushSingleDevice(){
		if(null != paramMap){
			String psw = paramMap.get("psw");
			String password = ((ManagerBean)(ActionContext.getContext().getSession().get("admin"))).getPassword();
			if(psw.equals(password)){
				String userIds = paramMap.get("userIds");
				String title = paramMap.get("title");
				String content = paramMap.get("content");
				String sendTime = StringUtils.replace(paramMap.get("sendTime"), "T", " ").concat(":00");
				if(null != userIds && !userIds.isEmpty() && null != title && !title.isEmpty() && null != content && !content.isEmpty()){
					String[] us = userIds.split(",");
					List<String> list = new ArrayList<String>();
					for (String uid : us) { list.add(uid);}
					clist.add(new ConditionBean("userId", list, ConditionBean.IN));
					List<PushDeviceBean> pushDevices = pushDeviceService.findPushDevice(clist);
					if(null != pushDevices && !pushDevices.isEmpty()){
						int resultCode = 1;
						for (PushDeviceBean pushDeviceBean : pushDevices) {
							//device_type为5是IOS,3是android
							//if(addSysNotify(pushDeviceBean.getUserId(), content)){
								if(3 == pushDeviceBean.getDeviceType()){
									Message message = new Message();
									message.setTitle(title);
									message.setSendTime(sendTime);    //发送的时间
									message.setContent(content);
									message.setType(Message.TYPE_NOTIFICATION);
									resultCode = pushMsgService.pushAndroidSingleDevice(pushDeviceBean.getId(), message);
								}else if(5 == pushDeviceBean.getDeviceType()){
									MessageIOS message = new MessageIOS();
									message.setAlert(content);
									message.setSendTime(sendTime);   
									message.setBadge(1);
									message.setSound("beep.wav");
									resultCode = pushMsgService.pushIosSingleDevice(pushDeviceBean.getId(), message);
								}
								if(resultCode != 0) break;
							//}
						}
						if(0 == resultCode) resultJson.put("result", "00000000");
						else resultJson.put("result", "00000001");	
					}	
				}else{
						resultJson.put("result", "00000001");
						resultJson.put("tip", "input param is null");
				}
			}else resultJson.put("result", "00000002");
		}else resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 添加记录到系统消息表、系统消息记录表
	 * @return
	 */
//	private boolean addSysNotify(String userId,String content){
//		SysNotifyBean sysNotify = new SysNotifyBean(Tools.getMD5AndUUID(32).toLowerCase(), 0, content, "10000000000000000000000000000000", userId, "8ac60f64d1cd7b320983f692d0e76041", 0, Tools.getNowUTC(), 0);
//		if(null != sysNotifyService.sysNotifyAdd(sysNotify)){
//			return true;
//		}
//		return false;
//	}
}
