package cn.broadin.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.PushDeviceBean;
import cn.broadin.oms.service.PushDeviceService;
import cn.broadin.oms.util.ConditionBean;

@Service
public class PushDeviceServiceImpl implements PushDeviceService{

	@Resource
	private DAO dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PushDeviceBean> findPushDevice(List<ConditionBean> clist) {
		
		return (List<PushDeviceBean>) dao.select(PushDeviceBean.class, clist, null, false, null, null);
	}

	
	
	
}
