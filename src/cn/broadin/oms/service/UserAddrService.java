package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

public interface UserAddrService {

	/**
	 * 条件查找用户收货地址
	 * @param conditions
	 * @param object
	 * @param b
	 * @param object2
	 * @param object3
	 * @return
	 */
	public PaginationBean pageList(List<ConditionBean> conditions, Integer i,
			boolean b, String column, PaginationBean page);

}
