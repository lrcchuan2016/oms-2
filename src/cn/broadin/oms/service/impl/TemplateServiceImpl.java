package cn.broadin.oms.service.impl;

import it.sauronsoftware.base64.Base64;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.Command;
import cn.broadin.oms.model.TemplateBean;
import cn.broadin.oms.model.TemplateGroupBean;
import cn.broadin.oms.model.TemplateMediaBean;
import cn.broadin.oms.service.TemplateService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

@Service
public class TemplateServiceImpl implements TemplateService {
	@Resource
	private DAO dao;
	@Resource
	private CommonServiceImpl commonService;
	
	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean findTPage(List<ConditionBean> arg0,Integer arg1,boolean arg2,String arg3,PaginationBean arg4) {
		if(arg4 != null) return (PaginationBean) dao.select(TemplateBean.class, arg0 , arg1, arg2, arg3, arg4);
		else {
			List<TemplateBean> tbs = (List<TemplateBean>) dao.select(TemplateBean.class, arg0 , arg1, arg2, arg3, arg4);
			PaginationBean pg = new PaginationBean(0,tbs.size(),tbs.size(),tbs);
			return pg;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean findTGPage(List<ConditionBean> arg0,PaginationBean arg1) {
		if(arg1 != null) return (PaginationBean) dao.select(TemplateGroupBean.class, arg0 , null, false, "createUtc", arg1);
		else {
			List<TemplateGroupBean> tbs = (List<TemplateGroupBean>) dao.select(TemplateGroupBean.class, arg0 , null, false, "createUtc", null);
			PaginationBean pg = new PaginationBean(0,tbs.size(),tbs.size(),tbs);
			return pg;
		}
	}

	@Override
	public ServiceResultBean TemplateAdd(TemplateBean arg0) {
		ServiceResultBean result = new ServiceResultBean();
		if(arg0 != null){
			if(dao.insert(arg0) != null){
				Command com = new Command("album_template:add", arg0.getId(), null, null);
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
	public ServiceResultBean TemplateGroupAdd(TemplateGroupBean arg0) {
		ServiceResultBean result = new ServiceResultBean();
		if(arg0 != null){
			if(dao.insert(arg0) != null){
				Command com = new Command("template_group:add", arg0.getId(), null, null);
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

	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public ServiceResultBean TemplateEdit(TemplateBean arg0) {
		ServiceResultBean result = new ServiceResultBean();	
		if(arg0 != null){
			//更新模版信息的时，相应的对模版xml进行更新
			List<ConditionBean> clist = new ArrayList<ConditionBean>();
			clist.add(new ConditionBean("template", arg0, ConditionBean.EQ));
			clist.add(new ConditionBean("type", "4", ConditionBean.EQ));
			List<TemplateMediaBean> tmList = (List<TemplateMediaBean>) dao.select(TemplateMediaBean.class, clist, null, null, null, null);
			if(null != tmList && 0 != tmList.size()){
				TemplateMediaBean tm = tmList.get(0);
				String xml = tm.getMedia().getContent();
				try {
					Base64 base64 = new Base64();
					xml = base64.decode(xml, "UTF-8");
					Document document = DocumentHelper.parseText(xml);
					Element e = document.getRootElement();
					e.element("id").setText(arg0.getId());
					e.element("tid").setText(arg0.getId());
					e.element("tname").setText(arg0.getName());
					e.element("name").setText(arg0.getName());
					e.element("detail").setText(arg0.getIntroduction());
					e.element("cover").setText(arg0.getCoverUrl());
					tm.getMedia().setContent(base64.encode(document.asXML(), "UTF-8"));
					if(null != dao.update(tm.getMedia())){
						Command command = new Command("media:update", tm.getMedia().getId(), null, null);
						dao.insert(command);
						if(dao.update(arg0) != null){
							Command com = new Command("album_template:update", arg0.getId(), null, null);
							dao.insert(com);
						}else{
							result.setCode(1);
							result.setSuccess(false);
						}
					}else {
						result.setCode(1);
						result.setSuccess(false);
					}
				} catch (DocumentException e) {
					e.printStackTrace();
				}	
			}else{
				if(dao.update(arg0) != null){
					Command com = new Command("album_template:update", arg0.getId(), null, null);
					dao.insert(com);
				}else{
					result.setCode(1);
					result.setSuccess(false);
				}
			}
		}else{
			result.setCode(0);
			result.setSuccess(false);
			result.setDescription("input param is null!");
		}
		return result;
	}

	@Override
	public ServiceResultBean TemplateGroupEdit(TemplateGroupBean arg0) {
		ServiceResultBean result = new ServiceResultBean();
		if(arg0 != null){
			if(dao.update(arg0) != null){
				Command com = new Command("template_group:update", arg0.getId(), null, null);
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
	public ServiceResultBean TemplateDel(List<String> arg0, String arg1) {
		ServiceResultBean result = new ServiceResultBean();
		if(null != arg0 && null != arg1){
			if(null != dao.delete(TemplateBean.class, "id", arg0)){
				for (String s : arg0) {
					Command com = new Command("album_template:delete", s, null, null);
					dao.insert(com);
				}
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
	public ServiceResultBean TemplateGroupDel(List<String> arg0, String arg1) {
		ServiceResultBean result = new ServiceResultBean();
		if(null != arg0 && null != arg1){
			if(null != dao.delete(TemplateGroupBean.class, "id", arg0)){
				commonService.deleteFile(commonService.createOSSClient(), arg1);
				List<Command> clist = new ArrayList<Command>();
				for (String s : arg0) {
					Command com = new Command("template_group:delete", s, null, null);
					clist.add(com);
				}
				dao.insert(clist);
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
	public TemplateBean findTemplateById(String arg0) {
		return (TemplateBean) dao.selectById(TemplateBean.class, arg0);
	}

	@Override
	public TemplateGroupBean findTemplateGroupById(String arg0) {
		return (TemplateGroupBean) dao.selectById(TemplateGroupBean.class, arg0);
	}

//	@Override
//	public ServiceResultBean alterTemplatePrimaryKey(String arg0, String arg1) {
//		ServiceResultBean result = new ServiceResultBean();
//		if(arg0 != null&& arg1 != null){
//			String hql1 = "update TemplateBean set id='"+arg1+"' where id='"+arg0+"'";
//			String hql2 = "delete from TemplateBean where id = '"+arg0+"'";
//			Object obj1 = dao.executeHql(hql1);
//			Object obj2 = dao.executeHql(hql2);
//			if(obj1 != null && obj2 != null){
//				Command com2 = new Command("album_template:update", arg1, null, null);
//				Command com1 = new Command("album_template:delete", arg0, null, null);
//				dao.insert(com1);
//				dao.insert(com2);
//			}else{
//				result.setCode(1);
//				result.setSuccess(false);
//			}
//		}else{
//			result.setCode(0);
//			result.setSuccess(false);
//			result.setDescription("input param is null!");
//		}
//		return result;
//	}
//
//	@Override
//	public ServiceResultBean alterTemplateGroupPrimaryKey(String arg0,
//			String arg1) {
//		ServiceResultBean result = new ServiceResultBean();
//		if(arg0 != null&& arg1 != null){
//			String hql1 = "update TemplateGroupBean set id='"+arg1+"' where id='"+arg0+"'";
//			String hql2 = "delete from TemplateGroupBean where id = '"+arg0+"'";
//			Object obj1 = dao.executeHql(hql1);
//			Object obj2 = dao.executeHql(hql2);
//			if(obj1 != null && obj2 != null){
//				Command com1 = new Command("template_group:delete", arg0, null, null);
//				Command com2 = new Command("template_group:update", arg1, null, null);
//				dao.insert(com1);
//				dao.insert(com2);
//			}else{
//				result.setCode(1);
//				result.setSuccess(false);
//			}
//		}else{
//			result.setCode(0);
//			result.setSuccess(false);
//			result.setDescription("input param is null!");
//		}
//		return result;
//	}

	@Override
	public ServiceResultBean TemplateMediaAdd(TemplateMediaBean tMedia) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null != dao.insert(tMedia)){
			Command com = new Command("template_media:add", tMedia.getId(), null, null);
			dao.insert(com);
			return srb;
		}
		srb.setSuccess(false);
		return srb;
	}

	@Override
	public ServiceResultBean delTemplateMedia(Class<TemplateMediaBean> tMedia, String id,
			List<String> list) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null!=dao.delete(tMedia, id, list)){
			if(null!=list && 0!=list.size()){
				List<Command> clist = new ArrayList<Command>();
				for (String tmid : list) {
					Command com = new Command("template_media:delete", tmid, null, null);
					clist.add(com);
				}
				dao.insert(clist);
				return srb;
			}
		}
		srb.setSuccess(false);
		return srb;
	}

	@Override
	public TemplateMediaBean findTemplateMedia(String templateMediaId) {
		
		return (TemplateMediaBean) dao.selectById(TemplateMediaBean.class, templateMediaId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TemplateMediaBean> findTMedia(List<ConditionBean> clist,
			PaginationBean p) {
		
		return (List<TemplateMediaBean>) dao.select(TemplateMediaBean.class, clist, null, false, null, p);
	}

	@Override
	public TemplateMediaBean TemplateMediaUpdate(TemplateMediaBean tm) {
		if(null != dao.update(tm)){
			Command com = new Command("template_media:update", tm.getId(), null, null);
			dao.insert(com);
			return tm;
		}
		return null;
	}

	@Override
	public TemplateBean TemplateUpdate(TemplateBean newTemplate) {
		
		if(null != dao.update(newTemplate)){
			Command com = new Command("album_template:update", newTemplate.getId(), null, null);
			dao.insert(com);
			return newTemplate;
		}
		return null;
	}

}
