package cn.broadin.oms.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.Command;
import cn.broadin.oms.model.SysNotifyBean;
import cn.broadin.oms.service.SysNotifyService;

/**
 * 系统消息业务类
 * @author huchanghuan
 *
 */
@Service
public class SysNotifyServiceImpl implements SysNotifyService {

	@Resource
	private DAO dao;
	
	@Override
	public SysNotifyBean sysNotifyAdd(SysNotifyBean sysNotify) {
		if(null != dao.insert(sysNotify)){
			Command command = new Command("sys_notify:add", "'"+sysNotify.getId()+"'", null, null);
			dao.insert(command);
			return sysNotify;
		}
		return null;
	}

}
