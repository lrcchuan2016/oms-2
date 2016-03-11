package cn.broadin.oms.action;

import it.sauronsoftware.base64.Base64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.broadin.oms.model.AlbumBean;
import cn.broadin.oms.model.AlbumMediaBean;
import cn.broadin.oms.model.ClubBean;
import cn.broadin.oms.model.ManagerBean;
import cn.broadin.oms.model.MediaBean;
import cn.broadin.oms.model.SourceAlbumMediaBean;
import cn.broadin.oms.model.UserBean;
import cn.broadin.oms.model.UserClubBean;
import cn.broadin.oms.service.AlbumService;
import cn.broadin.oms.service.ClubService;
import cn.broadin.oms.service.MediaService;
import cn.broadin.oms.service.SourceAlbumService;
import cn.broadin.oms.service.UserService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PagePlugin;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.Tools;

@Controller("userAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserAction extends ActionSupport{

	
	private static final long serialVersionUID = 1L;
	private int page; // 当前页码
	private int num = 6; // 分页链接数量(限定为5,总页数少于5全部显示)
	private String pageLinks; // 分页链接
	private int pageSize = 8; // 每页取多少记录
	private String keyWords;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private Map<String, MediaBean> mediaMap = new HashMap<String, MediaBean>();
	private Map<String, String> paramMap = new HashMap<String, String>();
	private PaginationBean paginationBean;
	private List<ConditionBean> clist = new ArrayList<ConditionBean>();
	
	@Resource
	private UserService userService;
	@Resource
	private ClubService clubService;
	@Resource
	private AlbumService albumService;
	@Resource
	private MediaService mediaService;
	@Resource
	private SourceAlbumService sourceAlbumService;
	private String success;
	private UserBean userBean;
	private List<ClubBean> cbList = new ArrayList<ClubBean>();
	private List<AlbumMediaBean> amList = new ArrayList<AlbumMediaBean>();
	private Set<UserClubBean> ucSet = new HashSet<UserClubBean>();

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PaginationBean getPaginationBean() {
		return paginationBean;
	}

	public void setPaginationBean(PaginationBean paginationBean) {
		this.paginationBean = paginationBean;
	}

	public List<ConditionBean> getClist() {
		return clist;
	}

	public void setClist(List<ConditionBean> clist) {
		this.clist = clist;
	}

	public String getPageLinks() {
		return pageLinks;
	}

	public void setPageLinks(String pageLinks) {
		this.pageLinks = pageLinks;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public Map<String, Object> getResultJson() {
		return resultJson;
	}

	public void setResultJson(Map<String, Object> resultJson) {
		this.resultJson = resultJson;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public List<ClubBean> getCbList() {
		return cbList;
	}

	public void setCbList(List<ClubBean> cbList) {
		this.cbList = cbList;
	}

	public List<AlbumMediaBean> getAmList() {
		return amList;
	}

	public void setAmList(List<AlbumMediaBean> amList) {
		this.amList = amList;
	}
	
	public Set<UserClubBean> getUcSet() {
		return ucSet;
	}

	public void setUcSet(Set<UserClubBean> ucSet) {
		this.ucSet = ucSet;
	}
	
	public Map<String, MediaBean> getMediaMap() {
		return mediaMap;
	}

	public void setMediaMap(Map<String, MediaBean> mediaMap) {
		this.mediaMap = mediaMap;
	}
	
	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 处理注册的方法
	 * 
	 * @return
	 */
	public String register() {
		if(null != paramMap.get("account") 
				&& null != paramMap.get("password")
				&& null != paramMap.get("email")
				&& null != paramMap.get("nickname")){
			
			String account = paramMap.get("account");
			String password = paramMap.get("password");
			String email = paramMap.get("email");
			String nickname = paramMap.get("nickname");
			
			clist.add(new ConditionBean("account", account, ConditionBean.EQ));
			List<UserBean> ulist = userService.selectByAccount(UserBean.class,clist,null,false,null,null); 
			
			if (null == ulist || ulist.isEmpty()) {
				//userBean = new UserBean(Tools.getMD5AndUUID(16), account, password, email, nickname, "", "", "", "0", "", "", "", "", "", "", "", 0, 0, "0", "0", "", "", "", Tools.getNowUTC(), Tools.getNowUTC(), 0, Tools.getNowUTC(), "");
				UserBean user = new UserBean(Tools.getMD5AndUUID(32), account, password, email, nickname, "", "", "", "0", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 0, 0, "0", "0", "", "", "", Tools.getNowUTC(), Tools.getNowUTC(), 0, Tools.getNowUTC(), "");
				if(null != userService.addUser(user)){
					resultJson.put("result", "00000000");
					resultJson.put("user", user);
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("user", user);
				}
			} else if(ulist!=null && ulist.size() != 0 && "2".equals(ulist.get(0).getStatus())){
				UserBean user = ulist.get(0);
				user.setStatus("1");
				user.setPassword(password);
				user.setNickname(nickname);
				user.setEmail(email);
				user.setLast_modify_utc(Tools.getNowUTC());
				if(null != userService.update(user)){
					resultJson.put("result", "00000002"); //还原成功
					resultJson.put("user", user);
					return Action.SUCCESS;
				}else{
					resultJson.put("result", "00000003"); //还原失败
					resultJson.put("user", user);
					return Action.SUCCESS;
				}
			}else{
				resultJson.put("result", "00000004");//手机号码已被注册
			}
			return Action.SUCCESS;
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}

	/**
	 * 处理用户分页的方法
	 * 
	 * @return
	 */
	public String userList() {
		Map<String, String> param = new HashMap<String, String>();
		if (0 == page) page = 1;
		int startIndex = (page - 1) * pageSize;
		paginationBean = new PaginationBean(startIndex, pageSize, 0,null);
		
		//用户状态（正常-禁用）
		if("1".equals(paramMap.get("status"))){
			clist.add(new ConditionBean("status", "1", ConditionBean.EQ));
			param.put("paramMap['status']", paramMap.get("status"));
		}else if("0".equals(paramMap.get("status"))){
			clist.add(new ConditionBean("status", "0", ConditionBean.EQ));
			param.put("paramMap['status']", paramMap.get("status"));
		}else{
			param.put("paramMap['status']", "2");
			List<String> sList = new ArrayList<String>();
			sList.add("0");
			sList.add("1");
			clist.add(new ConditionBean("status", sList, ConditionBean.IN));
		}
		//关键字查询
		if(null != keyWords && !"".equals(keyWords)){
			//利用Criterion
			Criterion[] cri = {Restrictions.like("account", "%"+keyWords+"%"),
					Restrictions.like("nickname", "%"+keyWords+"%")};
			clist.add(new ConditionBean(null, cri, ConditionBean.OR));
			param.put("keyWords", keyWords);
		}
		//排序
		if(!paramMap.isEmpty() && null != paramMap.get("orderColumn")){
			String orderColumn = paramMap.get("orderColumn");
			paginationBean = userService.pageList(UserBean.class, clist,null,false,orderColumn, paginationBean);
			param.put("paramMap['orderColumn']", orderColumn);
		}else paginationBean = userService.pageList(UserBean.class, clist,null,false,"register_utc", paginationBean);
		
		//获得分页的导航
		String href="userAction_userList";
		PagePlugin pagePlugin = new PagePlugin(startIndex, pageSize,num,page, paginationBean.getTotalCount(), href.toString(), param);
		pageLinks = pagePlugin.getPageLinkStr();
		success = "pages/user/userList.jsp";
		return Action.SUCCESS;
	}
//	/**
//	 * 获得页面超链接(分页链接)
//	 * 
//	 * @return
//	 * @return
//	 */
//	public String pageLinks(String method,String jspPage) {
//		totalPage = (int) Math.ceil((paginationBean.getTotalCount() * 1.0)
//				/ pageSize);
//		int pageUp = page - 1;
//		int pageDown = page + 1;
//		if (page - 1 < 1)
//			pageUp = 1;
//		if (page + 1 > totalPage)
//			pageDown = totalPage;
//		StringBuffer sb = new StringBuffer();
//		sb.append("<li><a href=\"" + method + "page=1\"> 首页 </a></li>");
//		sb.append("<li><a href=\"" + method + "page=" + pageUp
//				+ "\"> 上一页 </a></li>");
//		//分为①总页数大于指定链接数(5)②总页数小于等于5
//		if(totalPage>num){
//			if(page==1||page==2){
//				for(int i = 1;i<=num;i++){
//					sb.append(this.getCenterLinks(i,method));
//				}
//			}else if((page==totalPage-1)||page==totalPage){
//				for(int i=totalPage-num+1;i<=totalPage;i++){
//					sb.append(this.getCenterLinks(i,method));
//				}
//			}else{
//				for(int i=page-2;i<=page+2;i++){
//					sb.append(this.getCenterLinks(i,method));
//				}
//			}
//		}else{
//			for(int i = 1;i<=totalPage;i++){
//				sb.append(this.getCenterLinks(i,method));
//			}
//		}
//		sb.append("<li><a href=\"" + method + "page=" + pageDown
//				+ "\"> 下一页 </a></li>");
//		sb.append("<li><a href=\"" + method + "page=" + totalPage
//				+ "\"> 末页 </a></li>");
//		pageLinks = sb.toString();
//		totalUser = userService.selectAllUser(UserBean.class).size();
//		success = "pages//user//"+jspPage;
//		return pageLinks;
//	}
//
//	/**
//	 * 获得中间链接(1、2、3、4、5)
//	 * @param i          起始页
//	 * @param method	调用指定action的指定方法
//	 * @param sb		
//	 * @return
//	 */
//	private String getCenterLinks(int i, String method) {
//		String str = "";
//			if (i != page) {
//				str += "<li><a href=\"" + method + "page=" + i + "\">" + i
//						+ "</a></li>";
//			} else {
//				str += "<li class='active'><a>" + i + "</a></li>";
//			}
//		return str;
//	}

	/**
	 * 根据ID查找帐号信息
	 * 跳转至编辑页面
	 * @return
	 */
	public String selectUserBean() {
		userBean = userService.selectById(UserBean.class, paramMap.get("id"));
		if(null != userBean){
			success = "pages/user/edit.jsp";
		}
		return Action.SUCCESS;
	}

	/**
	 * 更新实体UserBean
	 * 
	 * @return
	 */
	public String editUserBean(){
		if(null != userBean){
			UserBean user = userService.selectById(UserBean.class, userBean.getId());
			if(null != user){
				user.setEmail(userBean.getEmail());
				user.setNickname(userBean.getNickname());
				if(null != userBean.getPassword() && !"".equals(userBean.getPassword()))
				user.setPassword(userBean.getPassword());
				if(null != userService.update(user)){
					resultJson.put("result","00000000");
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "update failed");
				}
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "input param is error");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is error");
		}
		return Action.SUCCESS;
	}

	/**
	 * 删除用户实体UserBean(把status设置为"2",表示删除状态，实际上放入了回收站)
	 * 实际并为彻底删除,可还原
	 * @return
	 */
	public String delUserBean() {
		if(paramMap.containsKey("id")){
			UserBean user = userService.selectById(UserBean.class, paramMap.get("id"));
			user.setStatus("2");
			user.setLast_modify_utc(Tools.getNowUTC());
			if(null != userService.update(user)){
				resultJson.put("result", "00000000");
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}

	/**
	 * 处理局部刷新 启用/禁用的方法
	 * 
	 * @return
	 */
	public String startOrForbid() {
		if(paramMap.containsKey("id")){
			UserBean userBean = userService.selectById(UserBean.class, paramMap.get("id"));
			if(null != userBean){
				if (!paramMap.containsKey("cause")) {
					userBean.setDisable_reason("");
					userBean.setStatus("1");
				} else {
					userBean.setStatus("0");
					userBean.setDisable_reason(paramMap.get("cause"));
				}
				userBean.setLast_modify_utc(Tools.getNowUTC());
				if(null != userService.update(userBean)){
					resultJson.put("result", "00000000");
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 回收站列表
	 * @return
	 */
	public String recycleList(){
		if (0 == page) page = 1;
		int startIndex = (page - 1) * pageSize;
		paginationBean = new PaginationBean(startIndex, pageSize, 0,null);
		
		Map<String, String> param = new HashMap<String, String>();
		String href = "userAction_recycleList";
		//回收站的状态字段
		clist.add(new ConditionBean("status", "2", ConditionBean.EQ));
		paramMap.put("status", "2");
		
		//关键字
		if(null != keyWords && !"".equals(keyWords)){
			Criterion[] cri = {Restrictions.like("account", "%"+keyWords+"%"),
					Restrictions.like("nickname", "%"+keyWords+"%")};
			clist.add(new ConditionBean(null, cri, ConditionBean.OR));
			param.put("keyWords", keyWords);
		}
		paginationBean = userService.pageList(UserBean.class, clist, null, false,"last_modify_utc", paginationBean);
		
		PagePlugin pagePlugin = new PagePlugin(startIndex, pageSize, num, page, paginationBean.getTotalCount(), href, param);
		pageLinks = pagePlugin.getPageLinkStr();
		success = "pages/user/recycle.jsp";
		return Action.SUCCESS;
	}
	
	/**
	 * 处理还原的方法
	 * @return
	 */
	public String restore(){
		if(paramMap.containsKey("id")){
			UserBean userBean = userService.selectById(UserBean.class, paramMap.get("id"));
			if(null != userBean){
				userBean.setStatus("1");	//还原成正常状态
				userBean.setLast_modify_utc(Tools.getNowUTC());
				if(null != userService.update(userBean)){
					resultJson.put("result", "00000000");
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}

	/**
	 * 彻底删除用户的方法
	 * @return
	 */
	public String delThorough(){
		if(paramMap.containsKey("id") && paramMap.containsKey("password")){
			ManagerBean manager = (ManagerBean) ActionContext.getContext().getSession().get("admin");
			if(manager.getPassword().equals(paramMap.get("password"))){
				UserBean user = userService.selectById(UserBean.class, paramMap.get("id"));
				if(null != user){
					if(null != userService.delUserBean(user)){
						resultJson.put("result", "00000000");
						return Action.SUCCESS;
					}
				}
				resultJson.put("result", "00000001");
				return Action.SUCCESS;
			}else resultJson.put("result", "00000002");   //账号密码错误
		}else resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 跳转显示家庭
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String familyInfo(){
		userBean = userService.selectById(UserBean.class, paramMap.get("id"));
		clist.add(new ConditionBean("creater", userBean, ConditionBean.EQ));
		cbList = (List<ClubBean>) ((PaginationBean)clubService.findClub(clist,null,false,"create_utc",null)).getRecords();	//指定ID所创建的群(家庭)的集合
		
		ucSet = userBean.getuSet();
//		Set<UserClubBean> ucbSet = new HashSet<UserClubBean>();				//所有加入的群（包括自己创建的群）
//		for (ClubBean cb : cbList) {
//			Iterator<UserClubBean> uc = ucSet.iterator();
//			while(uc.hasNext()){
//				UserClubBean ucb = uc.next();
//				if(cb.getId().equals(ucb.getClub().getId())) ucbSet.add(ucb);
//			}
//		}
		ucSet.removeAll(cbList);
		success = "pages/user/family.jsp";
		return Action.SUCCESS;
	}
	
	/**
	 * 查看指定家庭详细信息
	 * @return
	 */
	public String getFamilyInfo(){
		if(paramMap.containsKey("familyId")){
			ClubBean club = clubService.selectClubById(paramMap.get("familyId"));
			if(null!=club){
				resultJson.put("club", club);
				resultJson.put("creater", club.getCreaterPhone());
				clist.add(new ConditionBean("club", club, ConditionBean.EQ));
				clist.add(new ConditionBean("club_user_status", "1", ConditionBean.EQ));
				List<UserClubBean> ucList = clubService.selectUserClub(UserClubBean.class,clist,0,true,"join_utc",null);
				List<UserBean> users = new ArrayList<UserBean>(); 
				for (UserClubBean uc : ucList) {
					UserBean userBean  = uc.getUser();
					userBean.setNickname(uc.getUser_nickname());
					users.add(userBean);
				}
				resultJson.put("member", users);
				resultJson.put("result", "00000000");
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 退出家庭(放入回收站，club_user_status的值改为2)
	 * @return
	 */
	public String quitFamily(){
		if(paramMap.containsKey("familyId") && paramMap.containsKey("id")){
			ClubBean club = clubService.selectClubById(paramMap.get("familyId"));
			UserBean user = userService.selectById(UserBean.class, paramMap.get("id"));
			if(null != club && null != user){
				clist.add(new ConditionBean("club", club, ConditionBean.EQ));
				clist.add(new ConditionBean("user", user, ConditionBean.EQ));
				List<UserClubBean> ucs = clubService.selectUserClub(UserClubBean.class,clist,0,true,"join_utc",null);
				if(null != ucs && !ucs.isEmpty()){
					UserClubBean uc =ucs.get(0);
					uc.setClub_user_status("2");
					if(null != clubService.updateUserClub(uc)){
						resultJson.put("result", "00000000");
						return Action.SUCCESS;
					}
				}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 显示全部相册
	 * @return
	 */
	public String showAlbum(){
		userBean = userService.selectById(UserBean.class, paramMap.get("id"));
		
		pageSize = 12;    //设定相册一页显示12条记录
		if (0 == page) page = 1;
		int startIndex = (page - 1) * pageSize;
		
		paginationBean = new PaginationBean(startIndex, pageSize, 0,null);
		clist.add(new ConditionBean("creater", userBean, ConditionBean.EQ));
		Map<String, String> param = new HashMap<String, String>();
		param.put("paramMap['id']", paramMap.get("id"));
		
		String href = "userAction_showAlbum";
		if(paramMap.containsKey("option") && 0 != paramMap.get("option").trim().length()) {
			clist.add(new ConditionBean("type", paramMap.get("option"), ConditionBean.EQ));
			param.put("paramMap['option']", paramMap.get("option"));
		}
		paginationBean = userService.pageList(AlbumBean.class, clist, 0, false,
				"create_utc", paginationBean);

		PagePlugin pagePlugin = new PagePlugin(startIndex, pageSize, num, page, paginationBean.getTotalCount(), href, param);
		pageLinks = pagePlugin.getPageLinkStr();
		success = "pages/user/edit_album.jsp";
		return Action.SUCCESS;
	}
	
	/**
	 * 解锁/锁定相册（字段permission为"5"表示锁定，解锁后默认为"0"）
	 * @return
	 */
	public String unlockOrLock(){
		AlbumBean album = albumService.selectById(paramMap.get("albumId"));
		if(null!=album){
			if(album.getPermission().equals("5")){
				album.setPermission("0");
			}else{
				album.setPermission("5");
			}
			if(albumService.update(album)) resultJson.put("result", "00000000");
			return Action.SUCCESS;
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}	
	
	/**
	 * 跳转到查看相册里面的相片
	 * @return
	 */
	public String showAlbumPhoto(){
		pageSize = 12;    //设定相册一页显示12条记录
		userBean = userService.selectById(UserBean.class, paramMap.get("id"));
		AlbumBean album = albumService.selectById(paramMap.get("albumId"));
		paramMap.put("option", album.getType());
		userBean.setAddr(album.getName());   //把相册名设置给地址（借助）
		if (0 == page) page = 1;
		int startIndex = (page - 1) * pageSize;
		//参数MAP
		Map<String, String> param = new HashMap<String, String>();
		param.put("paramMap['id']", paramMap.get("id"));
		param.put("paramMap['albumId']", paramMap.get("albumId"));
		
		paginationBean = new PaginationBean(startIndex, pageSize, 0,null);
		clist.add(new ConditionBean("album", album, ConditionBean.EQ));
		clist.add(new ConditionBean("user", userBean, ConditionBean.EQ));
		paginationBean = albumService.pageList(AlbumMediaBean.class, clist, 0, false,
				"utc", paginationBean);
		
		String href = "userAction_showAlbumPhoto";
		PagePlugin pagePlugin = new PagePlugin(startIndex, pageSize, num, page, paginationBean.getTotalCount(), href, param);
		pageLinks = pagePlugin.getPageLinkStr();
		success = "pages/user/edit_album_show.jsp";
		return Action.SUCCESS;
		
	}
	
	/**
	 * 锁定/解锁相片（审核未通过2为锁定，审核通过1为解锁）
	 * @return
	 */
	public String unlockOrLockMedia(){
		if(paramMap.containsKey("mediaId")){
			MediaBean media = mediaService.findById(paramMap.get("mediaId"));
			if(null!=media){
				if("2".equals(media.getAudit_status())){
					media.setAudit_status("1");  //审核通过（解锁）
				}else{
					media.setAudit_status("2");  //审核未通过（锁定）
				}
				if(null!=mediaService.updateMedia(media)){
					resultJson.put("result", "00000000");
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 *查看相册里面的media 
	 */
	public String getAlbumMedia(){
		userBean = userService.selectById(UserBean.class, paramMap.get("id"));
		pageSize = 12;    //设定相册一页显示12条记录
		if (0 == page) page = 1;
		int startIndex = (page - 1) * pageSize;
		paginationBean = new PaginationBean(startIndex, pageSize, 0,null);
		Map<String, String> param = new HashMap<String, String>();
		param.put("paramMap['id']", paramMap.get("id"));
		//====================================
		
		clist.add(new ConditionBean("user", userBean, ConditionBean.EQ));
		List<String> slist = new ArrayList<String>();
		if("0".equals(paramMap.get("option")) || "3".equals(paramMap.get("option"))){
			slist.add("0");slist.add("4");
			clist.add(new ConditionBean("type", slist, ConditionBean.IN));
			param.put("paramMap['option']",paramMap.get("option"));
		}else{
			slist.add("4");slist.add(paramMap.get("option"));
			clist.add(new ConditionBean("type", slist, ConditionBean.IN));
			param.put("paramMap['option']", paramMap.get("option"));
		}
		List<AlbumMediaBean> albumMedia = mediaService.selectAlbumMedia(AlbumMediaBean.class, clist);
		if(null != albumMedia && !albumMedia.isEmpty()){
			List<MediaBean> mList = new ArrayList<MediaBean>();
			for (AlbumMediaBean aMedia : albumMedia) {
				MediaBean media = aMedia.getMedia();
				//普通
				if(aMedia.getAlbumType().equals("0")){
					mList.add(media);
				//时间
				}else{
					String[] s = media.getContent().split("\\|");
					for (String str: s) {
						if(null != str && !"".equals(str)){
							MediaBean m = mediaService.findById(str);
							if(null != m){
								if(Integer.parseInt(paramMap.get("option")) == m.getType()) mList.add(m);
								else if(m.getType() == 0 && "3".equals(paramMap.get("option"))) mList.add(m);
							}
						}
					}
				}
			}
			clist.clear();
			clist.add(new ConditionBean("sourceAlbumId", "SOURCE_ALBUM_PREFIX_"+paramMap.get("id"), ConditionBean.EQ));
			List<SourceAlbumMediaBean> saMedias = sourceAlbumService.pageList(SourceAlbumMediaBean.class,clist,null,false,"lastModifyUtc",null);
			if(null != saMedias && 0 != saMedias.size()){
				for (SourceAlbumMediaBean saMedia : saMedias) {
					MediaBean m = saMedia.getMedia();
					if(Integer.parseInt(paramMap.get("option")) == m.getType()) mList.add(m);
					else if(m.getType() == 0 && "3".equals(paramMap.get("option"))) mList.add(m);
				}
			}
			//筛选锁定的相片
			if(paramMap.get("option").equals("3")){
				List<MediaBean> ms = new ArrayList<MediaBean>();
				for (MediaBean mediaBean : mList) {
					if(mediaBean.getAudit_status().equals("2")) ms.add(mediaBean);
				}
				mList = ms;
			}
			List<MediaBean> medias = new ArrayList<MediaBean>();
			mList = new ArrayList<MediaBean>(new LinkedHashSet<MediaBean>(mList));
			int totalCount = mList.size();
			for (int i = startIndex; i<startIndex+pageSize&&i<totalCount;i++) {
				medias.add(mList.get(i));
			}
			paginationBean = new PaginationBean(startIndex, pageSize, totalCount, medias);
		}
		//====================================
		//分页导航
		String href="userAction_getAlbumMedia";
		if("2".equals(paramMap.get("option"))){
			success = "pages/user/album_audio_show.jsp";
		}else if("3".equals(paramMap.get("option"))){
			success = "pages/user/lock_photo.jsp";
		}else{
			success = "pages/user/album_media_show.jsp";
		}
		PagePlugin pagePlugin = new PagePlugin(startIndex, pageSize, num, page, paginationBean.getTotalCount(), href, param);
		pageLinks = pagePlugin.getPageLinkStr();
		return Action.SUCCESS;
	}
	
	/**
	 * 跳转到锁定相册列表
	 * @return
	 */
	public String showLockAlbum(){
		pageSize = 12;    //设定相册一页显示12条记录
		if (0 == page) page = 1;
		int startIndex = (page - 1) * pageSize;
		
		Map<String, String> param = new HashMap<String, String>();
		userBean = userService.selectById(UserBean.class, paramMap.get("id"));
		param.put("paramMap['id']", paramMap.get("id"));
		
		paginationBean = new PaginationBean(startIndex, pageSize, 0,null);
		//用户自己创建的锁定的相册
		clist.add(new ConditionBean("creater", userBean, ConditionBean.EQ));
		clist.add(new ConditionBean("permission", "5", ConditionBean.EQ));
		paginationBean = userService.pageList(AlbumBean.class, clist, 0, false,
				"create_utc", paginationBean);
		
		String href = "userAction_showLockAlbum";
		PagePlugin pagePlugin = new PagePlugin(startIndex, pageSize, num, page, paginationBean.getTotalCount(), href, param);
		pageLinks = pagePlugin.getPageLinkStr();
		success = "pages/user/lock_album.jsp";
		//		this.pageLinks("userAction_showLockAlbum?uid="+uid+"&&","lock_album.jsp");
		return Action.SUCCESS;
	}
	
	/**
	 * 查看时间相册里面的相片
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showAlbumTimer(){
		pageSize = 12;    //设定相册一页显示12条记录
		userBean = userService.selectById(UserBean.class, paramMap.get("id"));
		AlbumBean album = albumService.selectById(paramMap.get("albumId")); 
		paramMap.put("option", album.getType());
		userBean.setAddr(album.getName());
		if(null != userBean && null != album){
			if (0 == page) page = 1;
			int startIndex = (page - 1) * pageSize;
			paginationBean = new PaginationBean(startIndex, pageSize, 0,null);
			//用户创建的被锁定的相片
			clist.add(new ConditionBean("user", userBean, ConditionBean.EQ));
			clist.add(new ConditionBean("album", album, ConditionBean.EQ));
			paginationBean = albumService.pageList(AlbumMediaBean.class,clist,0,false,"utc",paginationBean);
			amList = (List<AlbumMediaBean>) paginationBean.getRecords();
			if(null != amList && 0 != amList.size()){
				for (int i=0;i<amList.size();i++) {
					MediaBean m = amList.get(i).getMedia();
					if(null != m){
						String[] sContent = m.getContent().split("\\|");
						List<MediaBean> mList = mediaService.findMedia(sContent);
						amList.get(i).setmList(mList);
					}
				}
			}
			
			String href = "userAction_showAlbumTimer";
			Map<String, String> param = new HashMap<String, String>();
			param.put("paramMap['id']", paramMap.get("id"));
			param.put("paramMap['albumId']", paramMap.get("albumId"));
			PagePlugin pagePlugin = new PagePlugin(startIndex, pageSize, num, page, paginationBean.getTotalCount(), href, param);
			pageLinks = pagePlugin.getPageLinkStr();
			success = "pages/user/timer_album_show.jsp";
//			this.pageLinks("userAction_showAlbumTimer?uid="+uid+"&&albumID="+albumID+"&&","timer_album_show.jsp");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 获得主题
	 * @return
	 */
	public String showAlbumTheme(){
		pageSize = 12;    //设定相册一页显示12条记录
		
		userBean = userService.selectById(UserBean.class, paramMap.get("id"));
		AlbumBean album = albumService.selectById(paramMap.get("albumId"));
		paramMap.put("option", album.getType());
		userBean.setAddr(album.getName());   //把相册名设置给地址（借助）
		if (0 == page) page = 1;
		int startIndex = (page - 1) * pageSize;
		paginationBean = new PaginationBean(startIndex,pageSize,0,null);
		
		clist.add(new ConditionBean("album", album, ConditionBean.EQ));
		clist.add(new ConditionBean("user", userBean, ConditionBean.EQ));
		clist.add(new ConditionBean("status", "0", ConditionBean.EQ));
		paginationBean = albumService.pageList(AlbumMediaBean.class, clist, 0, false,"utc", paginationBean);
		if(0 != paginationBean.getRecords().size()){
			MediaBean media = ((AlbumMediaBean)(paginationBean.getRecords().get(0))).getMedia();
			String xml = media.getContent();
			List<String> olist = this.parseXml(xml,0);
			List<MediaBean> mlist = new ArrayList<MediaBean>();
			int totalCount = olist.size();
			if(null != olist && 0 != olist.size()){
				for (int i=startIndex;i<(startIndex+pageSize)&&i<totalCount;i++) {
					String url = (String)(olist.get(i));
					media  = mediaService.findMediaByContent(url);
					mlist.add(media);
				}
			}
			paginationBean = new PaginationBean(startIndex, pageSize, totalCount, mlist);
		}
		String href = "userAction_showAlbumTheme";
		Map<String, String> param = new HashMap<String, String>();
		param.put("paramMap['id']", paramMap.get("id"));
		param.put("paramMap['albumId']", paramMap.get("albumId"));
		PagePlugin pagePlugin = new PagePlugin(startIndex, pageSize, num, page, paginationBean.getTotalCount(), href, param);
		pageLinks = pagePlugin.getPageLinkStr();
		success = "pages/user/theme_album_show.jsp";
//		this.pageLinks("userAction_showAlbumTheme?uid="+uid+"&&albumID="+albumID+"&&","theme_album_show.jsp");
		return Action.SUCCESS;
	}
	
	/**
	 * type 0-图片，1-音频    2-视频
	 * 解析获得媒资图片、音频、视频
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public List<String> parseXml(String xml,int type){
		Document document = null;
		try {
			Base64 base64 = new Base64();
			String s = base64.decode(xml, "UTF-8");
			document = DocumentHelper.parseText(s);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		List<String> slist = new ArrayList<String>();
		//图片媒资
		if(0 == type){
			String xPath = "//pic/resource";
			List<Element> elements = document.selectNodes(xPath);
			for (Element element : elements) {
				slist.add(element.attributeValue("url"));
			}
		//音频媒资
		}else if(1 == type){
			Element e = document.getRootElement();
			Element sound = e.element("sound");
			slist.add(sound.attributeValue("url"));
		//视频媒资
		}else{
			
		}
		return slist;
	}
	
	/**
	 * 查看家庭的相册
	 * @return
	 */
	public String showFamilyAlbum(){
		if(null != paramMap.get("familyId")){
			ClubBean club = clubService.selectClubById(paramMap.get("familyId"));
			if(null != club){
				clist.add(new ConditionBean("club", club, ConditionBean.EQ));
				List<AlbumBean> albums = albumService.selectByCondition(AlbumBean.class, clist, null, false, null, null);
				if(null != albums && 0 != albums.size()){
					resultJson.put("result", "00000000");
					resultJson.put("club", club);
					resultJson.put("albums", albums);
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 查看家庭相册的列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showFamilyAlbumMedia(){
		if(null != paramMap.get("albumId")){
			AlbumBean album = albumService.selectById(paramMap.get("albumId"));
			if(null != album){
				List<MediaBean> medias = new ArrayList<MediaBean>();
				clist.add(new ConditionBean("album", album, ConditionBean.EQ));
				List<AlbumMediaBean> albumMedias = (List<AlbumMediaBean>) albumService.pageList(AlbumMediaBean.class, clist, null, false, "utc", null).getRecords();
				JSONObject jo = new JSONObject();
				if(null != albumMedias){
					for (AlbumMediaBean am : albumMedias) {
						MediaBean media = am.getMedia();
						if(album.getType().equals("0")){		//普通
							medias.add(media);
						}else if(album.getType().equals("1")){	//主题
							String xml = media.getContent();
							List<String> strUrl = new ArrayList<String>(new LinkedHashSet<String>(this.parseXml(xml, 0)));
							for (String s : strUrl) {
								MediaBean m = mediaService.findMediaByContent(s);
								medias.add(m);
							}
						}else{							//时间
							String[] s = media.getContent().split("\\|");
							JSONArray ja = new JSONArray();
							for (String str: s) {
								if(null != str && !"".equals(str)){
									MediaBean m = mediaService.findById(str);
									ja.add(m);
								}
							}
							jo.element(am.getId(), ja);
						}
					}
					resultJson.put("result", "00000000");
					resultJson.put("medias", medias);
					resultJson.put("albumMedias", albumMedias);
					resultJson.put("mediaMap", jo);
					resultJson.put("club", album.getClub());
					resultJson.put("albumName", album.getName());
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
}
