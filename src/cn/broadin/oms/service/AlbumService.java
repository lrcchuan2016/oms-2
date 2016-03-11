package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.AlbumBean;
import cn.broadin.oms.model.AlbumMediaBean;
import cn.broadin.oms.model.AlbumRecommendBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

/**
 * 相册业务接口
 * @author huchanghuan
 *
 */
public interface AlbumService {

	/**
	 * 相册分页
	 * @param album
	 * @param object
	 * @param object2
	 * @param b
	 * @param string
	 * @param pBean
	 * @return
	 */
	public PaginationBean pageAlbum(Class<AlbumBean> album, List<ConditionBean> clist,
			Integer object2, boolean b, String string, PaginationBean pBean);

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public AlbumBean selectById(String id);

	/**
	 * 更新实体
	 * @param alBean
	 * @return 
	 */
	public boolean update(AlbumBean alBean);

	/**
	 * 删除实体
	 * @param alBean
	 */
	public boolean delete(AlbumBean alBean);

	

	/**
	 * 相册里面的相片分页显示
	 * @param class1
	 * @param clist
	 * @param i
	 * @param b
	 * @param string
	 * @param paginationBean
	 * @return
	 */
	public PaginationBean pageList(Class<AlbumMediaBean> class1,
			List<ConditionBean> clist, Integer i, boolean b, String string,
			PaginationBean paginationBean);

	/**
	 * 删除中间表的实体
	 * @param albumMediaBean
	 * @return
	 */
	public boolean deleteAlbumMedia(AlbumMediaBean albumMediaBean);

	/**
	 * 新增实体数据
	 * @param albumMediaBean
	 * @return
	 */
	public ServiceResultBean insertAlbumMedia(AlbumMediaBean albumMediaBean);

	/**
	 * 更新相册媒资
	 * @param albumMedia
	 * @return
	 */
	public AlbumMediaBean updateAlbumMedia(AlbumMediaBean albumMedia);

	/**
	 * 条件查找相册
	 * @return
	 */
	public List<AlbumBean> selectByCondition(Class<?> arg0, List<ConditionBean> arg1,
			Integer i, Boolean b, String s, PaginationBean p);

	/**
	 * 
	 * @param album_id
	 * @return
	 */
	public AlbumRecommendBean findARecommendById(String album_id);

	/**
	 * 查找权值最大的数
	 * @return
	 */
	public Integer findMaxWeight();

	/**
	 * 加入推荐记录表
	 * @param albumRecommend
	 * @return
	 */
	public AlbumRecommendBean insertAlbumRecommend(AlbumRecommendBean albumRecommend);

	/**
	 * 推荐相册更新
	 * @param albumRecommend
	 * @return
	 */
	public AlbumRecommendBean updateAlbumRecommend(AlbumRecommendBean albumRecommend);

	/**
	 * 删除相册推荐
	 * @param albumRecommend
	 * @return
	 */
	public AlbumRecommendBean delAlbumRecommend(AlbumRecommendBean albumRecommend);

	
}
