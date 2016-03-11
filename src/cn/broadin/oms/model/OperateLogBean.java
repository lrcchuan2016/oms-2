package cn.broadin.oms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.broadin.oms.util.Tools;

import com.opensymphony.xwork2.ActionContext;

/**
 * 操作日志数据结构类
 * 
 * @author xiejun
 */
@Entity
@Table(name = "operate_log")
public class OperateLogBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idx")
	private int id;             // 主键ID
	@Column(name = "manager_id")
	private int managerId;  // 操作员ID
	@Column(name = "utc")
	private long utc;           // 操作时间
	@Column(name = "description")
	private String description; // 操作描述
	
	public OperateLogBean() {}
	
	public OperateLogBean(String description) {
		super();
		ManagerBean admin = (ManagerBean) ActionContext.getContext().getSession().get("admin"); // 获取当前登录的操作员信息
		this.managerId = admin.getId();
		this.utc = Tools.getNowUTC();
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getManagerId() {
		return managerId;
	}
	
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	
	public long getUtc() {
		return utc;
	}
	
	public void setUtc(long utc) {
		this.utc = utc;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
