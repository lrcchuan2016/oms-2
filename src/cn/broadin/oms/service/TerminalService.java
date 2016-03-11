package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.TerminalBean;
import cn.broadin.oms.model.TerminalTypeBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

/**
 * 终端数据控制接口类
 * @author xiejun
 */

public interface TerminalService {
	/**
	 * 查找所有的终端类型
	 * @return
	 */
	public List<TerminalTypeBean> findTerminalTypes();

	/**
	 * 条件查找数据
	 * @param clist
	 * @return
	 */
	public List<TerminalBean> findDayOfData(List<ConditionBean> clist,Integer i,boolean b,String str,PaginationBean page);

	/**
	 * 根据ID查找终端类型
	 * @param valueOf
	 * @return
	 */
	public TerminalTypeBean findTerminalById(int id);


}
