package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

/**
 * 终端软件实体类
 * @author huchanghuan
 *
 */
@Entity
@Table(name="version_terminal")
public class VersionTerminalBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Column(name="name",nullable=false)
	private String name;
	@Column(name="description",nullable=false)
	private String description;
	@Column(name="create_utc",nullable=false)
	private long createUtc;
	@Column(name="modify_utc",nullable=false)
	private long modifyUtc;
	@Formula("(select count(*) from version_soft vs where vs.terminal_id = id)")
	private Integer versionNum;			//此软件终端的软件版本数量
	@Transient
	private String softUrl;
	@Transient
	private String svnUrl;
	
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
	public Integer getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
	public long getModifyUtc() {
		return modifyUtc;
	}
	public void setModifyUtc(long modifyUtc) {
		this.modifyUtc = modifyUtc;
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

}
