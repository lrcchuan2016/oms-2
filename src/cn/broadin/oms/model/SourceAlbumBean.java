package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 素材相册表
 * @author huchanghuan
 *
 */
@Entity
@Table(name="source_album")
public class SourceAlbumBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Column(name="photo_count",nullable=false)
	private int photoCount;
	@Column(name="video_count",nullable=false)
	private int videoCount;
	@Column(name="audio_count",nullable=false)
	private int audioCount;
	@Column(name="creater_id",nullable=false)
	private String createrId;
	@Column(name="create_utc",nullable=false)
	private long createUtc;
	@Column(name="last_upload_utc",nullable=false)
	private long lastUploadUtc;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPhotoCount() {
		return photoCount;
	}
	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}
	public int getVideoCount() {
		return videoCount;
	}
	public void setVideoCount(int videoCount) {
		this.videoCount = videoCount;
	}
	public int getAudioCount() {
		return audioCount;
	}
	public void setAudioCount(int audioCount) {
		this.audioCount = audioCount;
	}
	public String getCreaterId() {
		return createrId;
	}
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	public long getCreateUtc() {
		return createUtc;
	}
	public void setCreateUtc(long createUtc) {
		this.createUtc = createUtc;
	}
	public long getLastUploadUtc() {
		return lastUploadUtc;
	}
	public void setLastUploadUtc(long lastUploadUtc) {
		this.lastUploadUtc = lastUploadUtc;
	}
	
	public SourceAlbumBean() { }
	
	public SourceAlbumBean(String id, int photoCount, int videoCount,
			int audioCount, String createrId, long createUtc, long lastUploadUtc) {
		this.id = id;
		this.photoCount = photoCount;
		this.videoCount = videoCount;
		this.audioCount = audioCount;
		this.createrId = createrId;
		this.createUtc = createUtc;
		this.lastUploadUtc = lastUploadUtc;
	}
	
	
	
}
