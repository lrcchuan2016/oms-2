package cn.broadin.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.annotation.DataSource;
import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.dataSource.DBType;
import cn.broadin.oms.model.AlbumClickRecordBean;
import cn.broadin.oms.model.AlbumMakeRecordBean;
import cn.broadin.oms.model.AlbumSurferRecordBean;
import cn.broadin.oms.model.PhotoClickRecordBean;
import cn.broadin.oms.model.PhotoSurfedRecordBean;
import cn.broadin.oms.service.AlbumDataStatisticsService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

@Service
@DataSource(value=DBType.collectionDataSource)
public class AlbumDataStatisticsImpl implements AlbumDataStatisticsService{

	@Resource
	private DAO dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<AlbumClickRecordBean> findAlbumClickRecords(
			List<ConditionBean> conditions, Integer i, boolean b, String str,
			PaginationBean page) {
		
		return (List<AlbumClickRecordBean>) dao.select(AlbumClickRecordBean.class, conditions, i, b, str, page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> executeSql(String sql) {
		
		return (List<Object[]>) dao.executeSql(sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AlbumSurferRecordBean> findAlbumSurferRecords(
			List<ConditionBean> conditions, Integer i, boolean b, String s,
			PaginationBean page) {
		
		return (List<AlbumSurferRecordBean>) dao.select(AlbumSurferRecordBean.class, conditions, i, b, s, page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AlbumMakeRecordBean> findAlbumMakeRecords(
			List<ConditionBean> conditions, Integer i, boolean b, String str,
			PaginationBean page) {
		
		return (List<AlbumMakeRecordBean>) dao.select(AlbumMakeRecordBean.class,conditions, i, b, str, page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean findPhotoClickRecords(List<ConditionBean> conditions,
			Integer i, boolean b, String str, PaginationBean page) {
		
		if(null != page){
			page = (PaginationBean) dao.select(PhotoClickRecordBean.class, conditions, i, b, str, page);
		}else{
			
			List<PhotoClickRecordBean> records = (List<PhotoClickRecordBean>) dao.select(PhotoClickRecordBean.class, conditions, i, b, str, page);
			if(null != records && !records.isEmpty()){
				page = new PaginationBean(0, 1, records.size(), records);
			}
		}
		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> executeHql(String hql) {
		
		return (List<Object[]>) dao.executeHql(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhotoSurfedRecordBean> findPhotoSurfedRecords(
			List<ConditionBean> conditions, Integer i, boolean b, String str,
			PaginationBean page) {
		
		return (List<PhotoSurfedRecordBean>) dao.select(PhotoSurfedRecordBean.class, conditions, i, b, str, page);
	}
}
