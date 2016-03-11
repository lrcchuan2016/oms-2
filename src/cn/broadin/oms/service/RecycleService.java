package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.RecycleBean;
import cn.broadin.oms.util.ConditionBean;

public interface RecycleService {
	/**
	 * 添加到回收站
	 * @param arg0
	 * @return
	 */
	public boolean add(RecycleBean arg0);
	
	/**
	 * 从回收站中恢复
	 * @param arg0
	 * @return
	 */
	public boolean recover(List<Integer> arg0,int arg1,int arg2);
	
	/**
	 * 查找所有回收站数据
	 * @param arg0
	 * 			条件查找
	 * @return
	 */
	public List<RecycleBean> findAll(List<ConditionBean> conditions);

	/**
	 * 删除回收站的数据
	 * @param recycle
	 * @return
	 */
	public RecycleBean del(RecycleBean recycle);
}
