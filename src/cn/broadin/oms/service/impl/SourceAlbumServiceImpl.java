package cn.broadin.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.SourceAlbumMediaBean;
import cn.broadin.oms.service.SourceAlbumService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

/**
 * 相册素材业务实现类
 * @author huchanghuan
 *
 */
@Service
public class SourceAlbumServiceImpl implements SourceAlbumService{

	@Resource
	private DAO dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<SourceAlbumMediaBean> pageList(
			Class<SourceAlbumMediaBean> class1, List<ConditionBean> clist,
			Integer i, boolean b, String string, PaginationBean page) {
		
		return (List<SourceAlbumMediaBean>) dao.select(class1, clist, i, b, string, page);
	}
}
