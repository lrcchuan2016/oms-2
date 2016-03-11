package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.ImageBean;

public interface ImageService {
	
	/**
	 * 通过ID获取Image对象
	 * @param id
	 * @return
	 */
	public ImageBean getImageById(String id);
	
	/**
	 * 获取所有的图片对象
	 * @return
	 */
	public List<ImageBean> getAll();
	
}
