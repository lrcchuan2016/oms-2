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

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Formula;


/**
 * 用户实体类结构
 * @author huchanghuan
 *
 */

@Entity
@Table(name="user")
public class UserBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String account;				//帐号（手机号）
	private String password;
	private String email;
	private String nickname;			//呢称
	private String portrait;
	private String bkgimage;
	private String register_term_type;
	private String status;
	private String active_club_id;
	private String realname;
	private String addr;
	private String phone;
	private String country;
	private String mb_attr;
	private String mjx_id;		//美家秀ID
	private String qq_id;		//QQ
	private String weixin_id;	//微信
	private String weibo_id;	//微博
	private String qq_nick;
	private String weixin_nick;
	private String weibo_nick;
	private String postcode;
	private String birthday;
	private int online_termcnt;
	private int balances;
	private String level;
	private String friend_permission;
	private String friend_question;
	private String friend_answer;
	private String channel_id;
	private long register_utc;
	private long enable_utc;
	private long validto_utc;
	private long last_modify_utc;
	private String disable_reason;
	private Set<AlbumBean> alSet = new HashSet<AlbumBean>();
	private Set<UserClubBean> uSet = new HashSet<UserClubBean>();//user:user_club   1:n
	private Set<ClubBean> cSet = new HashSet<ClubBean>();		//用户创建的群集合
//	private Set<MediaBean> mSet = new HashSet<MediaBean>();     //用户与媒资的关系   1：n

	private int photoTag;	//相册册数
	private int commonNum;  //普通相册数量
	private int timeNum;	//时间轴相册数量
	private int themeNum;	//主题相册数量
	private Integer photoCount;	//相片数量
	private Integer videoCount; //视频数量
	private Integer audioCount; //音频数量
