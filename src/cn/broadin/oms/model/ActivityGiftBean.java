package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 活动礼物实体类
 * @author hch
 *
 */
@Entity
@Table(name="activity_gift")
public class ActivityGiftBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	@Column(name="name",nullable=false)
	private String name;
	@Column(name="url",nullable=false)
	private String url;
	@Column(name="gift_number",nullable=false)
	private int giftNumber;
	@Column(name="probability",nullable=false)
	private int probability;
	@Column(name="description",nullable=false)
	private String description;
	@Column(name="create_utc",nullable=false)
	private long createUtc;
	@Column(name="modify_utc")
	private long modifyUtc;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getCreateUtc() {
		return createUtc;
	}
	public void setCreateUtc(long createUtc) {
		this.createUtc = createUtc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getGiftNumber() {
		return giftNumber;
	}
	public void setGiftNumber(int giftNumber) {
		this.giftNumber = giftNumber;
	}
	public int getProbability() {
		return probability;
	}
	public void setProbability(int probability) {
		this.probability = probability;
	}
	public long getModifyUtc() {
		return modifyUtc;
	}
	public void setModifyUtc(long modifyUtc) {
		this.modifyUtc = modifyUtc;
	}
	
	public ActivityGiftBean() {}
	
	public ActivityGiftBean(String id, String name, String url, int giftNumber,
			int probability, String description, long createUtc,
			long modifyUtc) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.giftNumber = giftNumber;
		this.probability = probability;
		this.description = description;
		this.createUtc = createUtc;
		this.modifyUtc = modifyUtc;
	}
	
	
}
