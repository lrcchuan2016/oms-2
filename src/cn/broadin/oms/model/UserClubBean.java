package cn.broadin.oms.model;

import java.io.Serializable;

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

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Formula;

/**
 * 用户和群的中间表实体类
 * @author huchanghuan
 *
 */
@Entity
@Table(name = "user_club")
public class UserClubBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idx;
	private ClubBean club;
	private UserBean user;
	private String club_user_status;
	private String user_nickname;
	private long join_utc;
	private long last_modify_utc;
	private String creater;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	@ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="club_id")
	@JSON(serialize=false)
	public ClubBean getClub() {
		return club;
	}

	public void setClub(ClubBean club) {
		this.club = club;
	}

	@ManyToOne(fetch=FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	@JSON(serialize=false)
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	@Column(nullable=false)
	public String getClub_user_status() {
		return club_user_status;
	}

	public void setClub_user_status(String club_user_status) {
		this.club_user_status = club_user_status;
	}

	@Column(nullable=false)
	public String getUser_nickname() {
		return user_nickname;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	@Column(nullable=false)
	public long getJoin_utc() {
		return join_utc;
	}

	public void setJoin_utc(long join_utc) {
		this.join_utc = join_utc;
	}

	@Column(nullable=false)
	public long getLast_modify_utc() {
		return last_modify_utc;
	}

	public void setLast_modify_utc(long last_modify_utc) {
		this.last_modify_utc = last_modify_utc;
	}
	
	@Formula("(select u.nickname from user u,club c where u.id=c.creater_id and c.id=club_id)")
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((club_user_status == null) ? 0 : club_user_status.hashCode());
		result = prime * result + idx;
		result = prime * result + (int) (join_utc ^ (join_utc >>> 32));
		result = prime * result
				+ (int) (last_modify_utc ^ (last_modify_utc >>> 32));
		result = prime * result
				+ ((user_nickname == null) ? 0 : user_nickname.hashCode());
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
		UserClubBean other = (UserClubBean) obj;
		if (club_user_status == null) {
			if (other.club_user_status != null)
				return false;
		} else if (!club_user_status.equals(other.club_user_status))
			return false;
		if (idx != other.idx)
			return false;
		if (join_utc != other.join_utc)
			return false;
		if (last_modify_utc != other.last_modify_utc)
			return false;
		if (user_nickname == null) {
			if (other.user_nickname != null)
				return false;
		} else if (!user_nickname.equals(other.user_nickname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserClubBean [idx=" + idx + ", club=" + club + ", user=" + user
				+ ", club_user_status=" + club_user_status + ", user_nickname="
				+ user_nickname + ", join_utc=" + join_utc
				+ ", last_modify_utc=" + last_modify_utc + ", creater="
				+ creater + "]";
	}
	
	

}
