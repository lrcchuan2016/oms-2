package cn.broadin.oms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 终端上报app登陆登陆事件数据结构
 * @author xiejun
 */
@Entity
@Table(name = "l_log", schema = "")
public class LogBean{
	/** 数据编号 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idx", nullable = false, length=20)
	private Long id;
	
	/** 客户端类型*/
	@Column(name = "client_type", nullable = false)
	private String clientType;
	
	/** 操作系统类型  */
	@Column(name = "system_type", nullable = true, length = 255)
	private String systemType;
	
	/** 设备ID */
	@Column(name = "device_id", nullable = true, length = 255)
	private String deviceId;
	
	/** 家庭账号ID */
	@Column(name = "family_id", nullable = true, length = 255)
	private long familyId;
	
	/** 渠道商ID */
	@Column(name = "partner_code", nullable = false)
	private String partnerCode;

	/** 日志标识码 */
	@Column(name = "log_key", nullable = true, length = 255)
	private String logKey;

	/** 日志标签 */
	@Column(name = "log_label", nullable = true, length = 255)
	private String logLabel;
	
	/** 日志主类型  0未知操作 1-应用, 2-相册，3-事件，4-错误*/
	@Column(name = "event_type", nullable = false)
	private int evenType = 0;
	
	/** 日志子类型  0未知操作 11-init-应用启动，12-exit-应用退出, 21-simple-普通相册打开，22-title-主题打开，23-time-时间相册打开，
	 * 31-account.register-注册，32-account.login-登陆*/
	@Column(name = "subevent_type", nullable = false)
	private int subEvenType = 0;

	/** 动作产生时间 */
	@Column(name = "utc", nullable = true)
	private long logDate;
	
	/** 备注 */
	@Column(name = "extend", nullable = true, length = 255)
	private String extend;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(long familyId) {
		this.familyId = familyId;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getLogKey() {
		return logKey;
	}

	public void setLogKey(String logKey) {
		this.logKey = logKey;
	}

	public String getLogLabel() {
		return logLabel;
	}

	public void setLogLabel(String logLabel) {
		this.logLabel = logLabel;
	}

	public int getEvenType() {
		return evenType;
	}

	public void setEvenType(int evenType) {
		this.evenType = evenType;
	}

	public int getSubEvenType() {
		return subEvenType;
	}

	public void setSubEvenType(int subEvenType) {
		this.subEvenType = subEvenType;
	}

	public long getLogDate() {
		return logDate;
	}

	public void setLogDate(long logDate) {
		this.logDate = logDate;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}
	
	
}
