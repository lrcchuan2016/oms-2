package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.AlbumMediaBean;
import cn.broadin.oms.model.MediaBean;
import cn.broadin.oms.model.MediaTypeBean;
import cn.broadin.oms.model.TemplateMediaBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

/**
 * 美家秀媒资类接口
 * @author xiejun
 *
 */
public interface MediaService {
	
	/**
	 * 条件查找
	 * @param conditions
	 * @param page
	 * @return
	 */
	public PaginationBean findPage(List<ConditionBean> conditions,PaginationBean page);
	
	/**
	 * 获取媒资类型列表
	 * @return
	 */
	public List<MediaTypeBean> findTypeList();
	
	/**
	 * 通过ID获取MediaBean
	 * @param arg0
	 * @return
	 */
	public MediaBean findById(String arg0);
	
	/**
	 * 添加媒资
	 * @param arg0
	 * @param cMedia 
	 * @return
	 */
	public ServiceResultBean mediaAdd(MediaBean arg0);
	
	/**
	 * 编辑媒资
	 * @param arg0
	 * 			媒资对象
	 * @return
	 */
	public ServiceResultBean mediaEdit(MediaBean arg0);
	
	/**
	 * 删除媒资
	 * @param arg0
	 * @param arg1
	 * 			媒资存储对应的key
	 * 			用来作为媒资编码
	 * @return
	 */
	public ServiceResultBean mediaDel(List<String> arg0, String arg1);
	
	/**
	 * 修改媒资主键值
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public ServiceResultBean alterMediaKey(String arg0, String arg1);

	/**
	 * 更新媒资实体
	 * @param media
	 * @return
	 */
	public MediaBean updateMedia(MediaBean media);

	/**
	 * 根据相册ID，媒体ID查找
	 * @param class1
	 * @param clist
	 * @return
	 */
	public List<AlbumMediaBean> selectAlbumMedia(Class<AlbumMediaBean> class1,
			List<ConditionBean> clist);

	/**
	 * 查找模版媒资列表
	 * @param clist
	 * @param PaginationBean
	 * @return
	 */
	public List<TemplateMediaBean> findTMedia(List<ConditionBean> clist,
			PaginationBean p);

	/**
	 * 根据ID批量查找媒资
	 * @param sContent
	 * @return
	 */
	public List<MediaBean> findMedia(String[] sContent);

	/**
	 * 条件查找媒资
	 * @param class1
	 * @param clist
	 * @param i
	 * @param b
	 * @param string
	 * @param paginationBean
	 * @return
	 */
	public PaginationBean pageList(Class<MediaBean> class1,
			List<ConditionBean> clist, int i, boolean b, String string,
			PaginationBean paginationBean);

	/**
	 * 通过content查找media
	 * @param string
	 * @return
	 */
	public MediaBean findMediaByContent(String content);

	/**
	 * 查找相册媒资
	 * @param class1
	 * @param clist
	 * @param i
	 * @param b
	 * @param string
	 * @param paginationBean
	 * @return
	 */
	public PaginationBean getAlbumMedia(Class<AlbumMediaBean> class1,
			List<ConditionBean> clist, int i, boolean b, String string,
			PaginationBean paginationBean);

//	/**
//	 * 
//	 * @param class1
//	 * @param clist
//	 * @param i
//	 * @param b
//	 * @param string
//	 * @param paginationBean
//	 * @return
//	 */
//	public PaginationBean getAllAlbumMedia(Class<AlbumMediaBean> class1,
//			List<ConditionBean> clist, int i, boolean b, String string,
//			PaginationBean paginationBean);
	
}
