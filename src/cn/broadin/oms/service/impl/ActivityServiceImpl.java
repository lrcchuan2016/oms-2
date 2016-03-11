package cn.broadin.oms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.ActorBean;
import cn.broadin.oms.model.AdLocationBean;
import cn.broadin.oms.model.AdResourceBean;
import cn.broadin.oms.model.Command;
import cn.broadin.oms.service.ActivityService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

/**
 * 活动业务处理实现类
 * @author huchanghuan
 *
 */
@Service
public class ActivityServiceImpl implements ActivityService{

	@Resource
	private DAO dao;

	@Override
	public ActorBean actorAdd(ActorBean actor) {
		if(null != dao.insert(actor)){
			Command com = new Command("actor:add",actor.getId(),null,null);
			dao.insert(com);
			return actor;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActorBean> findActor(List<ConditionBean> clist) {
		List<ActorBean> actors = (List<ActorBean>) dao.select(ActorBean.class, clist, null, false, null, null);
		if(null != actors && 0 != actors.size()){
			return actors;
		}
		return null;
	}

	@Override
	public AdResourceBean activityAdd(AdResourceBean activity) {
		if(null != dao.insert(activity)){
			Command com = new Command("ad_resource:add",activity.getId(),null,null);
			dao.insert(com);
			return activity;
		}
		return null;
	}

	@Override
	public Object activityDel(String string, List<String> slist) {
		if(null != dao.delete(AdResourceBean.class, string, slist)){
			Command com = new Command("ad_resource:delete", slist.get(0), null, null);
			dao.insert(com);
			return string;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean pageList(List<ConditionBean> clist, Integer i,
			boolean b, String string, PaginationBean page) {
		if(null != page){
			page = (PaginationBean) dao.select(AdResourceBean.class, clist, i, b, string, page);
		}else{
			List<AdResourceBean> resources = (List<AdResourceBean>) dao.select(AdResourceBean.class, clist, i, b, string, null);
			page = new PaginationBean(0, resources.size(), resources.size(), resources);
		}
		return page;
	}

	@Override
	public AdResourceBean findActivity(String id) {
		
		return (AdResourceBean) dao.selectById(AdResourceBean.class, id);
	}

	@Override
	public AdResourceBean activityUpdate(AdResourceBean activity) {
		if(null != dao.update(activity)){
			Command com = new Command("ad_resource:update", activity.getId(), null, null);
			dao.insert(com);
			return activity;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean actorPageList(List<ConditionBean> clist, Integer i,
			boolean b, String string, PaginationBean page) {
		if(null != page){
			Object o = dao.select(ActorBean.class, clist, i, b, string, page);
			if(null != o){
				page = (PaginationBean)o;
				return page;
			}
		}else{
			List<ActorBean> actors = (List<ActorBean>) dao.select(ActorBean.class, clist, i, b, string, page);
			if(null != actors){
				page = new PaginationBean(0, actors.size(), actors.size(), actors);
				return page;
			}
		}
		page = new PaginationBean(0, 0, 0, new ArrayList<ActorBean>());
		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AdLocationBean findByName(String name) {
		List<ConditionBean> clist = new ArrayList<ConditionBean>();
		clist.add(new ConditionBean("ad_name", name, ConditionBean.EQ));
		List<AdLocationBean> locations = (List<AdLocationBean>) dao.select(AdLocationBean.class, clist, null, null, null, null);
		if(null != locations && 0 != locations.size()) return locations.get(0);
		return null;
	}
}
