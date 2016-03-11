package cn.broadin.oms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 媒资类型数据结构 
 * @author xiejun
 */
@Entity
@Table(name = "media_type")
public class MediaTypeBean {
	@Id
	private int id = 0;
	@Column(name = "description",nullable = false)
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
