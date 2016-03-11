package cn.broadin.oms.action;


import it.sauronsoftware.base64.Base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.AlbumBean;
import cn.broadin.oms.model.AlbumMediaBean;
import cn.broadin.oms.model.AlbumRecommendBean;
import cn.broadin.oms.model.CommonMediaBean;
import cn.broadin.oms.model.DecorateBean;
import cn.broadin.oms.model.DecorateGroupBean;
import cn.broadin.oms.model.ManagerBean;
import cn.broadin.oms.model.MediaBean;
import cn.broadin.oms.model.TemplateBean;
import cn.broadin.oms.model.TemplateMediaBean;
import cn.broadin.oms.service.AlbumService;
import cn.broadin.oms.service.CommonMediaService;
import cn.broadin.oms.service.DecorateService;
import cn.broadin.oms.service.MediaService;
import cn.broadin.oms.service.TemplateService;
import cn.broadin.oms.service.impl.CommonServiceImpl;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.OSSConst;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;
import cn.broadin.oms.util.Tools;

import com.aliyun.oss.OSSClient;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 相册推荐处理
 * 
 * @author hch
 * 
 */
@Controller("albumAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AlbumAction extends ActionSupport implements ServletResponseAware{

	private static final long serialVersionUID = 1L;
	@Resource
	private AlbumService albumService;
	@Resource
	private MediaService mediaService;
	@Resource
	private DecorateService decorateService;
	@Resource
	private TemplateService templateService;
	@Resource
	private CommonServiceImpl commonService;
	@Resource
	private CommonMediaService cMediaService;
	private int pageAlbum;
	private int pageRecommend;
	private int page; // 当前页
	private int pageSize = 5;
	private int num = 5;  //分页链接数
	private String type;
	private String keyWords;
	private String id;
	private String option;		//名称或者account
	private String success;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private Map<String, String> paramMap = new HashMap<String, String>();

	private String userId;
	private String categoryId;   //装饰品类别ID
	private String templateId;   //模版ID
	private String mediaId;      //媒资ID
	private String templateXml;   
	private String name;         //名称
	private String url;        //路径
	private String content;    //上传的xml形式的字符串
	private File file;			//图片文件
	private String fileFileName; 
	private String fileContentType;
	private String byteArray;    //字节流
	private String templateMediaId;
	private TemplateMediaBean templateMedia;
	private TemplateBean templateBean;
	private HttpServletResponse response;
	private List<String> base64Pic = new ArrayList<String>();
	
	public Map<String, Object> getResultJson() {
		return resultJson;
	}

	public void setResultJson(Map<String, Object> resultJson) {
		this.resultJson = resultJson;
	}

	public int getPageAlbum() {
		return pageAlbum;
	}

	public void setPageAlbum(int pageAlbum) {
		this.pageAlbum = pageAlbum;
	}

	public int getPageRecommend() {
		return pageRecommend;
	}

	public void setPageRecommend(int pageRecommend) {
		this.pageRecommend = pageRecommend;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getByteArray() {
		return byteArray;
	}

	public void setByteArray(String byteArray) {
		this.byteArray = byteArray;
	}

	public String getTemplateMediaId() {
		return templateMediaId;
	}

	public void setTemplateMediaId(String templateMediaId) {
		this.templateMediaId = templateMediaId;
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getTemplateXml() {
		return templateXml;
	}

	public void setTemplateXml(String templateXml) {
		this.templateXml = templateXml;
	}
	
	public TemplateMediaBean getTemplateMedia() {
		return templateMedia;
	}

	public void setTemplateMedia(TemplateMediaBean templateMedia) {
		this.templateMedia = templateMedia;
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

	public TemplateBean getTemplateBean() {
		return templateBean;
	}

	public void setTemplateBean(TemplateBean templateBean) {
		this.templateBean = templateBean;
	}
	
	
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public List<String> getBase64Pic() {
		return base64Pic;
	}

	public void setBase64Pic(List<String> base64Pic) {
		this.base64Pic = base64Pic;
	}
	
	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 跳转相册页面
	 * 
	 * @return
	 */
	public String albumList() {
		if(page==0) page=1;
		int startIndex = (page-1)*pageSize;
		PaginationBean pBean = new PaginationBean(startIndex, pageSize, 0,new ArrayList<AlbumBean>());
		List<ConditionBean> clist = new ArrayList<ConditionBean>();
		if (null == type || "".equals(type)) {
			clist.add(new ConditionBean("permission", "0", ConditionBean.EQ));
			pBean = albumService.pageAlbum(AlbumBean.class, clist, 0, false,"create_utc", pBean);
			resultJson.put("pageAlbum",(int) Math.ceil(pBean.getTotalCount() / (pageSize * 1.0)));
			resultJson.put("pBeanAlbum", pBean.getRecords().toArray());
			resultJson.put("totalAlbum", pBean.getTotalCount());
			resultJson.put("pageLinksAlbum", this.albumnList(pBean,"0"));
			clist.clear();
//			clist.add(0, new ConditionBean("permission", "4", ConditionBean.EQ));
			clist.add(new ConditionBean("weight", null, ConditionBean.IS_NOT_NULL));
			pBean = albumService.pageAlbum(AlbumBean.class, clist, 0, false,"weight", null);
			resultJson.put("pBeanRecommend", pBean.getRecords().toArray());
		} else if("0".equals(type)){
			clist.add(new ConditionBean("permission","0",ConditionBean.EQ));
			if("0".equals(option)){
				clist.add(new ConditionBean("name","%"+keyWords+"%",ConditionBean.LIKE));
			}else{
				clist.add(new ConditionBean("account","%"+keyWords+"%",ConditionBean.LIKE));
			}
			pBean = albumService.pageAlbum(AlbumBean.class, clist, 0, false,"create_utc", pBean);
			resultJson.put("pageAlbum",(int) Math.ceil(pBean.getTotalCount() / (pageSize * 1.0)));
			resultJson.put("pBeanAlbum", pBean.getRecords().toArray());
			resultJson.put("totalAlbum", pBean.getTotalCount());
			resultJson.put("pageLinksAlbum", this.albumnList(pBean,"0"));
		}else{
			clist.add(0, new ConditionBean("permission", "4", ConditionBean.EQ));
			pBean = albumService.pageAlbum(AlbumBean.class, clist, null, false,"create_utc", pBean);
			resultJson.put("pBeanRecommend", pBean.getRecords().toArray());
		}
		return Action.SUCCESS;
	}

	/**
	 * 处理推荐相册
	 * 
	 * @return
	 */
	public String recommendAlbum() {
		if(null != paramMap && null != paramMap.get("id") && null != paramMap.get("reason")){
			String album_id = paramMap.get("id");
			String reason = paramMap.get("reason");
			AlbumBean albumBean = albumService.selectById(album_id);
			if(null != albumBean){
					AlbumRecommendBean aRecommend = albumService.findARecommendById(album_id);
					if(null == aRecommend){
						Integer MaxWeight = albumService.findMaxWeight();
						int weight = MaxWeight==null?0:++MaxWeight;
						AlbumRecommendBean albumRecommend = new AlbumRecommendBean(albumBean.getId(), weight, reason, Tools.getNowUTC());
						AlbumRecommendBean ar = albumService.insertAlbumRecommend(albumRecommend);
						if(null != ar){
							resultJson.put("result", "00000000");
							return Action.SUCCESS;
						}
					}else{
						resultJson.put("result", "00000002");
						resultJson.put("tip", "请勿重复推荐同本相册");
						return Action.SUCCESS;
					}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 推荐相册的取消
	 * @return
	 */
	public String recommendAlbumCancel(){
		if(null != paramMap && null != paramMap.get("album_id")){
			AlbumRecommendBean albumRecommend = albumService.findARecommendById(paramMap.get("album_id"));
			if(null != albumRecommend){
				if(null != albumService.delAlbumRecommend(albumRecommend)){
					resultJson.put("result", "00000000");
				}else resultJson.put("result", "00000001");
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "this album can not find");
			}
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 推荐相册的置顶
	 * @return
	 */
	public String stickAlbum(){
		if(null != paramMap && null != paramMap.get("album_id")){
			String album_id = paramMap.get("album_id");
			AlbumRecommendBean albumRecommend = albumService.findARecommendById(album_id);
			if(null != albumRecommend){
				Integer MaxWeight = albumService.findMaxWeight();
				int weight = MaxWeight==null?0:++MaxWeight;
				albumRecommend.setWeight(weight++);
				if(null != albumService.updateAlbumRecommend(albumRecommend)){
					resultJson.put("result", "00000000");
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}

	/**
	 * 相册分页处理
	 * t(0表示左边相册，1表示右边推荐相册)
	 * @return
	 */
	public String albumnList(PaginationBean pBean,String t) {
		int totalPage = (int) Math.ceil((pBean.getTotalCount() * 1.0)/ pageSize);
		int pageUp = page - 1;
		int pageDown = page + 1;
		if(pageUp < 1) pageUp =1;
		if(pageDown > totalPage) pageDown = totalPage;
		StringBuffer sb = new StringBuffer();
		sb.append("<li><a href='javascript:void(0)' name='1,"+t+"'\">首页</a></li>");
		sb.append("<li><a href='javascript:void(0)' name='"+pageUp+","+t+"'\">上页</a></li>");
		if(totalPage>num){
			if(page==1||page==2){
				for(int i = 1;i<=num;i++){
					sb.append(this.getCenterLinks(i,t));
				}
			}else if((page==totalPage-1)||page==totalPage){
				for(int i=totalPage-num+1;i<=totalPage;i++){
					sb.append(this.getCenterLinks(i,t));
				}
			}else{
				for(int i=page-2;i<=page+2;i++){
					sb.append(this.getCenterLinks(i,t));
				}
			}
		}else{
			for(int i = 1;i<=totalPage;i++){
				sb.append(this.getCenterLinks(i,t));
			}
		}
		sb.append("<li><a href='javascript:void(0)' name='"+pageDown+","+t+"'\">下页</a></li>");
		sb.append("<li><a href='javascript:void(0)' name='"+totalPage+","+t+"'\">末页</a></li>");
		return sb.toString();
	}
	
	/**
	 * 分页中间链接处理
	 * @param i
	 * @param t
	 * @return
	 */
	private String getCenterLinks(int i, String t) {
		String str = "";
			if (i != page) {
				str += "<li><a href='javascript:void(0)' name='"+i+","+t+"'\">"+i+"</a></li>";
			} else {
				str += "<li class='active'><a href='javascript:void(0)' name='"+i+","+t+"'\">"+i+"</a></li>";
			}
		return str;
	}
	
	/**
	 * 1、上传媒资  (文件或字节流)
	 * @return
	 */
	public String uploadMedia(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if((null!=file || null!=byteArray) && null != type){
			if(null != byteArray && byteArray.contains("&#43;")) {
				byteArray = byteArray.replace("&#43;", "+");
			}
			OSSClient oc = commonService.createOSSClient();
			if(null==file && null!=byteArray)
				try {
					file = Tools.formatToFile(Tools.decodeBASE64(byteArray), "jpg");
				} catch (RuntimeException e1) {
					e1.printStackTrace();
				}
			String md5 = null;
			try {
				md5 = Tools.fileMD5(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			MediaBean media = mediaService.findById(md5);
			if(null == media){
				String ossUpKey = "",ObjectKey = "";
				if(type.equals("0")){
					ossUpKey = OSSConst.TEMPLATE_KEY_HEAD+md5;
				}else if(type.equals("1")){
					ossUpKey = OSSConst.MEDIA_VIDEO_KEY_HEAD+md5;
				}else if(type.equals("2")){
					ossUpKey = OSSConst.MEDIA_MUSIC_KEY_HEAD+md5;
				}
				if(null!=file && null!=byteArray){
					ObjectKey = ossUpKey+".jpg";
				}else{
					String[] s = fileFileName.split("\\.");
					ObjectKey= ossUpKey+"."+s[s.length-1];
					//ObjectKey = fileFileName;
				}
				String eTag = commonService.uploadSingleFile(oc, ObjectKey, file);
				if(eTag.equals(md5)){
					//上传成功后保存操作
					if(null == userId || "".equals(userId)){
						userId = "OMSADMIN"+((ManagerBean) ActionContext.getContext().getSession().get("admin")).getId();
					}else{
						userId = "OMSADMIN"+userId;
					}
					if("1".equals(type) || "2".equals(type)){
						url = OSSConst.ALIYUN_OTHER_DOMAIN+ObjectKey;
					}else{
						url = OSSConst.ALIYUN_IMAGE_DOMAIN+ObjectKey;
					}
					int timeLen = 0;
					//转换成图片，获取参数
					int w = 0;
					int h = 0;
					if(type.equals("0")){
						InputStream in = new ByteArrayInputStream(Tools.getBytes(file));
						try {
							BufferedImage bufImage = ImageIO.read(in);
							w = bufImage.getWidth();
							h = bufImage.getHeight();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
					media = new MediaBean(md5, Integer.parseInt(type), url, "", "", w, h, file.length(), timeLen, "0", userId, userId, Tools.getNowUTC(), Tools.getNowUTC());
					if(type.equals("1") || type.equals("2")) {
						media.setTimeLen(Tools.getAudioPlayTime(file));
						//media.setDescription(fileFileName);
					}
					ServiceResultBean srb1 = mediaService.mediaAdd(media);
					if(null!=file && null!=byteArray) file.delete();//
					if(srb1.isSuccess()){
						//把主题的背景音乐添加到通用媒资
						if(type.equals("2")){
							CommonMediaBean commonMedia = new CommonMediaBean(Tools.getMD5AndUUID(16), 1, 0, media.getId(), fileFileName, "", userId, Tools.getNowUTC());
							cMediaService.cMediaAdd(commonMedia);
						}
						//各种返回值
						resultJson.put("result", "00000000");
						resultJson.put("mediaId", md5);
						resultJson.put("url", media.getContent());
						if(null != fileFileName) resultJson.put("name", fileFileName);
						resultJson.put("width", media.getWidth());
						resultJson.put("height", media.getHeight());
						//resultJson.put("byteArray", byteArray);
						if(type.equals("1") || type.equals("2")) resultJson.put("duration", media.getTimeLen());
						return Action.SUCCESS;
					}else{
						resultJson.put("result", "00000001");
						resultJson.put("tip", "媒资保存失败");
						return Action.SUCCESS;
					}
				}
			}else{
				//删除字节流转换成文件后的垃圾
				if(null!=file && null!=byteArray) file.delete();
				resultJson.put("result", "00000000");
				resultJson.put("mediaId", md5);
				resultJson.put("url", media.getContent());
				if(null != fileFileName) resultJson.put("name", fileFileName);
				resultJson.put("width", media.getWidth());
				resultJson.put("height", media.getHeight());
				//resultJson.put("byteArray", byteArray);
				if(type.equals("1") || type.equals("2")) resultJson.put("duration", media.getTimeLen());
				return Action.SUCCESS;
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "参数错误");
			return Action.SUCCESS;
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 2、添加模版媒资  (name)
	 * @return
	 */
	public String saveTemplateMedia(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(null == userId || "".equals(userId)){
			userId = "OMSADMIN"+((ManagerBean) ActionContext.getContext().getSession().get("admin")).getId();
		}else{
			userId = "OMSADMIN"+userId;
		}
		TemplateBean template = templateService.findTemplateById(templateId);
		MediaBean media = mediaService.findById(mediaId);
		if(null!=template && null!=media){
			name = StringEscapeUtils.unescapeXml(name);
			TemplateMediaBean tmBean = new TemplateMediaBean(Tools.getUUIDString(""), template, media, "0", name, "", userId, "", Tools.getNowUTC(), userId, "", Tools.getNowUTC(), "", 0, 0, 0, type);
			ServiceResultBean srb = templateService.TemplateMediaAdd(tmBean);
			if(srb.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("templateMediaId", tmBean.getId());
				resultJson.put("name", name);
				resultJson.put("url", media.getContent());
				resultJson.put("width", media.getWidth());
				resultJson.put("height", media.getHeight());
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "添加模板失败");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "找不到此templateId或mediaId");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 3、添加饰品图(name)
	 * @return
	 */
	public String saveDecorateMedia(){
		response.setHeader("Access-Control-Allow-Origin","*");
		MediaBean media = mediaService.findById(mediaId);
		DecorateGroupBean dgBean = decorateService.findDecorateGroupById(categoryId);
		if(null != media && null != dgBean){
			DecorateBean decorate = new DecorateBean(Tools.getUUIDString(""), name, dgBean, media, Tools.getNowUTC());
			ServiceResultBean srb = decorateService.DecorateAdd(decorate);
			if(srb.isSuccess()){
				resultJson.put("result", "00000000");
				resultJson.put("decorateId", decorate.getId());
				resultJson.put("url", decorate.getMedia().getContent());
				resultJson.put("name", name);
				resultJson.put("width", decorate.getMedia().getWidth());
				resultJson.put("height", decorate.getMedia().getHeight());
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "添加饰品失败");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "找不到此mediaId或categoryId");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 4、获取装饰品类别列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDecorateGroup(){
		response.setHeader("Access-Control-Allow-Origin","*");
		List<DecorateGroupBean> dgList = (List<DecorateGroupBean>) decorateService.findDGPage(null, null).getRecords();
		if(null!=dgList){
			resultJson.put("result", "00000000");
			resultJson.put("dgList", dgList);
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "装饰品类别为空");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 5、获取指定类别下的饰品
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDGroupResource(){
		response.setHeader("Access-Control-Allow-Origin","*");
		DecorateGroupBean dgBean = decorateService.findDecorateGroupById(categoryId);
		if(null != dgBean){
			List<ConditionBean> clist = new ArrayList<ConditionBean>();
			clist.add(new ConditionBean("group", dgBean, ConditionBean.EQ));
			List<DecorateBean> dList = (List<DecorateBean>) decorateService.findDPage(clist,null,false,"createUtc", null).getRecords();
			if(null!=dList){
				resultJson.put("result", "00000000");
				resultJson.put("dList", dList);
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "找不到此类别下饰品");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "找不到此categoryId");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 6、获取指定模版信息
	 * @return
	 */
	public String getTemplateInfo(){
		response.setHeader("Access-Control-Allow-Origin","*");
		TemplateBean template = templateService.findTemplateById(templateId);
		if(null != template){
			List<ConditionBean> clist = new ArrayList<ConditionBean>();
			clist.add(new ConditionBean("template", template, ConditionBean.EQ));
			clist.add(new ConditionBean("type", "4", ConditionBean.EQ));
			List<TemplateMediaBean> tmList = templateService.findTMedia(clist,null);
			if(null != tmList && 0 != tmList.size()){
				resultJson.put("result", "00000000");
				resultJson.put("templateXml", tmList.get(0).getMedia().getContent());
			}else{
				resultJson.put("result", "00000000");
				resultJson.put("templateXml", "");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "找不到此templateId");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 7、保存模版信息
	 * @return
	 */
	public String saveTemplateInfo(){
		//if(templateXml.contains("&lt") || templateXml.contains("&gt") || templateXml.contains("&#43;")){
			templateXml = StringEscapeUtils.unescapeXml(templateXml);
		//}
		//templateXml = Tools.encodeBASE64(templateXml);
		response.setHeader("Access-Control-Allow-Origin","*");
		if(null == userId || "".equals(userId)){
			userId = "OMSADMIN"+((ManagerBean) ActionContext.getContext().getSession().get("admin")).getId();
		}else{
			userId = "OMSADMIN"+userId;
		}
		TemplateBean template = templateService.findTemplateById(templateId);
		List<ConditionBean> clist = new ArrayList<ConditionBean>();
		clist.add(new ConditionBean("template", template, ConditionBean.EQ));
		clist.add(new ConditionBean("type", "4", ConditionBean.EQ));
		//clist.add(new ConditionBean("user_id", userId, ConditionBean.EQ));
		List<TemplateMediaBean> tmList = (List<TemplateMediaBean>) mediaService.findTMedia(clist,null); 
		if(null != template && (null == tmList || 0 == tmList.size())){
			MediaBean media = new MediaBean(Tools.getUUIDString(""), 3, templateXml, "", "", 0, 0, 0, 0, "0", userId, userId, Tools.getNowUTC(), Tools.getNowUTC());
			ServiceResultBean srb = mediaService.mediaAdd(media);
			if(srb.isSuccess()){
				templateMedia = new TemplateMediaBean(Tools.getUUIDString(""), template, media, "0", "", "", userId, "", Tools.getNowUTC(), userId, "", Tools.getNowUTC(), "", 0, 0, 0, "4");
				srb = templateService.TemplateMediaAdd(templateMedia);
				if(srb.isSuccess()){
					resultJson.put("result", "00000000");
					return Action.SUCCESS;
				}
			}
		}else if(null != template && null != tmList && 0!= tmList.size()){
			MediaBean media = tmList.get(0).getMedia();
			media.setContent(templateXml);
			media.setCreateUtc(Tools.getNowUTC());
			media.setLastModUserId(userId);
			if(null != mediaService.updateMedia(media)){
				resultJson.put("result", "00000000");
				return Action.SUCCESS;
			}
		}else if(null == template){
			resultJson.put("result", "00000001");
			resultJson.put("tip", "找不到此templateId");
			return Action.SUCCESS;
		}
		resultJson.put("result", "00000001");
		resultJson.put("tip", "保存templateXml失败");
		return Action.SUCCESS;
	}
	
	/**
	 * 8、删除模版资源
	 * @return
	 */
	public String delTemplateMedia(){
		response.setHeader("Access-Control-Allow-Origin","*");
		templateMedia = templateService.findTemplateMedia(templateMediaId);
		if(null != templateMedia){
			List<String> list = new ArrayList<String>();
			list.add(templateMediaId);
			ServiceResultBean srb = templateService.delTemplateMedia(TemplateMediaBean.class, "id", list);
			if(srb.isSuccess()){
				resultJson.put("result", "00000000");
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "删除失败");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "找不到此templateMediaId");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 9、获取模版媒资列表
	 * @return
	 */
	public String getTemplateMediaList(){
		response.setHeader("Access-Control-Allow-Origin","*");
		TemplateBean tmBean = templateService.findTemplateById(templateId);
		if(null != tmBean && null != type){
			List<ConditionBean> clist = new ArrayList<ConditionBean>();
			clist.add(new ConditionBean("template", tmBean, ConditionBean.EQ));
			clist.add(new ConditionBean("type", type, ConditionBean.EQ));
			List<TemplateMediaBean> tmList = (List<TemplateMediaBean>) mediaService.findTMedia(clist,null); 
			List<MediaBean> mList = new ArrayList<MediaBean>();
			if( null != tmList && 0 != tmList.size()){
				for (TemplateMediaBean tm : tmList) {
					MediaBean media = tm.getMedia();
					media.setName(tm.getTitle());
					media.setId(tm.getId());
					mList.add(media);
				}
				if(null != mList && 0 != mList.size()){
					resultJson.put("result", "00000000");
					resultJson.put("mList", mList);
					return Action.SUCCESS;
				}
			}else{
				resultJson.put("result", "00000000");
				resultJson.put("mList", mList);
				return Action.SUCCESS;
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "找不到此templateI或type为空");
			return Action.SUCCESS;
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 获取后台上传的背景音乐
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getBgMusic(){
		if(0 != page && 0 != pageSize){
			int startIndex = (page-1)*pageSize;
			PaginationBean pagination = new PaginationBean(startIndex, pageSize, 0, null);
			List<ConditionBean> clist = new ArrayList<ConditionBean>();
			clist.add(new ConditionBean("type", 1, ConditionBean.EQ));
			clist.add(new ConditionBean("publicLevel", 0, ConditionBean.EQ));
			clist.add(new ConditionBean("userId", "%OMSADMIN%", ConditionBean.LIKE));
			pagination = cMediaService.pageList(CommonMediaBean.class, clist, 0, false, "utc", pagination);
			List<CommonMediaBean> records = (List<CommonMediaBean>) pagination.getRecords();
			if(null != records){
				List<MediaBean> mList = new ArrayList<MediaBean>();
				for (CommonMediaBean cMedia : records) {
					MediaBean media = mediaService.findById(cMedia.getMediaId());
					media.setName(cMedia.getTitle());
					media.setDescription(cMedia.getDetail());
					mList.add(media);
				}
				pagination.setRecords(mList);
				resultJson.put("result", "00000000");
				resultJson.put("pagination", pagination);
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "查询失败");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "传入的参数有误");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 跳转到模版制作页面      （提供templateId,返回templateMedia）
	 * @return
	 */
	public String templateMake(){
		ActionContext act = ActionContext.getContext();
		String token = Tools.getUUIDString("");
		act.getSession().put("token", token);
		templateBean = templateService.findTemplateById(templateId);
		success = "pages/template/templateMake.jsp";
		return Action.SUCCESS;
	}
	
	/**
	 * 解析xml获得相关数据
	 * @param xml
	 * @param type
	 * @return
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public List<String> parseXml(String xml,String type){
		Document document = null;
		try {
			Base64 base64 = new Base64();
			String s = base64.decode(xml, "UTF-8");
			document = DocumentHelper.parseText(s);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		List<String> slist = new ArrayList<String>();
		String xPath = null;
		//背景图片
		if("0".equals(type)) xPath = "//background";
		//缩略图
		else if("1".equals(type)) xPath = "//snap";
		//预览图
		else if("2".equals(type)) xPath = "//priview";
		//背景音乐
		else xPath = "//sound";
		List<Element> elements = document.selectNodes(xPath);
		for (Element element : elements) {
			slist.add(element.attributeValue("url"));
		}
		return slist;
	}
	
	/**
	 * 获取zip压缩
	 * @return
	 */
	public String getZip(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(null != base64Pic && 0 != base64Pic.size()){
			String path = ServletActionContext.getServletContext().getRealPath("/");
			String zipName = path+"FileResource\\"+Tools.getUUIDString("photo")+".zip";
			ZipOutputStream zip = null;
			try {
				zip = new ZipOutputStream(new FileOutputStream(zipName));
				for (String str : base64Pic) {
					str = str.replace("&#43;", "+");
					//System.out.println(str);
					File f = Tools.formatToFile(Tools.decodeBASE64(str), "jpg");
					Tools.getZip(zip, f);
					f.delete();
				}
				zip.close();	//注意要关闭zip流，否则上传到阿里云的数据会读取错误
				File file = new File(zipName); String ObjectKey = OSSConst.MEDIA_PHOTO_KEY_HEAD+Tools.fileMD5(file)+".zip";
				System.out.println(OSSConst.ALIYUN_OTHER_DOMAIN+ObjectKey);
				String eTag = commonService.uploadSingleFile(commonService.createOSSClient(), ObjectKey, file);
				if(Tools.fileMD5(file).equals(eTag)){
					file.delete();
					resultJson.put("result", "00000000");
					resultJson.put("zipName", OSSConst.ALIYUN_OTHER_DOMAIN+ObjectKey);
				}else resultJson.put("result", "00000001");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					zip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 解析主题相册xml,转为json对象返回
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getThemeAlbumXml(){
		response.setHeader("Access-Control-Allow-Origin","*");
		if(null != id){
			MediaBean media = mediaService.findById(id);
			if(null != media){
				String xml = media.getContent();
				Base64 base = new Base64();
				try {
					xml = base.decode(xml,"UTF-8");
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
				String json = Tools.xml2Json(xml);
				resultJson.put("result", "00000000");
				resultJson.put("jsonObj", json);
			}else resultJson.put("result", "00000001");
		}else resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 下载用户相册资源
	 * param option(0,普通;1,主题;2,时间)
	 * @return
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public String downAlbumResource(){
		if(null != id && !id.isEmpty()){
		AlbumBean album = albumService.selectById(id);
		if(null != album){
			String contextPath = ServletActionContext.getServletContext().getRealPath("/");
			ZipOutputStream zip = null;
			File f = new File(contextPath+"FileResource"+File.separator+album.getName()+".zip");
			try {
				zip = new ZipOutputStream(new FileOutputStream(f));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			//普通主题时光
			
			List<AlbumMediaBean> amList = album.getaMedias();
			if(null != amList && amList.size() != 0){
				if("0".equals(album.getType())){
					for (AlbumMediaBean albumMediaBean : amList) {
						String url = albumMediaBean.getMedia().getContent();
						if(url.startsWith("http")){
							File file = new File(Tools.getUrlFile(url));
							//放入压缩包后删除原图
							Tools.getZip(zip, file);
							file.delete();
						}
					}
				}else if("1".equals(album.getType())){
					String xml = amList.get(0).getMedia().getContent();
					Base64 base64 = new Base64();
					xml = base64.decode(xml, "UTF-8");
					try {
						Document document = DocumentHelper.parseText(xml);
						String xPath = "//resource";
						List<Element> elements = document.selectNodes(xPath);
						for (Element element : elements) {
							String url = element.attributeValue("url");
							if(url.startsWith("http")){
								File file = new File(Tools.getUrlFile(url));
								//放入压缩包后删除原图
								Tools.getZip(zip, file);
								file.delete();
							}
						}
					} catch (DocumentException e) {
						e.printStackTrace();
					}
					
				}else{
					for (AlbumMediaBean albumMediaBean : amList) {
						String content = albumMediaBean.getMedia().getContent();
						String[] ids = content.split("\\|");
						List<MediaBean> medias = mediaService.findMedia(ids);
						if(null != medias && medias.size() != 0){
							for (MediaBean mediaBean : medias) {
								String url = mediaBean.getContent();
								if(url.startsWith("http")){
									File file = new File(Tools.getUrlFile(url));
									//放入压缩包后删除原图
									Tools.getZip(zip, file);
									file.delete();
								}
							}
						}else continue;
					}	
				}
				try {
					//关闭流
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
}
