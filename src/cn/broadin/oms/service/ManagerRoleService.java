package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.ManagerRoleBean;
import cn.broadin.oms.util.JSTreeNodeBean;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 管理员权限业务处理接口
 * 
 * @author xiejun
 */

public interface ManagerRoleService {
	
	/**
	 * 新增操作员权限信息
	 * 
	 * @param arg0
	 *            操作员权限信息
	 * @return
	 */
	public boolean add(ManagerRoleBean arg0);
	
	/**
	 * 修改操作员权限信息
	 * 
	 * @param arg0
	 *            操作员权限信息
	 * @return
	 */
	public boolean edit(ManagerRoleBean arg0);
	
	/**
	 * 批量删除操作员权限信息
	 * 
	 * @param arg0
	 *            操作员权限ID集合
	 * @param arg1 
	 * 			      删除类型，0表示直接删除，1表示删除到回收站
	 * @return
	 */
	public boolean del(List<Integer> arg0,int arg1);
	
	/**
	 * 获取角色列表
	 * 
	 * @return
	 */
	public List<ManagerRoleBean> findAll();
	
	/**
	 * 获取权限列表
	 * 
	 * @param arg0
	 *            当前请求对象
	 * @return
	 */
	public List<JSTreeNodeBean> findPermissionList(ActionSupport arg0);
}
