package cn.broadin.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.ImageBean;
import cn.broadin.oms.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Resource
	private DAO dao;
	
	@Override
	public ImageBean getImageById(String id) {
		return (ImageBean) dao.selectById(ImageBean.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ImageBean> getAll() {
		Object ob = dao.select(ImageBean.class);
		if (ob != null) {
			return (List<ImageBean>) ob;
		} else {
			return null;
		}
	}
	
}
