package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name="common_media")
public class CommonMediaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@Column(name="type",nullable=false)
	private int type;
	@Column(name="public_level",nullable=false)
	private int publicLevel;
	@Column(name="media_id",nullable=false)
	private String mediaId;
	@Column(name="title",nullable=false)
	private String title;
	@Column(name="detail",nullable=false)
	private String detail;
	@Column(name="user_id",nullable=false)
	private String userId;
	@Column(name="utc",nullable=false)
	private long utc;
	@Formula("(select m.content from media m where m.id = media_id)")
	private String url;
	@Formula("(select m.time_Len from media m where m.id = media_id)")
	private String timeLen;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPublicLevel() {
		return publicLevel;
	}
	public void setPublicLevel(int publicLevel) {
		this.publicLevel = publicLevel;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getUtc() {
		return utc;
	}
	public void setUtc(long utc) {
		this.utc = utc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTimeLen() {
		return timeLen;
	}
	public void setTimeLen(String timeLen) {
		this.timeLen = timeLen;
	}
	public CommonMediaBean() {
		super();
	}
	public CommonMediaBean(String id, int type, int publicLevel, String mediaId,
			String title, String detail, String userId, long utc) {
		super();
		this.id = id;
		this.type = type;
		this.publicLevel = publicLevel;
		this.mediaId = mediaId;
		this.title = title;
		this.detail = detail;
		this.userId = userId;
		this.utc = utc;
	}
	@Override
	public String toString() {
		return "CommonMediaBean [id=" + id + ", type=" + type
				+ ", publicLevel=" + publicLevel + ", mediaId=" + mediaId
				+ ", title=" + title + ", detail=" + detail + ", userId="
				+ userId + ", utc=" + utc + ", url=" + url + ", timeLen="
				+ timeLen + "]";
	}
	
	
}
