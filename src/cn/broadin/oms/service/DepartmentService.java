package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.DepartmentBean;

/**
 * 美家秀运营部门接口类
 * @author XieJun
 *
 */
public interface DepartmentService {
	/**
	 * 查找所有部门信息
	 * @return
	 */
	public List<DepartmentBean> findAll();
	
	/**
	 * 添加部门
	 * @param arg0
	 * 			部门对象
	 * @return
	 */
	public boolean add(DepartmentBean arg0);
	
	/**
	 * 修改部门信息
	 * @param arg0
	 * 			部门对象
	 * @return
	 */
	public boolean edit(DepartmentBean arg0);
	
	/**
	 * 删除部门
	 * @param arg0
	 * 			部门ID
	 * @return
	 */
	public boolean del(List<Integer> arg0);
}
