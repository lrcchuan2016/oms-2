package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.SourceAlbumMediaBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

/**
 * 相册素材业务接口
 * @author huchanghuan
 *
 */
public interface SourceAlbumService {

	/**
	 * 条件查找相册素材
	 * @param class1
	 * @param clist
	 * @param i
	 * @param b
	 * @param string
	 * @param page
	 * @return
	 */
	public List<SourceAlbumMediaBean> pageList(Class<SourceAlbumMediaBean> class1,
			List<ConditionBean> clist, Integer i, boolean b, String string,
			PaginationBean page);

}
