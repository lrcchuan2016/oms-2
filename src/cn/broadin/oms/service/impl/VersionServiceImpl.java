package cn.broadin.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.ScreenshotBean;
import cn.broadin.oms.model.VersionSoftBean;
import cn.broadin.oms.model.VersionTerminalBean;
import cn.broadin.oms.service.VersionService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

@Service
public class VersionServiceImpl implements VersionService {

	@Resource
	private DAO dao;

	@Override
	public ServiceResultBean terminalAdd(VersionTerminalBean tSoft) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null != dao.insert(tSoft)) return srb;
		srb.setSuccess(false);
		return srb;
	}

	@Override
	public Object findById(String s, String id) {
		if(s.equals("TerminalSoftBean")) return (VersionTerminalBean) dao.selectById(VersionTerminalBean.class, id);
		if(s.equals("SoftVersionBean")) return dao.selectById(VersionSoftBean.class, id);
		if(s.equals("Screenshot")) return dao.selectById(ScreenshotBean.class, id);
		return null;
	}

	@Override
	public PaginationBean findPage(Class<?> class1,
			List<ConditionBean> clist, Integer i, boolean b, String string,
			PaginationBean pagination) {
		
		return (PaginationBean) dao.select(class1, clist, i, b, string, pagination);
	}

	@Override
	public ServiceResultBean terminalUpdate(VersionTerminalBean tSoft) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null != dao.update(tSoft)) return srb;
		srb.setSuccess(false);
		return srb;
	}

	@Override
	public ServiceResultBean Del(Object obj) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null != dao.delete(obj)) return srb;
		srb.setSuccess(false);
		return srb;
	}

	@Override
	public ServiceResultBean softVersionAdd(VersionSoftBean softVersion) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null != dao.insert(softVersion)) return srb; 
		srb.setSuccess(false);
		return srb;
	}

	@Override
	public ServiceResultBean screenShotAdd(List<ScreenshotBean> screenshots) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null != dao.insert(screenshots)) return srb;
		srb.setSuccess(false);
		return srb;
	}

	@Override
	public ServiceResultBean softVersionUpdate(VersionSoftBean softVersion) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null != dao.update(softVersion)) return srb;
		srb.setSuccess(false);
		return srb;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VersionSoftBean> pageList(Class<VersionSoftBean> class1,
			List<ConditionBean> clist, int i, boolean b, String string,
			PaginationBean pagination) {
		
		return (List<VersionSoftBean>) dao.select(class1, clist, i, b, string, pagination);
	}
}
