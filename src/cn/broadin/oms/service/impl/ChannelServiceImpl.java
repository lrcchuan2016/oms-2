package cn.broadin.oms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.ChannelBean;
import cn.broadin.oms.model.ChannelCoverBean;
import cn.broadin.oms.model.DecorateBean;
import cn.broadin.oms.service.ChannelService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

@Service
public class ChannelServiceImpl implements ChannelService {
	@Resource
	private DAO dao;
	@Resource
	private CommonServiceImpl commonService;
	
	@Override
	public ServiceResultBean add(ChannelBean arg0) {
		ServiceResultBean result = new ServiceResultBean();
		if(arg0 != null){
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			conditions.add(new ConditionBean("id",arg0.getId(),ConditionBean.EQ));
			if(dao.insert(conditions,arg0) != null) return result;
			result.setCode(1);
			result.setSuccess(false);
		}else{
			result.setCode(0);
			result.setSuccess(false);
			result.setDescription("input param is null!");
		}
		return result;
	}

	@Override
	public ServiceResultBean updateChannel(ChannelBean arg0) {
		ServiceResultBean result = new ServiceResultBean();		
		if(arg0 != null){
			if(dao.update(arg0) != null) return result;
			result.setCode(1);
			result.setSuccess(false);
		}else{
			result.setCode(0);
			result.setSuccess(false);
			result.setDescription("input param is null!");
		}
		return result;
	}

	@Override
	public ServiceResultBean del(List<ChannelBean> arg0) {
		ServiceResultBean result = new ServiceResultBean();
		if(null != arg0){
			List<String> ids = new ArrayList<String>();
			List<String> keys = new ArrayList<String>();
			for(ChannelBean cb : arg0) {
				ids.add(cb.getId());
				keys.add(cb.getLogoKey());
			}
			if(null != dao.delete(DecorateBean.class, "id", ids)){
				commonService.deleteFiles(commonService.createOSSClient(), ids);
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
		return null;
	}

	@Override
	public ChannelBean findById(String arg0) {
		return (ChannelBean) dao.selectById(ChannelBean.class, arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelBean> findAll() {
		return (List<ChannelBean>) dao.select(ChannelBean.class);
	}

	@Override
	public Object findPage(Class<?> class1,
			List<ConditionBean> arg0, int i, boolean b, String string,
			PaginationBean arg1) {
		
		return  dao.select(class1, arg0, i, b, string, arg1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> selectConditions(Class<?> c,
			List<ConditionBean> clist, Integer i, boolean b, String string,
			PaginationBean p) {
		
		return (List<ChannelCoverBean>) dao.select(c, clist, i, b, string, p);
	}

	@Override
	public ServiceResultBean update(Object c) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null == dao.update(c)) srb.setSuccess(false);
		return srb ;
	}

	@Override
	public ServiceResultBean addData(Object inst) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null == dao.insert(inst)) srb.setSuccess(false);
		return srb;
	}

}
