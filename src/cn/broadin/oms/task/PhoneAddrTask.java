package cn.broadin.oms.task;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.broadin.oms.model.UserBean;
import cn.broadin.oms.service.UserService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

/**
 * 手机归属地数据统计定时任务
 * @author hch
 *
 */
@Component("phoneAddrTask")
public class PhoneAddrTask {

	private final Logger log = Logger.getLogger(getClass());
	//获取手机号码归属地接口
	private final static String sUrl = "http://virtual.paipai.com/extinfo/GetMobileProductInfo?amount=10000&mobile=";
	@Resource
	private UserService userService;
	
	@SuppressWarnings("unchecked")
	public void execute(){
		log.info("手机号码归属地统计开始>>>");
		long startTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info("开始时间："+sdf.format(new Date(startTime)));
		List<UserBean> users = null;
		
		//查找有手机账号，并且没有统计过手机地址的用户
		List<ConditionBean> conditions = new ArrayList<ConditionBean>();
		conditions.add(new ConditionBean("account", "", ConditionBean.NOT_EQ));
		conditions.add(new ConditionBean("mb_attr", "", ConditionBean.EQ));
		PaginationBean pagination = userService.pageList(UserBean.class, conditions, null, false, null, null);
		if(null != pagination && null != pagination.getRecords() && !pagination.getRecords().isEmpty()){
			users =  (List<UserBean>) pagination.getRecords();
		}
		
		//查找全部用户
//		users = userService.selectAllUser(UserBean.class);
		//查找上周注册用户
//		users = this.getLastWeekdayUsers();
		if(null != users && !users.isEmpty()){
		int len = users.size();
			for(int i=0;i<len;i++){
				UserBean user = users.get(i);
				String addr = this.getPhoneAddr(user.getAccount());
				if(null != addr){
					user.setMb_attr(addr);
					if(null == userService.update(user)){
						log.warn("更新用户手机号码归属地发生错误");
						break;
					}
				}
			}
		}
		
	    long endTime = System.currentTimeMillis();
		log.info("统计结束!结束时间："+sdf.format(new Date(endTime))+"耗时："+(endTime-startTime)+"ms");
	  
	}

	/**
	 * 查询上周注册用户
	 * @return
	 */
//	@SuppressWarnings({ "unchecked" })
//	private List<UserBean> getLastWeekdayUsers() {
//		List<ConditionBean> clist = new ArrayList<ConditionBean>();
//		long lastWeekUtc = System.currentTimeMillis()-7*24*60*60*1000;
//		clist.add(new ConditionBean("register_utc", lastWeekUtc, ConditionBean.GE));
//		PaginationBean pagination = userService.pageList(UserBean.class, clist, null, false, null, null);
//		
//		if(null != pagination && null != pagination.getRecords() && !pagination.getRecords().isEmpty()){
//			return (List<UserBean>) pagination.getRecords();
//		}
//		
//		return null;
//	}

	/**
	 * 打开接口所对应的连接获取手机号码归属地信息
	 * @param phone
	 * @return
	 */
	private String getPhoneAddr(String phone) {
		String addr = null;
		try {
			URI url = new URI(sUrl+phone);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet get = new HttpGet(url);
			HttpResponse response = httpClient.execute(get);
			//response.setHeader("Content-Type","aplication/json;charset=UTF-8");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				JSONObject jo = null;
				if(null != entity){
 					String data = EntityUtils.toString(entity);
					data = data.substring(1,data.indexOf(")"));
					jo = JSONObject.fromObject(data);
				}
				String province = jo.getString("province");
				String cityname = jo.getString("cityname");
				if(province != cityname) addr = province+"-"+cityname;
				else addr = province;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return addr;
	}
	
	/**
	 * 获取各个省份信息，返回json数据
	 * @return
	 */
//	@SuppressWarnings("unused")
//	private JSONArray getPhoneAddrData() {
//		JSONArray ja = new JSONArray();
//		String hql = "select u.phone_addr,count(u.phone_addr) from UserBean u group by u.phone_addr";
//		List<Object> datas = dataStaService.statisticsPhoneAddr(hql);
//		if(null != datas && 0 != datas.size()){
//			for (Object o : datas) {
//				Object[] sData = (Object[]) o;
//				String area = String.valueOf(sData[0]);
//				int num = Integer.valueOf(String.valueOf(sData[1]));
//				JSONObject jo = new JSONObject();
//				if(!"".equals(area)) jo.element("area", area);
//				else jo.element("area", "未知");
//				jo.element("num", num);
//				ja.add(jo);
//				
//			}
//		}
//		return ja;
//	}
}
