package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.DecorateBean;
import cn.broadin.oms.model.DecorateGroupBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

/**
 * 装饰品业务处理接口
 * @author xiejun
 */

public interface DecorateService {
	/**
	 * 条件查找饰品列表
	 * @param arg0
	 * 			条件查找对象
	 * @param arg1
	 * 			分页对象
	 * @return
	 */
	public PaginationBean findDPage(List<ConditionBean> conditions,Integer i,boolean b,String str,PaginationBean page);
	
	/**
	 * 条件查找饰品分组列表
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public PaginationBean findDGPage(List<ConditionBean> arg0,PaginationBean arg1);
	
	/**
	 * 通过ID查找饰品
	 * @param arg0
	 * @return
	 */
	public DecorateBean findDecorateById(String arg0);
	
	/**
	 * 通过ID查找饰品分组
	 * @param arg0
	 * @return
	 */
	public DecorateGroupBean findDecorateGroupById(String arg0);
	
	/**
	 * 饰品添加
	 * @param arg0
	 * @return
	 */
	public ServiceResultBean DecorateAdd(DecorateBean arg0);
	
	/**
	 * 饰品分组添加
	 * @param arg0
	 * @return
	 */
	public ServiceResultBean DecorateGroupAdd(DecorateGroupBean arg0);
	
	/**
	 * 饰品编辑
	 * @param arg0
	 * @return
	 */
	public ServiceResultBean DecorateEdit(DecorateBean arg0);
	
	/**
	 * 饰品分组编辑
	 * @param arg0
	 * @return
	 */
	public ServiceResultBean DecorateGroupEdit(DecorateGroupBean arg0);
	
	/**
	 * 删除饰品
	 * @param arg0
	 * @return
	 */
	public ServiceResultBean DecorateDel(List<String> arg0, String arg1);
	
	/**
	 * 删除饰品分组
	 * @param arg0
	 * @return
	 */
	public ServiceResultBean DecorateGroupDel(List<String> arg0, String arg1);
	
	/**
	 * 更改对应ID饰品的主键值
	 * @param arg0
	 * 				原主键值
	 * @param arg1
	 * 				修改主键值
	 * @return
	 */
	public ServiceResultBean alterDecoratePrimaryKey(String arg0, String arg1);
	
	/**
	 * 更改饰品分组的主键值
	 * @param arg0
	 * 				原主键值
	 * @param arg1
	 * 				修改主键值
	 * @return
	 */
	public ServiceResultBean alterDecorateGroupPrimaryKey(String arg0, String arg1);


}
