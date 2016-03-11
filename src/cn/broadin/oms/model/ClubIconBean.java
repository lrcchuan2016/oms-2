package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 家庭头像实体类
 * @author huchanghuan
 *
 */
@Entity
@Table(name="club_icon")
public class ClubIconBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Column(name="url")
	private String url;
	@Column(name="type")
	private int type;
	@Column(name="create_utc")
	private long createUtc;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getCreateUtc() {
		return createUtc;
	}
	public void setCreateUtc(long createUtc) {
		this.createUtc = createUtc;
	}
	public ClubIconBean() {
		super();
	}
	
	public ClubIconBean(String id, String url, int type,long createUtc) {
		super();
		this.id = id;
		this.url = url;
		this.type = type;
		this.createUtc = createUtc;
	}
	
	
}
