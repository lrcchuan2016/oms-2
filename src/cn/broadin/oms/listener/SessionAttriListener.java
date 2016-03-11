package cn.broadin.oms.listener;


import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.log4j.Logger;

import cn.broadin.oms.model.ManagerBean;

/**
 * 管理员会话登录登出监听
 * @author huchanghuan
 *
 */
@WebListener
public class SessionAttriListener implements HttpSessionAttributeListener{

	private static Logger log = Logger.getLogger(SessionAttriListener.class);
	
	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		
		if(se.getName().equals("admin")){
			ManagerBean manager =(ManagerBean) se.getValue();
			if(null != manager){
				log.info("####登录成功#####管理员："+manager.getName()+" 账号："+manager.getAccount()+" 角色："+manager.getRoleName());
			}
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		if(se.getName().equals("admin")){
			ManagerBean manager = (ManagerBean) se.getValue();
			if(null != manager){
				log.info("####退出登录####管理员："+manager.getName()+" 账号："+manager.getAccount()+" 角色："+manager.getRoleName());
			}
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		
	}

}
