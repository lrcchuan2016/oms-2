package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 相册推荐表
 * @author hch
 *
 */
@Entity
@Table(name="album_recommend")
public class AlbumRecommendBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String album_id;
	@Column(nullable=false)
	private int weight;
	@Column(nullable=false)
	private String reason;
	@Column(nullable=false)
	private long utc;
	
	public String getAlbum_id() {
		return album_id;
	}
	public void setAlbum_id(String album_id) {
		this.album_id = album_id;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public long getUtc() {
		return utc;
	}
	public void setUtc(long utc) {
		this.utc = utc;
	}
	
	public AlbumRecommendBean() {
		super();
	}
	
	public AlbumRecommendBean(String album_id, int weight, String reason, long utc) {
		super();
		this.album_id = album_id;
		this.weight = weight;
		this.reason = reason;
		this.utc = utc;
	}
	
}
