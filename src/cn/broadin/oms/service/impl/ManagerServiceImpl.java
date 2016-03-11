package cn.broadin.oms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.ImageBean;
import cn.broadin.oms.model.ManagerBean;
import cn.broadin.oms.model.ManagerRoleBean;
import cn.broadin.oms.model.PermissionBean;
import cn.broadin.oms.model.RecycleBean;
import cn.broadin.oms.service.ManagerService;
import cn.broadin.oms.service.RecycleService;
import cn.broadin.oms.util.ConditionBean;

@Service
public class ManagerServiceImpl implements ManagerService {
	@Resource
	private DAO dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public ManagerBean login(ManagerBean manager) {
		Map<String,String> map = new HashMap<String,String>();
		List<ManagerBean> results = null;
		if(manager.getRelateFlag()==1){
			map.put("account", manager.getAccount());
			map.put("email", manager.getAccount());
			map.put("password", manager.getPassword());
			results = (List<ManagerBean>) (dao.select(ManagerBean.class,map,true));
		}else{
			map.put("account", manager.getAccount());
			map.put("password", manager.getPassword());
			results = (List<ManagerBean>) (dao.select(ManagerBean.class,map,false));
		}
		if (results!=null&&results.size() > 0) {
			ManagerBean admin = results.get(0);
			String[] permissionIds = admin.getRolePermission().split("/");
			for (String id : permissionIds) {
				if (!id.equals("*") && Integer.parseInt(id) % 1000 != 0) {
					admin.getRolePermissionMap().put(String.valueOf((int) Math.floor(Integer.parseInt(id) / 1000) * 1000), id);
				}
				admin.getRolePermissionMap().put(id, id);
			}
			Map<String, Object> updateColumns = new HashMap<String, Object>();
			updateColumns.put("loginUtc", new Date().getTime());
			List<String> ids = new ArrayList<String>();
			ids.add(manager.getAccount());
			if (null != dao.update(ManagerBean.class, updateColumns, "account", ids)){
				return admin;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ManagerBean> findManagerList(List<ConditionBean> conditions) {
		List<ManagerBean> managerList = null;
		if(conditions==null){
			managerList = (List<ManagerBean>) dao.select(ManagerBean.class);
		}else{
			managerList = (List<ManagerBean>) dao.select(ManagerBean.class, conditions, null, true, null, null);
		}
		for (ManagerBean bean : managerList) {
			bean.setPassword(""); // 不返回用户密码
			bean.setRolePermission(""); // 不返回用户权限详细信息
		}
		return managerList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ManagerRoleBean> findRoleList(List<ConditionBean> conditions) {
		if(conditions==null){
			return (List<ManagerRoleBean>) dao.select(ManagerRoleBean.class);
		}else{
			return (List<ManagerRoleBean>) dao.select(ManagerRoleBean.class,conditions, null, true, null, null);
		}
	}
	
	@Override
	public boolean addManager(ManagerBean manager) {
		List<ConditionBean> conditions = new ArrayList<ConditionBean>();
		conditions.add(new ConditionBean("account", manager.getAccount(), ConditionBean.EQ));
		return null != dao.insert(conditions, manager);
	}
	
	@Override
	public boolean editManager(Map<String, Object> map) {
		if(null != this.dao.update(ManagerBean.class, map, "id", map.get("id"))){
			if(map.get("status")!=null){
				int status = (Integer) map.get("status");
				if(status == 3){
					ManagerBean admin = (ManagerBean) ActionContext.getContext().getSession().get("admin");
					ManagerBean current = (ManagerBean) dao.selectById(ManagerBean.class, Integer.parseInt(map.get("id").toString()));
					RecycleBean rb = new RecycleBean();
					rb.setRefId(Integer.parseInt(map.get("id").toString()));
					rb.setDelUtc(new Date().getTime());
					rb.setManagerRole(current.getRoleName());
					rb.setName(current.getName());
					rb.setType(0);
					rb.setOperateName(admin.getName());
					//降低业务类之间的耦合
					RecycleService recycleService = new RecycleServiceImpl();
					recycleService.add(rb);
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean delManager(List<Integer> ids) {
		return null != dao.delete(ManagerBean.class, "id", ids);
	}
	
	@Override
	public boolean addRole(ManagerRoleBean role) {
		List<ConditionBean> conditions = new ArrayList<ConditionBean>();
		conditions.add(new ConditionBean("name", role.getName(), ConditionBean.EQ));
		return dao.insert(conditions, role) != null;
	}
	
	@Override
	public boolean editRole(ManagerRoleBean role) {
		List<ConditionBean> conditions = new ArrayList<ConditionBean>();
		conditions.add(new ConditionBean("id", role.getId(), ConditionBean.NOT_EQ));
		conditions.add(new ConditionBean("name", role.getName(), ConditionBean.EQ));
		return dao.update(conditions, role) != null;
	}
	
	@Override
	public boolean delRole(List<Integer> ids) {
		return null != dao.delete(ManagerRoleBean.class, "id", ids);
	}

	@Override
	public boolean uploadHeadSet(ManagerBean manager,ImageBean bean) {
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		Map<String, Object> updateColumns = new HashMap<String, Object>();
		List<Integer> ids = new ArrayList<Integer>();
		updateColumns.put("headsetUrl", manager.getHeadsetUrl());
		ids.add(manager.getId());
		if(null != dao.update(ManagerBean.class, updateColumns, "account", ids)){
			conditionList.add(new ConditionBean("id", bean.getId(), ConditionBean.EQ));
			this.dao.insert(conditionList, bean);
			return true;
		}
		return false;
	}

	@Override
	public ManagerBean findById(Integer id) {
		return (ManagerBean) this.dao.selectById(ManagerBean.class, id);
	}

	@Override
	public PermissionBean findPermissionById(int id) {
		
		return (PermissionBean) dao.selectById(PermissionBean.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PermissionBean> findPermissions() {
		
		return (List<PermissionBean>) dao.select(PermissionBean.class);
	}

	@Override
	public ManagerBean updateManager(ManagerBean admin) {
		if(null != dao.update(admin)) return admin;
		return null;
	}

	@Override
	public ManagerRoleBean findRole(int id) {
		
		return (ManagerRoleBean) dao.selectById(ManagerRoleBean.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManagerBean> findByUserName(List<ConditionBean> conditions) {
		
		return (List<ManagerBean>) dao.select(ManagerBean.class, conditions, null, false, "id", null);
	}

	
}
