package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_addr")
public class UserAddrBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "user_id", nullable = false)
	private String userId;
	@Column(name = "addr", nullable = false)
	private String addr;
	@Column(name = "zipCode", nullable = false)
	private String zipCode;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "phone", nullable = false)
	private String phone;
	@Column(name = "country", nullable = false)
	private String country;
	@Column(name = "province", nullable = false)
	private String province;
	@Column(name = "city", nullable = false)
	private String city;
	@Column(name = "is_default", nullable = false)
	private int isDefault;
	@Column(name = "last_modify_utc", nullable = false)
	private long lastModifyUtc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	public long getLastModifyUtc() {
		return lastModifyUtc;
	}

	public void setLastModifyUtc(long lastModifyUtc) {
		this.lastModifyUtc = lastModifyUtc;
	}

	public UserAddrBean() {

	}

	public UserAddrBean(int id, String userId, String addr, String zipCode,
			String name, String phone, String country, String province,
			String city, int isDefault, long lastModifyUtc) {
		this.id = id;
		this.userId = userId;
		this.addr = addr;
		this.zipCode = zipCode;
		this.name = name;
		this.phone = phone;
		this.country = country;
		this.province = province;
		this.city = city;
		this.isDefault = isDefault;
		this.lastModifyUtc = lastModifyUtc;
	}

}
