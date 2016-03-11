package cn.broadin.oms.model;

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

/**
 * 渠道注册用户数数据结构
 * @author xiejun
 */
@Entity
@Table(name = "channel_user")
public class ChannelUserBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "smarttv_user",nullable=true)
	private int smartTvUser = 0;
	@Column(name = "stb_user",nullable=true)
	private int stbUser = 0;
	@Column(name = "dvb_user",nullable=true)
	private int dvbUser = 0;
	@Column(name = "appstore_user",nullable=true)
	private int appstoreUser = 0;
	@Column(name = "other_user",nullable=true)
	private int otherUser = 0;
	@Column(name = "year",nullable=true)
	private int year = 0;
	@Column(name = "month",nullable=true)
	private int month = 0;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
	private ChannelBean channelUser;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSmartTvUser() {
		return smartTvUser;
	}
	public void setSmartTvUser(int smartTvUser) {
		this.smartTvUser = smartTvUser;
	}
	public int getStbUser() {
		return stbUser;
	}
	public void setStbUser(int stbUser) {
		this.stbUser = stbUser;
	}
	public int getDvbUser() {
		return dvbUser;
	}
	public void setDvbUser(int dvbUser) {
		this.dvbUser = dvbUser;
	}
	public int getAppstoreUser() {
		return appstoreUser;
	}
	public void setAppstoreUser(int appstoreUser) {
		this.appstoreUser = appstoreUser;
	}
	public int getOtherUser() {
		return otherUser;
	}
	public void setOtherUser(int otherUser) {
		this.otherUser = otherUser;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public ChannelBean getChannelUser() {
		return channelUser;
	}
	public void setChannelUser(ChannelBean channelUser) {
		this.channelUser = channelUser;
	}
	public ChannelUserBean() {
		super();
	}
	public ChannelUserBean(int smartTvUser, int stbUser, int dvbUser,
			int appstoreUser, int otherUser, int year, int month,
			ChannelBean channelUser) {
		super();
		this.smartTvUser = smartTvUser;
		this.stbUser = stbUser;
		this.dvbUser = dvbUser;
		this.appstoreUser = appstoreUser;
		this.otherUser = otherUser;
		this.year = year;
		this.month = month;
		this.channelUser = channelUser;
	}
	
	
}
