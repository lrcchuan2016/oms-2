package cn.broadin.oms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.OperateLogBean;
import cn.broadin.oms.service.ManagerLogService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

/**
 * 管理员操作日志业务处理实现类
 * 
 * @author xiejun
 */
@Service
public class ManagerLogServiceImpl implements ManagerLogService {
	
	@Resource
	private DAO dao;
	
	@Override
	public void add(String arg0) {
		this.dao.insert(new OperateLogBean(arg0));
	}
	
	@Override
	public void add(List<String> arg0) {
		if (null != arg0 && arg0.size() > 0) {
			List<OperateLogBean> logBeanList = new ArrayList<OperateLogBean>();
			for (String log : arg0) {
				logBeanList.add(new OperateLogBean(log));
			}
			this.dao.insert(logBeanList);
		}
	}
	
	@Override
	public PaginationBean findPage(List<ConditionBean> arg0, PaginationBean arg1) {
		return (PaginationBean) this.dao.select(OperateLogBean.class, arg0, null, false, "utc", arg1);
	}
	
}
