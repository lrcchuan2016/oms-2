package cn.broadin.oms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.ManagerBean;
import cn.broadin.oms.model.ManagerRoleBean;
import cn.broadin.oms.model.RecycleBean;
import cn.broadin.oms.service.RecycleService;
import cn.broadin.oms.util.ConditionBean;

@Service
public class RecycleServiceImpl implements RecycleService {
	@Resource
	private DAO dao;
	
	@Override
	public boolean add(RecycleBean arg0) {
		return null != dao.insert(arg0);
	}

	@Override
	public boolean recover(List<Integer> arg0,int arg1,int arg2) {
		if(null != dao.delete(RecycleBean.class, "id", arg0)){
			Map<String, Object> updateColumnMap = new HashMap<String, Object>();
			if(arg1==0){
				updateColumnMap.put("status", 2);
				return null != this.dao.update(ManagerBean.class, updateColumnMap, "id", arg2);
			}else{
				updateColumnMap.put("status", 1);
				return null != this.dao.update(ManagerRoleBean.class, updateColumnMap, "id", arg2);
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RecycleBean> findAll(List<ConditionBean> conditions) {
		if(conditions!=null){
			return (List<RecycleBean>) dao.select(RecycleBean.class,conditions, null, null, null, null);
		}else return (List<RecycleBean>) dao.select(RecycleBean.class);
	}

	public RecycleBean del(RecycleBean recycle) {
		//删除的实体为管理员
		List<Integer> list = new ArrayList<Integer>();
		list.add(recycle.getRefId());
		if(0 == recycle.getType()){
			if(null != dao.delete(recycle)){
				dao.delete(ManagerBean.class, "id", list);
				return recycle;
			} 
		}else{
		//删除的实体为管理角色组
			if(null != dao.delete(recycle)){
				dao.delete(ManagerRoleBean.class, "idx", list);
				return recycle;
			}
		}
		return null;
	}

}
