package cn.broadin.oms.service.impl;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


import org.json.JSONObject;
import org.springframework.stereotype.Service;



import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.XingeApp;

/**
 * 消息推送处理类
 * @author huchanghuan
 *
 */
@Service
public class PushMsgServiceImpl {

	private XingeApp androidXg;
	private XingeApp iosXg;
	@Resource
	protected Map<String, String> xingeConfig;
	/**
	 * servlet启动加载
	 */
	@SuppressWarnings("unused")
	@PostConstruct
	private void init(){
		long androidAccessId = Long.valueOf(xingeConfig.get("androidAccessId"));
		String androidSecretKey = xingeConfig.get("androidSecretKey");
		long iosAccessId = Long.valueOf(xingeConfig.get("iosAccessId"));
		String iosSecretKey = xingeConfig.get("iosSecretKey");
		androidXg = new XingeApp(androidAccessId, androidSecretKey);
		iosXg = new XingeApp(iosAccessId, iosSecretKey);
		
	}
	
	/**
	 * 推送消息给单个设备（android）
	 * @param deviceToken
	 * @param message
	 * @param content 
	 * @param userId 
	 * @return
	 */
	public int pushAndroidSingleDevice(String deviceToken,Message message){
		
			JSONObject ret = androidXg.pushSingleDevice(deviceToken, message);
			return ret.getInt("ret_code");
		
	}
	
	/**
	 * 推送消息给所有设备（android）
	 * @param message
	 * @return
	 */
	public int pushAllAndroidDevice(Message message){
		JSONObject ret = androidXg.pushAllDevice(0, message);
		return ret.getInt("ret_code");
	}
	
	/**
	 * 推送消息给单个设备（IOS）
	 * @param deviceToken
	 * @param message
	 * @param content 
	 * @param userId 
	 * @return
	 */
	public int pushIosSingleDevice(String deviceToken,MessageIOS message){
		
			JSONObject ret = iosXg.pushSingleDevice(deviceToken, message, XingeApp.IOSENV_DEV);
			return ret.getInt("ret_code");
		
	}
	
	/**
	 * 推送消息给所有设备（IOS）
	 * @param message
	 * @return
	 */
	public int pushIosAllDevice(MessageIOS message){
		JSONObject ret = iosXg.pushAllDevice(0, message, XingeApp.IOSENV_DEV);
		return ret.getInt("ret_code");
	}
	
	
	
}
