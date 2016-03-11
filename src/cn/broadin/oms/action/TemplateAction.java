package cn.broadin.oms.action;

import it.sauronsoftware.base64.Base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.TemplateBean;
import cn.broadin.oms.model.TemplateGroupBean;
import cn.broadin.oms.model.TemplateMediaBean;
import cn.broadin.oms.service.TemplateService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.OSSConst;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;
import cn.broadin.oms.util.Tools;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller("templateAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TemplateAction extends ActionSupport{
	private static final long serialVersionUID = 4211813621474919467L;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private Map<String, String> paramMap = new HashMap<String, String>();
	private List<TemplateBean> templateList;
	private List<TemplateGroupBean> templateGroupList;
	private TemplateBean templateBean;
	private TemplateGroupBean templateGroupBean;
	private PaginationBean page;
	private String input = "pages/template/templateList.jsp";
	private String tipMsg;
	
	@Resource
	private TemplateService templateService;
//	@Resource
//	private AlbumService albumService;
//	@Resource
//	private MediaService mediaService;
	
	public String getTipMsg() {
		return tipMsg;
	}
	
	public Map<String, Object> getResultJson() {
		return resultJson;
	}
	
	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	
	public PaginationBean getPage() {
		return page;
	}

	public void setPage(PaginationBean page) {
		this.page = page;
	}
	
	public List<TemplateBean> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<TemplateBean> templateList) {
		this.templateList = templateList;
	}

	public List<TemplateGroupBean> getTemplateGroupList() {
		return templateGroupList;
	}

	public void setTemplateGroupList(List<TemplateGroupBean> templateGroupList) {
		this.templateGroupList = templateGroupList;
	}

	public TemplateBean getTemplateBean() {
		return templateBean;
	}

	public void setTemplateBean(TemplateBean templateBean) {
		this.templateBean = templateBean;
	}

	public TemplateGroupBean getTemplateGroupBean() {
		return templateGroupBean;
	}

	public void setTemplateGroupBean(TemplateGroupBean templateGroupBean) {
		this.templateGroupBean = templateGroupBean;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * 条件查找模版列表
	 * (分组名称，tid，name)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String findTemplatePage() throws Exception{
		if(this.paramMap!=null){
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			String keywords = this.paramMap.get("searchKeyWord");
			String type = this.paramMap.get("type");
			if(type != null){
				if(!"0".equals(type)){
					PaginationBean ps = templateService.findTGPage(null, null);
					List<TemplateGroupBean> dgs = (List<TemplateGroupBean>) ps.getRecords();
					if(dgs!=null  && dgs.size()!=0){
						for(TemplateGroupBean dg : dgs){
							if(dg.getId().equals(type)){
								conditions.add(new ConditionBean("groupId",dg.getId(),ConditionBean.EQ));
								break;
							}
						}
					}
				}
				if(null != keywords && !"".equals(keywords)){
					//conditions.add(new ConditionBean("id","%"+keywords+"%",ConditionBean.LIKE));
					conditions.add(new ConditionBean("name","%"+keywords+"%",ConditionBean.LIKE));
				}
				resultJson.put("list", templateService.findTPage(conditions,null,false,"createUtc", page));
			}else resultJson.put("list", templateService.findTPage(null,null,false,"createUtc", page));
		}else resultJson.put("list", templateService.findTPage(null,null,false,"createUtc", page));
		return Action.SUCCESS;
	}
	
	/**
	 * 查找所有模版分组列表
	 * @return
	 * @throws Exception
	 */
	public String findTemplateGroupPage() throws Exception{
		resultJson.put("list", templateService.findTGPage(null, page));
		return Action.SUCCESS;
	}
	
	/**
	 * 主题模版页面间的跳转控制
	 * @return
	 * @throws Exception
	 */
	public String initTemplatePage() throws Exception{
		if(this.paramMap!=null){
			if(this.paramMap.get("tempType")!=null){
				String tempKey = this.paramMap.get("tempType");
				if(tempKey.equals("tempEdit")){		//模版编辑
					String templateId = this.paramMap.get("templateId");
					this.templateBean = templateService.findTemplateById(templateId);
					this.input = "pages/template/templateEdit.jsp";
				}else if(tempKey.equals("tempGroupEdit")){	//模版分组编辑
					String templateGroupId = this.paramMap.get("templateGroupId");
					this.templateGroupBean = templateService.findTemplateGroupById(templateGroupId);
					this.input = "pages/template/groupEdit.jsp";
				}
			}
		}else{
			this.tipMsg = "传递跳转参数为空，默认为您跳回模版列表页面。";
		}
		return Action.INPUT;
	}
	
	/**
	 * 添加模版
	 * @return
	 * @throws Exception
	 */
	public String addTemplate() throws Exception{
		if(templateList!=null && templateList.size()!=0){
			TemplateBean bean = templateList.get(0);
			bean.setCreateUtc(new Date().getTime());
			ServiceResultBean result = templateService.TemplateAdd(bean);
			if(result.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "Add template success.");
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Add template failed,"+result.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Add Template Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除模版
	 * @return
	 * @throws Exception
	 */
	public String delTemplate() throws Exception{
		if(templateList!=null && templateList.size()!=0){
			String id = this.templateList.get(0).getId();
			List<String> ids = new ArrayList<String>();
			ids.add(id);
			ServiceResultBean result = templateService.TemplateDel(ids,"id");
			if(result.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "Delete template success.");
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Delete template failed,"+result.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Delete template Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 添加模版分组
	 * @return
	 * @throws Exception
	 */
	public String addTemplateGroup() throws Exception{
		if(templateGroupList!=null && templateGroupList.size()!=0){
			TemplateGroupBean bean = templateGroupList.get(0);
			bean.setCreateUtc(new Date().getTime());
			ServiceResultBean result = templateService.TemplateGroupAdd(bean);
			if(result.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "Add template group success.");
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Add template group failed,"+result.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Add Template group Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除模版分组
	 * @return
	 * @throws Exception
	 */
	public String delTemplateGroup() throws Exception{
		if(templateGroupList!=null && templateGroupList.size()!=0){
			String id = this.templateGroupList.get(0).getId();
			List<String> ids = new ArrayList<String>();
			ids.add(id);
			String iconKey = this.templateGroupList.get(0).getIconUrl().split(OSSConst.TEMPLATE_GROUP_KEY_HEAD)[1];
			iconKey = OSSConst.TEMPLATE_GROUP_KEY_HEAD+iconKey;
			ServiceResultBean result = templateService.TemplateGroupDel(ids, iconKey);
			if(result.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "Delete template group success.");
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Delete template group failed,"+result.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Delete template group failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 编辑模版
	 * @return
	 * @throws Exception
	 */
	public String editTemplate() throws Exception{
		if(templateList!=null && templateList.size()!=0){
			TemplateBean bean = templateList.get(0);
			bean.setLastModifyUtc(Tools.getNowUTC());
//			if(!paramMap.get("templateEdit").equals(paramMap.get("mid"))){	//说明主键值更改了
//				ServiceResultBean resultPrimary = templateService.alterTemplatePrimaryKey(paramMap.get("mid"), paramMap.get("templateEdit"));
//				if(!resultPrimary.isSuccess()){
//					resultJson.put("result", "00000000");
//					resultJson.put("tip","Edit decorate failed.");
//					return Action.SUCCESS;
//				}
//			}
//			TemplateBean temBean = templateService.findTemplateById(bean.getId());
//			temBean.setCoverUrl(bean.getCoverUrl());
//			temBean.setGroupId(bean.getGroupId());
//			temBean.setIntroduction(bean.getIntroduction());
//			temBean.setLastModifyUtc(bean.getLastModifyUtc());
//			temBean.setName(bean.getName());
			ServiceResultBean result = templateService.TemplateEdit(bean);
			if(result.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "Edit template success.");
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Edit template failed,"+result.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Edit Template Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 编辑模版分组
	 * @return
	 * @throws Exception
	 */
	public String editTemplateGroup() throws Exception{
		if(templateGroupList!=null && templateGroupList.size()!=0){
			TemplateGroupBean bean = templateGroupList.get(0);
//			if(!paramMap.get("templateGroupEdit").equals(paramMap.get("mid"))){			//说明主键值更改了
//				ServiceResultBean resultPrimary = templateService.alterTemplateGroupPrimaryKey(paramMap.get("mid"), paramMap.get("templateGroupEdit"));
//				if(!resultPrimary.isSuccess()){
//					resultJson.put("result", "00000001");
//					resultJson.put("tip","Edit decorate failed.");
//					return Action.SUCCESS;
//				}
//			}
//			TemplateGroupBean tgBean = templateService.findTemplateGroupById(bean.getId());
//			tgBean.setIconUrl(bean.getIconUrl());
//			tgBean.setName(bean.getName());
//			tgBean.setDetail(bean.getDetail());
			ServiceResultBean result = templateService.TemplateGroupEdit(bean);
			if(result.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "Edit templateGroup success.");
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Edit templateGroup failed,"+result.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Edit TemplateGroup Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 获取xml文件的预览图
	 * @return
	 */
	public String findTemplateInfo(){
		if(null != paramMap.get("templateId")){
			TemplateBean template = templateService.findTemplateById(paramMap.get("templateId"));
			if(null != template){
				List<ConditionBean> clist = new ArrayList<ConditionBean>();
				clist.add(new ConditionBean("template", template, ConditionBean.EQ));
				clist.add(new ConditionBean("type", "4", ConditionBean.EQ));
				List<TemplateMediaBean> tmList = templateService.findTMedia(clist,null);
				if(null != tmList && 0 != tmList.size()){
					String templateXml = tmList.get(0).getMedia().getContent();
					List<String> url = this.getPreview(templateXml);
					resultJson.put("result", "00000000");
					resultJson.put("url", url);
				}else{
					resultJson.put("result", "00000001");
				}
			}else{
				resultJson.put("result", "00000001");
			}
		}else resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}

	/**
	 * 解析xml获取预览图
	 * @param templateXml
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	private List<String> getPreview(String templateXml) {
		List<String> slist = new ArrayList<String>();
		try {
			Base64 base64 = new Base64();
			String xml = base64.decode(templateXml, "UTF-8");
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			List elements = root.element("previews").elements("preview");
			Iterator it = elements.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String s = e.attributeValue("url");
				slist.add(s);
			}
			Element sound = root.element("sound");
			Attribute url = sound.attribute("url");
			if(null != url && null != url.getValue()) slist.add(url.getValue());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return slist;
	}
//	
//	public String update(){
//		List<ConditionBean> clist = new ArrayList<ConditionBean>();
//		clist.add(new ConditionBean("type","4",ConditionBean.EQ));
//		List<TemplateMediaBean> tmList = templateService.findTMedia(clist,null);
//		for (TemplateMediaBean tm : tmList) {
//			//if(null != templateService.TemplateMediaUpdate(tm)){
//			if(null != mediaService.updateMedia(tm.getMedia())){
//				resultJson.put("result", "00000000");
//			}else resultJson.put("result", "00000001");
//		}
//		return Action.SUCCESS;
//	}
	
//	/**
//	 * 修改xml
//	 * @return
//	 */
//	@SuppressWarnings({ "static-access", "unchecked" })
//	public String editXml(){
//		List<String> ids = new ArrayList<String>();
//		List<ConditionBean> conditions = new ArrayList<ConditionBean>();
//		conditions.add(new ConditionBean("type", "4", ConditionBean.EQ));
//		List<TemplateMediaBean> tms = templateService.findTMedia(conditions, null);
//		Map<String, String[]> templateMap = new HashMap<String, String[]>();
//		for (TemplateMediaBean tm : tms) {
//			String base64Xml = tm.getMedia().getContent();
//			Base64 base64 = new Base64();
//			String xml = base64.decode(base64Xml, "UTF-8");
//			try {
//				Document document = DocumentHelper.parseText(xml);
//				Element root  = document.getRootElement();
//				String xPath = "//background";
//				List<Element> elements = root.selectNodes(xPath);
//				String bgUrl = elements.get(0).attributeValue("url");
//				String tid = root.elementText("tid");
//				String tname = root.elementText("name");
//				
//				String[] strs = new String[2];
//				strs[0] = tid;
//				strs[1] = tname;
//				
//				templateMap.put(bgUrl, strs);
//			} catch (DocumentException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		//修改
//		conditions.clear();
//		conditions.add(new ConditionBean("albumType", "1", ConditionBean.EQ));
//		conditions.add(new ConditionBean("media", null, ConditionBean.IS_NOT_NULL));
//		List<AlbumMediaBean> ams = (List<AlbumMediaBean>) ((PaginationBean)albumService.pageList(AlbumMediaBean.class, conditions, null, false, null, null)).getRecords();
//		int successN = 0;
//		int failNum = 0;
//		for (AlbumMediaBean am : ams) {
//			try{
//			Hibernate.initialize(am.getMedia());
//			}catch (Exception e) {
//				continue;
//			}
//			MediaBean media = am.getMedia();
//			if(null != media && null != media .getContent()){
//				try {
//				System.out.println(am.getId());
//				Base64 base64 = new Base64();
//				String xml = base64.decode(media.getContent(), "UTF-8");
//		
//				Document document = DocumentHelper.parseText(xml);
//				Element root = document.getRootElement();
//				String xPath = "//background";
//				List<Element> elements = root.selectNodes(xPath);
//				if(null != elements && 0 != elements.size()){
//					String url = elements.get(0).attributeValue("url");
//					
//					String[] s = templateMap.get(url);
//					if(s != null){
//						Element tid = root.element("tid");
//						tid.setText(s[0]);
//						
//						Element tname = root.element("tname");
//						if(tname == null) tname = root.addElement("tname");
//						tname.setText(s[1]);
//						
//						String content = base64.encode(document.asXML(), "UTF-8");
//						
//						am.getMedia().setContent(content);
//						
//						if(null != albumService.updateAlbumMedia(am)){
//							successN++;
//							System.out.println(successN);
//						}else ids.add(am.getMedia().getId());
//					}else{
//						failNum++;
//						ids.add(am.getMedia().getId());
//					}
//				}
//				} catch (DocumentException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		resultJson.put("result", "00000000");
//		resultJson.put("successN", successN);
//		resultJson.put("failNum", failNum);
//		resultJson.put("n", ams.size());
//		resultJson.put("list", ids);
//		return Action.SUCCESS;
//	}
	
//	@SuppressWarnings("static-access")
//	public String editXml(){
//	List<ConditionBean> conditions = new ArrayList<ConditionBean>();
//	conditions.add(new ConditionBean("type", "4", ConditionBean.EQ));
//	List<TemplateMediaBean> tms = templateService.findTMedia(conditions, null);
//	for (TemplateMediaBean tm : tms) {
//		String base64Xml = tm.getMedia().getContent();
//		Base64 base64 = new Base64();
//		String xml = base64.decode(base64Xml, "UTF-8");
//		try {
//			Document document = DocumentHelper.parseText(xml);
//			Element root  = document.getRootElement();
//			String name = root.elementText("name");
//			Element tname = root.addElement("tname");
//			tname.setText(name);
//			String content = base64.encode(document.asXML(), "UTF-8");
//			tm.getMedia().setContent(content);
//			 TemplateMediaBean tMedia = templateService.TemplateMediaUpdate(tm);
//			 if(null != tMedia){
//				 resultJson.put("result", "00000000");
//			 }else resultJson.put("result", "00000001");
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//	}
//	return Action.SUCCESS;
//}
	
	/**
	 * 模板资源下载
	 * @return
	 */
	public String templateDownload(){
		if(null != paramMap.get("id")){
			String id = paramMap.get("id");
			TemplateBean template = templateService.findTemplateById(id);
			if(null != template){
				List<ConditionBean> conditions = new ArrayList<ConditionBean>();
				conditions.add(new ConditionBean("template", template, ConditionBean.EQ));
				conditions.add(new ConditionBean("type", "2", ConditionBean.EQ));
				List<TemplateMediaBean> tms = templateService.findTMedia(conditions, null);
				if(null != tms && 0 != tms.size()){
					String contextPath = ServletActionContext.getServletContext().getRealPath("/");
					ZipOutputStream zip = null;
					File f = new File(contextPath+"FileResource"+File.separator+template.getName()+".zip");
					try {
						zip = new ZipOutputStream(new FileOutputStream(f));
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					for (TemplateMediaBean tMedia: tms) {
						String url = tMedia.getMedia().getContent();
						File file = new File(Tools.getUrlFile(url));
						//
						Tools.getZip(zip, file);
						file.delete();
					}
					
					try {
						zip.finish();
						zip.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					resultJson.put("result", "00000000");
					resultJson.put("path", "FileResource"+f.getPath().split("FileResource")[1]);
					return Action.SUCCESS;
				}
			}
		}
		
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 排序
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String sortTemplate(){
		
		if(paramMap.containsKey("ids") && paramMap.containsKey("flag")){
		//找出要更新的模板
			String[] ids = paramMap.get("ids").split(",");
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			Map<String, TemplateBean> templateMap = new HashMap<String, TemplateBean>();
			for (String id : ids) {
				conditions.add(new ConditionBean("id", id, ConditionBean.EQ));
			}
			PaginationBean paginations = templateService.findTPage(conditions, 1, false, null, null);
			if(null != paginations){
				List<TemplateBean> templates = (List<TemplateBean>) paginations.getRecords();
				for (TemplateBean templateBean : templates) {
					//放入map
					templateMap.put(templateBean.getId(), templateBean);
				}
				
				if("0".equals(paramMap.get("flag"))){
					long newElementUtc = templateMap.get(ids[0]).getCreateUtc();
					int len = ids.length;
					for(int i = 0;i<len;i++){
						TemplateBean template = templateMap.get(ids[i]);
						if((i+1)<len){
							template.setCreateUtc(templateMap.get(ids[i+1]).getCreateUtc());
						}else template.setCreateUtc(newElementUtc);
						template.setLastModifyUtc(Tools.getNowUTC());
						if(null == templateService.TemplateUpdate(template)){
							resultJson.put("result", "00000001");
							return Action.SUCCESS;
						}
					}
				}else if("1".equals(paramMap.get("flag"))){
					int len = ids.length;
					long newElementUtc = templateMap.get(ids[len-1]).getCreateUtc();
					for (int i = len-1; i>=0; i--) {
						TemplateBean template = templateMap.get(ids[i]);
						if(i-1>=0)
							template.setCreateUtc(templateMap.get(ids[i-1]).getCreateUtc());
						else template.setCreateUtc(newElementUtc);
						template.setLastModifyUtc(Tools.getNowUTC());
						if(null == templateService.TemplateUpdate(template)){
							resultJson.put("result", "00000001");
							return Action.SUCCESS;
						}
					}
				}else{
					resultJson.put("result", "00000002");
					resultJson.put("tip", "error param");
					return Action.SUCCESS;
				} 
				
				resultJson.put("result", "00000000");
				return Action.SUCCESS;
			}
	
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
//	@SuppressWarnings("unchecked")
//	public String update(){
//		PaginationBean paginations = templateService.findTPage(null, null, false, null, null);
//		if(null != paginations){
//			List<TemplateBean> templates = (List<TemplateBean>) paginations.getRecords();
//			for(int i = 0;i<templates.size();i++){
//				TemplateBean template = templates.get(i);
//				template.setCreateUtc(template.getCreateUtc()+(i*100));
//				if(null == templateService.TemplateUpdate(template)){
//					resultJson.put("result", "00000001");
//					return Action.SUCCESS;
//				}
//			}
//			resultJson.put("result", "00000000");
//			return Action.SUCCESS;
//		}
//		resultJson.put("result", "00000001");
//		return Action.SUCCESS;
//	}
}
