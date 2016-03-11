package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 版本截图实体类
 * @author huchanghuan
 *
 */
@Entity
@Table(name="version_screenshot")
public class ScreenshotBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@Column(name="url",nullable=false)
	private String url;
	@Column(name="name",nullable=false)
	private String name;
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="soft_id")
	private VersionSoftBean version;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public VersionSoftBean getVersion() {
		return version;
	}
	public void setVersion(VersionSoftBean version) {
		this.version = version;
	}
	
	
	
}
