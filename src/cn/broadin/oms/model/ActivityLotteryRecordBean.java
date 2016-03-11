package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

/**
 * 抽奖记录表
 * @author huchanghuan
 *
 */
@Entity
@Table(name="activity_lottery_record")
public class ActivityLotteryRecordBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY)
	@JoinColumn(name="club_id")
	private ClubBean club;
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserBean user;
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY)
	@JoinColumn(name="activity_id")
	private AdResourceBean activity;
	@Column(name="lottery_number",nullable=false)
	private int lotteryNumber;
	@Column(name="is_lottery_now",nullable=false)
	private int isLotteryNow;
	@Column(name="is_rotate",nullable=false)
	private int isRotate;
	@Column(name="terminal_addr",nullable=false)
	private String terminalAddr;
	@Column(name="start_utc",nullable=false)
	private long startUtc;
	@Column(name="end_utc",nullable=false)
	private long endUtc;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@JSON(serialize=false)
	public ClubBean getClub() {
		return club;
	}
	public void setClub(ClubBean club) {
		this.club = club;
	}
	
	@JSON(serialize=false)
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	
	@JSON(serialize=false)
	public AdResourceBean getActivity() {
		return activity;
	}
	public void setActivity(AdResourceBean activity) {
		this.activity = activity;
	}
	public int getLotteryNumber() {
		return lotteryNumber;
	}
	public void setLotteryNumber(int lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}
	public int getIsLotteryNow() {
		return isLotteryNow;
	}
	public void setIsLotteryNow(int isLotteryNow) {
		this.isLotteryNow = isLotteryNow;
	}
	
	public int getIsRotate() {
		return isRotate;
	}
	public void setIsRotate(int isRotate) {
		this.isRotate = isRotate;
	}
	public long getStartUtc() {
		return startUtc;
	}
	public void setStartUtc(long startUtc) {
		this.startUtc = startUtc;
	}
	public long getEndUtc() {
		return endUtc;
	}
	public void setEndUtc(long endUtc) {
		this.endUtc = endUtc;
	}
	public String getTerminalAddr() {
		return terminalAddr;
	}
	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}
	public ActivityLotteryRecordBean(String id, ClubBean club, UserBean user,
			AdResourceBean activity, int lotteryNumber, int isLotteryNow,
			int isRotate, long startUtc, long endUtc,String terminalAddr) {
		super();
		this.id = id;
		this.club = club;
		this.user = user;
		this.activity = activity;
		this.lotteryNumber = lotteryNumber;
		this.isLotteryNow = isLotteryNow;
		this.isRotate = isRotate;
		this.startUtc = startUtc;
		this.endUtc = endUtc;
		this.terminalAddr = terminalAddr;
	}
	public ActivityLotteryRecordBean() {}
	
	
	
	
}
