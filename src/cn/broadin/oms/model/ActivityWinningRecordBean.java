package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;



/**
 * 中奖记录表
 * @author huchanghuan
 *
 */
@Entity
@Table(name="activity_winning_record")
public class ActivityWinningRecordBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@Column(name="name",nullable=false)
	private String name;
	@Column(name="content",nullable=false)
	private String content;
	@Column(name="lottery_id",nullable=false)
	private String lotteryId;
	@Column(name="gift_id",nullable=false)
	private String giftId;
	@Column(name="receiver",nullable=false)
	private String receiver;
	@Column(name="receiver_phone",nullable=false)
	private String receiverPhone;
	@Column(name="addr_id",nullable=false)
	private int addrId;
	@Column(name="receiver_addr",nullable=false)
	private String receiverAddr;
	@Column(name="is_deliver",nullable=false)
	private int isDeliver;
	@Column(name="logistics",nullable=false)
	private String logistics;
	@Column(name="logist_number",nullable=false)
	private String logistNumber;
	@Column(name="winning_utc",nullable=false)
	private long winningUtc;
	@Column(name="deliver_utc",nullable=false)
	private long deliverUtc;
	@Column(name="accept_prize_utc",nullable=false)
	private long acceptPrizeUtc;
	
	@Formula("(select u.account from user u,activity_lottery_record alr where u.id = alr.user_id and alr.id = lottery_id)")
	private String account;
	@Formula("(select u.nickname from user u,activity_lottery_record alr where u.id = alr.user_id and alr.id = lottery_id)")
	private String nickname;
	@Formula("(select c.num_id from club c,activity_lottery_record alr where c.id = alr.club_id and alr.id = lottery_id)")
	private String clubNum;
	@Formula("(select uc.user_nickname from user_club uc,activity_lottery_record alr where alr.club_id = uc.club_id and alr.user_id = uc.user_id and alr.id = lottery_id)")
	private String clubNickname;
	@Formula("(select g.url from activity_gift g where g.id = gift_id)")
	private String giftUrl;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	public String getGiftId() {
		return giftId;
	}
	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverAddr() {
		return receiverAddr;
	}
	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}
	public int getIsDeliver() {
		return isDeliver;
	}
	public void setIsDeliver(int isDeliver) {
		this.isDeliver = isDeliver;
	}
	public long getWinningUtc() {
		return winningUtc;
	}
	public void setWinningUtc(long winningUtc) {
		this.winningUtc = winningUtc;
	}
	public long getDeliverUtc() {
		return deliverUtc;
	}
	public void setDeliverUtc(long deliverUtc) {
		this.deliverUtc = deliverUtc;
	}
	
	public int getAddrId() {
		return addrId;
	}
	public void setAddrId(int addrId) {
		this.addrId = addrId;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getClubNum() {
		return clubNum;
	}
	public void setClubNum(String clubNum) {
		this.clubNum = clubNum;
	}
	
	public String getLogistics() {
		return logistics;
	}
	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}
	public String getLogistNumber() {
		return logistNumber;
	}
	public void setLogistNumber(String logistNumber) {
		this.logistNumber = logistNumber;
	}
	
	public long getAcceptPrizeUtc() {
		return acceptPrizeUtc;
	}
	public void setAcceptPrizeUtc(long acceptPrizeUtc) {
		this.acceptPrizeUtc = acceptPrizeUtc;
	}

	public String getClubNickname() {
		return clubNickname;
	}
	public void setClubNickname(String clubNickname) {
		this.clubNickname = clubNickname;
	}
	public String getGiftUrl() {
		return giftUrl;
	}
	public void setGiftUrl(String giftUrl) {
		this.giftUrl = giftUrl;
	}
	public ActivityWinningRecordBean() {

	}
	
	public ActivityWinningRecordBean(String id, String name, String content,
			String lotteryId, String giftId, String receiver,
			String receiverPhone, int addrId,String receiverAddr, int isDeliver,
			long winningUtc, long deliverUtc,String logistics,String logistNumber,long acceptPrizeUtc) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.lotteryId = lotteryId;
		this.giftId = giftId;
		this.receiver = receiver;
		this.receiverPhone = receiverPhone;
		this.receiverAddr = receiverAddr;
		this.isDeliver = isDeliver;
		this.winningUtc = winningUtc;
		this.addrId = addrId;
		this.deliverUtc = deliverUtc;
		this.logistics = logistics;
		this.logistNumber = logistNumber;
		this.acceptPrizeUtc = acceptPrizeUtc;
	}
	
	
}
