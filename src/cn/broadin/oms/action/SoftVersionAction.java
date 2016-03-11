package cn.broadin.oms.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.VersionSoftBean;
import cn.broadin.oms.model.VersionTerminalBean;
import cn.broadin.oms.service.VersionService;
import cn.broadin.oms.util.PaginationBean;

import com.opensymphony.xwork2.Action;

@Controller("softVersionAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SoftVersionAction implements ServletResponseAware{
	
	@Resource
	private VersionService versionService;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private PaginationBean pagination;
	private HttpServletResponse resp;
	
	
	public PaginationBean getPagination() {
		return pagination;
	}

	public void setPagination(PaginationBean pagination) {
		this.pagination = pagination;
	}

	public VersionService getVersionService() {
		return versionService;
	}

	public void setVersionService(VersionService versionService) {
		this.versionService = versionService;
	}

	public Map<String, Object> getResultJson() {
		return resultJson;
	}

	public void setResultJson(Map<String, Object> resultJson) {
		this.resultJson = resultJson;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.resp = arg0;
	}
	

	/**
	 * 获得各个最新的版本
	 * @return
	 * @throws IOException 
	 */
	public String getVersionList() throws IOException{
		resp.setHeader("Access-Control-Allow-Origin","*");
			List<VersionSoftBean> versions = (List<VersionSoftBean>) versionService.pageList(VersionSoftBean.class, null, 0, false, "createUtc", pagination);
			if(null != versions){
				Map<String, VersionTerminalBean> map = new HashMap<String, VersionTerminalBean>();
				for (VersionSoftBean versionSoft : versions) {
					String id = versionSoft.getVt().getId();
					VersionTerminalBean terminal = versionSoft.getVt();
					if(null == map.get(id)){
						terminal.setSoftUrl(versionSoft.getSoftUrl());
						terminal.setSvnUrl(versionSoft.getSvnUrl());
						map.put(id, terminal);
					}else continue;
				}
				resultJson.put("version", map);
				
			}
		return Action.SUCCESS;
	}

	

	
}
