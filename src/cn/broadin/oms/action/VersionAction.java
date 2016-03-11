package cn.broadin.oms.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.ScreenshotBean;
import cn.broadin.oms.model.VersionSoftBean;
import cn.broadin.oms.model.VersionTerminalBean;
import cn.broadin.oms.service.VersionService;
import cn.broadin.oms.service.impl.CommonServiceImpl;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.OSSConst;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;
import cn.broadin.oms.util.Tools;

import com.aliyun.oss.OSSClient;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 版本处理类
 * @author huchanghuan
 *
 */
@Controller("versionAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class VersionAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private VersionService versionService;
	@Resource
	private CommonServiceImpl commonService;
	
	private VersionTerminalBean terminal;
	private VersionSoftBean softVersion;
	private List<ScreenshotBean> screenshots;       //版本截图
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private String param;
	private String id;
	
	private int page;
	private int startIndex;
	private int pageSize = 8;
	private PaginationBean pagination;
	private String pageLinks;
	private String success;
	private int totalPage;
	private int num = 5;
	private String keyWords;
	private File file;
	private String fileFileName; 
	private String fileContentType;
	   
	
	

	public VersionTerminalBean getTerminal() {
		return terminal;
	}

	public void setTerminal(VersionTerminalBean terminal) {
		this.terminal = terminal;
	}

	public void setSoftVersion(VersionSoftBean softVersion) {
		this.softVersion = softVersion;
	}

	public VersionSoftBean getSoftVersion() {
		return softVersion;
	}

	public Map<String, Object> getResultJson() {
		return resultJson;
	}

	public void setResultJson(Map<String, Object> resultJson) {
		this.resultJson = resultJson;
	}

	public List<ScreenshotBean> getScreenshots() {
		return screenshots;
	}

	public void setScreenshots(List<ScreenshotBean> screenshots) {
		this.screenshots = screenshots;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PaginationBean getPagination() {
		return pagination;
	}

	public void setPagination(PaginationBean pagination) {
		this.pagination = pagination;
	}

	public String getPageLinks() {
		return pageLinks;
	}

	public void setPageLinks(String pageLinks) {
		this.pageLinks = pageLinks;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * 新增软件终端
	 * @return
	 */
	public String terminalAdd(){
		if(null != terminal){
			terminal.setId(Tools.getMD5AndUUID(16));
			terminal.setCreateUtc(Tools.getNowUTC());
			terminal.setModifyUtc(Tools.getNowUTC());
			ServiceResultBean srb = versionService.terminalAdd(terminal);
			if(srb.isSuccess()){
				resultJson.put("result", "00000000");
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "add is failed");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 软件终端编辑
	 * @return
	 */
	public String terminalEdit(){
		if(null != terminal){
			terminal.setModifyUtc(Tools.getNowUTC());
			ServiceResultBean srb = versionService.terminalUpdate(terminal);
			if(srb.isSuccess()){
				resultJson.put("result", "00000000");
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "terminal_soft update failed");
			}
			
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is null");
		}
		return Action.SUCCESS;
	}
	
	
	/**
	 * 软件终端、软件版本、截图删除
	 * @return
	 */
	public String Del(){
		if(null != param && null != id){
			if("0".equals(param)){
				terminal = (VersionTerminalBean) versionService.findById("TerminalSoftBean", id);
				if(null != terminal){
					ServiceResultBean srb = versionService.Del(terminal);
					if(srb.isSuccess()){
						resultJson.put("result", "00000000");
					}else{
						resultJson.put("result", "00000001");
						resultJson.put("tip", "delete terminal_soft failed");
					}
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "can not find record");
				}
			}else if("1".equals(param)){
				softVersion = (VersionSoftBean) versionService.findById("SoftVersionBean", id);
				if(null != softVersion){
					ServiceResultBean srb = versionService.Del(softVersion);
					if(srb.isSuccess()){
						resultJson.put("result", "00000000");
					}else{
						resultJson.put("result", "00000001");
						resultJson.put("tip", "delete soft_version failed");
					}
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "can not find soft_version");
				}
			}else{
				ScreenshotBean screenshot = (ScreenshotBean) versionService.findById("Screenshot", id);
				if(null != screenshot){
					ServiceResultBean srb = versionService.Del(screenshot);
					if(srb.isSuccess()){
						resultJson.put("result", "00000000");
					}else{
						resultJson.put("result", "00000001");
						resultJson.put("tip", "delete screenshot failed");
					}
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "can not find screenshot");
				}
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 终端列表
	 * @return
	 */
	public String terminalList(){
		if (0 == page) page = 1;
		startIndex = (page - 1) * pageSize;
		pagination = new PaginationBean(startIndex, pageSize, 0,new ArrayList<VersionTerminalBean>());
		String href = "versionAction_terminalList?";
		List<ConditionBean> clist = new ArrayList<ConditionBean>();
		if(null != keyWords && !"".equals(keyWords)) {
			clist.add(new ConditionBean("name", "%"+keyWords+"%", ConditionBean.LIKE)); 
			href += "keyWords="+keyWords+"&&";
		}
		//获取分页记录
		pagination = versionService.findPage(VersionTerminalBean.class,clist,null,false,"createUtc",pagination);
		//获得链接
		this.pageLinks(href);
		//跳转页面
		success = "pages/versions/terminalList.jsp";
		return Action.SUCCESS;
	}
	
	/**
	 * 新建软件版本
	 * @return
	 */
	public String versionAdd(){
		if(null != softVersion 
			&& null != screenshots 
			&& 0 != screenshots.size()
			&& null != id){
			terminal = (VersionTerminalBean) versionService.findById("TerminalSoftBean", id);	
			if(null != terminal){
				softVersion.setCreateUtc(Tools.getNowUTC());
				softVersion.setModifyUtc(Tools.getNowUTC());
				softVersion.setVt(terminal);
				List<ScreenshotBean> sst = new ArrayList<ScreenshotBean>();
				for (int i=0;i<screenshots.size();i++) {
					if(null != screenshots.get(i)){
						screenshots.get(i).setVersion(softVersion);
						sst.add(screenshots.get(i));
					}
				}
				//添加
				ServiceResultBean srb = versionService.softVersionAdd(softVersion);
				if(srb.isSuccess()){
					srb = versionService.screenShotAdd(sst);
					if(srb.isSuccess()) resultJson.put("result", "00000000");
					else resultJson.put("result", "00000001");
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "soft_version add failed");
				}
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "can not find terminal_soft");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 更新软件版本
	 * @return
	 */
	public String versionEdit(){
		if(null != softVersion 
			&& null != id){
			terminal = (VersionTerminalBean) versionService.findById("TerminalSoftBean", id);	
			if(null != terminal){
				softVersion.setModifyUtc(Tools.getNowUTC());
				softVersion.setVt(terminal);
				List<ScreenshotBean> sst = new ArrayList<ScreenshotBean>();
				if(null != screenshots && 0 != screenshots.size()){
					for (int i=0;i<screenshots.size();i++) {
						if(null != screenshots.get(i)){
							screenshots.get(i).setVersion(softVersion);
							sst.add(screenshots.get(i));
						}
					}
				}
				//更新
				ServiceResultBean srb = versionService.softVersionUpdate(softVersion);
				if(srb.isSuccess()){
					if(0 != sst.size()){
						srb = versionService.screenShotAdd(sst);
						if(srb.isSuccess()) resultJson.put("result", "00000000");
						else resultJson.put("result", "00000001");
					}else resultJson.put("result", "00000000");
				}else{
					resultJson.put("result", "00000001");
					resultJson.put("tip", "soft_version add failed");
				}
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "can not find terminal_soft");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 页面跳转
	 * @return
	 */
	public String initPage(){
		//进入软件终端编辑页面
		if("0".equals(param)){
			terminal = (VersionTerminalBean) versionService.findById("TerminalSoftBean",id);
			success = "pages/versions/terminal_Edit.jsp";
			//进入指定软件终端的版本列表	
		}else if("1".equals(param)){
			//softVersion = 
			if (0 == page) page = 1;
			startIndex = (page - 1) * pageSize;
			pagination = new PaginationBean(startIndex, pageSize, 0,new ArrayList<VersionSoftBean>());
			String href = "versionAction_initPage?param=1&&id="+id+"&&";
			//获取软件
			terminal = (VersionTerminalBean) versionService.findById("TerminalSoftBean", id);
			List<ConditionBean> clist = new ArrayList<ConditionBean>();
			clist.add(new ConditionBean("vt", terminal, ConditionBean.EQ));
			//获取分页记录
			pagination = versionService.findPage(VersionSoftBean.class,clist,null,false,"createUtc",pagination);
			//获得链接
			this.pageLinks(href);
			success = "pages/versions/versionsList.jsp";
		//进入软件版本编辑
		}else if("2".equals(param)){
			softVersion = (VersionSoftBean) versionService.findById("SoftVersionBean", id);
			success = "pages/versions/versions_Edit.jsp";
		//进入软件版本新增页面
		}else{
			terminal = (VersionTerminalBean) versionService.findById("TerminalSoftBean", id);
			success = "pages/versions/versions_add.jsp";
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 上传
	 * @return
	 */
	public String upload(){
		if(null != file){
			OSSClient oc = commonService.createOSSClient();
			String key = Tools.getUUIDString("");
			String[] type = fileFileName.split("\\."); 
			String fileName = OSSConst.VERSION_KEY_HEAD+key+"."+fileFileName.split("\\.")[type.length-1];
			String eTag = commonService.uploadSingleFile(oc, fileName, this.file);
			String md5 = null;
			try {
				md5 = Tools.fileMD5(this.file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(md5.equals(eTag)){
				resultJson.put("result", "00000000");
				resultJson.put("url", OSSConst.ALIYUN_OTHER_DOMAIN+fileName);
				resultJson.put("name", fileFileName);
				resultJson.put("key", key);
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "upload is failed");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "input param is null");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 获得链接
	 * @param method
	 * @return
	 */
	public String pageLinks(String method) {
		totalPage = (int) Math.ceil((pagination.getTotalCount()*1.0)/pageSize);
		int pageUp = page - 1;
		int pageDown = page + 1;
		if (page - 1 < 1) pageUp = 1;
		if (page + 1 > totalPage) pageDown = totalPage;
		StringBuffer sb = new StringBuffer();
		sb.append("<li><a href=\"" + method + "page=1\"> 首页 </a></li>");
		sb.append("<li><a href=\"" + method + "page=" + pageUp
				+ "\"> 上一页 </a></li>");
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
		sb.append("<li><a href=\"" + method + "page=" + pageDown
				+ "\"> 下一页 </a></li>");
		sb.append("<li><a href=\"" + method + "page=" + totalPage
				+ "\"> 末页 </a></li>");
		pageLinks = sb.toString();
		return pageLinks;
	}
	
	/**
	 * 获得中间链接
	 * @param i
	 * @param method
	 * @return
	 */
	private String getCenterLinks(int i, String method) {
		String str = "";
			if (i != page) {
				str += "<li><a href=\"" + method + "page=" + i + "\">" + i
						+ "</a></li>";
			} else {
				str += "<li class='active'><a>" + i + "</a></li>";
			}
		return str;
	}
	
}
