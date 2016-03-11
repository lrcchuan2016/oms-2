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
 * 渠道覆盖数表数据结构
 * @author xiejun
 */
@Entity
@Table(name = "channel_cover")
public class ChannelCoverBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "smarttv_cover",nullable=true)
	private int smartTvCover = 0;
	@Column(name = "stb_cover",nullable=true)
	private int stbCover = 0;
	@Column(name = "dvb_cover",nullable=true)
	private int dvbCover = 0;
	@Column(name = "appstore_cover",nullable=true)
	private int appstoreCover = 0;
	@Column(name="other_cover",nullable=true)
	private int otherCover = 0;
	@Column(name = "year",nullable=true)
	private int year = 0;
	@Column(name = "month",nullable=true)
	private int month = 0;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
	private ChannelBean channelCover;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSmartTvCover() {
		return smartTvCover;
	}
	public void setSmartTvCover(int smartTvCover) {
		this.smartTvCover = smartTvCover;
	}
	public int getStbCover() {
		return stbCover;
	}
	public void setStbCover(int stbCover) {
		this.stbCover = stbCover;
	}
	public int getDvbCover() {
		return dvbCover;
	}
	public void setDvbCover(int dvbCover) {
		this.dvbCover = dvbCover;
	}
	public int getAppstoreCover() {
		return appstoreCover;
	}
	public void setAppstoreCover(int appstoreCover) {
		this.appstoreCover = appstoreCover;
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
	public ChannelBean getChannelCover() {
		return channelCover;
	}
	public void setChannelCover(ChannelBean channelCover) {
		this.channelCover = channelCover;
	}
	public int getOtherCover() {
		return otherCover;
	}
	public void setOtherCover(int otherCover) {
		this.otherCover = otherCover;
	}
	public ChannelCoverBean() {
		super();
	}
	public ChannelCoverBean(int smartTvCover, int stbCover, int dvbCover,
			int appstoreCover, int otherCover, int year, int month,
			ChannelBean channelCover) {
		super();
		this.smartTvCover = smartTvCover;
		this.stbCover = stbCover;
		this.dvbCover = dvbCover;
		this.appstoreCover = appstoreCover;
		this.otherCover = otherCover;
		this.year = year;
		this.month = month;
		this.channelCover = channelCover;
	}
	
}
