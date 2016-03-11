package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.AlbumBean;
import cn.broadin.oms.model.UserBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

public interface UserService {

	/**
	 * 新增用户
	 * 
	 * @param user
	 * 
	 */
	public UserBean addUser(UserBean user);

	/**
	 * 分页处理
	 * 
	 * @param class1
	 * @param clist1
	 * @param i
	 * @param b
	 * @param string
	 * @param paginationBean
	 * @return
	 */
	public PaginationBean pageList(Class<UserBean> class1,
			List<ConditionBean> clist, Integer i, boolean b, String string,
			PaginationBean paginationBean);

	/**
	 * 根据id查找UserBean对象
	 * 
	 * @param class1
	 * @param uid
	 * @return
	 */
	public UserBean selectById(Class<UserBean> class1, String uid);

	/**
	 * 更新实体userBean
	 * 
	 * @param userBean
	 */
	public UserBean update(UserBean userBean);

	/**
	 * 删除实体UserBean
	 * 
	 * @param userBean
	 */
	public UserBean delUserBean(UserBean userBean);

	/**
	 * 查询所有实体UserBean
	 * 
	 * @param userBean
	 * @return
	 */
	public List<UserBean> selectAllUser(Class<UserBean> userBean);

	/**
	 * 根据实体account查找
	 * @param class1
	 * @param clist
	 * @param i
	 * @param b
	 * @param string
	 * @return
	 */
	public List<UserBean> selectByAccount(
			Class<UserBean> class1,
			List<ConditionBean> clist, Integer i, boolean b, String string,
			PaginationBean paginationBean);

	/**
	 * 用户相册的分页
	 * @param class1
	 * @param clist
	 * @param i
	 * @param b
	 * @param string
	 * @param paginationBean
	 * @return
	 */
	public PaginationBean pageList(Class<AlbumBean> class1,
			List<ConditionBean> clist, int i, boolean b, String string,
			PaginationBean paginationBean);

}
