package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.PushDeviceBean;
import cn.broadin.oms.util.ConditionBean;

public interface PushDeviceService {

	
	/**
	 * 条件查找
	 * @param clist
	 * @return
	 */
	public List<PushDeviceBean> findPushDevice(List<ConditionBean> clist);

	
}