//	private Integer monthActivityNum;   //本月相册点击活跃数

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(nullable=false)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(nullable=false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable=false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(nullable=false)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(nullable=false)
	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	@Column(nullable=false)
	public String getBkgimage() {
		return bkgimage;
	}

	public void setBkgimage(String bkgimage) {
		this.bkgimage = bkgimage;
	}

	@Column(nullable=false)
	public String getRegister_term_type() {
		return register_term_type;
	}

	public void setRegister_term_type(String register_term_type) {
		this.register_term_type = register_term_type;
	}

	@Column(nullable=false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(nullable=true)
	public String getActive_club_id() {
		return active_club_id;
	}

	public void setActive_club_id(String active_club_id) {
		this.active_club_id = active_club_id;
	}

	@Column(nullable=false)
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Column(nullable=false)
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(nullable=false)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(nullable=false)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(nullable=false)
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(nullable=false)
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(nullable=false)
	public int getOnline_termcnt() {
		return online_termcnt;
	}

	public void setOnline_termcnt(int online_termcnt) {
		this.online_termcnt = online_termcnt;
	}

	@Column(nullable=false)
	public int getBalances() {
		return balances;
	}

	public void setBalances(int balances) {
		this.balances = balances;
	}

	@Column(nullable=false)
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Column(nullable=false)
	public String getFriend_permission() {
		return friend_permission;
	}

	public void setFriend_permission(String friend_permission) {
		this.friend_permission = friend_permission;
	}

	@Column(nullable=false)
	public String getFriend_question() {
		return friend_question;
	}

	public void setFriend_question(String friend_question) {
		this.friend_question = friend_question;
	}

	@Column(nullable=false)
	public String getFriend_answer() {
		return friend_answer;
	}

	public void setFriend_answer(String friend_answer) {
		this.friend_answer = friend_answer;
	}

	@Column(nullable=false)
	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	@Column(nullable=false)
	public long getRegister_utc() {
		return register_utc;
	}

	public void setRegister_utc(long register_utc) {
		this.register_utc = register_utc;
	}

	@Column(nullable=false)
	public long getEnable_utc() {
		return enable_utc;
	}

	public void setEnable_utc(long enable_utc) {
		this.enable_utc = enable_utc;
	}

	@Column(nullable=false)
	public long getValidto_utc() {
		return validto_utc;
	}

	public void setValidto_utc(long validto_utc) {
		this.validto_utc = validto_utc;
	}

	@Column(nullable=false)
	public long getLast_modify_utc() {
		return last_modify_utc;
	}

	public void setLast_modify_utc(long last_modify_utc) {
		this.last_modify_utc = last_modify_utc;
	}
	
	@Column(nullable=false)
	public String getMjx_id() {
		return mjx_id;
	}

	public void setMjx_id(String mjx_id) {
		this.mjx_id = mjx_id;
	}

	@Column(nullable=false)
	public String getQq_id() {
		return qq_id;
	}

	public void setQq_id(String qq_id) {
		this.qq_id = qq_id;
	}

	@Column(nullable=false)
	public String getWeixin_id() {
		return weixin_id;
	}

	public void setWeixin_id(String weixin_id) {
		this.weixin_id = weixin_id;
	}

	@Column(nullable=false)
	public String getWeibo_id() {
		return weibo_id;
	}

	public void setWeibo_id(String weibo_id) {
		this.weibo_id = weibo_id;
	}

	//级联更新删除用户相关的Album
	@OneToMany(mappedBy="creater",cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	@JSON(serialize=false)
	public Set<AlbumBean> getAlSet() {
			return alSet;
	}

	public void setAlSet(Set<AlbumBean> alSet) {
		this.alSet = alSet;
	}

	@Column(nullable=false)
	public String getDisable_reason() {
		return disable_reason;
	}

	public void setDisable_reason(String disable_reason) {
		this.disable_reason = disable_reason;
	}
	
	//删除/更新用户时，级联用户与群（家庭）的中间表实体  UserClubBean
	@OneToMany(mappedBy="user",cascade={CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE})
	@JSON(serialize=false)
	public Set<UserClubBean> getuSet() {
		return uSet;
	}

	public void setuSet(Set<UserClubBean> uSet) {
		this.uSet = uSet;
	}

	//删除/更新用户时，级联用户创建的群（家庭）（目前保留群）
	@OneToMany(mappedBy="creater",cascade={CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST})  
	@JSON(serialize=false)
	public Set<ClubBean> getcSet() {
		return cSet;
	}
//	@OneToMany(mappedBy="")
//	public Set<MediaBean> getmSet() {
//		return mSet;
//	}
//
//	public void setmSet(Set<MediaBean> mSet) {
//		this.mSet = mSet;
//	}

	public void setcSet(Set<ClubBean> cSet) {
		this.cSet = cSet;
	}
	@Formula("(select count(*) from album a where a.creater_id = id)")
	public int getPhotoTag() {
		return photoTag;
	}

	public void setPhotoTag(int photoTag) {
		this.photoTag = photoTag;
	}
	
	@Formula("(select count(*) from album a where a.creater_id = id and a.type = '0')")
	public int getCommonNum() {
		return commonNum;
	}

	public void setCommonNum(int commonNum) {
		this.commonNum = commonNum;
	}

	@Formula("(select count(*) from album a where a.creater_id = id and a.type = '1')")
	public int getTimeNum() {
		return timeNum;
	}

	public void setTimeNum(int timeNum) {
		this.timeNum = timeNum;
	}

	@Formula("(select count(*) from album a where a.creater_id = id and a.type = '2')")
	public int getThemeNum() {
		return themeNum;
	}

	public void setThemeNum(int themeNum) {
		this.themeNum = themeNum;
	}
	
	@Formula("(select sum(a.photo_count) from album a where a.creater_id = id)")
	public Integer getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(Integer photoCount) {
		this.photoCount = photoCount;
	}

	@Formula("(select sum(a.video_count) from album a where a.creater_id = id)")
	public Integer getVideoCount() {
		return videoCount;
	}

	public void setVideoCount(Integer videoCount) {
		this.videoCount = videoCount;
	}

	@Formula("(select count(*) from media m where m.type= 2 and m.creater_id = id)")
	public Integer getAudioCount() {
		return audioCount;
	}

	public void setAudioCount(Integer audioCount) {
		this.audioCount = audioCount;
	}
	
	//月活跃数(联合collect数据库)
//	@Formula("(select count(*) from collect.album_click_record ca where ca.user_id = id and from_unixtime(ca.click_utc/1000)>=concat(year(curdate()),'-',month(curdate()),'-',1))")
//	public Integer getMonthActivityNum() {
//		return monthActivityNum;
//	}
//	
//	public void setMonthActivityNum(Integer monthActivityNum) {
//		this.monthActivityNum = monthActivityNum;
//	}
	
	@Column(nullable=false)
	public String getMb_attr() {
		return mb_attr;
	}

	public void setMb_attr(String mb_attr) {
		this.mb_attr = mb_attr;
	}

	public String getQq_nick() {
		return qq_nick;
	}

	public void setQq_nick(String qq_nick) {
		this.qq_nick = qq_nick;
	}

	public String getWeixin_nick() {
		return weixin_nick;
	}

	public void setWeixin_nick(String weixin_nick) {
		this.weixin_nick = weixin_nick;
	}

	public String getWeibo_nick() {
		return weibo_nick;
	}

	public void setWeibo_nick(String weibo_nick) {
		this.weibo_nick = weibo_nick;
	}

	public UserBean() {
		
	}

	public UserBean(String id, String account, String password, String email,
			String nickname) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
	}

	public UserBean(String id, String account, String password, String email,
			String nickname, String portrait, String bkgimage,
			String register_term_type, String status, String active_club_id,
			String realname, String addr, String phone, String country,
			String postcode, String birthday, int online_termcnt, int balances,
			String level, String friend_permission, String friend_question,
			String friend_answer, String channel_id, long register_utc,
			long enable_utc, long validto_utc, long last_modify_utc,
			String disable_reason) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
		this.portrait = portrait;
		this.bkgimage = bkgimage;
		this.register_term_type = register_term_type;
		this.status = status;
		this.active_club_id = active_club_id;
		this.realname = realname;
		this.addr = addr;
		this.phone = phone;
		this.country = country;
		this.postcode = postcode;
		this.birthday = birthday;
		this.online_termcnt = online_termcnt;
		this.balances = balances;
		this.level = level;
		this.friend_permission = friend_permission;
		this.friend_question = friend_question;
		this.friend_answer = friend_answer;
		this.channel_id = channel_id;
		this.register_utc = register_utc;
		this.enable_utc = enable_utc;
		this.validto_utc = validto_utc;
		this.last_modify_utc = last_modify_utc;
		this.disable_reason = disable_reason;
	}

	public UserBean(String id, String account, String password, String email,
			String nickname, String portrait, String bkgimage,
			String register_term_type, String status, String active_club_id,
			String realname, String addr, String phone, String country,
			String mb_attr, String mjx_id, String qq_id, String weixin_id,
			String weibo_id, String qq_nick, String weixin_nick,
			String weibo_nick, String postcode, String birthday,
			int online_termcnt, int balances, String level,
			String friend_permission, String friend_question,
			String friend_answer, String channel_id, long register_utc,
			long enable_utc, long validto_utc, long last_modify_utc,
			String disable_reason) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
		this.portrait = portrait;
		this.bkgimage = bkgimage;
		this.register_term_type = register_term_type;
		this.status = status;
		this.active_club_id = active_club_id;
		this.realname = realname;
		this.addr = addr;
		this.phone = phone;
		this.country = country;
		this.mb_attr = mb_attr;
		this.mjx_id = mjx_id;
		this.qq_id = qq_id;
		this.weixin_id = weixin_id;
		this.weibo_id = weibo_id;
		this.qq_nick = qq_nick;
		this.weixin_nick = weixin_nick;
		this.weibo_nick = weibo_nick;
		this.postcode = postcode;
		this.birthday = birthday;
		this.online_termcnt = online_termcnt;
		this.balances = balances;
		this.level = level;
		this.friend_permission = friend_permission;
		this.friend_question = friend_question;
		this.friend_answer = friend_answer;
		this.channel_id = channel_id;
		this.register_utc = register_utc;
		this.enable_utc = enable_utc;
		this.validto_utc = validto_utc;
		this.last_modify_utc = last_modify_utc;
		this.disable_reason = disable_reason;
	}

	public UserBean(String id, String account, String nickname, String portrait) {
		super();
		this.id = id;
		this.account = account;
		this.nickname = nickname;
		this.portrait = portrait;
	}

	
}
