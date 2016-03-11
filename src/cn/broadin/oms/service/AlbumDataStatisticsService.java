package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.AlbumClickRecordBean;
import cn.broadin.oms.model.AlbumMakeRecordBean;
import cn.broadin.oms.model.AlbumSurferRecordBean;
import cn.broadin.oms.model.PhotoSurfedRecordBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

public interface AlbumDataStatisticsService {

	/**
	 * 条件查找相册点击记录
	 * @param conditions
	 * @param i
	 * @param b
	 * @param str
	 * @param page
	 * @return
	 */
	List<AlbumClickRecordBean> findAlbumClickRecords(
			List<ConditionBean> conditions, Integer i, boolean b,
			String str, PaginationBean page);

	/**
	 * 执行sql
	 * @param sql
	 * @return
	 */
	List<Object[]> executeSql(String sql);

	/**
	 * 条件查找相册浏览记录
	 * @param conditions  查询条件
	 * @param i				条件之间的逻辑关系
	 * @param b				排序规则
	 * @param s				排序字段
	 * @param page			分页条件
	 * @return
	 */
	List<AlbumSurferRecordBean> findAlbumSurferRecords(
			List<ConditionBean> conditions, Integer i, boolean b,
			String s, PaginationBean page);

	
	/**
	 * 条件查找相册制作记录
	 * @param conditions
	 * @param i
	 * @param b
	 * @param str
	 * @param page
	 * @return
	 */
	List<AlbumMakeRecordBean> findAlbumMakeRecords(
			List<ConditionBean> conditions, Integer i, boolean b,
			String str, PaginationBean page);

	/**
	 * 条件查找照片点击记录
	 * @param conditions
	 * @param object
	 * @param b
	 * @param string
	 * @param page
	 * @return
	 */
	PaginationBean findPhotoClickRecords(List<ConditionBean> conditions,
			Integer i, boolean b, String str, PaginationBean page);

	/**
	 * 执行HQL
	 * @param hql
	 * @return
	 */
	List<Object[]> executeHql(String hql);

	/**
	 * 条件查询照片记录
	 * @param conditions
	 * @param i
	 * @param b
	 * @param str
	 * @param page
	 * @return
	 */
	List<PhotoSurfedRecordBean> findPhotoSurfedRecords(
			List<ConditionBean> conditions, Integer i, boolean b,
			String str, PaginationBean page);

	
}
