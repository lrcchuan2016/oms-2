package cn.broadin.oms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 管理员角色数据结构类
 * @author xiejun
 */
@Entity
@Table(name = "manager_role")
public class ManagerRoleBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idx")
	private int id; // 主键ID
	
	@Column(name = "name")
	private String name; // 角色名
	
	@Column(name = "status")
	private int status;  //状态
	
	@Column(name = "permission")
	private String permission; // 权限值
	
	@OneToMany(mappedBy = "role")
	private  List<ManagerBean> managerList = new ArrayList<ManagerBean>();
	
	
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
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPermission() {
		return permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public List<ManagerBean> getManagerList() {
		return managerList;
	}

	public void setManagerList(List<ManagerBean> managerList) {
		this.managerList = managerList;
	}
	
}
