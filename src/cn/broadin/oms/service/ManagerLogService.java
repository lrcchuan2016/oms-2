package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

/**
 * 管理员操作日志业务处理接口
 * 
 * @author xiejun
 */
public interface ManagerLogService {
	
	/**
	 * 新增管理员操作日志信息
	 * 
	 * @param arg0
	 *            日志信息
	 * @return
	 */
	public void add(String arg0);
	
	/**
	 * 批量新增管理员操作日志信息
	 * 
	 * @param arg0
	 *            日志信息集合
	 */
	public void add(List<String> arg0);
	
	/**
	 * 分页获取管理员操作日志数据
	 * 
	 * @param arg0
	 *            条件对象集合
	 * @param arg1
	 *            分页相关对象
	 * @return
	 */
	public PaginationBean findPage(List<ConditionBean> arg0, PaginationBean arg1);
}
