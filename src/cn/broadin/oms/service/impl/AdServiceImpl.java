package cn.broadin.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.AdLocationBean;
import cn.broadin.oms.model.AdResourceBean;
import cn.broadin.oms.model.Command;
import cn.broadin.oms.service.AdService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

/**
 * 广告业务处理实现类
 * @author huchanghuan
 *
 */
@Service
public class AdServiceImpl implements AdService {

	@Resource
	private DAO dao;
	@Resource
	private CommonServiceImpl commonService;

	@Override
	public AdLocationBean add(List<ConditionBean> clist, AdLocationBean adLocationBean) {
			if(null != dao.insert(clist, adLocationBean)){ 
				Command command = new Command("ad_location:add", adLocationBean.getId(), null, null);
				dao.insert(command);
				return adLocationBean;
			}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdLocationBean> findAllLocation(Class<AdLocationBean> class1) {
		
		return (List<AdLocationBean>) dao.select(class1);
	}

	@Override
	public AdLocationBean selectById(Class<AdLocationBean> class1,String id) {
		
		return (AdLocationBean) dao.selectById(class1, id);
	}

	@Override
	public AdResourceBean addResource(AdResourceBean adResourceBean) {
		if(null != dao.insert(adResourceBean)){
			Command command = new Command("ad_resource:add", adResourceBean.getId(), null, null);
			dao.insert(command);
			return adResourceBean;
		}
		return null;
	}

	public AdLocationBean delAdLocation(AdLocationBean adLocationBean) {
		if(null != dao.delete(adLocationBean)){
				Command command = new Command("ad_location:delete", adLocationBean.getId(), null, null);
				dao.insert(command);
			return adLocationBean;
		}
		return null;
	}

	@Override
	public AdLocationBean update(List<ConditionBean> clist,AdLocationBean adLocationBean) {
		if(null != clist){
			if(null != dao.update(clist, adLocationBean)){
				Command command = new Command("ad_location:update", adLocationBean.getId(), null, null);
				dao.insert(command);
				return adLocationBean;
			}
		}else{
			if(null != dao.update(adLocationBean)){
				Command command = new Command("ad_location:update", adLocationBean.getId(), null, null);
				dao.insert(command);
				return adLocationBean;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdResourceBean> findAllResource(Class<AdResourceBean> class1,
			List<ConditionBean> list, Integer i, boolean b, String string,
			PaginationBean p) {
		
		return (List<AdResourceBean>) dao.select(class1, list, i, b, string, p);
	}

	@Override
	public AdResourceBean selectResourceById(Class<AdResourceBean> class1,
			String id) {
		
		return (AdResourceBean) dao.selectById(class1, id);
	}

	@Override
	public ServiceResultBean delAdResource(AdResourceBean adResourceBean,String key) {
		ServiceResultBean result = new ServiceResultBean();
		if(null!=adResourceBean && null!=key){
			if(null!=dao.delete(adResourceBean)){
				if(!"".equals(key)) commonService.deleteFile(commonService.createOSSClient(), key);
				Command command = new Command("ad_resource:delete", adResourceBean.getId(), null, null);
				dao.insert(command);
			}else{
				result.setSuccess(false);
			}
		}else{
			if(null==adResourceBean){
				commonService.deleteFile(commonService.createOSSClient(), key);
			}else{
				result.setSuccess(false);
			}
		}
		return result;
	}

	@Override
	public AdResourceBean updateAdResource(AdResourceBean adResourceBean) {
		if(null!=dao.update(adResourceBean)){
			commonService.deleteFile(commonService.createOSSClient(), adResourceBean.getId());
			Command command = new Command("ad_resource:update", adResourceBean.getId(), null, null);
			dao.insert(command);
			return adResourceBean;
		}
		return null;
	}

}
