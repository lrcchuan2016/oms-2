package cn.broadin.oms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
 * 相册属性数据结构
 * 
 * @author huchanghuan
 * 
 */
@Entity
@Table(name = "album")
public class AlbumBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "introduction", nullable = false)
	private String introduction;
	@Column(name="type",nullable=false)
	private String type;
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="club_id",nullable=false)
	private ClubBean club;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
	@JoinColumn(name="template_id",nullable=false)
	private TemplateBean template;     //相册主题模版
	@Column(name="cover",nullable=false)
	private String cover;				//相册封面图URL
	@Column(name="sound_url",nullable=false)
	private String sound_url;			//相册音频介绍文件
	@Column(name="status",nullable=false)
	private String status;				//状态
	@Column(name="permission",nullable=false)
	private String permission;			//相册权限
	@Column(name="photo_count",nullable=false)
	private int photo_count;
	@Column(name="video_count",nullable=false)
	private int video_count;
	@Column(name="password",nullable=false)
	private String password;
	@Column(name="question",nullable=false)
	private String question;
	@Column(name="answer",nullable=false)
	private String answer;
	@Column(name="remark",nullable=false)
	private String remark;
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="creater_id")
	private UserBean creater;			//创建者
	@Formula("(select u.account from user u where u.id=creater_id)")
	private String account;
	@Column(name="create_utc",nullable=false)
	private long create_utc;
	@Column(name="last_modify_utc",nullable=false)
	private long last_modify_utc;
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="last_mod_userid")
	private UserBean last_mod_user;		//最后修改者
	@Column(name="last_upload_utc",nullable=false)
	private long last_upload_utc;
	@OneToMany(mappedBy="album",cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	private List<AlbumMediaBean> aMedias = new ArrayList<AlbumMediaBean>();	//album:album_media   1:n

	@Formula("(select ar.weight from album_recommend ar where ar.album_id = id)")
	private Integer weight;
	
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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JSON(serialize=false)
	public TemplateBean getTemplate() {
		return template;
	}

	public void setTemplate(TemplateBean template) {
		this.template = template;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getSound_url() {
		return sound_url;
	}

	public void setSound_url(String sound_url) {
		this.sound_url = sound_url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int getPhoto_count() {
		return photo_count;
	}

	public void setPhoto_count(int photo_count) {
		this.photo_count = photo_count;
	}

	public int getVideo_count() {
		return video_count;
	}

	public void setVideo_count(int video_count) {
		this.video_count = video_count;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JSON(serialize=false)
	public UserBean getCreater() {
		return creater;
	}

	public void setCreater(UserBean creater) {
		this.creater = creater;
	}

	public long getCreate_utc() {
		return create_utc;
	}

	public void setCreate_utc(long create_utc) {
		this.create_utc = create_utc;
	}

	public long getLast_modify_utc() {
		return last_modify_utc;
	}

	public void setLast_modify_utc(long last_modify_utc) {
		this.last_modify_utc = last_modify_utc;
	}

	@JSON(serialize=false)
	public UserBean getLast_mod_user() {
		return last_mod_user;
	}

	public void setLast_mod_user(UserBean last_mod_user) {
		this.last_mod_user = last_mod_user;
	}

	public long getLast_upload_utc() {
		return last_upload_utc;
	}

	public void setLast_upload_utc(long last_upload_utc) {
		this.last_upload_utc = last_upload_utc;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	@JSON(serialize = false)
	public ClubBean getClub() {
		return club;
	}

	public void setClub(ClubBean club) {
		this.club = club;
	}
	
	@JSON(serialize = false)
	public List<AlbumMediaBean> getaMedias() {
		return aMedias;
	}

	public void setaMedias(List<AlbumMediaBean> aMedias) {
		this.aMedias = aMedias;
	}
	
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public AlbumBean() {}

	public AlbumBean(String id, String name, String introduction, String type,
			TemplateBean template, String cover, String sound_url,
			String status, String permission, int photo_count, int video_count,
			String password, String question, String answer, String remark,
			UserBean creater, long create_utc, long last_modify_utc,
			UserBean last_mod_user, long last_upload_utc,ClubBean club) {
		super();
		this.id = id;
		this.name = name;
		this.introduction = introduction;
		this.type = type;
		this.club = club;
		this.template = template;
		this.cover = cover;
		this.sound_url = sound_url;
		this.status = status;
		this.permission = permission;
		this.photo_count = photo_count;
		this.video_count = video_count;
		this.password = password;
		this.question = question;
		this.answer = answer;
		this.remark = remark;
		this.creater = creater;
		this.create_utc = create_utc;
		this.last_modify_utc = last_modify_utc;
		this.last_mod_user = last_mod_user;
		this.last_upload_utc = last_upload_utc;
	}



}
