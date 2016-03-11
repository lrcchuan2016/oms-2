package cn.broadin.oms.dao;

/**
 * 公共数据库操作接口
 * 
 * @author zhoudeming
 */
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;


public interface DAO {
	
	/**
	 * 插入实体
	 * 
	 * @param arg0
	 *            实体
	 * @return
	 */
	public Object insert(Object arg0);
	
	/**
	 * 插入实体前先判断数据是否已存在，再插入实体
	 * 
	 * @param arg0
	 *            条件集合
	 * @param arg1
	 *            实体
	 * @return
	 */
	public Object insert(List<ConditionBean> arg0, Object arg1);
	
	/**
	 * 批量插入实体
	 * 
	 * @param arg0
	 *            实体集合
	 * @return
	 */
	public Object insert(List<?> arg0);
	
	/**
	 * 更新实体
	 * 
	 * @param arg0
	 *            实体
	 * @return
	 */
	public Object update(Object arg0);
	
	/**
	 * 更新实体前先判断数据是否已存在，再更新
	 * 
	 * @param arg0
	 *            条件集合
	 * @param arg1
	 *            实体
	 * @return
	 */
	public Object update(List<ConditionBean> arg0, Object arg1);
	
	/**
	 * 根据条件更新实体指定字段
	 * 
	 * @param arg0
	 *            实体类名
	 * @param arg1
	 *            更新字段集合
	 * @param arg2
	 *            条件字段
	 * @param arg3
	 *            条件值
	 * @return
	 */
	public Object update(Class<?> arg0, Map<String, ?> arg1, String arg2, Object arg3);
	
	/**
	 * 批量更新实体
	 * 
	 * @param arg0
	 *            实体类名
	 * @param arg1
	 *            更新字段集合
	 * @param arg2
	 *            条件字段
	 * @param arg3
	 *            条件集合
	 * @return
	 */
	public Object update(Class<?> arg0, Map<String, ?> arg1, String arg2, List<?> arg3);
	
	/**
	 * 删除实体
	 * 
	 * @param arg0
	 *            实体
	 * @return
	 */
	public Object delete(Object arg0);
	
	/**
	 * 根据字段批量删除实体
	 * 
	 * @param arg0
	 *            实体类名
	 * @param arg1
	 *            条件字段
	 * @param arg2
	 *            条件集合
	 * @return
	 */
	public Object delete(Class<?> arg0, String arg1, List<?> arg2);
	
	/**
	 * 清除表数据
	 * 
	 * @param arg0
	 *            实体类名
	 * @return
	 */
	public Object clear(Class<?> arg0);
	
	/**
	 * 查询实体
	 * 
	 * @param arg0
	 *            实体类名
	 * @return
	 */
	public Object select(Class<?> arg0);
	
	/**
	 * 根据主键查询
	 * 
	 * @param arg0
	 *            实体类名
	 * @param arg1
	 *            主键值
	 * @return
	 */
	public Object selectById(Class<?> arg0, Serializable arg1);
	
	/**
	 * 根据条件查询（带分页功能）
	 * 
	 * @param arg0
	 *            实体类名
	 * @param arg1
	 *            条件集合
	 * @param arg2
	 *            条件之间的关系 and 或者 or
	 * @param arg3
	 *            是否正序
	 * @param arg4
	 *            排序字段，只支持一个字段
	 * @param arg5
	 *            是否分页， 不分页传null
	 * @return
	 */
	public Object select(Class<?> arg0, List<ConditionBean> arg1, Integer arg2, Boolean arg3, String arg4, PaginationBean arg5);
	
	/**
	 * 登陆专用查询
	 * @param arg0
	 * 			实体类名
	 * @param arg1
	 * 			条件键值队
	 * @param arg2
	 * 			是否关键邮箱
	 * @return
	 */
	public Object select(Class<?> arg0, Map<String,String> arg1, boolean arg2);
	
	/**
	 * 执行HQL
	 * 
	 * @param arg0
	 */
	public Object executeHql(String arg0);

	/**
	 * 执行sql
	 * @param sql
	 * @return
	 */
	public Object executeSql(String sql);

}
