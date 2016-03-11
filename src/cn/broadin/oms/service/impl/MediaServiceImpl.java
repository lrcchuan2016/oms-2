package cn.broadin.oms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.AlbumMediaBean;
import cn.broadin.oms.model.Command;
import cn.broadin.oms.model.MediaBean;
import cn.broadin.oms.model.MediaTypeBean;
import cn.broadin.oms.model.TemplateMediaBean;
import cn.broadin.oms.service.MediaService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

@Service
public class MediaServiceImpl implements MediaService{
	@Resource
	private DAO dao;
	@Resource
	private CommonServiceImpl commonService;
	
	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean findPage(List<ConditionBean> arg0,
			PaginationBean arg1) {
		if(arg1 != null) return (PaginationBean) dao.select(MediaBean.class, arg0 , null, false, "createUtc", arg1);
		else {
			List<MediaBean> mbs = (List<MediaBean>) dao.select(MediaBean.class, arg0 , null, false, "createUtc", null);
			PaginationBean pg = new PaginationBean(0,mbs.size(),mbs.size(),mbs);
			return pg;
		}
	}
	@Override
	public ServiceResultBean mediaAdd(MediaBean media) {
		ServiceResultBean result = new ServiceResultBean();
		if(media != null){
			if(null != dao.insert(media)){
				Command com = new Command("media:add", media.getId(), null, null);
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
	public ServiceResultBean mediaEdit(MediaBean arg0) {
		ServiceResultBean result = new ServiceResultBean();		
		if(arg0 != null){
			if(dao.update(arg0) != null){
				Command com = new Command("media:update", arg0.getId(), null, null);
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
	public ServiceResultBean mediaDel(List<String> arg0, String arg1) {
		ServiceResultBean result = new ServiceResultBean();
		if(null != arg0 && null != arg1){
			if(null != dao.delete(MediaBean.class, "id", arg0)){
				commonService.deleteFile(commonService.createOSSClient(), arg1);
				List<Command> clist = new ArrayList<Command>();
				for (String s : arg0) {
					Command com = new Command("media:delete", s, null, null);
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
	public ServiceResultBean alterMediaKey(String arg0, String arg1) {
		ServiceResultBean result = new ServiceResultBean();
		if(arg0 != null&& arg1 != null){
			String hql = "update MediaBean set id='"+arg1+"' where id='"+arg0+"'";
			Object obj = dao.executeHql(hql);
			if(obj != null){
				Command com = new Command("media:update", arg0, null, null);
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
	@SuppressWarnings("unchecked")
	@Override
	public List<MediaTypeBean> findTypeList() {
		return (List<MediaTypeBean>) dao.select(MediaTypeBean.class);
	}
	@Override
	public MediaBean findById(String arg0) {
		return (MediaBean) dao.selectById(MediaBean.class, arg0);
	}
	@Override
	public MediaBean updateMedia(MediaBean media) {
		if(null!=dao.update(media)){
			Command com = new Command("media:update", media.getId(), null, null);
			if(null!=dao.insert(com)) return media;
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AlbumMediaBean> selectAlbumMedia(Class<AlbumMediaBean> class1,
			List<ConditionBean> clist) {
		
		return (List<AlbumMediaBean>) dao.select(class1, clist, null, false, "utc", null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TemplateMediaBean> findTMedia(List<ConditionBean> clist,
			PaginationBean p) {
	
		return (List<TemplateMediaBean>) dao.select(TemplateMediaBean.class, clist, null, true, "utc", null);
	}
	@Override
	public List<MediaBean> findMedia(String[] sContent) {
		List<MediaBean> mList = new ArrayList<MediaBean>();
		if(null != sContent && 0 != sContent.length){
			for (String mediaId : sContent) {
				MediaBean media = (MediaBean) dao.selectById(MediaBean.class, mediaId);
				if(null != media) mList.add(media);
			}
		}
		return mList;
	}
	@Override
	public PaginationBean pageList(Class<MediaBean> class1,
			List<ConditionBean> clist, int i, boolean b, String string,
			PaginationBean paginationBean) {
		return (PaginationBean) dao.select(class1, clist, i, b, string, paginationBean);
	}
	@SuppressWarnings("unchecked")
	@Override
	public MediaBean findMediaByContent(String content) {
		if(null != content && !"".equals(content)){
			List<ConditionBean> clist = new ArrayList<ConditionBean>();
			clist.add(new ConditionBean("content",content,ConditionBean.EQ));
			List<MediaBean> list = (List<MediaBean>) dao.select(MediaBean.class, clist, null, false, null, null);
			if(null != list && 0 != list.size()) return list.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean getAlbumMedia(Class<AlbumMediaBean> class1,
			List<ConditionBean> clist, int i, boolean b, String string,
			PaginationBean paginationBean) {
		
		if(null != paginationBean){
			return (PaginationBean) dao.select(class1, clist, i, b, string, paginationBean);
		}else{
			List<AlbumMediaBean> albumMedias = (List<AlbumMediaBean>) dao.select(class1, clist, i, b, string, paginationBean);
			if(null != albumMedias){
				paginationBean = new PaginationBean(0, albumMedias.size(), albumMedias.size(), albumMedias);
			}
		}
		return null;
	}
	
}
