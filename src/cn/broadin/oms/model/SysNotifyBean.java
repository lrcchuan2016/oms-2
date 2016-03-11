package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统消息表
 * @author huchanghuan
 *
 */
@Entity
@Table(name="sys_notify")
public class SysNotifyBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@Column(name="type",nullable=false)
	private int type;
	@Column(name="content",nullable=false)
	private String content;
	@Column(name="from_id",nullable=false)
	private String fromId;
	@Column(name="to_id",nullable=false)
	private String toId;
	@Column(name="club_id",nullable=false)
	private String clubId;
	@Column(name="receive_status",nullable=false)
	private int receiveStatus;
	@Column(name="create_utc",nullable=false)
	private long createUtc;
	@Column(name="expiry_utc",nullable=false)
	private long expiryUtc;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getClubId() {
		return clubId;
	}
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	public int getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(int receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
	public long getCreateUtc() {
		return createUtc;
	}
	public void setCreateUtc(long createUtc) {
		this.createUtc = createUtc;
	}
	public long getExpiryUtc() {
		return expiryUtc;
	}
	public void setExpiryUtc(long expiryUtc) {
		this.expiryUtc = expiryUtc;
	}
	public SysNotifyBean() {}
	
	public SysNotifyBean(String id, int type, String content, String fromId,
			String toId, String clubId, int receiveStatus, long createUtc,
			long expiryUtc) {
		super();
		this.id = id;
		this.type = type;
		this.content = content;
		this.fromId = fromId;
		this.toId = toId;
		this.clubId = clubId;
		this.receiveStatus = receiveStatus;
		this.createUtc = createUtc;
		this.expiryUtc = expiryUtc;
	}
	
	
}
