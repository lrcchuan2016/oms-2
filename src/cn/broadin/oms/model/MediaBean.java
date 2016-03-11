package cn.broadin.oms.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Formula;

/**
 * 媒资数据结构
 * 
 * @author xiejun
 * 
 */
@Entity
@Table(name = "media")
public class MediaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Column(name = "type", nullable = false)
	private int type = 0; // 媒资类型ID
	@Formula("(select mt.description from media_type mt where mt.id=type)")
	private String typeName;
	@Column(name = "content", nullable = true)
	private String content;
	@Column(name = "description", nullable = true)
	private String description;
	@Column(name = "thumbnail", nullable = false)
	private String thumbnail;
	@Column(name = "width", nullable = false)
	private int width = 0; // 资源（图片）的宽度
	@Column(name = "height", nullable = false)
	private int height = 0; // 资源（图片）的高度
	@Column(name = "byte_len", nullable = true)
	private long byteLen = 0; // 资源文件字节长度
	@Column(name = "time_len", nullable = true)
	private int timeLen = 0; // 资源文件时间长度
	@Column(name = "audit_status", nullable = false)
	private String audit_status;
	@Column(name = "creater_id", nullable = false)
	private String createrId; // 创建者ID
	@Column(name = "last_mod_userid", nullable = false)
	private String lastModUserId; // 最后修改者ID
	@Column(name = "create_utc", nullable = false)
	private long createUtc = 0; // 创建时间
	@Column(name = "modify_utc", nullable = false)
	private long modifyUtc = 0; // 最后修改时间
	@OneToMany(mappedBy = "media", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
	private Set<AlbumMediaBean> mSet = new HashSet<AlbumMediaBean>();

	@Transient
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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

	public int getTimeLen() {
		return timeLen;
	}

	public void setTimeLen(int timeLen) {
		this.timeLen = timeLen;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getLastModUserId() {
		return lastModUserId;
	}

	public void setLastModUserId(String lastModUserId) {
		this.lastModUserId = lastModUserId;
	}

	public long getCreateUtc() {
		return createUtc;
	}

	public void setCreateUtc(long createUtc) {
		this.createUtc = createUtc;
	}

	public long getModifyUtc() {
		return modifyUtc;
	}

	public void setModifyUtc(long modifyUtc) {
		this.modifyUtc = modifyUtc;
	}

	public String getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(String audit_status) {
		this.audit_status = audit_status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JSON(serialize = false)
	public Set<AlbumMediaBean> getmSet() {
		return mSet;
	}

	public void setmSet(Set<AlbumMediaBean> mSet) {
		this.mSet = mSet;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((audit_status == null) ? 0 : audit_status.hashCode());
		result = prime * result + (int) (byteLen ^ (byteLen >>> 32));
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + (int) (createUtc ^ (createUtc >>> 32));
		result = prime * result
				+ ((createrId == null) ? 0 : createrId.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + height;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastModUserId == null) ? 0 : lastModUserId.hashCode());
		result = prime * result + ((mSet == null) ? 0 : mSet.hashCode());
		result = prime * result + (int) (modifyUtc ^ (modifyUtc >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((thumbnail == null) ? 0 : thumbnail.hashCode());
		result = prime * result + timeLen;
		result = prime * result + type;
		result = prime * result
				+ ((typeName == null) ? 0 : typeName.hashCode());
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MediaBean other = (MediaBean) obj;
		if (audit_status == null) {
			if (other.audit_status != null)
				return false;
		} else if (!audit_status.equals(other.audit_status))
			return false;
		if (byteLen != other.byteLen)
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (createUtc != other.createUtc)
			return false;
		if (createrId == null) {
			if (other.createrId != null)
				return false;
		} else if (!createrId.equals(other.createrId))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (height != other.height)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastModUserId == null) {
			if (other.lastModUserId != null)
				return false;
		} else if (!lastModUserId.equals(other.lastModUserId))
			return false;
		if (mSet == null) {
			if (other.mSet != null)
				return false;
		} else if (!mSet.equals(other.mSet))
			return false;
		if (modifyUtc != other.modifyUtc)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (thumbnail == null) {
			if (other.thumbnail != null)
				return false;
		} else if (!thumbnail.equals(other.thumbnail))
			return false;
		if (timeLen != other.timeLen)
			return false;
		if (type != other.type)
			return false;
		if (typeName == null) {
			if (other.typeName != null)
				return false;
		} else if (!typeName.equals(other.typeName))
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	public MediaBean() {}

	public MediaBean(String id, int type, String content, String description,
			String thumbnail, int width, int height, long byteLen, int timeLen,
			String audit_status, String createrId, String lastModUserId,
			long createUtc, long modifyUtc) {
		this.id = id;
		this.type = type;
		this.content = content;
		this.description = description;
		this.thumbnail = thumbnail;
		this.width = width;
		this.height = height;
		this.byteLen = byteLen;
		this.timeLen = timeLen;
		this.audit_status = audit_status;
		this.createrId = createrId;
		this.lastModUserId = lastModUserId;
		this.createUtc = createUtc;
		this.modifyUtc = modifyUtc;
	}

}
