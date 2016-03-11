package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 消息推送实体
 * @author huchanghuan
 *
 */
@Entity
@Table(name="push_device")
public class PushDeviceBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@Column(nullable=false,name="user_id")
	private String userId;
	@Column(nullable=false,name="device_type")
	private int deviceType;
	@Column(nullable=false,name="terminal_id")
	private String terminalId;
	@Column(nullable=false,name="last_login_utc")
	private long lastLoginUtc;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public long getLastLoginUtc() {
		return lastLoginUtc;
	}
	public void setLastLoginUtc(long lastLoginUtc) {
		this.lastLoginUtc = lastLoginUtc;
	}
	
	public PushDeviceBean() {
		super();
	}
	public PushDeviceBean(String id, String userId, int deviceType,
			String terminalId, long lastLoginUtc) {
		super();
		this.id = id;
		this.userId = userId;
		this.deviceType = deviceType;
		this.terminalId = terminalId;
		this.lastLoginUtc = lastLoginUtc;
	}
	
	
}
