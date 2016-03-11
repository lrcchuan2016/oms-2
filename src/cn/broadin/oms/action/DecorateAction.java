package cn.broadin.oms.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.DecorateBean;
import cn.broadin.oms.model.DecorateGroupBean;
import cn.broadin.oms.model.ManagerBean;
import cn.broadin.oms.model.MediaBean;
import cn.broadin.oms.service.DecorateService;
import cn.broadin.oms.service.MediaService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.OSSConst;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;
import cn.broadin.oms.util.Tools;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller("decorateAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DecorateAction extends ActionSupport{
	private static final long serialVersionUID = -1520149460084363294L;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private Map<String, String> paramMap = new HashMap<String, String>();
	private List<DecorateBean> decorateList;
	private List<DecorateGroupBean> decorateGroupList;
	private DecorateBean decorateBean;
	private DecorateGroupBean decorateGroupBean;
	private PaginationBean page;
	private String input = "pages/decorate/decorateList.jsp";
	private String tipMsg;
	
	@Resource
	private DecorateService decorateService;
	@Resource
	private MediaService mediaService;
	
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
	
	public List<DecorateBean> getDecorateList() {
		return decorateList;
	}

	public void setDecorateList(List<DecorateBean> decorateList) {
		this.decorateList = decorateList;
	}

	public List<DecorateGroupBean> getDecorateGroupList() {
		return decorateGroupList;
	}

	public void setDecorateGroupList(List<DecorateGroupBean> decorateGroupList) {
		this.decorateGroupList = decorateGroupList;
	}

	public DecorateBean getDecorateBean() {
		return decorateBean;
	}

	public void setDecorateBean(DecorateBean decorateBean) {
		this.decorateBean = decorateBean;
	}

	public DecorateGroupBean getDecorateGroupBean() {
		return decorateGroupBean;
	}

	public void setDecorateGroupBean(DecorateGroupBean decorateGroupBean) {
		this.decorateGroupBean = decorateGroupBean;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	/**
	 * 查找所有装饰品列表
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String findDecoratePage() throws Exception{
		if(this.paramMap!=null){
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			String type = this.paramMap.get("type");
			String keywords = this.paramMap.get("searchKeyWord");
			if(!"0".equals(type)){
				List<DecorateGroupBean> dgList = (List<DecorateGroupBean>) decorateService.findDGPage(null, null).getRecords();
				for (DecorateGroupBean dg : dgList) {
					if(dg.getId().equals(type)) {
						conditions.add(new ConditionBean("group", dg, ConditionBean.EQ));
						break;
					}
				}
				//resultJson.put("list", decorateService.findDPage(conditions, page));
			}
			if(null != keywords && !"".equals(keywords)) conditions.add(new ConditionBean("name", "%"+keywords+"%", ConditionBean.LIKE));
			resultJson.put("list", decorateService.findDPage(conditions,null,false,"createUtc",page));
		}else resultJson.put("list", decorateService.findDPage(null,null,false,"createUtc",page));
		return Action.SUCCESS;
	}
	
	/**
	 * 查找所有装饰品分组列表
	 * @return
	 * @throws Exception
	 */
	public String findDecorateGroupPage() throws Exception{
		resultJson.put("list", decorateService.findDGPage(null, page));
		return Action.SUCCESS;
	}
	
	/**
	 * 主题装饰品页面间的跳转控制
	 * @return
	 * @throws Exception
	 */
	public String initDecoratePage() throws Exception{
		if(this.paramMap!=null){
			if(this.paramMap.get("decorateType")!=null){
				String tempKey = this.paramMap.get("decorateType");
				if(tempKey.equals("decorateEdit")){		//装饰品编辑
					String decorateId = this.paramMap.get("decorateId");
					this.decorateBean = decorateService.findDecorateById(decorateId);
					this.input = "pages/decorate/decorateEdit.jsp";
				}else if(tempKey.equals("decorateGroupEdit")){	//装饰品分组编辑
					String decorateGroupId = this.paramMap.get("decorateGroupId");
					this.decorateGroupBean = decorateService.findDecorateGroupById(decorateGroupId);
					this.input = "pages/decorate/groupEdit.jsp";
				}
			}
		}else{
			this.tipMsg = "传递跳转参数为空，默认为您跳回装饰品列表页面。";
		}
		return Action.INPUT;
	}
	
	/**
	 * 添加装饰品
	 * @return
	 * @throws Exception
	 */
	public String addDecorate() throws Exception{
		if(decorateList!=null && decorateList.size()!=0){
			DecorateBean bean = decorateList.get(0);
			String userId = "OMSADMIN"+((ManagerBean)ActionContext.getContext().getSession().get("admin")).getId();
			MediaBean media = new MediaBean(Tools.getUUIDString(""), 0, bean.getUrl(), "", "", bean.getWidth(), bean.getHeight(), bean.getByteLen(), 0, "0", userId, userId, Tools.getNowUTC(), Tools.getNowUTC());
			DecorateGroupBean dgBean = decorateService.findDecorateGroupById(bean.getGroupName());
			if(null != mediaService.mediaAdd(media) && null != dgBean){
				bean.setCreateUtc(new Date().getTime());
				bean.setGroup(dgBean);
				bean.setMedia(media);
				ServiceResultBean result = decorateService.DecorateAdd(bean);
				if(result.isSuccess()){
					resultJson.put("result", "00000000");
					resultJson.put("tip", "Add decorate success.");
				}else {
					resultJson.put("result", "00000001");
					resultJson.put("tip", "Add decorate failed,"+result.getDescription());
				}
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Add decorate Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除装饰品
	 * @return
	 * @throws Exception
	 */
	public String delDecorate() throws Exception{
		if(decorateList!=null && decorateList.size()!=0){
			String id = this.decorateList.get(0).getId();
			List<String> ids = new ArrayList<String>();
			ids.add(id);
			String urlKey = this.decorateList.get(0).getUrl().split(OSSConst.ALIYUN_IMAGE_DOMAIN)[1];
			DecorateBean decorate = decorateService.findDecorateById(id);
			String mediaId = decorate.getMedia().getId(); 
			ServiceResultBean result = decorateService.DecorateDel(ids, urlKey);
			if(null != decorate){
				ids.clear();
				ids.add(mediaId);
				ServiceResultBean srb = mediaService.mediaDel(ids, urlKey);
				if(result.isSuccess() && srb.isSuccess()){
					resultJson.put("result", "00000000");
					resultJson.put("tip", "Delete decorate success.");
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000001");
		resultJson.put("tip","Delete decorate Failed,input param is null");
		return Action.SUCCESS;
	}
	
	/**
	 * 添加装饰品分组
	 * @return
	 * @throws Exception
	 */
	public String addDecorateGroup() throws Exception{
		if(decorateGroupList!=null && decorateGroupList.size()!=0){
			DecorateGroupBean bean = decorateGroupList.get(0);
			bean.setCreateUtc(new Date().getTime());
			ServiceResultBean result = decorateService.DecorateGroupAdd(bean);
			if(result.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "Add decorate group success.");
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Add decorate group failed,"+result.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Add decorate group Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除装饰品分组
	 * @return
	 * @throws Exception
	 */
	public String delDecorateGroup() throws Exception{
		if(decorateGroupList!=null && decorateGroupList.size()!=0){
			String id = this.decorateGroupList.get(0).getId();
			List<String> ids = new ArrayList<String>();
			ids.add(id);
			String iconKey = this.decorateGroupList.get(0).getIconUrl().split(OSSConst.DECORATE_KEY_GROUP_HEAD)[1];
			iconKey = OSSConst.DECORATE_KEY_GROUP_HEAD+iconKey;
			ServiceResultBean result = decorateService.DecorateGroupDel(ids, iconKey);
			if(result.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "Delete decorate group success.");
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Delete decorate group failed,"+result.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Delete decorate group failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 编辑装饰品
	 * @return
	 * @throws Exception
	 */
	public String editDecorate() throws Exception{
		if(decorateList!=null && decorateList.size()!=0){
			DecorateBean bean = decorateList.get(0);
			if(!paramMap.get("decorateEdit").equals(paramMap.get("mid"))){			//说明主键值更改了
				ServiceResultBean resultPrimary = decorateService.alterDecoratePrimaryKey(paramMap.get("mid"), paramMap.get("decorateEdit"));
				if(!resultPrimary.isSuccess()){
					resultJson.put("result", "00000001");
					resultJson.put("tip","Edit decorate failed.");
					return Action.SUCCESS;
				}
			}
			DecorateBean dtBean = decorateService.findDecorateById(bean.getId());
			DecorateGroupBean dgBean = decorateService.findDecorateGroupById(bean.getGroupName());
			if(null != dtBean && null != dgBean){
				dtBean.getMedia().setWidth(bean.getWidth());
				dtBean.getMedia().setHeight(bean.getHeight());
				dtBean.getMedia().setContent(bean.getUrl());
				dtBean.getMedia().setModifyUtc(Tools.getNowUTC());
				dtBean.setName(bean.getName());
				dtBean.setGroup(dgBean);
				ServiceResultBean result = decorateService.DecorateEdit(dtBean);
				if(result.isSuccess()){
					resultJson.put("result", "00000000");
					resultJson.put("tip", "Edit decorate success.");
				}else {
					resultJson.put("result", "00000001");
					resultJson.put("tip", "Edit decorate failed,"+result.getDescription());
				}
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Edit decorate Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 装饰品分组编辑
	 * @return
	 * @throws Exception
	 */
	public String editDecorateGroup() throws Exception{
		if(decorateGroupList!=null && decorateGroupList.size()!=0){
			DecorateGroupBean bean = decorateGroupList.get(0);
			if(!paramMap.get("decorateGroupEdit").equals(paramMap.get("mid"))){			//说明主键值更改了
					ServiceResultBean resultPrimary = decorateService.alterDecorateGroupPrimaryKey(paramMap.get("mid"), paramMap.get("decorateGroupEdit"));
					if(!resultPrimary.isSuccess()){
						resultJson.put("result", "00000001");
						resultJson.put("tip","Edit decorate failed.");
						return Action.SUCCESS;
					}
			}
			DecorateGroupBean dgBean = decorateService.findDecorateGroupById(bean.getId());
			dgBean.setDetail(bean.getDetail());
			dgBean.setIconUrl(bean.getIconUrl());
			dgBean.setName(bean.getName());
			ServiceResultBean result = decorateService.DecorateGroupEdit(dgBean);
			if(result.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "Edit decorateGroup success.");
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Edit decorateGroup failed,"+result.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Edit decorateGroup Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 拖动排序
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String sortDecorate(){
		if(paramMap.containsKey("ids") && paramMap.containsKey("flag")){
			String flag = paramMap.get("flag");
			String[] ids = paramMap.get("ids").split(",");
			
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			for (String id : ids) {conditions.add(new ConditionBean("id", id, ConditionBean.EQ));}
			PaginationBean pagination = decorateService.findDPage(conditions, 1, false, "createUtc", null);
			
			if(null != pagination && null != pagination.getRecords() && !pagination.getRecords().isEmpty()){
				List<DecorateBean> decorates = (List<DecorateBean>) pagination.getRecords();
				Map<String, DecorateBean> decorateMap = new HashMap<String, DecorateBean>();
				for (DecorateBean decorate : decorates) {
					decorateMap.put(decorate.getId(), decorate);
				}
				
				long newElementUtc = 0;
				int len = ids.length;
				if("0".equals(flag)){
					newElementUtc = decorateMap.get(ids[0]).getCreateUtc();
					for(int i=0;i<len;i++){
						DecorateBean decorate = decorateMap.get(ids[i]);
						if((i+1)<len){
							decorate.setCreateUtc(decorateMap.get(ids[i+1]).getCreateUtc());
						}else
							decorate.setCreateUtc(newElementUtc);
						
						if(!decorateService.DecorateEdit(decorate).isSuccess()){
							resultJson.put("result", "00000001");
							return Action.SUCCESS;
						}
						
					}
				}else{
					newElementUtc = decorateMap.get(ids[len-1]).getCreateUtc();
					for(int i=len-1;i>=0;i--){
						DecorateBean decorate = decorateMap.get(ids[i]);
						if((i-1)>=0){
							decorate.setCreateUtc(decorateMap.get(ids[i-1]).getCreateUtc());
						}else
							decorate.setCreateUtc(newElementUtc);
						
						if(!decorateService.DecorateEdit(decorate).isSuccess()){
							resultJson.put("result", "00000001");
							return Action.SUCCESS;
						}
						
					}
				}
				resultJson.put("result", "00000000");
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
}
