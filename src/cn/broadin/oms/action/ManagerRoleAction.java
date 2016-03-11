package cn.broadin.oms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.ManagerRoleBean;
import cn.broadin.oms.service.ManagerLogService;
import cn.broadin.oms.service.ManagerRoleService;
import cn.broadin.oms.service.ManagerService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 操作员角色请求处理类
 * 
 * @author xiejun
 */
@Controller("managerRoleAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ManagerRoleAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private List<ManagerRoleBean> roleList;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	@Resource
	private ManagerRoleService roleService;
	@Resource
	private ManagerLogService logService;
	@Resource
	private ManagerService managerService;
	
	
	public List<ManagerRoleBean> getRoleList() {
		return roleList;
	}
	
	public void setRoleList(List<ManagerRoleBean> roleList) {
		this.roleList = roleList;
	}
	
	public Map<String, Object> getResultJson() {
		return resultJson;
	}
	
	/**
	 * 获取操作员角色列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String findAll() throws Exception {
		this.resultJson.put("list", this.roleService.findAll());
		return Action.SUCCESS;
	}
	
	/**
	 * 新增操作员角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String add() throws Exception {
		if (null != this.roleList && null != this.roleList.get(0)) {
			ManagerRoleBean bean = this.roleList.get(0);
			if (this.roleService.add(bean)) {
				this.logService.add("Add operator role, result:true, role name:" + bean.getName());
				this.resultJson.put("result", "00000000");
				this.resultJson.put("tip", "添加管理组成功");
			} else {
				this.logService.add("Add operator role, result:false, role name:" + bean.getName());
				this.resultJson.put("result", "00000001");
				this.resultJson.put("tip", "添加管理组失败");
			}
		} else {
			this.resultJson.put("result", "00000001");
			this.resultJson.put("tip", "添加管理组失败，未能传入有效的管理组信息");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 修改操作员角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String edit() throws Exception {
		if (null != this.roleList && null != this.roleList.get(0)) {
			ManagerRoleBean bean = this.roleList.get(0);
			bean.setStatus(0);
			if (this.roleService.edit(bean)) {
				this.logService.add("Edit operator role, result:true, role name:" + bean.getName());
				this.resultJson.put("result", "00000000");
				this.resultJson.put("tip", "编辑成功");
			} else {
				this.logService.add("Edit operator role, result:false, role name:" + bean.getName());
				this.resultJson.put("result", "00000001");
				this.resultJson.put("tip", "编辑失败");
			}
		} else {
			this.resultJson.put("result", "00000001");
			this.resultJson.put("tip", "编辑失败，未能传入有效的编辑信息");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除操作员角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String del() throws Exception {
		if (null != this.roleList && null != this.roleList.get(0)) {
			List<Integer> idList = new ArrayList<Integer>();
			for (ManagerRoleBean bean : this.roleList) {
				idList.add(bean.getId());
			}
			boolean result = true;
			if (this.roleService.del(idList,1)) {
				this.resultJson.put("result", "00000000");
				this.resultJson.put("tip", "删除管理组成功");
			} else {
				result = false;
				this.resultJson.put("result", "00000001");
				this.resultJson.put("tip", "删除管理组失败");
			}
			List<String> logList = new ArrayList<String>();
			for (ManagerRoleBean bean : this.roleList) {
				logList.add("Delete operator role, result:" + result + ", role name:" + bean.getName());
			}
			this.logService.add(logList);
		} else {
			this.resultJson.put("result", "00000001");
			this.resultJson.put("tip", "删除管理组失败，未能传入有效的删除信息");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 获取权限列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String findPermissionList() throws Exception {
		this.resultJson.put("list", this.roleService.findPermissionList(this));
		return Action.SUCCESS;
	}
	
	/**
	 * 更新管理角色状态（管理组）
	 * @return
	 */
	public String updateStatus(){
		if(null != roleList){
			ManagerRoleBean mr = managerService.findRole(roleList.get(0).getId());
			if(0 == mr.getStatus()) mr.setStatus(1);
			else mr.setStatus(0);
			if(managerService.editRole(mr)){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "更改管理组状态成功");
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "更新管理组状态失败");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "缺少必要的参数");
		}
		return Action.SUCCESS;
	}
}
