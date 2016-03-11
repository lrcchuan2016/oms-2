package cn.broadin.oms.action;

import it.sauronsoftware.base64.Base64;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.ActorBean;
import cn.broadin.oms.model.AdLocationBean;
import cn.broadin.oms.model.AdResourceBean;
import cn.broadin.oms.model.AlbumBean;
import cn.broadin.oms.model.ManagerBean;
import cn.broadin.oms.model.MediaBean;
import cn.broadin.oms.model.UserBean;
import cn.broadin.oms.service.ActivityService;
import cn.broadin.oms.service.AlbumService;
import cn.broadin.oms.service.MediaService;
import cn.broadin.oms.service.UserService;
import cn.broadin.oms.service.impl.AlbumPrintServiceImpl;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.Tools;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 活动管理
 * @author huchanghuan
 *
 */
@Controller("activityAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActivityAction extends ActionSupport implements ServletResponseAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String phone;
	private String address;
	private String activityId;
	private String albumName;
	private String startTime;
	private String endTime;
	private HttpServletResponse resp;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private Map<String, String> paramMap = new HashMap<String, String>();
	private PaginationBean page;
	private AdResourceBean activity;
	private String success;
	
	@Resource
	private ActivityService activityService;
	@Resource
	private UserService userService;
	@Resource
	private AlbumService albumService;
	@Resource
	private MediaService mediaService;
	@Resource
	private AlbumPrintServiceImpl albumPrintService;
	
	
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	public Map<String, Object> getResultJson() {
		return resultJson;
	}

	public void setResultJson(Map<String, Object> resultJson) {
		this.resultJson = resultJson;
	}
	
	public AdResourceBean getActivity() {
		return activity;
	}

	public void setActivity(AdResourceBean activity) {
		this.activity = activity;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.resp = arg0;
	}
	
	/**
	 * 新增活动
	 * @return
	 */
	public String addActivity(){
		if(null != activity){
			ManagerBean manager = (ManagerBean)ActionContext.getContext().getSession().get("admin");
			AdLocationBean adLocation = activityService.findByName("活动专用");
			if(null != adLocation){
				activity.setId(Tools.getUUIDString(""));
				activity.setStart_utc(Tools.string2utc(startTime, 8, "yyyy-MM-dd HH:mm"));
				activity.setEnd_utc(Tools.string2utc(endTime, 8, "yyyy-MM-dd HH:mm"));
				activity.setCreate_utc(Tools.getNowUTC());
				activity.setAdLocation_id(adLocation.getId());
				activity.setPublisher_id(String.valueOf(manager.getId()));
				activity.setAliyunKey("");
				if(null != activityService.activityAdd(activity)){
					resultJson.put("result", "00000000");
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "新增活动失败！");
				}
			}else resultJson.put("result", "00000002");
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is error");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 活动列表
	 * @return
	 */
	public String activityList(){
		if(null != paramMap){
			List<ConditionBean> clist = new ArrayList<ConditionBean>();
			String keyWords = paramMap.get("keyWords");
			clist.add(new ConditionBean("type", 1, ConditionBean.EQ));
			if(null != keyWords && !"".equals(keyWords)){
				clist.add(new ConditionBean("content", "%"+keyWords+"%", ConditionBean.LIKE));
			}
			resultJson.put("list",activityService.pageList(clist,null,false,"create_utc",page));
		}else resultJson.put("list", activityService.pageList(null,null,false,"create_utc",page));
		return Action.SUCCESS;
	}
	
	/**
	 * 更新活动
	 * @return
	 */
	public String activityUpdate(){
		if(null != activity && null != startTime && null != endTime){
			activity.setStart_utc(Tools.string2utc(startTime, 8, "yyyy-MM-dd HH:mm"));
			activity.setEnd_utc(Tools.string2utc(endTime, 8, "yyyy-MM-dd HH:mm"));
			activity.setAliyunKey("");
			if(null != activityService.activityUpdate(activity)){
				resultJson.put("result", "00000000");
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "update activity failed");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is error");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 查看活动参与者
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String actorList(){
		if(null != paramMap){
			List<ConditionBean> clist = new ArrayList<ConditionBean>();
			String keyWords = paramMap.get("keyWords");
			System.out.println(paramMap.get("id"));
			clist.add(new ConditionBean("activityId", paramMap.get("id"), ConditionBean.EQ));
			if(null != keyWords && !"".equals(keyWords)){
				clist.add(new ConditionBean("phone", "%"+keyWords+"%", ConditionBean.LIKE));
				clist.add(new ConditionBean("albumName", "%"+keyWords+"%", ConditionBean.LIKE));
			}
			page = activityService.actorPageList(clist,1,false,"participateTime",page);
		}else page = activityService.actorPageList(null,1,false,"participateTime",page);
		if(0 != page.getTotalCount()){
			long l = Tools.getTimeInMillis(1);
			for (ActorBean actor : (List<ActorBean>)(page.getRecords())) {
				List<ConditionBean> clist = new ArrayList<ConditionBean>();
				clist.add(new ConditionBean("account", actor.getPhone(), ConditionBean.EQ));
				if(actor.getParticipateTime() > l) actor.setPhone("$"+actor.getPhone());
				List<UserBean> users = userService.selectByAccount(UserBean.class, clist, null, false, null, null);
				if(null != users && 0 != users.size()){
					clist.clear();
					clist.add(new ConditionBean("type", "1", ConditionBean.EQ));
					clist.add(new ConditionBean("name", actor.getAlbumName(), ConditionBean.EQ));
					clist.add(new ConditionBean("creater", users.get(0), ConditionBean.EQ));
					List<AlbumBean> albums = albumService.selectByCondition(AlbumBean.class, clist, null, null, null, null);
					if(0 != albums.size() && 0 != albums.get(0).getaMedias().size()){
						//String content = albums.get(0).getaMedias().get(0).getMedia().getContent();
						String mid = albums.get(0).getaMedias().get(0).getMedia().getId();
						String albumCover = albums.get(0).getCover();
						actor.setLink(mid);
						if(null != albumCover && !"".equals(albumCover)) actor.setAlbumCover(albumCover);
					}
				}
			}
		}
		resultJson.put("list", page);
		return Action.SUCCESS;
	}

	/**
	 * 删除活动
	 * @return
	 */
	public String activityDel(){
		if(null != paramMap){
			List<String> slist = new ArrayList<String>();
			slist.add(paramMap.get("id"));
			if(null != activityService.activityDel("id",slist)){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "activity delete success");
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "activity delete exception");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is error");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 初始化页面跳转
	 * @return
	 */
	public String initActivityPage(){
		if(null != paramMap){
			String param = paramMap.get("param");
			if("edit".equals(param)){
				activity = activityService.findActivity(paramMap.get("id"));
				success = "pages/activity/activityEdit.jsp";
			}else if("searchActorPhoto".equals(param)){
				activityId  = paramMap.get("id");
				success = "pages/activity/actorList.jsp";
			}else if("searchTurntable".equals(param)){
				activityId  = paramMap.get("id");
				success = "pages/activity/activityWinRecord.jsp";
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is error");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 新增活动参与者
	 * @return
	 */
	public String addActor(){
		resp.setHeader("Access-Control-Allow-Origin","*");
		if(null != activityId && null != phone && null != address && null != albumName){
			List<ConditionBean> clist = new ArrayList<ConditionBean>();
			clist.add(new ConditionBean("account", phone, ConditionBean.EQ));
			List<UserBean> users = userService.selectByAccount(UserBean.class, clist, null, false, null, null);
			if(null != users && 0 != users.size()){
				ActorBean actor = new ActorBean(Tools.getMD5AndUUID(16), activityId, phone, albumName, address, Tools.getNowUTC());
				clist.clear();
				clist.add(new ConditionBean("activityId", activityId, ConditionBean.EQ));
				clist.add(new ConditionBean("phone", phone, ConditionBean.EQ));
				//参与者本周1后参加的
				clist.add(new ConditionBean("participateTime", Tools.getTimeInMillis(1), ConditionBean.GE));
				if(null == activityService.findActor(clist)){
					if(null != activityService.actorAdd(actor)){
						resultJson.put("result", "00000000");
					}else{
						resultJson.put("result", "00000001");
						resultJson.put("tip", "添加活动参与者失败!");
					}
				}else{
					resultJson.put("result", "00000002");
					resultJson.put("tip", "您已参加过此活动！");
				}
			}else resultJson.put("result", "00000003");
		} else {
			resultJson.put("result", "00000001");
			resultJson.put("tip", "参数错误！");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 根据手机号查找主题相册
	 * @return
	 */
	public String findByPhone(){
		if(null != phone){
			List<ConditionBean> clist = new ArrayList<ConditionBean>();
			clist.add(new ConditionBean("account", phone, ConditionBean.EQ));
			List<UserBean> users = userService.selectByAccount(UserBean.class,clist,null,false, null, null);
			if(null != users && 0 != users.size()){
				clist.clear();
				clist.add(new ConditionBean("type", "1", ConditionBean.EQ));
				clist.add(new ConditionBean("creater", users.get(0), ConditionBean.EQ));
				List<AlbumBean> alist = albumService.selectByCondition(AlbumBean.class, clist, null, false, null, null);
				if(null != alist && 0 != alist.size()){
					resultJson.put("result", "00000000");
					resultJson.put("alist", alist);
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "本手机号没有主题相册！");
				}
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "手机号未注册！");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "参数错误！");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 下载主题相册图片
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String downPicture(){
		//resp.setHeader("content-type", "application/octet-stream");
		if(null != paramMap){
			String id = paramMap.get("id");
			String albumName = paramMap.get("name");
			MediaBean media = mediaService.findById(id);
			if(null != media){
				Base64 base64 = new Base64();
				String xml = base64.decode(media.getContent(),"UTF-8");
				XMLSerializer xmlSerializer = new XMLSerializer();
				JSONObject json = (JSONObject) xmlSerializer.read(xml);
				System.out.println(json.toString());
				File f = albumPrintService.JsonTraverse(json,albumName);
				String abstractPath = "FileResource"+f.getPath().split("FileResource")[1];
				//压缩文件
				resultJson.put("result", "00000000");
				resultJson.put("url", abstractPath);
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 获得的图片
	 */
	@SuppressWarnings("static-access")
	public String downPagePic(){
		if(null != paramMap){
			//int page = Integer.valueOf(paramMap.get("page"))-1;
			String id = paramMap.get("id");
			MediaBean media = mediaService.findById(id);
			if(media != null){
				Base64 base64 = new Base64();
				String xml = base64.decode(media.getContent(), "UTF-8");
				XMLSerializer xmlSerializer = new XMLSerializer();
				JSONObject json = (JSONObject) xmlSerializer.read(xml);
				JSONArray jaPages = json.getJSONArray("pages");
				JSONArray array = new JSONArray();
				if(null != jaPages){
					int length = jaPages.size();
					JSONObject jsonObj = new JSONObject();
					for(int j=0;j<length;j++){
						JSONObject jo = (JSONObject) jaPages.get(j);
						String bgUrl = jo.getJSONObject("background").getString("@url");
						jsonObj.element("bgUrl", bgUrl);
						Object objFields = jo.get("fields");
						JSONArray picArr = new JSONArray();
						if(objFields instanceof net.sf.json.JSONObject){
							JSONObject joField = ((JSONObject)objFields).getJSONObject("field");
							JSONObject resource  = joField.getJSONObject("pic").getJSONObject("resource");
							if(resource.containsKey("@url")){
								String picUrl = resource.getString("@url");
								if(null != picUrl && !picUrl.isEmpty()){
									picArr.add(picUrl);
									jsonObj.element("picUrls", picArr);
								}
							}
						}else{
							JSONArray jaFields = (JSONArray)objFields;
							int len = jaFields.size();
							for(int i=0;i<len;i++){
								JSONObject joField = (JSONObject) jaFields.get(i);
								JSONObject resource  = joField.getJSONObject("pic").getJSONObject("resource");
								if(resource.containsKey("@url")){
									String picUrl = resource.getString("@url");
									if(null != picUrl && !picUrl.isEmpty()){
										picArr.add(picUrl);
									}
								}
							}
							jsonObj.element("picUrls", picArr);
						}
						
						//饰品
						Object objDecorates = jo.get("decorates");
						JSONArray decArr = new JSONArray();
						if(objDecorates instanceof net.sf.json.JSONObject){
							JSONObject joDecorate = ((JSONObject)objDecorates).getJSONObject("decorate");
							String decUrl = joDecorate.getJSONObject("resource").getString("@url");
							if(null != decUrl && !decUrl.isEmpty()){
								decArr.add(decUrl);
								jsonObj.element("decorateUrls", decArr);
							}
						}else{
							JSONArray joDecorates = (JSONArray)objDecorates;
							int len = joDecorates.size();
							for(int i=0;i<len;i++){
								JSONObject joDecorate = (JSONObject) joDecorates.get(i);
								String decUrl = joDecorate.getJSONObject("resource").getString("@url");
								if(null != decUrl && !decUrl.isEmpty()){
									decArr.add(decUrl);
								}
							}
							jsonObj.element("decorateUrls", decArr);
						}
						array.add(jsonObj);
					}
				}
				resultJson.put("result", "00000000");
				resultJson.put("urls", array);
				return Action.SUCCESS;
			}
			
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	
	
}
