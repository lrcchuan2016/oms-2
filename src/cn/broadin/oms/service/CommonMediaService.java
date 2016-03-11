package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.CommonMediaBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

/**
 * 通用媒资接口
 * @author huchanghuan
 *
 */
public interface CommonMediaService {

	/**
	 * 添加通用媒资common_media
	 * @param cMedia
	 * @return
	 */
	public ServiceResultBean cMediaAdd(CommonMediaBean cMedia);

	/**
	 * 编辑通用媒资
	 * @param cMedia
	 * @return
	 */
	public ServiceResultBean cMediaEdit(CommonMediaBean cMedia);

	/**
	 * 查找推荐的背景音乐
	 * @param conditions
	 * @param page
	 * @return
	 */
	public PaginationBean findMusicPage(List<ConditionBean> conditions,Integer i,boolean b,String str,
			PaginationBean page);

	/**
	 * 根据ID查找通用媒资对象
	 * @param id
	 * @return
	 */
	public CommonMediaBean findById(String id);

	/**
	 * 通用媒资分页
	 * @param class1
	 * @param clist
	 * @param i
	 * @param b
	 * @param string
	 * @param pagination
	 * @return
	 */
	public PaginationBean pageList(Class<CommonMediaBean> class1,
			List<ConditionBean> clist, int i, boolean b, String string,
			PaginationBean pagination);

	public ServiceResultBean cMediaDel(List<String> ids);
}
