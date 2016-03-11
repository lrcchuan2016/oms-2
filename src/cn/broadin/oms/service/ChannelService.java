package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.ChannelBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

/**
 * 渠道操作查询接口类
 * @author huchanghuan
 *
 */

public interface ChannelService {
	
	/**
	 * 条件查找实体
	 * @param class1 
	 * @param arg0
	 * @param string 
	 * @param b 
	 * @param i 
	 * @param arg1
	 * @return
	 */
	public Object findPage(Class<?> class1, List<ConditionBean> arg0,int i, boolean b, String string, PaginationBean arg1);
	
	/**
	 * 查找所有渠道
	 * @return
	 */
	public List<ChannelBean> findAll();
	
	
	/**
	 * 通过ID查找渠道商
	 * @param arg0
	 * @return
	 */
	public ChannelBean findById(String arg0);
	
	/**
	 * 添加渠道
	 * @param arg0
	 * @return
	 */
	public ServiceResultBean add(ChannelBean arg0);
	
	/**
	 * 编辑渠道
	 * @param arg0
	 * @return
	 */
	public ServiceResultBean updateChannel(ChannelBean arg0);
	
	/**
	 * 删除渠道
	 * @param arg0
	 * 		ID集合，便于以后扩展批量删除
	 * @return
	 */
	public ServiceResultBean del(List<ChannelBean> arg0);

	/**
	 * 条件查找实体
	 * @param class1
	 * @param object
	 * @param object2
	 * @param b
	 * @param string
	 * @param object3
	 * @return
	 */
	public List<?> selectConditions(
			Class<?> c, List<ConditionBean> clist, Integer i,
			boolean b, String string, PaginationBean p);

	/**
	 * 更新渠道数据
	 * @param c
	 * @return
	 */
	public ServiceResultBean update(Object c);

	/**
	 * 新增渠道数据
	 * @param inst
	 * @return
	 */
	public ServiceResultBean addData(Object inst);
}
