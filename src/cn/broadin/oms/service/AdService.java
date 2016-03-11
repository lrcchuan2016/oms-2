package cn.broadin.oms.service;

import java.util.List;
import cn.broadin.oms.model.AdLocationBean;
import cn.broadin.oms.model.AdResourceBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

/**
 * 广告Service接口
 * @author huchanghuan
 *
 */
public interface AdService {


	/**
	 * 添加广告栏位
	 * @param clist
	 * @param adLocationBean
	 * @return
	 */
	public AdLocationBean add(List<ConditionBean> clist, AdLocationBean adLocationBean);

	/**
	 * 查找所有广告栏位
	 * @param class1 
	 * @return
	 */
	public List<AdLocationBean> findAllLocation(Class<AdLocationBean> class1);

	/**
	 * 根据ID查找实体AdLocationBean
	 * @param class1 
	 * @param id
	 * @return
	 */
	public AdLocationBean selectById(Class<AdLocationBean> class1, String id);

	/**
	 * 插入指定栏位的广告
	 * @param adResourceBean
	 */
	public AdResourceBean addResource(AdResourceBean adResourceBean);

	

	/**
	 * 删除指定广告栏位
	 * @param adLocationBean
	 * @param set 
	 * 
	 */
	public AdLocationBean delAdLocation(AdLocationBean adLocationBean);

	/**
	 * 更新实体类adLocationBean
	 * @param clist 
	 * @param adLocationBean
	 */
	public AdLocationBean update(List<ConditionBean> clist, AdLocationBean adLocationBean);

	/**
	 * 查找所有AdResource实体
	 * @param class1
	 * @param object
	 * @param object2
	 * @param b
	 * @param string
	 * @param object3
	 * @return
	 */
	public List<AdResourceBean> findAllResource(Class<AdResourceBean> class1,
			List<ConditionBean> list, Integer i, boolean b, String string,
			PaginationBean p);

	/**
	 * 查找AdResourceBean实体
	 * @param class1
	 * @param id
	 * @return
	 */
	public AdResourceBean selectResourceById(Class<AdResourceBean> class1, String id);

	/**
	 * 删除实体AdResourceBean(包括阿里云上的图片/视频)
	 * @param adResourceBean
	 */
	public ServiceResultBean delAdResource(AdResourceBean adResourceBean,String key);

	/**
	 * 更新实体adResourceBean
	 * @param adResourceBean
	 */
	public AdResourceBean updateAdResource(AdResourceBean adResourceBean);


}
