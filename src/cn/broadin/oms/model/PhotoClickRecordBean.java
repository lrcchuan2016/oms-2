package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

/**
 * 相片点击记录
 * @author huchanghuan
 *
 */
@Entity
@Table(name="photo_click_record")
public class PhotoClickRecordBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	@Column(name="album_id",nullable=false)
    private String albumId;
	@Column(name="album_name",nullable=false)
    private String albumName;
	@Column(name="album_type",nullable=false)
    private int albumType;
	@Column(name="user_id",nullable=false)
    private String userId;
	@Column(name="club_id",nullable=false)
	private String clubId;
	@Column(name="media_id",nullable=false)
    private String mediaId;
	@Column(name="click_utc",nullable=false)
    private long clickUtc;
	@Column(name="terminal_type",nullable=false)
    private int terminalType;
	@Column(name="device_id",nullable=false)
    private String deviceId;
	
	@Formula("(select c.num_id from oss.club c where c.id = club_id)")    //注意此处的连表查询oss
	private String clubNum;    //家庭号
	@Formula("(select c.nickname from oss.club c where c.id = club_id)")
	private String clubName;	//家庭呢称
	@Formula("(select count(*) from photo_click_record r where r.album_id = album_id)")
	private Integer browseNum;	//统计该相片浏览次数
	@Formula("(select m.content from oss.media m where m.id = media_id)")
	private String photoUrl;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public int getAlbumType() {
		return albumType;
	}
	public void setAlbumType(int albumType) {
		this.albumType = albumType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getClubId() {
		return clubId;
	}
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public long getClickUtc() {
		return clickUtc;
	}
	public void setClickUtc(long clickUtc) {
		this.clickUtc = clickUtc;
	}
	public int getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(int terminalType) {
		this.terminalType = terminalType;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getClubNum() {
		return clubNum;
	}
	public void setClubNum(String clubNum) {
		this.clubNum = clubNum;
	}
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	
	public Integer getBrowseNum() {
		return browseNum;
	}
	public void setBrowseNum(Integer browseNum) {
		if(browseNum != null)
			this.browseNum = browseNum;
		else 
			this.browseNum = 0;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
    
    

}
