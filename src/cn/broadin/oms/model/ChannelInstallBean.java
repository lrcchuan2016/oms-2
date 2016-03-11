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
 * 渠道新增安装数 数据结构
 * @author xiejun
 *
 */
@Entity
@Table(name = "channel_install")
public class ChannelInstallBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "smarttv_install",nullable=true)
	private int smartTvInstall = 0;
	@Column(name = "stb_install",nullable=true)
	private int stbInstall = 0;
	@Column(name = "dvb_install",nullable=true)
	private int dvbInstall = 0;
	@Column(name = "appstore_install",nullable=true)
	private int appstoreInstall = 0;
	@Column(name = "other_install",nullable=true)
	private int otherInstall = 0;
	@Column(name = "year",nullable=true)
	private int year = 0;
	@Column(name = "month",nullable=true)
	private int month = 0;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
	private ChannelBean channelInstall;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSmartTvInstall() {
		return smartTvInstall;
	}
	public void setSmartTvInstall(int smartTvInstall) {
		this.smartTvInstall = smartTvInstall;
	}
	public int getStbInstall() {
		return stbInstall;
	}
	public void setStbInstall(int stbInstall) {
		this.stbInstall = stbInstall;
	}
	public int getDvbInstall() {
		return dvbInstall;
	}
	public void setDvbInstall(int dvbInstall) {
		this.dvbInstall = dvbInstall;
	}
	public int getAppstoreInstall() {
		return appstoreInstall;
	}
	public void setAppstoreInstall(int appstoreInstall) {
		this.appstoreInstall = appstoreInstall;
	}
	public int getOtherInstall() {
		return otherInstall;
	}
	public void setOtherInstall(int otherInstall) {
		this.otherInstall = otherInstall;
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
	public ChannelBean getChannelInstall() {
		return channelInstall;
	}
	public void setChannelInstall(ChannelBean channelInstall) {
		this.channelInstall = channelInstall;
	}
	
	public ChannelInstallBean() {
		super();
	}
	public ChannelInstallBean(int smartTvInstall, int stbInstall,
			int dvbInstall, int appstoreInstall, int otherInstall, int year,
			int month, ChannelBean channelInstall) {
		super();
		this.smartTvInstall = smartTvInstall;
		this.stbInstall = stbInstall;
		this.dvbInstall = dvbInstall;
		this.appstoreInstall = appstoreInstall;
		this.otherInstall = otherInstall;
		this.year = year;
		this.month = month;
		this.channelInstall = channelInstall;
	}
	
}
