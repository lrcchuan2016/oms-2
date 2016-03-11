package cn.broadin.oms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.Command;
import cn.broadin.oms.model.DecorateBean;
import cn.broadin.oms.model.DecorateGroupBean;
import cn.broadin.oms.service.DecorateService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

@Service
public class DecorateServiceImpl implements DecorateService {
	@Resource
	private DAO dao;
	@Resource
	private CommonServiceImpl commonService;
	
	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean findDPage(List<ConditionBean> conditions,Integer i,boolean b,String str,
			PaginationBean page) {
		if(page != null) return (PaginationBean) dao.select(DecorateBean.class, conditions , i, b, str, page);
		else {
			List<DecorateBean> tbs = (List<DecorateBean>) dao.select(DecorateBean.class, conditions , i, b, str, page);
			PaginationBean pg = new PaginationBean(0,tbs.size(),tbs.size(),tbs);
			return pg;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean findDGPage(List<ConditionBean> arg0,
			PaginationBean arg1) {
		if(arg1 != null) return (PaginationBean) dao.select(DecorateGroupBean.class, arg0 , null, false, "createUtc", arg1);
		else {
			List<DecorateGroupBean> tbs = (List<DecorateGroupBean>) dao.select(DecorateGroupBean.class, arg0 , null, false, "createUtc", null);
			PaginationBean pg = new PaginationBean(0,tbs.size(),tbs.size(),tbs);
			return pg;
		}
	}

	@Override
	public DecorateBean findDecorateById(String arg0) {
		return (DecorateBean) dao.selectById(DecorateBean.class, arg0);
	}

	@Override
	public DecorateGroupBean findDecorateGroupById(String arg0) {
		return (DecorateGroupBean) dao.selectById(DecorateGroupBean.class, arg0);
	}

	@Override
	public ServiceResultBean DecorateAdd(DecorateBean arg0) {
		ServiceResultBean result = new ServiceResultBean();
		if(arg0 != null){
			if(dao.insert(arg0) != null){
				Command com = new Command("decorate:add", arg0.getId(), null, null);
				dao.insert(com);
			}else{
				result.setCode(1);
				result.setSuccess(false);
			}
		}else{
			result.setCode(0);
			result.setSuccess(false);
			result.setDescription("input param is null!");
		}
		return result;
	}

	@Override
	public ServiceResultBean DecorateGroupAdd(DecorateGroupBean arg0) {
		ServiceResultBean result = new ServiceResultBean();
		if(arg0 != null){
			if(dao.insert(arg0) != null){
				Command com = new Command("decorate_group:add", arg0.getId(), null, null);
				dao.insert(com);
			}else{
				result.setCode(1);
				result.setSuccess(false);
			}
		}else{
			result.setCode(0);
			result.setSuccess(false);
			result.setDescription("input param is null!");
		}
		
		return result;
	}

	@Override
	public ServiceResultBean DecorateEdit(DecorateBean arg0) {
		ServiceResultBean result = new ServiceResultBean();		
		if(arg0 != null){
			if(dao.update(arg0) != null){
				Command com = new Command("decorate:update", arg0.getId(), null, null);
				dao.insert(com);
			}else{
				result.setCode(1);
				result.setSuccess(false);
			}
		}else{
			result.setCode(0);
			result.setSuccess(false);
			result.setDescription("input param is null!");
		}
		return result;
	}

	@Override
	public ServiceResultBean DecorateGroupEdit(DecorateGroupBean arg0) {
		ServiceResultBean result = new ServiceResultBean();		
		if(arg0 != null){
			if(dao.update(arg0) != null){
				Command com = new Command("decorate_group:update", arg0.getId(), null, null);
				dao.insert(com);
			}else{
				result.setCode(1);
				result.setSuccess(false);
			}
		}else{
			result.setCode(0);
			result.setSuccess(false);
			result.setDescription("input param is null!");
		}
		return result;
	}

	@Override
	public ServiceResultBean DecorateDel(List<String> arg0, String arg1) {
		ServiceResultBean result = new ServiceResultBean();
		if(null != arg0 && null != arg1){
			if(null != dao.delete(DecorateBean.class, "id", arg0)){
				commonService.deleteFile(commonService.createOSSClient(), arg1);
				List<Command> list = new ArrayList<Command>();
				for (String s : arg0) {
					Command com = new Command("decorate:delete", s, null, null);
					list.add(com);
				}
				dao.insert(list);
				return result;
			}else{
				result.setCode(1);
				result.setSuccess(false);
			}
		}else{
			result.setCode(0);
			result.setSuccess(false);
			result.setDescription("input param is null!");
		}
		return result;
	}

	@Override
	public ServiceResultBean DecorateGroupDel(List<String> arg0, String arg1) {
		ServiceResultBean result = new ServiceResultBean();
		if(null != arg0 && null != arg1){
			if(null != dao.delete(DecorateGroupBean.class, "id", arg0)){
				commonService.deleteFile(commonService.createOSSClient(), arg1);
				List<Command> list = new ArrayList<Command>();
				for (String s : arg0) {
					Command com = new Command("decorate_group:delete", s, null, null);
					list.add(com);
				}
				dao.insert(list);
				return result;
			}else{
				result.setCode(1);
				result.setSuccess(false);
			}
		}else{
			result.setCode(0);
			result.setSuccess(false);
			result.setDescription("input param is null!");
		}
		return result;
	}

	@Override
	public ServiceResultBean alterDecoratePrimaryKey(String arg0, String arg1) {
		ServiceResultBean result = new ServiceResultBean();
		if(arg0 != null&& arg1 != null){
			String hql = "update DecorateBean set id='"+arg1+"' where id='"+arg0+"'";
			Object obj = dao.executeHql(hql);
			if(obj != null){
				Command com = new Command("decorate:update", arg0, null, null);
				dao.insert(com);
			}else{
				result.setCode(1);
				result.setSuccess(false);
			}
		}else{
			result.setCode(0);
			result.setSuccess(false);
			result.setDescription("input param is null!");
		}
		return result;
	}

	@Override
	public ServiceResultBean alterDecorateGroupPrimaryKey(String arg0,
			String arg1) {
		ServiceResultBean result = new ServiceResultBean();
		if(arg0 != null&& arg1 != null){
			String hql = "update DecorateGroupBean set id='"+arg1+"' where id='"+arg0+"'";
			Object obj = dao.executeHql(hql);
			if(obj != null){
				Command com = new Command("decorate_group:update", arg0, null, null);
				dao.insert(com);
			}else{
				result.setCode(1);
				result.setSuccess(false);
			}
		}else{
			result.setCode(0);
			result.setSuccess(false);
			result.setDescription("input param is null!");
		}
		return result;
	}

}
