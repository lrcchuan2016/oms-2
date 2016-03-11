package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 活动参与者实体类
 * @author huchanghuan
 *
 */
@Entity
@Table(name="actor")
public class ActorBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Column(name="ad_id",nullable=false)
	private String activityId;
	@Column(name="phone",nullable=false)
	private String phone;
	@Column(name="album_name",nullable=false)
	private String albumName;
	@Column(name="address",nullable=false)
	private String address;
	@Column(name="participate_time",nullable=false)
	private long participateTime;
	@Transient
	private String link;
	@Transient
	private String albumCover;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getParticipateTime() {
		return participateTime;
	}
	public void setParticipateTime(long participateTime) {
		this.participateTime = participateTime;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getAlbumCover() {
		return albumCover;
	}
	public void setAlbumCover(String albumCover) {
		this.albumCover = albumCover;
	}
	public ActorBean() {}
	
	public ActorBean(String id, String activityId, String phone,
			String albumName, String address, long participateTime) {
		this.id = id;
		this.activityId = activityId;
		this.phone = phone;
		this.albumName = albumName;
		this.address = address;
		this.participateTime = participateTime;
	}

	
}
