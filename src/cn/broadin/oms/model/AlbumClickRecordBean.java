package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 相册点击记录实体类
 * @author huchanghuan
 *
 */
@Entity
@Table(name="album_click_record")
public class AlbumClickRecordBean implements Serializable{

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
	@Column(name="click_utc",nullable=false)
    private long clickUtc;
	@Column(name="terminal_type",nullable=false)
    private int terminalType;
	@Column(name="device_id",nullable=false)
    private String deviceId;
    
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

    
}
