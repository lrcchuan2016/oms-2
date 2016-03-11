package cn.broadin.oms.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.ChannelBean;
import cn.broadin.oms.model.ChannelContactsBean;
import cn.broadin.oms.model.ChannelCoverBean;
import cn.broadin.oms.model.ChannelInstallBean;
import cn.broadin.oms.model.ChannelUserBean;
import cn.broadin.oms.service.ChannelService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;
import cn.broadin.oms.util.Tools;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller("channelAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ChannelAction extends ActionSupport{
	private static final long serialVersionUID = -4386902124958014120L;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private Map<String, String> paramMap = new HashMap<String, String>();
	private PaginationBean pagination;
	private String input = "pages/channel/channelList.jsp";
	private String tipMsg;
	private List<ChannelBean> channels;
	private ChannelBean channel;
	private List<ChannelContactsBean> contacts;
	private List<ChannelCoverBean> covers;
	private List<ChannelInstallBean> installs;   //安装
	private List<ChannelUserBean> users;
	
	private String channelId;    //渠道ID
	private String keyWords; //关键字
	private int startIndex;
	private int page;
	private int pageSize = 5;
	private String pageLinks;
	private int totalPage;
	private int num = 5;   //分页链接数，小于5时全部显示，超过只显示5个
	private int status;     //渠道状态   0表正常   1进入回收站
	private String createUtc;
	private int year;
	private int month;
	private int tvNumber;			//数量
	private int stbNumber;
	private int dvbNumber;
	private int storeNumber;
	private int otherNumber;
	private String sortFiled = "coverNum";    //排序字段
	private String sortMod = "desc";		//升序或者降序
	
	@Resource
	private ChannelService channelService;
	
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
	public PaginationBean getPagination() {
		return pagination;
	}
	public void setPagination(PaginationBean pagination) {
		this.pagination = pagination;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public List<ChannelBean> getChannels() {
		return channels;
	}
	public void setChannels(List<ChannelBean> channels) {
		this.channels = channels;
	}
	public ChannelBean getChannel() {
		return channel;
	}
	public void setChannel(ChannelBean channel) {
		this.channel = channel;
	}
	public List<ChannelContactsBean> getContacts() {
		return contacts;
	}
	public void setContacts(List<ChannelContactsBean> contacts) {
		this.contacts = contacts;
	}
	public List<ChannelCoverBean> getCovers() {
		return covers;
	}
	public void setCovers(List<ChannelCoverBean> covers) {
		this.covers = covers;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageLinks() {
		return pageLinks;
	}
	public void setPageLinks(String pageLinks) {
		this.pageLinks = pageLinks;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getCreateUtc() {
		return createUtc;
	}
	public void setCreateUtc(String createUtc) {
		this.createUtc = createUtc;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public List<ChannelInstallBean> getInstalls() {
		return installs;
	}
	public void setInstalls(List<ChannelInstallBean> installs) {
		this.installs = installs;
	}
	public List<ChannelUserBean> getUsers() {
		return users;
	}
	public void setUsers(List<ChannelUserBean> users) {
		this.users = users;
	}
	
	public int getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(int storeNumber) {
		this.storeNumber = storeNumber;
	}
	public int getStbNumber() {
		return stbNumber;
	}
	public void setStbNumber(int stbNumber) {
		this.stbNumber = stbNumber;
	}
	public int getDvbNumber() {
		return dvbNumber;
	}
	public void setDvbNumber(int dvbNumber) {
		this.dvbNumber = dvbNumber;
	}
	
	public int getTvNumber() {
		return tvNumber;
	}
	public void setTvNumber(int tvNumber) {
		this.tvNumber = tvNumber;
	}
	public int getOtherNumber() {
		return otherNumber;
	}
	public void setOtherNumber(int otherNumber) {
		this.otherNumber = otherNumber;
	}
	public String getSortFiled() {
		return sortFiled;
	}
	public void setSortFiled(String sortFiled) {
		this.sortFiled = sortFiled;
	}
	public String getSortMod() {
		return sortMod;
	}
	public void setSortMod(String sortMod) {
		this.sortMod = sortMod;
	}
	/**
	 * 查找渠道列表
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String channelList(){
		String href = "channelAction_channelList?sortFiled="+sortFiled+"&&sortMod="+sortMod+"&&";
		if (0 == page) page = 1;
		startIndex = (page - 1) * pageSize;
		pagination = new PaginationBean(startIndex, pageSize, 0, new ArrayList<ChannelBean>());
		List<ConditionBean> clist = new ArrayList<ConditionBean>();
		clist.add(new ConditionBean("status", status, ConditionBean.EQ));
		if(null != keyWords && !"".equals(keyWords)){
			//渠道名称
			clist.add(new ConditionBean("name", "%"+keyWords+"%", ConditionBean.LIKE));
			channels = (List<ChannelBean>) channelService.findPage(ChannelBean.class,clist,0,false,null ,null);
			href += "keyWords="+keyWords+"&&"; 
		}else channels = (List<ChannelBean>) channelService.findPage(ChannelBean.class,clist,0,false,null ,null);
		//排序处理
		channels = Tools.sortList(channels,sortFiled,sortMod);
		List<ChannelBean> list = new ArrayList<ChannelBean>();
		if(null != channels && 0 != channels.size()){
			for(int i=startIndex;i<pageSize+startIndex;i++){
				list.add(channels.get(i));
				if(i+1>=channels.size()) break;
			}
		}
		pagination.setTotalCount(channels.size());
		pagination.setRecords(list);
		if(status == 1){ 
			href += "status=1&&"; 
			input = "pages/channel/channelRecycle.jsp";
		}
		this.pageLinks(href);
		return Action.INPUT;
	}
	
	/**
	 * 运营情况
	 * @return
	 */
	public String Operate(){
		channels = channelService.findAll();
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		if(channels != null && 0 != channels.size()){
			for (ChannelBean ch : channels) {
				Object[] o = new Object[5];
				int coverNum = 0;
				int regNum = 0;
				int instNum = 0;
				int lastMonthNum = 0;
				int monthNum = 0;
				if(null != ch.getCoverNum()) coverNum = ch.getCoverNum();
				if(null != ch.getRegisterNum()) regNum = ch.getRegisterNum();
				if(null != ch.getInstallNum()) instNum = ch.getInstallNum();
				if(null != ch.getLastMonthRegNum()) lastMonthNum = ch.getLastMonthRegNum();
				if(null != ch.getMonthRegNum()) monthNum = ch.getMonthRegNum();
				//覆盖数
				Object[] n = new Object[2];
				n[0] = coverNum; n[1] = -1; o[0] = n;
				//安装率
				n = new Object[2];
				if(0 != coverNum){ 
					n[0] = (instNum*1.0)/coverNum; n[1] = -1; o[1] = n;
				}else{
					n[0] = 0; n[1] = -1; o[1] = n;
				}
				//注册数
				n = new Object[2];
				n[0] = regNum; n[1] = -1; o[2] = n;
				//转换率
				n = new Object[2];
				if(0 != instNum){
					n[0] = (regNum*1.0)/instNum; n[1] = -1; o[3] = n;
				}else{
					n[0] = 0; n[1] = -1; o[3] = n;
				}
				//增长率
				n = new Object[2];
				if(0 != lastMonthNum){
					n[0] = (monthNum-lastMonthNum)/(lastMonthNum*1.0); n[1] = -1; o[4] = n;
				}else{
					n[0] = 0; n[1] = -1; o[4] = n;
				}
				map.put(ch.getId(),o);
			}
			map = this.sort(channelId,map,5);
			
			resultJson.put("result", "00000000");
			resultJson.put("array", map.get(channelId));
			return Action.SUCCESS;
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 添加渠道数据
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		if(this.channel!=null){
			String channelId = Tools.getUUIDString("");
			if(this.contacts!=null&&this.contacts.size()!=0){ 
				for (ChannelContactsBean contact : contacts) {
					contact.setChannelContact(channel);
				}
				this.channel.setContacts(this.contacts);
			}
			this.channel.setId(channelId);
			this.channel.setCreateUtc(Tools.string2utc(createUtc, 8, "yyyy-MM-dd HH:mm"));
			ServiceResultBean result = this.channelService.add(this.channel);
			if(result.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "Add Channel success.");
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Add Channel failed,"+result.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Add Channel Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 跳转至渠道编辑
	 * @return
	 */
	public String initChannel(){
		channel = channelService.findById(channelId);
		if(null == keyWords){
			input = "pages/channel/channelShow.jsp";
		}else input = "pages/channel/channelEdit.jsp";
		return Action.INPUT;
	}
	
	/**
	 * 编辑渠道数据
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		if(this.channel!=null){
			if(this.contacts!=null&&this.contacts.size()!=0){
				for (ChannelContactsBean contact : contacts) {
					contact.setChannelContact(channel);
				}
				this.channel.setContacts(this.contacts);
				this.channel.setCreateUtc(Tools.string2utc(createUtc, 8, "yyyy-MM-dd HH:mm"));
			}
			if(this.covers!=null&&this.covers.size()!=0) this.channel.setCovers(this.covers);
			ServiceResultBean result = this.channelService.updateChannel(this.channel);
			if(result.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "Edit Channel success.");
			}else {
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Edit Channel failed,"+result.getDescription());
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Edit Channel Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * keyWords  0-覆盖用户  1-注册用户   2-安装
	 * 查询渠道数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String searchChannelData(){
		if(null != keyWords && null != channelId && 0 != year){
			channel = channelService.findById(channelId);
			if(null != channel){
				int[] yearData = new int[12]; 
				if("0".equals(keyWords)){
					//覆盖
					List<ConditionBean> clist = new ArrayList<ConditionBean>();
					clist.add(new ConditionBean("channelCover", channel, ConditionBean.EQ));
					clist.add(new ConditionBean("year", year, ConditionBean.EQ));
					covers = (List<ChannelCoverBean>) channelService.selectConditions(ChannelCoverBean.class,clist,null,true,"month",null);
					if(null != covers){
						if(0 != covers.size()){
							//List<Integer> yearData = new ArrayList<Integer>();
							for (ChannelCoverBean cover : covers) {
								yearData[cover.getMonth()-1] = cover.getSmartTvCover()+cover.getAppstoreCover()+cover.getDvbCover()+cover.getStbCover()+cover.getOtherCover();
							}
							resultJson.put("result", "00000000");
							resultJson.put("yearData", yearData);
						}else{
							resultJson.put("result", "00000000");
						}
					}else{
						resultJson.put("result", "00000001");
						resultJson.put("tip", "without data");
					}
				}else if("1".equals(keyWords)){
					//注册
					List<ConditionBean> clist = new ArrayList<ConditionBean>();
					clist.add(new ConditionBean("channelUser", channel, ConditionBean.EQ));
					clist.add(new ConditionBean("year", year, ConditionBean.EQ));
					users = (List<ChannelUserBean>) channelService.selectConditions(ChannelUserBean.class, clist, null, true, "month", null);
					if(null != users){
						if(0 != users.size()){
							for (ChannelUserBean user : users) {
								yearData[user.getMonth()-1] = user.getSmartTvUser()+user.getAppstoreUser()+user.getDvbUser()+user.getStbUser()+user.getOtherUser();
							}
							resultJson.put("result", "00000000");
							resultJson.put("yearData", yearData);
						}else{
							resultJson.put("result", "00000000");
						}
					}else{
						resultJson.put("result", "00000001");
						resultJson.put("tip", "without data");
					}
				}else{
					//安装
					List<ConditionBean> clist = new ArrayList<ConditionBean>();
					clist.add(new ConditionBean("channelInstall", channel, ConditionBean.EQ));
					clist.add(new ConditionBean("year", year, ConditionBean.EQ));
					installs = (List<ChannelInstallBean>) channelService.selectConditions(ChannelInstallBean.class,clist,null,true,"month",null);
					if(null != installs){
						if(0 != installs.size()){
							for (ChannelInstallBean install : installs) {
								yearData[install.getMonth()-1] = install.getSmartTvInstall()+install.getAppstoreInstall()+install.getDvbInstall()+install.getStbInstall()+install.getOtherInstall();
							}
							resultJson.put("result", "00000000");
							resultJson.put("yearData", yearData);
						}else{
							resultJson.put("result", "00000000");
						}
					}else{
						resultJson.put("result", "00000001");
						resultJson.put("tip", "without data");
					}
				}
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "input param can not find");
				return Action.SUCCESS;
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 更新渠道数据
	 * @return
	 */
	public String updateChannelData(){
		List<ConditionBean> clist = new ArrayList<ConditionBean>();
		channel = channelService.findById(channelId);
		if(null != channel){
		if("0".equals(keyWords)){
				//条件查找
				clist.add(new ConditionBean("channelCover", channel, ConditionBean.EQ));
				clist.add(new ConditionBean("year", year, ConditionBean.EQ));
				clist.add(new ConditionBean("month", month+1, ConditionBean.EQ));
				pagination = (PaginationBean) channelService.findPage(ChannelCoverBean.class, clist, 0, false, null, new PaginationBean());
				if(null != pagination.getRecords() && 0 != pagination.getRecords().size()){
					ChannelCoverBean cover = (ChannelCoverBean) pagination.getRecords().get(0);
					cover.setAppstoreCover(storeNumber);
					cover.setSmartTvCover(tvNumber);
					cover.setOtherCover(otherNumber);
					cover.setStbCover(stbNumber);
					cover.setDvbCover(dvbNumber);
					ServiceResultBean srb = channelService.update(cover);
					if(srb.isSuccess()){
						resultJson.put("result", "00000000");
					}else{
						resultJson.put("result", "00000001");
						resultJson.put("tip", "update failed");
					}
				}else{
					ChannelCoverBean cover = new ChannelCoverBean(tvNumber, stbNumber, dvbNumber, storeNumber, otherNumber, year, month+1, channel);
					ServiceResultBean srb = channelService.addData(cover);
					if(srb.isSuccess()){
						resultJson.put("result", "00000000");
					}else{
						resultJson.put("result", "00000001");
					}
				}
		}else if("1".equals(keyWords)){
			clist.add(new ConditionBean("channelUser", channel, ConditionBean.EQ));
			clist.add(new ConditionBean("year", year, ConditionBean.EQ));
			clist.add(new ConditionBean("month", month+1, ConditionBean.EQ));
			pagination = (PaginationBean) channelService.findPage(ChannelUserBean.class, clist, 0, false, null, new PaginationBean());
			if(null != pagination.getRecords() && 0 != pagination.getRecords().size()){
				ChannelUserBean user = (ChannelUserBean) pagination.getRecords().get(0);
				user.setAppstoreUser(storeNumber);
				user.setDvbUser(dvbNumber);
				user.setSmartTvUser(tvNumber);
				user.setOtherUser(otherNumber);
				user.setStbUser(stbNumber);
				ServiceResultBean srb = channelService.update(user);
				if(srb.isSuccess()){
					resultJson.put("result", "00000000");
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "update failed");
				}
			}else{
				ChannelUserBean user = new ChannelUserBean(tvNumber, stbNumber, dvbNumber, storeNumber, otherNumber, year, month+1, channel);
				ServiceResultBean srb = channelService.addData(user);
				if(srb.isSuccess()){
					resultJson.put("result", "00000000");
				}else{
					resultJson.put("result", "00000001");
				}
			}
		}else{
			//2
			clist.add(new ConditionBean("channelInstall", channel, ConditionBean.EQ));
			clist.add(new ConditionBean("year", year, ConditionBean.EQ));
			clist.add(new ConditionBean("month", month+1, ConditionBean.EQ));
			pagination = (PaginationBean) channelService.findPage(ChannelInstallBean.class, clist, 0, false, null, new PaginationBean());
			if(null != pagination.getRecords() && 0 != pagination.getRecords().size()){
				ChannelInstallBean install = (ChannelInstallBean) pagination.getRecords().get(0);
				install.setAppstoreInstall(storeNumber);
				install.setDvbInstall(dvbNumber);
				install.setSmartTvInstall(tvNumber);
				install.setOtherInstall(otherNumber);
				install.setStbInstall(stbNumber);
				ServiceResultBean srb = channelService.update(install);
				if(srb.isSuccess()){
					resultJson.put("result", "00000000");
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "update failed");
				}
			}else{
				ChannelInstallBean install = new ChannelInstallBean(tvNumber, stbNumber, dvbNumber, storeNumber, otherNumber, year, month+1, channel);
				ServiceResultBean srb = channelService.addData(install);
				if(srb.isSuccess()){
					resultJson.put("result", "00000000");
				}else{
					resultJson.put("result", "00000001");
				}
			}
		}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is error");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除渠道数据至回收站 / 从回收站恢复渠道数据
	 * status   渠道状态改变1变0，0变1
	 * @return
	 * @throws Exception
	 */
	public String updateChannel(){
		ChannelBean channelBean = channelService.findById(channelId);
		if(null != channelBean){
			if(0 == channelBean.getStatus()){
				channelBean.setStatus(1);
			}else{
				channelBean.setStatus(0);
			}
			ServiceResultBean srb = channelService.updateChannel(channelBean);
			if(srb.isSuccess()){
				resultJson.put("result", "00000000");
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "Channel Update failed");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param can not find");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 彻底删除渠道数据
	 * @return
	 * @throws Exception
	 */
	public String del() throws Exception{
		if(null != channelId && !"".equals(channelId)){
			ChannelBean channelBean = channelService.findById(channelId);
			if(null != channelBean){
				List<ChannelBean> channelList = new ArrayList<ChannelBean>();
				channelList.add(channelBean);
				ServiceResultBean result = this.channelService.del(channelList);
				if(result.isSuccess()){
					resultJson.put("result", "00000000");
					resultJson.put("tip", "Delete Channel success.");
				}else {
					resultJson.put("result", "00000001");
					resultJson.put("tip", "Delete Channel failed,"+result.getDescription());
				}
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "input param can not find");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","Delete Channel Failed,input param is null");
		}
		return Action.SUCCESS;
	}
	
	//分页链接
	public String pageLinks(String method) {
		totalPage = (int) Math.ceil((pagination.getTotalCount()*1.0)/pageSize);
		int pageUp = page - 1;
		int pageDown = page + 1;
		if (page - 1 < 1) pageUp = 1;
		if (page + 1 > totalPage) pageDown = totalPage;
		StringBuffer sb = new StringBuffer();
		sb.append("<li><a href=\"" + method + "page=1\"> 首页 </a></li>");
		sb.append("<li><a href=\"" + method + "page="+pageUp+"\"> 上一页 </a></li>");
		//分为①总页数大于指定链接数(5)②总页数小于等于5
		if(totalPage>num){
			if(page==1||page==2){
				for(int i = 1;i<=num;i++){
					sb.append(this.getCenterLinks(i,method));
				}
			}else if((page==totalPage-1)||page==totalPage){
				for(int i=totalPage-num+1;i<=totalPage;i++){
					sb.append(this.getCenterLinks(i,method));
				}
			}else{
				for(int i=page-2;i<=page+2;i++){
					sb.append(this.getCenterLinks(i,method));
				}
			}
		}else{
			for(int i = 1;i<=totalPage;i++){
				sb.append(this.getCenterLinks(i,method));
			}
		}
		sb.append("<li><a href=\"" + method + "page="+pageDown+"\"> 下一页 </a></li>");
		sb.append("<li><a href=\"" + method + "page=" +totalPage+"\"> 末页 </a></li>");
		pageLinks = sb.toString();
		return pageLinks;
	}

	/**
	 * 获得中间链接(1、2、3、4、5)
	 * @param i          起始页
	 * @param method	调用指定action的指定方法
	 * @param sb		
	 * @return
	 */
	private String getCenterLinks(int i, String method) {
		String str = "";
			if (i != page) {
				str += "<li><a href=\"" + method + "page=" + i + "\">"+i+"</a></li>";
			} else {
				str += "<li class='active'><a>" + i + "</a></li>";
			}
		return str;
	}
	
	/**
	 * 对Map的值排序
	 * @param map   Object数组长度
	 * @return
	 */
	private Map<String, Object[]> sort(String id, Map<String, Object[]> map,int len) {
		//切割数据
		for(int i=0;i<len;i++){
			List<Double> list = new ArrayList<Double>();
			for (Entry<String, Object[]> it : map.entrySet()) {
				Object b = ((Object[])it.getValue()[i])[0];
				Double d = 0.0;
				if(b instanceof Integer){
					d = ((Integer)b).doubleValue();
				}else{
					d = (Double)b;
				}
				list.add(d);
			}
			Double[]  dArray = list.toArray(new Double[list.size()]);
			//冒泡排序
			for(int j=dArray.length-1;j>0;j--){
				double temp = 0;
				for(int k=0;k<j;k++){
					if(dArray[k]<dArray[k+1]){
						temp = dArray[k+1];
						dArray[k+1] = dArray[k];
						dArray[k] = temp;
					}
				}
			}
			list = Arrays.asList(dArray);
			//排名
			Object[] o = map.get(id);
			for (int j=0;j<list.size();j++) {
				Object obj = ((Object[])o[i])[0];
				Double d = 0.0;
				if(obj instanceof Integer){
					d = ((Integer)obj).doubleValue();
				}else{
					d = (Double) obj;
				}
				if(list.get(j).equals(d)) {
					((Object[])map.get(id)[i])[1] = j+1;
					break;
				}
			}
		}
		return map;
	}
	
}
