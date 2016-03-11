package cn.broadin.oms.model;

import java.util.ArrayList;
import java.util.List;


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
 * 渠道数据结构类
 * @author xiejun
 */
@Entity
@Table(name = "channel")
public class ChannelBean {
	@Id
	private String id; // 渠道ID
	@Column(name = "name",nullable=false)
	private String name;
	@Column(name = "description",nullable = true)
	private String description; // 渠道描述
	@Column(name = "company_name",nullable=false)
	private String companyName;
	@Column(name = "address",nullable=false)
	private String address;
	@Column(name = "logo_key",nullable=false)
	private String logoKey;
	@Column(name = "logo_url",nullable=true)
	private String logoUrl;
	@Column(name = "status",nullable = false)
	private int status = 0;
	@Column(name="creater",nullable=false)
	private String creater;
	@Column(name="create_utc",nullable=false)
	private long createUtc;
	@OneToMany(mappedBy = "channelContact",cascade={CascadeType.ALL})
	private List<ChannelContactsBean> contacts = new ArrayList<ChannelContactsBean>();
	@OneToMany(mappedBy = "channelCover",cascade={CascadeType.ALL})
	private List<ChannelCoverBean> covers = new ArrayList<ChannelCoverBean>();
	@OneToMany(mappedBy = "channelUser",cascade={CascadeType.ALL})
	private List<ChannelUserBean> users = new ArrayList<ChannelUserBean>();
	@OneToMany(mappedBy = "channelInstall",cascade={CascadeType.ALL})
	private List<ChannelInstallBean> installs = new ArrayList<ChannelInstallBean>();
	@Formula("(select sum(c.smarttv_cover+c.stb_cover+c.dvb_cover+c.appstore_cover+c.other_cover) from channel_cover c group by c.channel_id having c.channel_id = id)")
	private Integer coverNum;	
	@Formula("(select sum(c.smarttv_user+c.stb_user+c.dvb_user+c.appstore_user+c.other_user) from channel_user c group by c.channel_id having c.channel_id = id)")
	private Integer registerNum;
	@Formula("(select sum(c.smarttv_install+c.stb_install+c.dvb_install+c.appstore_install+c.other_install) from channel_install c group by c.channel_id having c.channel_id = id)")
	private Integer installNum;
	//@Formula("(select sum(c.smarttv_user+c.stb_user+c.dvb_user+c.appstore_user+c.other_user) from channel_user c where c.month<MONTH(now()) group by c.channel_id having c.channel_id = id)")
	@Formula("(select (b.smarttv_user+b.stb_user+b.dvb_user+b.appstore_user+b.other_user) from channel_user b where b.channel_id = id and b.month = MONTH(now())-1)")
	private Integer lastMonthRegNum;   //上月新增
	@Formula("(select (b.smarttv_user+b.stb_user+b.dvb_user+b.appstore_user+b.other_user) from channel_user b where b.channel_id = id and b.month = MONTH(now()))")
	private Integer monthRegNum;    //本月新增
	
	@SuppressWarnings("unused")
	@Transient
	private double installRate;    //安装率
	@SuppressWarnings("unused")
	@Transient
	private double conversionRate; //转换率
	@SuppressWarnings("unused")
	@Transient
	private double growthRate;     //增长率
	
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLogoKey() {
		return logoKey;
	}
	public void setLogoKey(String logoKey) {
		this.logoKey = logoKey;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@JSON(serialize = false)
	public List<ChannelContactsBean> getContacts() {
		return contacts;
	}
	public void setContacts(List<ChannelContactsBean> contacts) {
		this.contacts = contacts;
	}
	@JSON(serialize = false)
	public List<ChannelCoverBean> getCovers() {
		return covers;
	}
	public void setCovers(List<ChannelCoverBean> covers) {
		this.covers = covers;
	}
	@JSON(serialize = false)
	public List<ChannelUserBean> getUsers() {
		return users;
	}
	public void setUsers(List<ChannelUserBean> users) {
		this.users = users;
	}
	@JSON(serialize = false)
	public List<ChannelInstallBean> getInstalls() {
		return installs;
	}
	public void setInstalls(List<ChannelInstallBean> installs) {
		this.installs = installs;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public long getCreateUtc() {
		return createUtc;
	}
	public void setCreateUtc(long createUtc) {
		this.createUtc = createUtc;
	}
	public Integer getCoverNum() {
		return coverNum;
	}
	public void setCoverNum(Integer coverNum) {
		this.coverNum = coverNum;
	}
	public Integer getRegisterNum() {
		return registerNum;
	}
	public void setRegisterNum(Integer registerNum) {
		this.registerNum = registerNum;
	}
	public Integer getInstallNum() {
		return installNum;
	}
	public void setInstallNum(Integer installNum) {
		this.installNum = installNum;
	}
	public Integer getLastMonthRegNum() {
		return lastMonthRegNum;
	}
	public void setLastMonthRegNum(Integer lastMonthRegNum) {
		this.lastMonthRegNum = lastMonthRegNum;
	}
	public Integer getMonthRegNum() {
		return monthRegNum;
	}
	public void setMonthRegNum(Integer monthRegNum) {
		this.monthRegNum = monthRegNum;
	}
	public double getInstallRate() {
		if(null == coverNum || 0 == coverNum || null == installNum) return 0;
		return (installNum*1.0)/coverNum;
	}
	public void setInstallRate(double installRate) {
		this.installRate = installRate;
	}
	public double getConversionRate() {
		if(null == installNum || 0 == installNum || null == registerNum) return 0;
		return (registerNum*1.0)/installNum;
	}
	public void setConversionRate(double conversionRate) {
		this.conversionRate = conversionRate;
	}
	public double getGrowthRate() {
		if(null == monthRegNum) monthRegNum = 0;
		if(null == lastMonthRegNum) lastMonthRegNum = 0;
		if(lastMonthRegNum == 0 && monthRegNum != 0){
			return 1;
		}else if(monthRegNum == 0){
			return 0;
		}else{
			return (monthRegNum-lastMonthRegNum)/(lastMonthRegNum*1.0);
		}
	}
	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}
	

}
