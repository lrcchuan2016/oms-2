package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 浏览时间记录
 * @author huchanghuan
 *
 */
@Entity
@Table(name="album_surfer_record")
public class AlbumSurferRecordBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	@Column(name="album_id",nullable=false)
    private String albumId;
	@Column(name="album_type",nullable=false)
    private int albumType;
	@Column(name="user_id",nullable=false)
    private String userId;
	@Column(name="club_id",nullable=false)
	private String clubId;
	@Column(name="surf_utc",nullable=false)
    private long surfUtc;
	@Column(name="quit_utc",nullable=false)
    private long quitUtc;
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
	
	public int getAlbumType() {
		return albumType;
	}
	public void setAlbumType(int albumType) {
		this.albumType = albumType;
	}
	public String getUserId() {
		return userId;
	}
	
	public String getClubId() {
		return clubId;
	}
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public long getSurfUtc() {
		return surfUtc;
	}
	public void setSurfUtc(long surfUtc) {
		this.surfUtc = surfUtc;
	}
	public long getQuitUtc() {
		return quitUtc;
	}
	public void setQuitUtc(long quitUtc) {
		this.quitUtc = quitUtc;
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
