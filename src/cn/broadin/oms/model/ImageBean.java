package cn.broadin.oms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.broadin.oms.util.Tools;

/**
 * 图片数据结构类
 * 
 * @author xiejun
 */
@Entity
@Table(name = "image")
public class ImageBean {
	
	@Id
	private String id;    // 主键ID
	@Column(name = "image")
	private byte[] image; // 图片数据流
	
	public ImageBean() {};
	
	/**
	 * 将上传图片文件转换为ImageBean对象
	 * 
	 * @param file
	 */
	public ImageBean(byte[] data) {
		super();
		this.image = data;
		this.id = Tools.getMD5(this.image, this.image.length); // 对文件内容进行MD5处理后的字符串作为ID
	};
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
}
