package cn.broadin.oms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

/**
 * 软件版本实体类
 * @author huchanghuan
 *
 */
@Entity
@Table(name="version_soft")
public class VersionSoftBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Column(name="version_num",nullable=false)
	private String versionNum;
	@Column(name="soft_url",nullable=false)
	private String softUrl;
	@Column(name="svn_url",nullable=false)
	private String svnUrl;
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="terminal_id")
	private VersionTerminalBean vt;
	@Column(name="description",nullable=false)
	private String description;
	@Column(name="create_utc",nullable=false)
	private long createUtc;
	@Column(name="modify_utc",nullable=false)
	private long modifyUtc;
	@OneToMany(mappedBy="version")
	private List<ScreenshotBean> screenShots = new ArrayList<ScreenshotBean>();
	
	public String getId() {
		return id;
	}
	public String getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
	public String getSoftUrl() {
		return softUrl;
	}
	public void setSoftUrl(String softUrl) {
		this.softUrl = softUrl;
	}
	public String getSvnUrl() {
		return svnUrl;
	}
	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}
	public VersionTerminalBean getVt() {
		return vt;
	}
	public void setVt(VersionTerminalBean vt) {
		this.vt = vt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getCreateUtc() {
		return createUtc;
	}
	public void setCreateUtc(long createUtc) {
		this.createUtc = createUtc;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ScreenshotBean> getScreenShots() {
		return screenShots;
	}
	@JSON(serialize=false)
	public void setScreenShots(List<ScreenshotBean> screenShots) {
		this.screenShots = screenShots;
	}
	public long getModifyUtc() {
		return modifyUtc;
	}
	public void setModifyUtc(long modifyUtc) {
		this.modifyUtc = modifyUtc;
	}
	
}
