package cn.broadin.oms.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.ImageBean;
import cn.broadin.oms.model.ManagerBean;
import cn.broadin.oms.model.ManagerRoleBean;
import cn.broadin.oms.model.PermissionBean;
import cn.broadin.oms.service.ManagerService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.Tools;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller("managerAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ManagerAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private ManagerBean managerBean;
	private List<ManagerBean> managerList;
	private List<ManagerRoleBean> managerRoleList;
	private Map<String,String> paramMap;
	private String tipMsg;
	private String input;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	@Resource
	private ManagerService managerService;
	
	public String getTipMsg() {
		return tipMsg;
	}
	
	public Map<String, Object> getResultJson() {
		return resultJson;
	}

	public String getInput() {
		return input;
	}
	
	public ManagerBean getManagerBean() {
		return managerBean;
	}

	public void setManagerBean(ManagerBean managerBean) {
		this.managerBean = managerBean;
	}

	public List<ManagerBean> getManagerList() {
		return managerList;
	}

	public void setManagerList(List<ManagerBean> managerList) {
		this.managerList = managerList;
	}

	public List<ManagerRoleBean> getManagerRoleList() {
		return managerRoleList;
	}

	public void setManagerRoleList(List<ManagerRoleBean> managerRoleList) {
		this.managerRoleList = managerRoleList;
	}
	
	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 操作员登录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String login(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		//判断是否已经登录
		if(null == session.get("admin")){
			if (null != managerList&&managerList.size()!=0) {
				ManagerBean bean = managerList.get(0);
				ManagerBean admin = managerService.login(bean);
				if (null != admin) { // 登录成功进入主页面
					if(admin.getRoleStatus() == 0){
						if(admin.getStatus()==0){
							//判断密码是否失效
							long t1 = admin.getPasswordLimitUtc()+admin.getPasswordCreateUtc();
							long t2 = Tools.getNowUTC();
							if((t2-t1)<=0 || admin.getPasswordLimitUtc()==0 || admin.getPasswordLimitUtc() ==-1){
								session.clear();
								session.put("admin", admin);
								session.put("pMap", this.initPermission(admin.getRolePermission()));
								tipMsg = "登陆成功";
								return "main";
							}else{
								admin.setStatus(2);
								managerService.updateManager(admin);
								tipMsg = "帐号密码已失效，无法登录！";
							}
						}else if(admin.getStatus()==1){
							tipMsg = "帐户已被冻结，无法登陆.";
						}else tipMsg = "帐户已不可用，无法登陆.";
					}else tipMsg = "管理组不可用.无法登录";
				}else tipMsg = "登陆失败,用户名或者密码错误";
			}else tipMsg = "登陆失败";
			return Action.LOGIN; // 返回登录界面
		}
		return "main";
	}
	

	/**
	 * 上传头像
	 * @return
	 * @throws Exception
	 */
	public String uploadHeadset() throws Exception {
		ManagerBean admin = (ManagerBean) ActionContext.getContext().getSession().get("admin");
		if(admin!=null){
			if(paramMap!=null){
				byte[] data = Tools.decode(this.paramMap.get("imageData"));
				ImageBean image = new ImageBean(data);
				admin.setHeadsetUrl("oms://"+image.getId()+".png");
				if(managerService.uploadHeadSet(admin, image)){
					resultJson.put("result", "00000000");
					resultJson.put("tip", "上传头像成功");
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "上传头像失败");
				}
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "上传头像失败");
			}
		}
		return Action.SUCCESS;
	}
	
	public String initEditPage() throws Exception{
		int managerId = Integer.parseInt(this.paramMap.get("id"));
		if(this.paramMap.get("tipMsg")!=null) this.tipMsg = this.paramMap.get("tipMsg");
		this.managerBean = managerService.findById(managerId);
		String type = this.paramMap.get("type");
		if(type.equals("0")) this.input = "pages/manager/edit.jsp";
		else this.input = "pages/index/edit.jsp";
		return Action.INPUT;
	}
	
	/**
	 * 管理员退出
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String logout() throws Exception {
		
		ActionContext.getContext().getSession().clear();
		
		return Action.LOGIN;
	}
	
	/**
	 * 获取所有未删除管理员数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String findAllManager() throws Exception {
		List<ConditionBean> conditions = new ArrayList<ConditionBean>();
		conditions.add(new ConditionBean("status",3,ConditionBean.NOT_EQ));
		resultJson.put("list", managerService.findManagerList(conditions));
		return Action.SUCCESS;
	}
	
	/**
	 * 获取所有权限数据
	 * 需过滤已删除的角色
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String findAllRole() throws Exception {
		List<ConditionBean> conditions = new ArrayList<ConditionBean>();
		conditions.add(new ConditionBean("status",2,ConditionBean.NOT_EQ));
		List<ManagerRoleBean> beans = managerService.findRoleList(conditions);
		List<ManagerRoleBean> newBeans = new ArrayList<ManagerRoleBean>();
		for(ManagerRoleBean bean : beans){
			ManagerRoleBean newBean = bean;
			List<ManagerBean> list = new ArrayList<ManagerBean>();
			if(bean.getManagerList()!=null && bean.getManagerList().size()!=0){
				for(ManagerBean mb : bean.getManagerList()){
					if(mb.getStatus()!=3) list.add(mb);
				}
			}
			newBean.setManagerList(list);
			newBeans.add(newBean);
		}
		resultJson.put("list", newBeans);
		return Action.SUCCESS;
	}
	
	/**
	 * 添加操作员信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addManager() throws Exception {
		if(managerList!=null&&managerList.size()!=0){
			ManagerBean mb = managerList.get(0);
			mb.setPasswordCreateUtc(new Date().getTime());
			if(mb.getEmail()!="") mb.setRelateFlag(1);
			if(managerRoleList!=null&&managerRoleList.size()!=0){
				ManagerRoleBean mrb = managerRoleList.get(0);
				int roleId = mrb.getId();
				List<ManagerRoleBean> mrbs = managerService.findRoleList(null);
				for(ManagerRoleBean bean : mrbs){
					if(bean.getId()==roleId) mb.setRole(bean);
				}
				if(managerService.addManager(mb)){
					resultJson.put("result", "00000000");
					resultJson.put("tip", "添加管理员成功");
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "添加管理员失败");
				}
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "添加管理员失败,未找到适配的管理组");
			}
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 编辑管理员信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editManager() throws Exception {
		if(paramMap!=null ){
			Map<String,Object> map = new HashMap<String, Object>();
			if(paramMap.get("id")!=null) map.put("id", Integer.parseInt(paramMap.get("id")));
			if(paramMap.get("account")!=null) {
				List<ConditionBean> conditions = new ArrayList<ConditionBean>();
				conditions.add(new ConditionBean("account",paramMap.get("account"),ConditionBean.EQ));
				List<ManagerBean> mlist = managerService.findManagerList(conditions);
				if(mlist!=null && 0!=mlist.size()){
					resultJson.put("result", "00000001");
					resultJson.put("tip", "修改管理员资料信息失败,账号："+paramMap.get("account")+"已存在");
					return Action.SUCCESS;
				}
				map.put("account", paramMap.get("account"));
			}
			if(paramMap.get("password")!=null) map.put("password", paramMap.get("password"));
			if(paramMap.get("passwordLimitUtc")!=null) map.put("passwordLimitUtc", Long.parseLong(paramMap.get("passwordLimitUtc")));
			if(paramMap.get("email")!=null) map.put("email", paramMap.get("email"));
			if(paramMap.get("roleId")!=null) map.put("role", Integer.parseInt(paramMap.get("roleId")));
			if(paramMap.get("departmentId")!=null) map.put("departmentId", paramMap.get("departmentId"));
			if(paramMap.get("status")!=null) {
				map.put("status", Integer.parseInt(paramMap.get("status")));
			}
			if(paramMap.get("headsetUrl")!=null) map.put("headsetUrl", paramMap.get("headsetUrl"));
			if (managerService.editManager(map)) {
				resultJson.put("result", "00000000");
				resultJson.put("tip", "管理员信息修改成功");
			} else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "管理员信息修改失败");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "修改管理员资料信息失败,未能传入有效修改信息!");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除操作员信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String delManager() throws Exception {
		if(managerList!=null && managerList.size()!=0){
			List<Integer> ids = new ArrayList<Integer>();
			for(ManagerBean bean : managerList) ids.add(bean.getId());
			if (managerService.delManager(ids)) {
				resultJson.put("result", "00000000");
				resultJson.put("tip", "删除管理员成功");
			} else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "删除管理员失败");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "删除管理员失败,未能传入有效管理员ID");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 验证密码
	 * @return
	 */
	public String editPassword(){
		if(null != paramMap){
			ManagerBean manager = managerService.findById(Integer.parseInt(paramMap.get("id")));
			if(null != manager){
				if(manager.getPassword().equals(paramMap.get("password")))
				resultJson.put("result", "00000000");
				else resultJson.put("result", "00000001");
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 添加角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addRole() throws Exception {
		if(managerRoleList!=null && managerRoleList.size()!=0){
			ManagerRoleBean bean = managerRoleList.get(0);
			if (managerService.addRole(bean)) {
				resultJson.put("result", "00000000");
				resultJson.put("tip", "管理组添加成功");
			} else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "管理组添加失败");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "管理组添加失败,未能传入有效添加信息");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 编辑角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editRole() throws Exception {
		if(managerRoleList!=null && managerRoleList.size()!=0){
			ManagerRoleBean bean = managerRoleList.get(0);
			if (managerService.editRole(bean)) {
				resultJson.put("result", "00000000");
				resultJson.put("tip", "管理组信息修改成功");
			} else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "管理组信息修改失败");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "管理组信息修改失败,未能传入有效修改信息");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String delRole() throws Exception {
		if(managerRoleList!=null && managerRoleList.size()!=0){
			List<Integer> ids = new ArrayList<Integer>();
			for(ManagerRoleBean bean : managerRoleList) ids.add(bean.getId());
			if (managerService.delRole(ids)) {
				resultJson.put("result", "00000000");
				resultJson.put("tip", "管理组删除成功");
			} else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "管理组删除失败");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "管理组删除失败,未能传入有效ID");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 初始化管理员权限模块
	 * @param rolePermission
	 * @return
	 */
	public Map<String, List<PermissionBean>> initPermission(String rolePermission){
		Map<String, List<PermissionBean>> map = new HashMap<String, List<PermissionBean>>();
		if(null!=rolePermission && !"".equals(rolePermission)){
			String[] role = rolePermission.split("/"); 
			List<PermissionBean> permissions = managerService.findPermissions();
			for (String id : role) {
				int idx = Integer.parseInt(id);
				for (PermissionBean p : permissions) {
					if(p.getId() == idx){
						if(idx%1000!=0){
							if(null != map.get((idx/1000)*1000+"")) {
								map.get((idx/1000)*1000+"").add(p);
							}else {
								List<PermissionBean> pList = new ArrayList<PermissionBean>();
								pList.add(p);
								map.put((idx/1000)*1000+"", pList);
							}
						}else {
							if(null == map.get(id)) map.put(id, new ArrayList<PermissionBean>());
						}
						break;
					}
				}
			}
		}
		return map;
	}
}
