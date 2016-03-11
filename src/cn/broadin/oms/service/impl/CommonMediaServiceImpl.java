package cn.broadin.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.Command;
import cn.broadin.oms.model.CommonMediaBean;
import cn.broadin.oms.service.CommonMediaService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

@Service
public class CommonMediaServiceImpl implements CommonMediaService{

	@Resource
	private DAO dao;
	
	@Override
	public ServiceResultBean cMediaAdd(CommonMediaBean cMedia) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null != dao.insert(cMedia)){
			Command com = new Command("common_media:add", cMedia.getId(), null, null);
			dao.insert(com);
		}else {
			srb.setSuccess(false);
		}
		return srb;
	}

	@Override
	public ServiceResultBean cMediaEdit(CommonMediaBean cMedia) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null!=dao.update(cMedia)){
			Command com = new Command("common_media:update", cMedia.getId(), null, null);
			dao.insert(com);
		}else{
			srb.setSuccess(false);
		}
		return srb;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean findMusicPage(List<ConditionBean> conditions,Integer i,boolean b,String str,
			PaginationBean page) {
		if(page != null) return (PaginationBean) dao.select(CommonMediaBean.class, conditions , i, b, str, page);
		else {
			List<CommonMediaBean> mbs = (List<CommonMediaBean>) dao.select(CommonMediaBean.class, conditions , i, b, str, page);
			PaginationBean pg = new PaginationBean(0,mbs.size(),mbs.size(),mbs);
			return pg;
		}
	}

	@Override
	public CommonMediaBean findById(String id) {
		
		return (CommonMediaBean) dao.selectById(CommonMediaBean.class, id);
	}

	public ServiceResultBean cMediaDel(List<String> ids) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null != dao.delete(CommonMediaBean.class, "id", ids)){
			for (String id : ids) {
				Command com = new Command("common_media:delete", id, null, null);
				dao.insert(com);
			}
		}else{
			srb.setSuccess(false);
			srb.setDescription("Delete media failed");
		}
		return srb;
	}

	@Override
	public PaginationBean pageList(Class<CommonMediaBean> class1,
			List<ConditionBean> clist, int i, boolean b, String string,
			PaginationBean pagination) {
		
		return (PaginationBean) dao.select(class1, clist, i, b, string, pagination);
	}
}
