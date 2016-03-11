package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单栏目模块数据结构类 
 * @author xiejun
 */
@Entity
@Table(name = "permission")
public class PermissionBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id; // 模块ID
	private String description; // 模块描述
	private String url;
	
	@Id
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
