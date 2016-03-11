package cn.broadin.oms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.broadin.oms.model.AdLocationBean;
import cn.broadin.oms.model.AdResourceBean;
import cn.broadin.oms.model.ManagerBean;
import cn.broadin.oms.service.AdService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;
import cn.broadin.oms.util.Tools;

/**
 * 	广告管理处理
 * @author huchanghuan
 *
 */
@Controller("adAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AdAction extends ActionSupport{

	
	private static final long serialVersionUID = 1L;
	@Resource
	private AdService adService;
	private String success;
	private List<ConditionBean> clist = new ArrayList<ConditionBean>();
	private PaginationBean paginationBean;
	private String id;
	private String ad_name;
	private String terminal_type;
	private String type;
	private int width;
	private int height;
	private int bitrate;
	private AdLocationBean adLocationBean;
	private AdResourceBean adResourceBean;
	private List<AdLocationBean> alist = new ArrayList<AdLocationBean>();
	private String content;
	private String startTime;
	private String endTime;
	private String remark;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private String adType = "1";     //默认显示页面(手机广告列表)
	private String resourceKey;		//媒资在阿里云的编码
	private Map<String, List<AdResourceBean>> aMap = new HashMap<String, List<AdResourceBean>>();
	private List<String> slist = new ArrayList<String>();


	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<ConditionBean> getClist() {
		return clist;
	}

	public void setClist(List<ConditionBean> clist) {
		this.clist = clist;
	}

	public void setPaginationBean(PaginationBean paginationBean) {
		this.paginationBean = paginationBean;
	}

	public PaginationBean getPaginationBean() {
		return paginationBean;
	}

	public String getAd_name() {
		return ad_name;
	}

	public void setAd_name(String ad_name) {
		this.ad_name = ad_name;
	}

	public String getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getBitrate() {
		return bitrate;
	}

	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

	public AdLocationBean getAdLocationBean() {
		return adLocationBean;
	}

	public void setAdLocationBean(AdLocationBean adLocationBean) {
		this.adLocationBean = adLocationBean;
	}
	
	public Map<String, Object> getResultJson() {
		return resultJson;
	}

	public void setResultJson(Map<String, Object> resultJson) {
		this.resultJson = resultJson;
	}

	public List<AdLocationBean> getAlist() {
		return alist;
	}

	public void setAlist(List<AdLocationBean> alist) {
		this.alist = alist;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public AdResourceBean getAdResourceBean() {
		return adResourceBean;
	}

	public void setAdResourceBean(AdResourceBean adResourceBean) {
		this.adResourceBean = adResourceBean;
	}
	
	public Map<String, List<AdResourceBean>> getaMap() {
		return aMap;
	}

	public void setaMap(Map<String, List<AdResourceBean>> aMap) {
		this.aMap = aMap;
	}
	
	public List<String> getSlist() {
		return slist;
	}

	public void setSlist(List<String> slist) {
		this.slist = slist;
	}
	
	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	/**
	 * 处理广告列表(adType   
	 * 0-全局广告列表，
	 * 1-手机广告列表，
	 * 2-电视广告列表，
	 * 3-网站广告列表，
	 * )
	 * @return
	 */
	public String adList() {
		clist.add(new ConditionBean("type", 0, ConditionBean.EQ));
		List<AdResourceBean> list = adService.findAllResource(AdResourceBean.class,null,null,false,"create_utc",null);
		List<AdResourceBean> list2 = new ArrayList<AdResourceBean>();
		for (AdResourceBean adResourceBean : list) {
			if(adType.equals("0") && adType.equals(adResourceBean.getTerminal_type())){
				list2.add(adResourceBean);
			}else if(adType.equals("1") && adType.equals(adResourceBean.getTerminal_type())){
				list2.add(adResourceBean);
			}else if(adType.equals("2") && adType.equals(adResourceBean.getTerminal_type())){
				list2.add(adResourceBean);
			}else if(adType.equals("3") && adType.equals(adResourceBean.getTerminal_type())){
				list2.add(adResourceBean);
			}
		}
		for (AdResourceBean adResource : list2) {
			String name = adResource.getAd_name();
			if("活动专用".equals(name)) continue;
			if(null == aMap.get(name)){
				slist.add(name);
				List<AdResourceBean> arList = new ArrayList<AdResourceBean>();
				arList.add(adResource);
				aMap.put(name, arList);
			}else{
				aMap.get(name).add(adResource);
			}
		}
		ActionContext.getContext().put("aMap", aMap);
		if(adType.equals("0")){
			success = "pages/ad/adList_global.jsp";
		}else if(adType.equals("1")){
			success = "pages/ad/adList.jsp";
		}else if(adType.equals("2")){
			success = "pages/ad/adList_tv.jsp";
		}else if(adType.equals("3")){
			success = "pages/ad/adList_pc.jsp";
		}
		return Action.SUCCESS;
	}

	/**
	 * 广告栏位列表请求
	 * 
	 * @return
	 */
	public String locationList() {
		alist = adService.findAllLocation(AdLocationBean.class);
		success = "pages//ad//ad_location.jsp";
		return Action.SUCCESS;
	}

	/**
	 * 处理新建广告栏位请求
	 * 
	 * @return
	 */
	public String createLocation() {
		if(null != adLocationBean){
			adLocationBean.setId(Tools.getMD5AndUUID(16));
			clist.add(new ConditionBean("ad_name", adLocationBean.getAd_name(), ConditionBean.EQ));
			clist.add(new ConditionBean("terminal_type", adLocationBean.getTerminal_type(), ConditionBean.EQ));
			clist.add(new ConditionBean("type", adLocationBean.getType(), ConditionBean.EQ));
			if(null != adService.add(clist, adLocationBean)) resultJson.put("result", "00000000");
			else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "广告位已存在");
			}
		}else {
			resultJson.put("result", "00000001");
			resultJson.put("tip", "请求参数异常");
		}
		return Action.SUCCESS;
	}


	/**
	 * 跳转新建广告页面
	 * 
	 * @return
	 */
	public String createAd() {
		adLocationBean = adService.selectById(AdLocationBean.class, id);
		success = "pages//ad//ad_add.jsp";
		return Action.SUCCESS;
	}

	/**
	 * 跳转到广告栏位
	 * 
	 * @return
	 */
	public String editAd() {
		adLocationBean = adService.selectById(AdLocationBean.class, id);
		success = "pages//ad//ad_location_edit.jsp";
		return Action.SUCCESS;
	}

	/**
	 * 创建广告并绑定栏位
	 * @return
	 */
	public String addResource(){
		if(null != adResourceBean 
				&& null != adLocationBean
				&& null != startTime
				&& null != endTime){
			//AdLocationBean adLocation = adService.selectById(AdLocationBean.class, adLocationBean.getId());
				ManagerBean manager = (ManagerBean)ActionContext.getContext().getSession().get("admin");
				if(null == adResourceBean.getAliyunKey()) adResourceBean.setAliyunKey("");
				adResourceBean.setId(Tools.getMD5AndUUID(16));
				adResourceBean.setCreate_utc(Tools.getNowUTC());
				adResourceBean.setPublisher_id(manager.getId()+"");
				adResourceBean.setAdLocation_id(adLocationBean.getId());
				adResourceBean.setStart_utc(Tools.string2utc(startTime, 8, "yyyy-MM-dd HH:mm"));
				adResourceBean.setEnd_utc(Tools.string2utc(endTime, 8, "yyyy-MM-dd HH:mm"));
				if(null != adService.addResource(adResourceBean))
				resultJson.put("result", "00000000");
				else resultJson.put("result", "00000001");
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "参数异常！");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除栏位
	 * @return
	 */
	public String delAdLocation(){
		adLocationBean = adService.selectById(AdLocationBean.class,id);
		if(null != adLocationBean){
			if(null != adService.delAdLocation(adLocationBean)) resultJson.put("result", "00000000");
			else resultJson.put("result", "00000001");
		}else resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 编辑修改广告栏位
	 * @return
	 */
	public String editLocation(){
		if(null != adLocationBean){
			if(null != adService.update(null,adLocationBean)) resultJson.put("result", "00000000");
			else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "编辑保存异常！");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "输入的参数不正确！");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 进入广告编辑页面
	 * @return
	 */
	public String editAdResource(){
		adResourceBean = adService.selectResourceById(AdResourceBean.class, id);
		success = "pages/ad/adList_edit.jsp";
		return Action.SUCCESS;
	}
	
	/**
	 * 删除广告
	 * resourceKey   为表主键，也是URL中间部分
	 * @return
	 */
	public String delAdResource(){
		AdResourceBean adResourceBean = adService.selectResourceById(AdResourceBean.class, id);
		ServiceResultBean result = adService.delAdResource(adResourceBean,adResourceBean.getAliyunKey());
		if(result.isSuccess()) resultJson.put("result", "00000000");    //成功
		else resultJson.put("result", "00000001");	//失败
		return Action.SUCCESS;
	}
	
	/**
	 * 修改/更新广告信息
	 * resourceKey   为表主键，也是URL中间部分
	 * @return
	 */
	public String updateAdResource(){
		if(null != adResourceBean
			&& null != startTime
			&& null != endTime){
			AdResourceBean adResource = adService.selectResourceById(AdResourceBean.class, adResourceBean.getId());
			if(null != adResource){
				adResource.setStart_utc(Tools.string2utc(startTime, 8, "yyyy-MM-dd HH:mm"));
				adResource.setEnd_utc(Tools.string2utc(endTime, 8, "yyyy-MM-dd HH:mm"));
				adResource.setContent(adResourceBean.getContent());
				adResource.setUrl(adResourceBean.getUrl());
				if(null == adResourceBean.getAliyunKey()) adResourceBean.setAliyunKey("");
				adResource.setRemark(adResourceBean.getRemark());
				if(null != adService.updateAdResource(adResource)){
					resultJson.put("result", "00000000");
					return Action.SUCCESS;
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "更新失败！");
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000001");
		resultJson.put("tip", "参数异常！");
		return Action.SUCCESS;
	}
	
	/**
	 * 处理新建广告上传到阿里云，但是没有提交的请求
	 *
	 * @return
	 */
	public String createBack(){
		if(null != adResourceBean.getAliyunKey()){
			ServiceResultBean srb = adService.delAdResource(null, adResourceBean.getAliyunKey());
			if(srb.isSuccess()) resultJson.put("result", "00000000");
		}
		resultJson.put("result", "00000001");	
		return Action.SUCCESS;
	}
	
}
