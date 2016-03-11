package cn.broadin.oms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.ManagerBean;
import cn.broadin.oms.model.ManagerRoleBean;
import cn.broadin.oms.model.PermissionBean;
import cn.broadin.oms.model.RecycleBean;
import cn.broadin.oms.service.ManagerRoleService;
import cn.broadin.oms.service.RecycleService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.JSTreeNodeBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 操作员权限业务处理实现类
 * 
 * @author xiejun
 */
@Service
public class ManagerRoleServiceImpl implements ManagerRoleService {
	
	
	@Resource
	private DAO dao;
	
	@Override
	public boolean add(ManagerRoleBean arg0) {
		if (null != arg0) return null != this.dao.insert(arg0);
		return false;
	}
	
	@Override
	public boolean edit(ManagerRoleBean arg0) {
		if (null != arg0) {
			Map<String, String> updateColumnMap = new HashMap<String, String>();
			updateColumnMap.put("permission", arg0.getPermission());
			updateColumnMap.put("name", arg0.getName());
			return null != this.dao.update(ManagerRoleBean.class, updateColumnMap, "id", arg0.getId());
		}
		return false;
	}
	
	@Override
	public boolean del(List<Integer> arg0,int arg1) {
		if(arg1==0){
			if (null != arg0) return null != this.dao.delete(ManagerRoleBean.class, "id", arg0);
		}else{
			Map<String, Object> updateColumnMap = new HashMap<String, Object>();
			updateColumnMap.put("status","2");
			if(this.dao.update(ManagerRoleBean.class, updateColumnMap, "id", arg0.get(0))!=null){
				ManagerBean admin = (ManagerBean) ActionContext.getContext().getSession().get("admin");
				ManagerRoleBean roleBean = (ManagerRoleBean) dao.selectById(ManagerRoleBean.class, arg0.get(0));
				RecycleBean rb = new RecycleBean();
				rb.setRefId(arg0.get(0));
				rb.setDelUtc(new Date().getTime());
				rb.setManagerRole("");
				rb.setName(roleBean.getName());
				rb.setType(1);
				rb.setOperateName(admin.getName());
				
				//降低业务类之间的耦合
				RecycleService recycleService = new RecycleServiceImpl();
				recycleService.add(rb);
			}
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ManagerRoleBean> findAll() {
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		conditionList.add(new ConditionBean("permission", "*", ConditionBean.NOT_EQ)); // 去除超级管理员角色
		return (List<ManagerRoleBean>) this.dao.select(ManagerRoleBean.class, conditionList, null, null, null, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<JSTreeNodeBean> findPermissionList(ActionSupport arg0) {
		//降低业务类之间的耦合
		//CommonServiceImpl commonService = new CommonServiceImpl();
		List<PermissionBean> permissionList = (List<PermissionBean>) dao.select(PermissionBean.class);
		List<PermissionBean> childPermissionList = new ArrayList<PermissionBean>();
		List<JSTreeNodeBean> resultList = new ArrayList<JSTreeNodeBean>();
		for (PermissionBean bean : permissionList) {
			if (bean.getId() % 1000 == 0) { // 提取父栏目权限列表
				JSTreeNodeBean tempBean = new JSTreeNodeBean(String.valueOf(bean.getId()), "glyphicon glyphicon-book", arg0.getText(bean.getDescription()), "");
				resultList.add(tempBean);
			} else {
				childPermissionList.add(bean);
			}
		}
		for (JSTreeNodeBean parentBean : resultList) {
			List<JSTreeNodeBean> childrenList = new ArrayList<JSTreeNodeBean>();
			for (PermissionBean bean : childPermissionList) {
				if (bean.getId() % 10 == 0) { // 提取子栏目权限列表
					if (String.valueOf(bean.getId() / 1000 * 1000).equals(parentBean.getId())) {
						JSTreeNodeBean tempBean = new JSTreeNodeBean(String.valueOf(bean.getId()), "glyphicon glyphicon-leaf", arg0.getText(bean.getDescription()), "");
						childrenList.add(tempBean);
					}
				}
			}
			parentBean.setChildren(childrenList);
		}
		return resultList;
	}
	
}
