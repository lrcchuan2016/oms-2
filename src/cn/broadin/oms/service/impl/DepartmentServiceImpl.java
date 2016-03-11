package cn.broadin.oms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.DepartmentBean;
import cn.broadin.oms.service.DepartmentService;
import cn.broadin.oms.util.ConditionBean;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Resource
	private DAO dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentBean> findAll() {
		return (List<DepartmentBean>) dao.select(DepartmentBean.class);
	}

	@Override
	public boolean add(DepartmentBean arg0) {
		List<ConditionBean> conditions = new ArrayList<ConditionBean>();
		conditions.add(new ConditionBean("name", arg0.getName(), ConditionBean.EQ));
		if(dao.insert(conditions,arg0)!=null) return true;
		return false;
	}

	@Override
	public boolean edit(DepartmentBean arg0) {
		return null != this.dao.update(arg0);
	}

	@Override
	public boolean del(List<Integer> arg0) {
		return null != dao.delete(DepartmentBean.class, "id", arg0);
	}

}
