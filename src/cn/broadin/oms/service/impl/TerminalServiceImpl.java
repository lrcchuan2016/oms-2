package cn.broadin.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.TerminalBean;
import cn.broadin.oms.model.TerminalTypeBean;
import cn.broadin.oms.service.TerminalService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

@Service
public class TerminalServiceImpl implements TerminalService {
	@Resource
	private DAO dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TerminalTypeBean> findTerminalTypes() {
		return (List<TerminalTypeBean>) dao.select(TerminalTypeBean.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TerminalBean> findDayOfData(List<ConditionBean> clist,Integer i,boolean b,String str,PaginationBean page) {
		
		return (List<TerminalBean>) dao.select(TerminalBean.class, clist, i, b, str, page);
	}

	@Override
	public TerminalTypeBean findTerminalById(int id) {
		
		return (TerminalTypeBean) dao.selectById(TerminalTypeBean.class, id);
	}

}
