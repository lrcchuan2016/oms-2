package cn.broadin.oms.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 相册饰品分组数据结构
 * @author xiejun
 */

@Entity
@Table(name = "decorate_group")
public class DecorateGroupBean {
	@Id
	private String id;			//分组Id
	@Column(name = "name",nullable=false)
	private String name;		//分组名称
	@Column(name = "icon_url",nullable=false)
	private String iconUrl;		//饰品图标URL
	@Column(name = "detail",nullable=false)
	private String detail;
	@Column(name = "create_utc",nullable=false)
	private long createUtc = 0;
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
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public long getCreateUtc() {
		return createUtc;
	}
	public void setCreateUtc(long createUtc) {
		this.createUtc = createUtc;
	}
}
