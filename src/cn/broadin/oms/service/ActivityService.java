package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.ActorBean;
import cn.broadin.oms.model.AdLocationBean;
import cn.broadin.oms.model.AdResourceBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

public interface ActivityService {

	/**
	 * 添加活动参与者
	 * @param actor
	 * @return
	 */
	public ActorBean actorAdd(ActorBean actor);

	/**
	 * 查找用户是否参加过此活动
	 * @param clist
	 * @return
	 */
	public List<ActorBean> findActor(List<ConditionBean> clist);

	/**
	 * 新增活动
	 * @param activity
	 * @return
	 */
	public AdResourceBean activityAdd(AdResourceBean activity);

	/**
	 * 删除活动
	 * @param string
	 * @param slist
	 * @return
	 */
	public Object activityDel(String string, List<String> slist);

	/**
	 * 活动条件查找
	 * @param clist
	 * @param object
	 * @param b
	 * @param string
	 * @param page
	 * @return
	 */
	public PaginationBean pageList(List<ConditionBean> clist, Integer object, boolean b,
			String string, PaginationBean page);

	/**
	 * 根据ID查找活动
	 * @param string
	 * @return
	 */
	public AdResourceBean findActivity(String string);

	/**
	 * 更新活动信息
	 * @param activity
	 * @return
	 */
	public AdResourceBean activityUpdate(AdResourceBean activity);

	/**
	 * 查看活动参与者分页显示
	 * @param clist
	 * @param object
	 * @param b
	 * @param string
	 * @param page
	 * @return
	 */
	public PaginationBean actorPageList(List<ConditionBean> clist, Integer i,
			boolean b, String string, PaginationBean page);

	/**
	 * 根据名称查找广告栏位
	 * @param string
	 * @return
	 */
	public AdLocationBean findByName(String string);

}
