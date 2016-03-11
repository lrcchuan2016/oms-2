package cn.broadin.oms.service;

import cn.broadin.oms.model.SysNotifyBean;

public interface SysNotifyService {

	/**
	 * 添加系统消息
	 * @return
	 */
	public SysNotifyBean sysNotifyAdd(SysNotifyBean sysNotify);
	
}
