package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 广告位实体类结构
 * @author Administrator
 *
 */
@Entity
@Table(name="ad_location")
public class AdLocationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String ad_name;
	private String terminal_type;
	private String type;
	private int width;
	private int height;
	private int bitrate;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(nullable = false)
	public String getAd_name() {
		return ad_name;
	}

	public void setAd_name(String ad_name) {
		this.ad_name = ad_name;
	}

	@Column(nullable = false)
	public String getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}

	@Column(nullable = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(nullable = false)
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Column(nullable = false)
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Column(nullable = false)
	public int getBitrate() {
		return bitrate;
	}

	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}
	
	
	public AdLocationBean() { }


	public AdLocationBean(String id, String ad_name, String terminal_type,
			String type, int width, int height, int bitrate) {
		super();
		this.id = id;
		this.ad_name = ad_name;
		this.terminal_type = terminal_type;
		this.type = type;
		this.width = width;
		this.height = height;
		this.bitrate = bitrate;
	}
	
}
