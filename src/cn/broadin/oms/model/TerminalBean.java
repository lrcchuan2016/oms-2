package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 终端表
 * @author 
 *
 */
@Entity
@Table(name="terminal")
public class TerminalBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	@Column(nullable=false,name="user_id")
	private String user_id;
	@Column(nullable=false,name="ip")
	private String ip;
	@Column(nullable=false,name="mac")
	private String mac;
	@Column(nullable=false,name="type")
	private int type = 0; 
	@Column(nullable=false,name="soft_ver")
	private String soft_ver;
	@Column(nullable=false,name="hard_ver")
	private String hard_ver;
	@Column(nullable=false,name="online")
	private String online;
	@Column(nullable=false,name="login_utc")
	private long login_utc;
	@Column(nullable=false,name="addr")
	private String addr;
	@Column(nullable=false,name="channel_id")
	private String channel_id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSoft_ver() {
		return soft_ver;
	}
	public void setSoft_ver(String soft_ver) {
		this.soft_ver = soft_ver;
	}
	public String getHard_ver() {
		return hard_ver;
	}
	public void setHard_ver(String hard_ver) {
		this.hard_ver = hard_ver;
	}
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	public long getLogin_utc() {
		return login_utc;
	}
	public void setLogin_utc(long login_utc) {
		this.login_utc = login_utc;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	
	public TerminalBean() { }
	
	public TerminalBean(String id, String user_id, String ip, String mac,
			int type, String soft_ver, String hard_ver, String online,
			long login_utc, String addr, String channel_id) {
		this.id = id;
		this.user_id = user_id;
		this.ip = ip;
		this.mac = mac;
		this.type = type;
		this.soft_ver = soft_ver;
		this.hard_ver = hard_ver;
		this.online = online;
		this.login_utc = login_utc;
		this.addr = addr;
		this.channel_id = channel_id;
	}
	
	
}
