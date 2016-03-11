package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

@Entity
@Table(name="source_album_media")
public class SourceAlbumMediaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Column(name="source_album_id",nullable=false)
	private String sourceAlbumId;
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	@JoinColumn(name="media_id")
	private MediaBean media;
	@Column(name="title",nullable=false)
	private String title;
	@Column(name="user_id",nullable=false)
	private String userId;
	@Column(name="user_ip",nullable=false)
	private String userIp;
	@Column(name="last_mod_userid",nullable=false)
	private String lastModUserid;
	@Column(name="last_mod_userip",nullable=false)
	private String lastModUserip;
	@Column(name="utc",nullable=false)
	private long utc;
	@Column(name="last_modify_utc",nullable=false)
	private long lastModifyUtc;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSourceAlbumId() {
		return sourceAlbumId;
	}
	public void setSourceAlbumId(String sourceAlbumId) {
		this.sourceAlbumId = sourceAlbumId;
	}
	@JSON(serialize=false)
	public MediaBean getMedia() {
		return media;
	}
	public void setMedia(MediaBean media) {
		this.media = media;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getLastModUserid() {
		return lastModUserid;
	}
	public void setLastModUserid(String lastModUserid) {
		this.lastModUserid = lastModUserid;
	}
	public String getLastModUserip() {
		return lastModUserip;
	}
	public void setLastModUserip(String lastModUserip) {
		this.lastModUserip = lastModUserip;
	}
	public long getUtc() {
		return utc;
	}
	public void setUtc(long utc) {
		this.utc = utc;
	}
	public long getLastModifyUtc() {
		return lastModifyUtc;
	}
	public void setLastModifyUtc(long lastModifyUtc) {
		this.lastModifyUtc = lastModifyUtc;
	}
	
	
}
