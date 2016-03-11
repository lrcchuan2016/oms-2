package cn.broadin.oms.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 联系人数据结构类
 * @author xiejun
 */
@Entity
@Table(name = "channel_contacts")
public class ChannelContactsBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "name",nullable = false)
	private String name;
	@Column(name = "department",nullable = true)
	private String department;
	@Column(name = "job_position",nullable = true)
	private String jobPosition;
	@Column(name = "mobile",nullable = true)
	private String mobile;
	@Column(name = "email",nullable = true)
	private String email;
	@Column(name = "phone",nullable = true)
	private String phone;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
	private ChannelBean channelContact;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getJobPosition() {
		return jobPosition;
	}
	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public ChannelBean getChannelContact() {
		return channelContact;
	}
	public void setChannelContact(ChannelBean channelContact) {
		this.channelContact = channelContact;
	}
}
