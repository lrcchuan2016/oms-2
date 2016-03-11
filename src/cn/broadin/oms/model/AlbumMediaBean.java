package cn.broadin.oms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Formula;


/**
 * 相册媒资数据结构
 * 
 * @author huchanghuan
 * 
 */
@Entity
@Table(name = "album_media")
public class AlbumMediaBean {

	@Id
	private String id;
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="album_id")
	private AlbumBean album;
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="media_id")
	private MediaBean media;     
	@Column(nullable = false)
	private String status;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String detail;
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserBean user;
	@Column(nullable = false)
	private String user_ip;
	@Column(nullable = false)
	private long last_modify_utc;
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="last_mod_userid")
	private UserBean last_mod_user;
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
	@Formula("(select m.type from media m where m.id = media_id)")
	private String type;
	@Formula("(select m.audit_status from media m where m.id = media_id)")
	private String audit_status;
	@Formula("(select a.type from album a where a.id = album_id)")
	private String albumType;
	@Transient
	private List<MediaBean> mList = new ArrayList<MediaBean>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AlbumBean getAlbum() {
		return album;
	}

	public void setAlbum(AlbumBean album) {
		this.album = album;
	}

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

	@JSON(serialize=false)
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	@JSON(serialize=false)
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

	@JSON(serialize=false)
	public UserBean getLast_mod_user() {
		return last_mod_user;
	}

	public void setLast_mod_user(UserBean last_mod_user) {
		this.last_mod_user = last_mod_user;
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
	
	public String getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(String audit_status) {
		this.audit_status = audit_status;
	}
	
	public String getAlbumType() {
		return albumType;
	}

	public void setAlbumType(String albumType) {
		this.albumType = albumType;
	}

	@JSON(serialize = false)
	public List<MediaBean> getmList() {
		return mList;
	}

	public void setmList(List<MediaBean> mList) {
		this.mList = mList;
	}
	
	public AlbumMediaBean() {
		super();
	}

	public AlbumMediaBean(String id, AlbumBean album, MediaBean media,
			String status, String title, String detail, UserBean user,
			String user_ip, long last_modify_utc, UserBean last_mod_user,
			String last_mod_userip, long utc, String longitude_latitude,
			int read_count, int share_count, int forward_count) {
		super();
		this.id = id;
		this.album = album;
		this.media = media;
		this.status = status;
		this.title = title;
		this.detail = detail;
		this.user = user;
		this.user_ip = user_ip;
		this.last_modify_utc = last_modify_utc;
		this.last_mod_user = last_mod_user;
		this.last_mod_userip = last_mod_userip;
		this.utc = utc;
		this.longitude_latitude = longitude_latitude;
		this.read_count = read_count;
		this.share_count = share_count;
		this.forward_count = forward_count;
	}

	
}
