package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

/**
 * 订单实体类
 * @author huchanghuan
 *
 */
@Entity
@Table(name="transaction")
public class TransactionBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@Column(nullable=false,name="product_id")
	private String productId;
	@Column(nullable=false,name="user_id")
	private String userId;
	@Column(nullable=false,name="content")
	private String content;
	@Column(nullable=false,name="product_count")
	private int productCount = 1;
	@Column(nullable=false,name="product_name")
	private String productName;
	@Column(nullable=false,name="product_thumb")
	private String productThumb;
	@Column(nullable=false,name="total_fee")
  	private int totalFee;
	@Column(nullable=false,name="start_utc")
	private long startUtc;
	@Column(nullable=false,name="end_utc")
	private long endUtc;
	@Column(nullable=false,name="pay_type")
	private int payType;
	//(0-NOTPAY未支付 1-SUCCESS支付成功 2-REFUND转入退款 3-CLOSED已关闭 4-REVOKED已撤销 5-USERPAYING用户支付中 6-PAYERROR支付失败)
	@Column(nullable=false,name="trade_state")
	private int tradeState;
	@Column(nullable=false,name="trade_state_desc")
	private String tradeStateDesc;
	@Column(nullable=false,name="recv_addr")
	private String recvAddr;
	@Column(nullable=false,name="postcode")
	private String postcode;
	@Column(nullable=false,name="recv_name")
	private String recvName;
	@Column(nullable=false,name="recv_phone")
	private String recvPhone;
	@Column(nullable=false,name="addr_id")
	private String addrId;
	@Column(nullable=false,name="user_remark")
	private String userRemark;
	@Column(nullable=false,name="logistics")
	private String logistics;
	@Column(nullable=false,name="logst_number")
	private String logstNumber;
	@Column(nullable=false,name="deliver_state")
	private int deliverState;
	@Column(nullable=false,name="deliver_utc")
	private long deliverUtc;
	@Column(nullable=false,name="recv_utc")
	private long recvUtc;
	@Column(nullable=false,name="ret_utc")
	private long retUtc; 
	@Column(nullable=false,name="ret_finish_utc")
	private long retFinishUtc;
	@Column(nullable=false,name="ret_reason")
	private String retReason;
	@Column(nullable=false,name="remark")
	private String remark;
	
	@Formula("(select u.account from user u where u.id = user_id)")
	private String userPhone;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductThumb() {
		return productThumb;
	}
	public void setProductThumb(String productThumb) {
		this.productThumb = productThumb;
	}
	public int getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
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
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public int getTradeState() {
		return tradeState;
	}
	public void setTradeState(int tradeState) {
		this.tradeState = tradeState;
	}
	public String getTradeStateDesc() {
		return tradeStateDesc;
	}
	public void setTradeStateDesc(String tradeStateDesc) {
		this.tradeStateDesc = tradeStateDesc;
	}
	public String getRecvAddr() {
		return recvAddr;
	}
	public void setRecvAddr(String recvAddr) {
		this.recvAddr = recvAddr;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getRecvName() {
		return recvName;
	}
	public void setRecvName(String recvName) {
		this.recvName = recvName;
	}
	public String getUserRemark() {
		return userRemark;
	}
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}
	public String getLogistics() {
		return logistics;
	}
	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}
	public String getLogstNumber() {
		return logstNumber;
	}
	public void setLogstNumber(String logstNumber) {
		this.logstNumber = logstNumber;
	}
	public int getDeliverState() {
		return deliverState;
	}
	public void setDeliverState(int deliverState) {
		this.deliverState = deliverState;
	}
	public long getDeliverUtc() {
		return deliverUtc;
	}
	public void setDeliverUtc(long deliverUtc) {
		this.deliverUtc = deliverUtc;
	}
	public long getRecvUtc() {
		return recvUtc;
	}
	public void setRecvUtc(long recvUtc) {
		this.recvUtc = recvUtc;
	}
	public long getRetUtc() {
		return retUtc;
	}
	public void setRetUtc(long retUtc) {
		this.retUtc = retUtc;
	}
	public long getRetFinishUtc() {
		return retFinishUtc;
	}
	public void setRetFinishUtc(long retFinishUtc) {
		this.retFinishUtc = retFinishUtc;
	}
	public String getRetReason() {
		return retReason;
	}
	public void setRetReason(String retReason) {
		this.retReason = retReason;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddrId() {
		return addrId;
	}
	public void setAddrId(String addrId) {
		this.addrId = addrId;
	}
	public String getRecvPhone() {
		return recvPhone;
	}
	public void setRecvPhone(String recvPhone) {
		this.recvPhone = recvPhone;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
}
