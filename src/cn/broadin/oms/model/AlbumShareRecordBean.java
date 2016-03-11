package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 分享记录
 * @author huchanghuan
 *
 */
@Entity
@Table(name="album_share_record")
public class AlbumShareRecordBean implements Serializable{

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
    private String albumType;
	@Column(name="user_id",nullable=false)
    private String userId;
	@Column(name="club_id",nullable=false)
	private String clubId;
	@Column(name="share_utc",nullable=false)
    private long shareUtc;
	@Column(name="terminal_type",nullable=false)
    private int terminalType;
	@Column(name="device_id",nullable=false)
    private String deviceId;
	@Column(name="platform",nullable=false)
    private int platform;
    
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
	public String getAlbumType() {
		return albumType;
	}
	public void setAlbumType(String albumType) {
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
	public long getShareUtc() {
		return shareUtc;
	}
	public void setShareUtc(long shareUtc) {
		this.shareUtc = shareUtc;
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
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	

    
}
