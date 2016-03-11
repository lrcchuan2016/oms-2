package cn.broadin.oms.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.CommonMediaBean;
import cn.broadin.oms.model.ManagerBean;
import cn.broadin.oms.model.MediaBean;
import cn.broadin.oms.service.CommonMediaService;
import cn.broadin.oms.service.MediaService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;
import cn.broadin.oms.util.Tools;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller("mediaAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MediaAction extends ActionSupport{
	private static final long serialVersionUID = 4211813621474919467L;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private Map<String, String> paramMap = new HashMap<String, String>();
	private List<MediaBean> mediaList;
	private List<CommonMediaBean> commonMedias;
	private MediaBean mediaBean;
	private CommonMediaBean commonMedia;
	private PaginationBean page;
	private String input = "pages/media/meidaList.jsp";
	private String tipMsg;
	
	@Resource
	private MediaService mediaService;
	@Resource
	private CommonMediaService cMediaService;
	
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

	public MediaBean getMediaBean() {
		return mediaBean;
	}

	public void setMediaBean(MediaBean mediaBean) {
		this.mediaBean = mediaBean;
	}

	public List<MediaBean> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<MediaBean> mediaList) {
		this.mediaList = mediaList;
	}
	
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	public List<CommonMediaBean> getCommonMedias() {
		return commonMedias;
	}

	public void setCommonMedias(List<CommonMediaBean> commonMedias) {
		this.commonMedias = commonMedias;
	}
	public CommonMediaBean getCommonMedia() {
		return commonMedia;
	}

	public void setCommonMedia(CommonMediaBean commonMedia) {
		this.commonMedia = commonMedia;
	}
	
	public String findMusicPage() throws Exception{
	if(paramMap!=null){
		List<ConditionBean> conditions = new ArrayList<ConditionBean>();
		//查管理员上传的媒资音频
		String searchMode = paramMap.get("searchMode");
		String searchType = paramMap.get("searchType");
		String searchKeyWord = paramMap.get("searchKeyWord");
		if(searchMode!=null&&searchKeyWord!=null){
			if(searchMode.equals("0")){			//通过媒资编码 ID查找
				conditions.add(new ConditionBean("id",searchKeyWord,ConditionBean.EQ));
			}else if(searchMode.equals("1")){
				conditions.add(new ConditionBean("title","%"+searchKeyWord+"%",ConditionBean.LIKE));
			}
		}
		if(searchType!=null){
			int sType = Integer.parseInt(searchType);
			conditions.add(new ConditionBean("type",sType,ConditionBean.EQ));
		}
		conditions.add(new ConditionBean("publicLevel", 1, ConditionBean.EQ));
		conditions.add(new ConditionBean("userId", "OMSADMIN%", ConditionBean.LIKE));
		page = cMediaService.findMusicPage(conditions,null,false,"utc", page);
		resultJson.put("list",page);
	}else resultJson.put("list", cMediaService.findMusicPage(null,null,false,"utc",page));
	return Action.SUCCESS;
}
	
	/**
	 * 查找媒资类型列表
	 * @return
	 * @throws Exception
	 */
	public String findTypeList() throws Exception{
		resultJson.put("list", mediaService.findTypeList());
		return Action.SUCCESS;
	}
	
	/**
	 * 主题模版页面间的跳转控制
	 * @return
	 * @throws Exception
	 */
	public String initMediaPage() throws Exception{
		if(this.paramMap!=null){
			if(this.paramMap.get("mediaType")!=null){
				String tempKey = this.paramMap.get("mediaType");
				if(tempKey.equals("mediaEdit")){		//模版编辑
					String mediaId = this.paramMap.get("mediaId");
					String id = this.paramMap.get("id");
					mediaBean = mediaService.findById(mediaId);
					commonMedia= cMediaService.findById(id);
					this.input = "pages/media/mediaEdit.jsp";
				}
			}
		}else{
			this.tipMsg = "传递跳转参数为空，默认为您跳回模版列表页面。";
		}
		return Action.INPUT;
	}
	
	/**
	 * 添加媒资
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		if(mediaList!=null && mediaList.size()!=0
				&& null!=commonMedias && commonMedias.size()!=0){
			MediaBean bean = mediaList.get(0);
			CommonMediaBean cMedia = commonMedias.get(0);
			bean.setCreateUtc(new Date().getTime());
			bean.setModifyUtc(new Date().getTime());
			ManagerBean admin = (ManagerBean) ActionContext.getContext().getSession().get("admin");
			bean.setLastModUserId(""+admin.getId());
			bean.setCreaterId(""+admin.getId());
			bean.setType(2);
			bean.setAudit_status("0");
			bean.setDescription("");
			bean.setThumbnail("");
			
			cMedia.setId(Tools.getMD5AndUUID(16));
			cMedia.setMediaId(bean.getId());
			cMedia.setPublicLevel(1);
			cMedia.setUserId("OMSADMIN"+admin.getId());
			cMedia.setUtc(Tools.getNowUTC());
			cMedia.setTitle(StringEscapeUtils.unescapeXml(cMedia.getTitle()));
			ServiceResultBean result = mediaService.mediaAdd(bean);
			if(result.isSuccess()){
				ServiceResultBean srb = cMediaService.cMediaAdd(cMedia);
				if(srb.isSuccess()){
					resultJson.put("result", "00000000");
					resultJson.put("tip", "Add media success");
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "Add media failed,"+result.getDescription());
				}
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Add media failed,"+result.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Add media Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 编辑媒资
	 * @return
	 * @throws Exception
	 */
	public String edit(){
		if(this.mediaList!=null && this.mediaList.size()!=0
				&&null != this.commonMedias && this.commonMedias.size() !=0){
			MediaBean bean = this.mediaList.get(0);
			CommonMediaBean cMedia = commonMedias.get(0);
			ManagerBean admin = (ManagerBean) ActionContext.getContext().getSession().get("admin");
			bean.setLastModUserId("OMSADMIN"+admin.getId());
			bean.setModifyUtc(new Date().getTime());
			bean.setType(2);
			if(paramMap!=null){			//说明主键值更改了
				if(paramMap.get("mediaEdit")!=null){
					ServiceResultBean resultPrimary = mediaService.alterMediaKey(bean.getId(), paramMap.get("mediaEdit"));
					if(!resultPrimary.isSuccess()){
						resultJson.put("result", "00000001");
						resultJson.put("tip","Edit media failed,mediaId is exist!");
						return Action.SUCCESS;
					}
					bean.setId(paramMap.get("mediaEdit"));
					cMedia.setMediaId(paramMap.get("mediaEdit"));
				}
			}
			if(null == bean.getThumbnail()) bean.setThumbnail("");
			if(null == bean.getDescription()) bean.setDescription("");
			ServiceResultBean result = mediaService.mediaEdit(bean);
			if(result.isSuccess()){
				cMedia.setMediaId(bean.getId());
				cMedia.setTitle(StringEscapeUtils.unescapeXml(cMedia.getTitle()));
				ServiceResultBean srb = cMediaService.cMediaEdit(cMedia);
				if(srb.isSuccess()){
					resultJson.put("result", "00000000");
					resultJson.put("tip", "Edit media success");
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "Edit media failed,"+result.getDescription());
				}
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Edit media failed,"+result.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Edit media Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除通用媒资
	 * @return
	 * @throws Exception
	 */
	public String del() throws Exception{
		if(mediaList!=null && mediaList.size()!=0){
			String id = this.mediaList.get(0).getId();
			//int type = this.mediaList.get(0).getType();
			List<String> ids = new ArrayList<String>();
			ids.add(id);
			ServiceResultBean srb = cMediaService.cMediaDel(ids);
			if(srb.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "Delete Media success.");
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Delete Media failed,"+srb.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Delete Media Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 加入/取消默认音乐处理
	 * @return
	 */
	public String recommendHandler(){
		if(null != paramMap){
			String id = paramMap.get("id");
			String option = paramMap.get("option");
			//加入默认
			if(option.equals("0")){
				CommonMediaBean cMedia = cMediaService.findById(id);
				List<ConditionBean> clist = new ArrayList<ConditionBean>();
				clist.add(new ConditionBean("mediaId", cMedia.getMediaId(), ConditionBean.EQ));
				clist.add(new ConditionBean("publicLevel", 1, ConditionBean.EQ));
				clist.add(new ConditionBean("title", cMedia.getTitle(), ConditionBean.EQ));
				clist.add(new ConditionBean("detail", cMedia.getDetail(), ConditionBean.EQ));
				clist.add(new ConditionBean("type", 2, ConditionBean.EQ));
				page = (PaginationBean)cMediaService.findMusicPage(clist,null,false,"utc", page);
				if(0 == page.getTotalCount()){
					CommonMediaBean commonMedia = new CommonMediaBean(Tools.getMD5AndUUID(16), 2, 1, cMedia.getMediaId(), cMedia.getTitle(), cMedia.getDetail(), cMedia.getUserId(), Tools.getNowUTC());
					ServiceResultBean srb = cMediaService.cMediaAdd(commonMedia);
					if(srb.isSuccess())
					resultJson.put("result", "00000000");
					else resultJson.put("result", "00000001");
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "请勿重复添加默认同一首");
				}
			//取消默认
			}else{
				List<String> ids = new ArrayList<String>();
				ids.add(id);
				ServiceResultBean srb = cMediaService.cMediaDel(ids);
				if(srb.isSuccess())
				resultJson.put("result", "00000000");
				else {
					resultJson.put("result", "00000001");
					resultJson.put("tip", "cancel default music failed");
				}
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 计算推荐音乐数
	 * @return
	 */
	public String recommendCount(){
		List<ConditionBean> conditions = new ArrayList<ConditionBean>();
		conditions.add(new ConditionBean("type",2,ConditionBean.EQ));
		conditions.add(new ConditionBean("audit_status", "3", ConditionBean.EQ));
		conditions.add(new ConditionBean("createrId", "OMSADMIN%", ConditionBean.LIKE));
		page = mediaService.findPage(conditions, null);
		resultJson.put("recomendCount", page.getTotalCount());
		return Action.SUCCESS;
	}
	
	/**
	 * 推荐音乐的排序（按照创建时间降序）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String sortMusic(){
		if(paramMap.containsKey("flag") && paramMap.containsKey("ids")){
			String[] ids = paramMap.get("ids").split(",");
			String flag = paramMap.get("flag");
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			for (String id : ids) {conditions.add(new ConditionBean("id", id, ConditionBean.EQ));}
			PaginationBean pagination = (PaginationBean)cMediaService.findMusicPage(conditions,1,false,null, null);
			
			if(null != pagination && null != pagination.getRecords() && !pagination.getRecords().isEmpty()){
				//要排序的实体加入到map
				List<CommonMediaBean> commonMedias = (List<CommonMediaBean>) pagination.getRecords();
				Map<String, CommonMediaBean> commonMediaMap = new HashMap<String, CommonMediaBean>();
				for (CommonMediaBean commonMediaBean : commonMedias) {
					commonMediaMap.put(commonMediaBean.getId(), commonMediaBean);
				}
				long newElementUtc = 0;
				int len = ids.length;
				if("0".equals(flag)){
					newElementUtc = commonMediaMap.get(ids[0]).getUtc();
					for(int i=0;i<len;i++){
						CommonMediaBean commonMedia = commonMediaMap.get(ids[i]);
						if((i+1)<len){
							commonMedia.setUtc(commonMediaMap.get(ids[i+1]).getUtc());
						}else
							commonMedia.setUtc(newElementUtc);
						if(!cMediaService.cMediaEdit(commonMedia).isSuccess()){
							resultJson.put("result", "00000001");
							return Action.SUCCESS;
						}
					}
				}else if("1".equals(flag)){
					newElementUtc = commonMediaMap.get(ids[len-1]).getUtc();
					for(int i=len-1;i>=0;i--){
						CommonMediaBean commonMedia = commonMediaMap.get(ids[i]);
						if((i-1)>=0){
							commonMedia.setUtc(commonMediaMap.get(ids[i-1]).getUtc());
						}else
							commonMedia.setUtc(newElementUtc);
						
						if(!cMediaService.cMediaEdit(commonMedia).isSuccess()){
							resultJson.put("result", "00000001");
							return Action.SUCCESS;
						}
					}
				}else{
					resultJson.put("result", "00000001");
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
}
