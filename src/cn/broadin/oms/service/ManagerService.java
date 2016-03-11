package cn.broadin.oms.service;

import java.util.List;
import java.util.Map;

import cn.broadin.oms.model.ImageBean;
import cn.broadin.oms.model.ManagerBean;
import cn.broadin.oms.model.ManagerRoleBean;
import cn.broadin.oms.model.PermissionBean;
import cn.broadin.oms.util.ConditionBean;

/**
 * 管理员操作接口类
 * @author XieJun
 *
 */
public interface ManagerService {
	
	/**
	 * 管理员登录接口
	 * 
	 * @param manager
	 * @return
	 */
	public ManagerBean login(ManagerBean manager);
	
	/**
	 * 获取所有管理员信息
	 * 
	 * @return
	 */
	public List<ManagerBean> findManagerList(List<ConditionBean> conditions);
	
	/**
	 * 通过ID查找管理员
	 * @param id
	 * @return
	 */
	public ManagerBean findById(Integer id);
	
	/**
	 * 获取所有角色信息
	 * 
	 * @return
	 */
	public List<ManagerRoleBean> findRoleList(List<ConditionBean> conditions);
	
	/**
	 * 添加管理员
	 * 
	 * @param manager
	 * @return
	 */
	public boolean addManager(ManagerBean manager);
	
	/**
	 * 编辑管理员信息
	 * 
	 * @param map
	 * @return
	 */
	public boolean editManager(Map<String, Object> map);
	
	/**
	 * 删除管理员
	 * 
	 * @param ids
	 * @return
	 */
	public boolean delManager(List<Integer> ids);
	
	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 */
	public boolean addRole(ManagerRoleBean role);
	
	/**
	 * 编辑角色
	 * 
	 * @param role
	 * @return
	 */
	public boolean editRole(ManagerRoleBean role);
	
	/**
	 * 删除角色
	 * 
	 * @param ids
	 * @return
	 */
	public boolean delRole(List<Integer> ids);
	
	/**
	 * 上传头像
	 * @param image
	 * @param manager
	 * @return
	 */
	public boolean uploadHeadSet(ManagerBean manager,ImageBean image);

	/**
	 * 通过ID找到permission实体
	 * @param idx
	 * @return
	 */
	public PermissionBean findPermissionById(int idx);

	/**
	 * 查找所有permission实体
	 * @return
	 */
	public List<PermissionBean> findPermissions();

	/**
	 * 更新managerBean实体
	 * @param admin
	 * @return
	 */
	public ManagerBean updateManager(ManagerBean admin);
	
	/**
	 * 通过ID查找角色（管理组）
	 * @param id
	 * @return
	 */
	public ManagerRoleBean findRole(int id);

	/**
	 * 条件查找管理员
	 * @param conditions
	 * @return
	 */
	public List<ManagerBean> findByUserName(List<ConditionBean> conditions);
		

}
