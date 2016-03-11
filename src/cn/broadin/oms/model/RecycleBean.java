package cn.broadin.oms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 管理员回收站数据结构类
 * @author xiejun
 */
@Entity
@Table(name = "recycle")
public class RecycleBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idx")
	private int id;
	
	@Column(name = "type",nullable = false)
	private int type;
	
	@Column(name = "ref_id",nullable = false)
	private int refId;
	
	@Column(name = "name",nullable = false)
	private String name;
	
	@Column(name = "manage_role",nullable = true)
	private String managerRole;
	
	@Column(name = "del_utc",nullable = true)
	private long delUtc;
	
	@Column(name = "operate_name",nullable = false)
	private String operateName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRefId() {
		return refId;
	}
	public void setRefId(int refId) {
		this.refId = refId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getManagerRole() {
		return managerRole;
	}
	public void setManagerRole(String managerRole) {
		this.managerRole = managerRole;
	}
	public long getDelUtc() {
		return delUtc;
	}
	public void setDelUtc(long delUtc) {
		this.delUtc = delUtc;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
}
