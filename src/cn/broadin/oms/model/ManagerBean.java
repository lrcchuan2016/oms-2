package cn.broadin.oms.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

/**
 * 管理员数据结构类
 * @author xiejun
 */
@Entity
@Table(name = "manager")
public class ManagerBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "name",nullable = false)
	private String name;
	@Column(name = "account",nullable = false)
	private String account;
	@Column(name = "password",nullable = false)
	private String password; // 管理员密码
	@Column(name = "password_limit_utc",nullable = false)
	private long passwordLimitUtc = 0;
	@Column(name = "password_create_utc",nullable = false)
	private long passwordCreateUtc = 0;		//密码生效时间
	@Column(name = "email",nullable = true)
	private String email;
	@Column(name = "relate_flag",nullable = false)
	private int relateFlag = 0;
	@Column(name = "department_id",nullable = false)
	private int departmentId; // 所属部门ID
	@Formula("(select d.name from department d where d.id=department_id)")
	private String departmentName;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
	private ManagerRoleBean role;
	@Column(name = "status",nullable = false)
	private int status = 0; //使用状态
	@Column(name = "headset_url",nullable = false)
	private String headsetUrl;	//头像URL
	@Formula("(select r.name from manager_role r where r.idx=role_id)")
	private String roleName; // 角色名称
	@Formula("(select r.permission from manager_role r where r.idx=role_id)")
	private String rolePermission; // 角色值
	@Formula("(select r.status from manager_role r where r.idx=role_id)")
	private int roleStatus;
	@Transient
	private Map<String, String> rolePermissionMap = new HashMap<String, String>();
	@Column(name = "login_utc",nullable = false)
	private long loginUtc; // 登录时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getPasswordLimitUtc() {
		return passwordLimitUtc;
	}
	public void setPasswordLimitUtc(long passwordLimitUtc) {
		this.passwordLimitUtc = passwordLimitUtc;
	}
	public long getPasswordCreateUtc() {
		return passwordCreateUtc;
	}
	public void setPasswordCreateUtc(long passwordCreateUtc) {
		this.passwordCreateUtc = passwordCreateUtc;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getRelateFlag() {
		return relateFlag;
	}
	public void setRelateFlag(int relateFlag) {
		this.relateFlag = relateFlag;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public ManagerRoleBean getRole() {
		return role;
	}
	public void setRole(ManagerRoleBean role) {
		this.role = role;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getHeadsetUrl() {
		return headsetUrl;
	}
	public void setHeadsetUrl(String headsetUrl) {
		this.headsetUrl = headsetUrl;
	}
	//@Formula("(select r.name from manager_role r where r.idx=role_id)")
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRolePermission() {
		return rolePermission;
	}
	public void setRolePermission(String rolePermission) {
		this.rolePermission = rolePermission;
	}
	public Map<String, String> getRolePermissionMap() {
		return rolePermissionMap;
	}
	public void setRolePermissionMap(Map<String, String> rolePermissionMap) {
		this.rolePermissionMap = rolePermissionMap;
	}
	public long getLoginUtc() {
		return loginUtc;
	}
	public void setLoginUtc(long loginUtc) {
		this.loginUtc = loginUtc;
	}
	public int getRoleStatus() {
		return roleStatus;
	}
	public void setRoleStatus(int roleStatus) {
		this.roleStatus = roleStatus;
	}
	
}
