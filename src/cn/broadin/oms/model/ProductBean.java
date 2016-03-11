package cn.broadin.oms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品实体类
 * @author huchanghuan
 *
 */
@Entity
@Table(name="product")
public class ProductBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Column(nullable=false,name="category_id")
	private int categoryId;
	@Column(nullable=false,name="name")
	private String name;
	@Column(nullable=false,name="introduction")
	private String introduction;
	@Column(nullable=false,name="price")
	private int price;     //价格以分为单位
	@Column(nullable=false,name="integral")
	private int integral;
	@Column(nullable=false,name="discount")
	private int discount;  //折扣
	@Column(nullable=false,name="thumbnail")
	private String thumbnail;
	@Column(nullable=false,name="pictures")
	private String pictures;
	@Column(nullable=false,name="startsale_Utc")
	private long startSaleUtc;
	@Column(nullable=false,name="endsale_utc")
	private long endSaleUtc;
	@Column(nullable=false,name="count")
	private int count;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	public long getStartSaleUtc() {
		return startSaleUtc;
	}
	public void setStartSaleUtc(long startSaleUtc) {
		this.startSaleUtc = startSaleUtc;
	}
	public long getEndSaleUtc() {
		return endSaleUtc;
	}
	public void setEndSaleUtc(long endSaleUtc) {
		this.endSaleUtc = endSaleUtc;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	
	
}
