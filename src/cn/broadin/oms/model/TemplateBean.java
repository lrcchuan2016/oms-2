package cn.broadin.oms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

/**
 * 主题相册模版数据结构
 * @author XieJun
 */

@Entity
@Table(name = "album_template")
public class TemplateBean {
	@Id
	private String id;			//模版Id
	@Column(name = "name",nullable=false)
	private String name;		//模版名称
	@Column(name = "group_id",nullable=false)
	private String groupId;			//模版分类ID
	@Formula("(select tg.name from template_group tg where tg.id=group_id)")
	private String groupName;
	@Column(name = "introduction",nullable=false)
	private String introduction;	//简介
	@Column(name = "cover",nullable=false)
	private String coverUrl;		//封面模版URL
	@Column(name = "create_utc",nullable=false)
	private long createUtc = 0;
	@Column(name = "last_modify_utc",nullable=false)
	private long lastModifyUtc = 0;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public long getCreateUtc() {
		return createUtc;
	}
	public void setCreateUtc(long createUtc) {
		this.createUtc = createUtc;
	}
	public long getLastModifyUtc() {
		return lastModifyUtc;
	}
	public void setLastModifyUtc(long lastModifyUtc) {
		this.lastModifyUtc = lastModifyUtc;
	}
	
	
}
