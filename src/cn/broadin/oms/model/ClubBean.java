package cn.broadin.oms.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Formula;

/**
 * 家庭(群)实体类
 * @author huchanghuan
 *
 */
@Entity
@Table(name = "club")
public class ClubBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String portrait;
	private String nickname;
	private String introduction;
	private String bkgimage;
	private String join_permission;
	private int max_member_cnt;
	private long create_utc;
	private UserBean creater;
	private String createrPhone;
	private UserBean modify;
	private long last_modify_utc;
	private String numId;
	private Set<UserClubBean> cSet = new HashSet<UserClubBean>();

	
	private Integer memberNum;
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="portrait",nullable=false)
	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	@Column(name="nickname",nullable=false)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name="introduction",nullable=false)
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Column(name="bkgimage",nullable=false)
	public String getBkgimage() {
		return bkgimage;
	}

	public void setBkgimage(String bkgimage) {
		this.bkgimage = bkgimage;
	}

	@Column(name="join_permission",nullable=false)
	public String getJoin_permission() {
		return join_permission;
	}

	public void setJoin_permission(String join_permission) {
		this.join_permission = join_permission;
	}

	@Column(name="max_member_cnt",nullable=false)
	public int getMax_member_cnt() {
		return max_member_cnt;
	}

	public void setMax_member_cnt(int max_member_cnt) {
		this.max_member_cnt = max_member_cnt;
	}

	@Column(name="create_utc",nullable=false)
	public long getCreate_utc() {
		return create_utc;
	}

	public void setCreate_utc(long create_utc) {
		this.create_utc = create_utc;
	}

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="creater_id")
	@JSON(serialize=false)
	public UserBean getCreater() {
		return creater;
	}

	public void setCreater(UserBean creater) {
		this.creater = creater;
	}

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="last_modify_userid")
	@JSON(serialize=false)
	public UserBean getModify() {
		return modify;
	}

	public void setModify(UserBean modify) {
		this.modify = modify;
	}

	@Column(name="last_modify_utc",nullable=false)
	public long getLast_modify_utc() {
		return last_modify_utc;
	}

	public void setLast_modify_utc(long last_modify_utc) {
		this.last_modify_utc = last_modify_utc;
	}

	//删除/更新ClubBean时，级联UserClubBean
	@OneToMany(mappedBy="club",cascade={CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE}) 
	@JSON(serialize=false)
	public Set<UserClubBean> getcSet() {
		return cSet;
	}

	public void setcSet(Set<UserClubBean> cSet) {
		this.cSet = cSet;
	}
	
	@Column(name="num_id",nullable=false)
	public String getNumId() {
		return numId;
	}

	@Formula("(select u.account from user u where u.id=creater_id)")
	public String getCreaterPhone() {
		return createrPhone;
	}

	public void setCreaterPhone(String createrPhone) {
		this.createrPhone = createrPhone;
	}

	public void setNumId(String numId) {
		this.numId = numId;
	}

	@Formula("(select count(*) from user_club uc where uc.club_id=id)")
	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bkgimage == null) ? 0 : bkgimage.hashCode());
		result = prime * result + (int) (create_utc ^ (create_utc >>> 32));
		result = prime * result
				+ ((createrPhone == null) ? 0 : createrPhone.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((introduction == null) ? 0 : introduction.hashCode());
		result = prime * result
				+ ((join_permission == null) ? 0 : join_permission.hashCode());
		result = prime * result
				+ (int) (last_modify_utc ^ (last_modify_utc >>> 32));
		result = prime * result + max_member_cnt;
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result
				+ ((portrait == null) ? 0 : portrait.hashCode());
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
		ClubBean other = (ClubBean) obj;
		if (bkgimage == null) {
			if (other.bkgimage != null)
				return false;
		} else if (!bkgimage.equals(other.bkgimage))
			return false;
		if (create_utc != other.create_utc)
			return false;
		if (createrPhone == null) {
			if (other.createrPhone != null)
				return false;
		} else if (!createrPhone.equals(other.createrPhone))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (introduction == null) {
			if (other.introduction != null)
				return false;
		} else if (!introduction.equals(other.introduction))
			return false;
		if (join_permission == null) {
			if (other.join_permission != null)
				return false;
		} else if (!join_permission.equals(other.join_permission))
			return false;
		if (last_modify_utc != other.last_modify_utc)
			return false;
		if (max_member_cnt != other.max_member_cnt)
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (portrait == null) {
			if (other.portrait != null)
				return false;
		} else if (!portrait.equals(other.portrait))
			return false;
		return true;
	}

	public ClubBean() {
		
	}

	
	

}
