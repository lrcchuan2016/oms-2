package cn.broadin.oms.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.AlbumBean;
import cn.broadin.oms.model.Command;
import cn.broadin.oms.model.UserBean;
import cn.broadin.oms.service.UserService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
/**
 * 用户业务处理实现类
 * @author huchanghuan
 *
 */
@Service
public class UserServiceImpl implements UserService{

	@Resource
	private DAO dao;
	
	@Override
	public UserBean addUser(UserBean user) {
		if(null != dao.insert(user)){
			Command command = new Command("user:add", user.getId(), null, null);
			dao.insert(command);
			return user;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean pageList(Class<UserBean> class1,
			List<ConditionBean> clist, Integer i, boolean b, String string,
			PaginationBean paginationBean) {
		if(null != paginationBean){
			paginationBean = (PaginationBean) dao.select(class1, clist, i, b, string, paginationBean);
		}else{
			List<UserBean> users = (List<UserBean>) dao.select(class1, clist, i, b, string, paginationBean);
			if(null != users && 0 != users.size()){
				paginationBean = new PaginationBean(0, 1, users.size(), users);
			}
		}
		return paginationBean;
	}

	@Override
	public UserBean selectById(Class<UserBean> class1, String uid) {
		
		return (UserBean) dao.selectById(class1, uid);
	}

	@Override
	public UserBean update(UserBean userBean) {
		if(null != dao.update(userBean)){
			Command command = new Command("user:update", userBean.getId(), null, null);
			dao.insert(command);
			return userBean;
		}
		return null;
	}

	@Override
	public UserBean delUserBean(UserBean userBean) {
		if(null!=dao.delete(userBean)){
			Command command = new Command("user:delete", userBean.getId(), null, null);
			if(null!=dao.insert(command)) return userBean;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserBean> selectAllUser(Class<UserBean> userBean) {
		
		return (List<UserBean>) dao.select(userBean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserBean> selectByAccount(Class<UserBean> class1,
			List<ConditionBean> clist, Integer i, boolean b, String string,
			PaginationBean paginationBean) {
		return  (List<UserBean>)dao.select(class1, clist, i, b, string, paginationBean);
	}

	@Override
	public PaginationBean pageList(Class<AlbumBean> class1,
			List<ConditionBean> clist, int i, boolean b, String string,
			PaginationBean paginationBean) {
		
		return (PaginationBean) dao.select(class1, clist, i, b, string, paginationBean);
	}

}
