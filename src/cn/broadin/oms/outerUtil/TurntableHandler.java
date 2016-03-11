package cn.broadin.oms.outerUtil;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;

import cn.broadin.oms.util.Tools;


/**
 * 转盘处理
 * @author huchanghuan
 *
 */
@Component
public class TurntableHandler {

	private static Map<String, String> tokenMap = new HashMap<String, String>(); 
	/**
	 * 算出剩余抽奖次数
	 * @param obj
	 * @param num
	 * @return
	 */
//	public int queryTime(Object obj,int num) {
//		int time = num;
//		if(obj != null){
//			time = num - (Integer) obj;
//		}
//		return time;
//	}

	/**
	 * 算超时
	 * @param startUtc
	 * @param endUtc
	 * @param sec
	 * @return
	 */
	public boolean validateSec(long startUtc, long sec) {
		boolean flag = false;
		long endUtc = Tools.getNowUTC();
		if(endUtc-startUtc>=sec)
			flag = true;
		
		return flag;
	}

	public void getToken() {
	
		Map<String, Object> session = ActionContext.getContext().getSession();
		String token = Tools.getMD5AndUUID(32);
		tokenMap.put(token, token);
		System.out.println(token);
		session.put("token", token);
		
		
	}

	public boolean validateToken(String string) {
		System.out.println(string);
		if(null != tokenMap.get(string)){
			return true;
		}
		return false;
	}
	
	
	
	

	
}
