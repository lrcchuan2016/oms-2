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

import org.apache.struts2.json.annotations.JSON;

/**
 * 模版媒资实体类
 * 
 * @author hch
 * 
 */
@Entity
@Table(name = "template_media")
public class TemplateMediaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "template_id")
	private TemplateBean template;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "media_id")
	private MediaBean media;
	@Column(nullable = false)
	private String status;
	@Column(nullable = false)
	private String type;		//类型
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String detail;
	@Column(nullable = false)
	private String user_id;
	@Column(nullable = false)
	private String user_ip;
	@Column(nullable = false)
	private long last_modify_utc;
	@Column(nullable = false)
	private String last_mod_userid;
	@Column(nullable = false)
	private String last_mod_userip;
	@Column(nullable = false)
	private long utc;
	@Column(nullable = false)
	private String longitude_latitude;
	@Column(nullable = false)
	private int read_count;
	@Column(nullable = false)
	private int share_count;
	@Column(nullable = false)
	private int forward_count;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JSON(serialize = false)
	public TemplateBean getTemplate() {
		return template;
	}

	public void setTemplate(TemplateBean template) {
		this.template = template;
	}

	@JSON(serialize = false)
	public MediaBean getMedia() {
		return media;
	}

	public void setMedia(MediaBean media) {
		this.media = media;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public long getLast_modify_utc() {
		return last_modify_utc;
	}

	public void setLast_modify_utc(long last_modify_utc) {
		this.last_modify_utc = last_modify_utc;
	}

	public String getLast_mod_userid() {
		return last_mod_userid;
	}

	public void setLast_mod_userid(String last_mod_userid) {
		this.last_mod_userid = last_mod_userid;
	}

	public String getLast_mod_userip() {
		return last_mod_userip;
	}

	public void setLast_mod_userip(String last_mod_userip) {
		this.last_mod_userip = last_mod_userip;
	}

	public long getUtc() {
		return utc;
	}

	public void setUtc(long utc) {
		this.utc = utc;
	}

	public String getLongitude_latitude() {
		return longitude_latitude;
	}

	public void setLongitude_latitude(String longitude_latitude) {
		this.longitude_latitude = longitude_latitude;
	}

	public int getRead_count() {
		return read_count;
	}

	public void setRead_count(int read_count) {
		this.read_count = read_count;
	}

	public int getShare_count() {
		return share_count;
	}

	public void setShare_count(int share_count) {
		this.share_count = share_count;
	}

	public int getForward_count() {
		return forward_count;
	}

	public void setForward_count(int forward_count) {
		this.forward_count = forward_count;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public TemplateMediaBean() {
		super();
	}

	public TemplateMediaBean(String id,TemplateBean template, MediaBean media,
			String status, String title, String detail, String user_id,
			String user_ip, long last_modify_utc, String last_mod_userid,
			String last_mod_userip, long utc, String longitude_latitude,
			int read_count, int share_count, int forward_count,String type) {
		super();
		this.id = id;
		this.template = template;
		this.media = media;
		this.status = status;
		this.type = type;
		this.title = title;
		this.detail = detail;
		this.user_id = user_id;
		this.user_ip = user_ip;
		this.last_modify_utc = last_modify_utc;
		this.last_mod_userid = last_mod_userid;
		this.last_mod_userip = last_mod_userip;
		this.utc = utc;
		this.longitude_latitude = longitude_latitude;
		this.read_count = read_count;
		this.share_count = share_count;
		this.forward_count = forward_count;
	}
	
	

}
