package cn.broadin.oms.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Formula;

/**
 * 主题模版装饰品数据结构
 * 
 * @author xiejun
 */

@Entity
@Table(name = "decorate")
public class DecorateBean {
	@Id
	private String id; // 饰品Id
	@Column(name = "name", nullable = false)
	private String name; // 饰品名称
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private DecorateGroupBean group; // 饰品分类ID
	@Formula("(select dg.name from decorate_group dg where dg.id=group_id)")
	private String groupName;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "media_id")
	private MediaBean media;
	@Column(name = "create_utc", nullable = false)
	private long createUtc = 0;
	@Formula("(select m.content from media m where m.id = media_id )")
	private String url;
	@Formula("(select m.width from media m where m.id = media_id)")
	private int width;
	@Formula("(select m.height from media m where m.id = media_id)")
	private int height;
	@Transient
	private long byteLen;
	
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

	@JSON(serialize = false)
	public DecorateGroupBean getGroup() {
		return group;
	}

	public void setGroup(DecorateGroupBean group) {
		this.group = group;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@JSON(serialize = false)
	public MediaBean getMedia() {
		return media;
	}

	public void setMedia(MediaBean media) {
		this.media = media;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public long getByteLen() {
		return byteLen;
	}

	public void setByteLen(long byteLen) {
		this.byteLen = byteLen;
	}

	public DecorateBean() {
		super();
	}

	public DecorateBean(String id, String name, DecorateGroupBean group,
			MediaBean media, long createUtc) {
		super();
		this.id = id;
		this.name = name;
		this.group = group;
		this.media = media;
		this.createUtc = createUtc;
	}

}
