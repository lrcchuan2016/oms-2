package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

/**
 * 广告实体类结构
 * 
 * @author huchanghuan
 * 
 */
@Entity
@Table(name = "ad_resource")
public class AdResourceBean implements Serializable{


	private static final long serialVersionUID = 1L;
	private String id;
	private String content; 		// URL或者文本
	private int type;
	private String url;
	private long create_utc;
	private long start_utc;
	private long end_utc;
	private String publisher_id;
	private String publisher;
	private String adLocation_id;
	private String remark;
	private String aliyunKey;
	
	private String terminal_type;
	private String ad_name;
	private String contentType;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(nullable = false)
	public long getCreate_utc() {
		return create_utc;
	}

	public void setCreate_utc(long create_utc) {
		this.create_utc = create_utc;
	}

	@Column(nullable = false)
	public long getStart_utc() {
		return start_utc;
	}

	public void setStart_utc(long start_utc) {
		this.start_utc = start_utc;
	}

	@Column(nullable = false)
	public long getEnd_utc() {
		return end_utc;
	}

	public void setEnd_utc(long end_utc) {
		this.end_utc = end_utc;
	}

	@Column(nullable = false)
	public String getPublisher_id() {
		return publisher_id;
	}
	
	public void setPublisher_id(String publisher_id) {
		this.publisher_id = publisher_id;
	}

	@Column(nullable = false)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Formula("(select m.name from manager m where m.id=publisher_id)")
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	@Column(name="location_id",nullable=false)
	public String getAdLocation_id() {
		return adLocation_id;
	}

	public void setAdLocation_id(String adLocation_id) {
		this.adLocation_id = adLocation_id;
	}

	@Column(name="aliyun_key",nullable=false)
	public String getAliyunKey() {
		return aliyunKey;
	}

	public void setAliyunKey(String aliyunKey) {
		this.aliyunKey = aliyunKey;
	}
	
	@Column(name="type",nullable=false)
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name="url",nullable=false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Formula("(select ad_l.terminal_type from ad_location ad_l where ad_l.id = location_id)")
	public String getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}
	
	@Formula("(select ad_l.ad_name from ad_location ad_l where ad_l.id = location_id)")
	public String getAd_name() {
		return ad_name;
	}

	public void setAd_name(String ad_name) {
		this.ad_name = ad_name;
	}
	
	@Formula("(select ad_l.type from ad_location ad_l where ad_l.id = location_id)")
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public AdResourceBean() {}

	public AdResourceBean(String id, String content, int type, String url,
			long create_utc, long start_utc, long end_utc, String publisher_id,
			String publisher, String location_id, String remark,
			String aliyunKey) {
		this.id = id;
		this.content = content;
		this.type = type;
		this.url = url;
		this.create_utc = create_utc;
		this.start_utc = start_utc;
		this.end_utc = end_utc;
		this.publisher_id = publisher_id;
		this.adLocation_id = location_id;
		this.remark = remark;
		this.aliyunKey = aliyunKey;
	}

	

	

}
