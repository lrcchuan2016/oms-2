package cn.broadin.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.UserAddrBean;
import cn.broadin.oms.service.UserAddrService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

@Service
public class UserAddrServiceImpl implements UserAddrService {

	@Resource
	private DAO dao;

	@Override
	public PaginationBean pageList(List<ConditionBean> conditions, Integer i,
			boolean b, String column, PaginationBean page) {
		if(null != page){
			page = (PaginationBean) dao.select(UserAddrBean.class, conditions, i, b, column, page);
		}else{
			@SuppressWarnings("unchecked")
			List<UserAddrBean> userAddrs = (List<UserAddrBean>) dao.select(UserAddrBean.class, conditions, i, b, column, page);
			if(null != userAddrs){
				page = new PaginationBean(0, 1, userAddrs.size(), userAddrs);
			}
		}
		return page;
	}
}
