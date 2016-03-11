package cn.broadin.oms.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.TerminalTypeBean;
import cn.broadin.oms.util.OSSConst;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * 公共不变属性业务处理类
 * 
 * @author xiejun
 */
@Service
public class CommonServiceImpl {
	
	private String endPoint = "http://res.meijx.cn/";
	private String bucket = "album-res";
	private String accessId = "96x5U9fL2wWGdYYy";
	private String accessKey = "1waGRiAdGUlZs8WryjfgRxeAp2uTnS";
	private OSSClient oc = null;
	private List<String> folders = new ArrayList<String>();
	private List<TerminalTypeBean> terminalTypes = new ArrayList<TerminalTypeBean>();
	@Resource
	private DAO dao;
	@Resource
	protected Map<String, String> aliyunConfig;// 对应config.properties文件
	
	
	
	public  String getEndPoint() {
		return endPoint;
	}
	
	public  void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public  String getBucket() {
		return bucket;
	}

	public  void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public  String getAccessId() {
		return accessId;
	}

	public  void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public  String getAccessKey() {
		return accessKey;
	}

	public  void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public  OSSClient getOc() {
		return oc;
	}

	public  void setOc(OSSClient oc) {
		this.oc = oc;
	}

	public List<String> getFolders() {
		return folders;
	}

	public void setFolders(List<String> folders) {
		this.folders = folders;
	}
	
	public List<TerminalTypeBean> getTerminalTypes() {
		return terminalTypes;
	}

	/**
	 * 随系统启动时执行，将数据库中公共不变属性加载到内存中
	 */
	@PostConstruct
	public void init() {
		this.endPoint = this.aliyunConfig.get("endPoint").trim();
		this.bucket = this.aliyunConfig.get("bucket").trim();
		this.accessId = this.aliyunConfig.get("accessId").trim();
		this.accessKey = this.aliyunConfig.get("accessKey").trim();
		initAliyunFolders();
	}
	
	@SuppressWarnings("unchecked")
	public void initTerminalTypes(){
		this.terminalTypes = (List<TerminalTypeBean>) dao.select(TerminalTypeBean.class);
	}
	
	public void initAliyunFolders(){
		this.folders.add(OSSConst.TEMPLATE_GROUP_KEY_HEAD);
		this.folders.add(OSSConst.TEMPLATE_KEY_HEAD);
		this.folders.add(OSSConst.DECORATE_KEY_GROUP_HEAD);
		this.folders.add(OSSConst.DECORATE_KEY_HEAD);
		this.folders.add(OSSConst.MEDIA_MUSIC_KEY_HEAD);
		this.folders.add(OSSConst.MEDIA_COMM_TEXT_KEY_HEAD);
		this.folders.add(OSSConst.MEDIA_PHOTO_KEY_HEAD);
		this.folders.add(OSSConst.MEDIA_VIDEO_KEY_HEAD);
		this.folders.add(OSSConst.MEDIA_XML_TEXT_KEY_HEAD);
		this.folders.add(OSSConst.AD_RESOURCE_KEY_HEAD);
		this.folders.add(OSSConst.VERSION_KEY_HEAD);
		this.folders.add(OSSConst.VERSION_SCREENSHOT_KEY_HEAD);
	}
	
	/**
	 * 创建OSSClient
	 * @return
	 */
	public OSSClient createOSSClient(){
		ClientConfiguration conf = createConf();
		System.out.println("--------------create OSSClient----------------");
		if(null == oc){
			oc = new OSSClient(this.endPoint,this.accessId,this.accessKey,conf);
			//oc = new OSSClient(this.endPoint,this.accessId,this.accessKey);
			System.out.println("--------------OSSClient create success----------------");
			bucketCheck(oc);
		}else{
			System.out.println("--------------OSSClient create already----------------");
		}
		return oc;
	}
	
	/**
	 * 参数配置
	 * @return
	 */
	private ClientConfiguration createConf(){
		ClientConfiguration conf = new ClientConfiguration();
		// 设置HTTP最大连接数为50
		conf.setMaxConnections(50);
		// 设置TCP连接超时为5000毫秒
		conf.setConnectionTimeout(5000);
		// 设置最大的重试次数为3
		conf.setMaxErrorRetry(3);
		// 设置Socket传输数据超时的时间为60000毫秒 （看看100M一分钟能不能上传完）
		conf.setSocketTimeout(60000);
		return conf;
	}
	
	/**
	 * 判断bucket是否已经存在,没有则创建
	 * @return
	 */
	private void bucketCheck(OSSClient client){
		// 获取Bucket的存在信息
		boolean exists = client.doesBucketExist(this.bucket);
		if(!exists){
			client.createBucket(this.bucket);
		}
	}
	
	/**
	 * 创建模拟文件夹
	 * @param client
	 * @param bucketName
	 * @param folderName
	 * 	folderName (Object)的命名规范如下：使用UTF-8编码
	 *                  长度必须在1-1023字节之间
     *                  不能以“/”或者“\”字符开头
	 *				            不能含有“\r”或者“\n”的换行符
	 */
	public void createFolder(OSSClient client,String folderName){
		ObjectMetadata objectMeta = new ObjectMetadata();
		/*这里的size为0,注意OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object,dataStream仍然可以有数据*/
		byte[] buffer = new byte[0];
		ByteArrayInputStream in = new ByteArrayInputStream(buffer);  
		objectMeta.setContentLength(0);
		try {
		    client.putObject(this.bucket, folderName, in, objectMeta);
		} finally {
		    try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 上传单个文件
	 * @param client
	 * @param key
	 * @param file
	 * @return
	 */
	public String uploadSingleFile(OSSClient client, String key, File file){
	    InputStream content = null;
		try {
			content = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    // 创建上传Object的Metadata
	    ObjectMetadata meta = new ObjectMetadata();
	    // 必须设置ContentLength
	    meta.setContentLength(file.length());
	    // 上传Object.   music/2015081DEF683D3315A4E14FF3E34F5A.jpg
	    PutObjectResult result = client.putObject(this.bucket, key, content, meta);
	    // 打印ETag
	    System.out.println("--------------------etag:"+result.getETag());
		if(result!=null && result.getETag() != "") return result.getETag();
	    return "non";
	}
	
	/**
	 * 删除单个文件
	 * @param client
	 * @param key
	 */
	public void deleteFile(OSSClient client, String key) {
	    // 删除Object
	    client.deleteObject(this.bucket, key);
	}
	
	/**
	 * 批量删除Object
	 * @param client
	 * @param keys
	 */
	public void deleteFiles(OSSClient client,List<String> keys){
		for(String id : keys) client.deleteObject(this.bucket, id);
	}
	
	/**
	 * 判断当前key在bucket下是否存在
	 * @param client
	 * @param key
	 * @return
	 */
	public boolean checkObjectMeta(OSSClient client, String key){
		System.out.println("-----------------in checkObjectMeta:"+this.bucket);
		OSSObject object = client.getObject(this.bucket, key);
		System.out.println("----------------------if:"+object);
		if(null != object) return true;
		return false;
	}
	/**
	 * 获得某个文件夹下的所有文件Object
	 * @param client
	 * 				OSS客户端连接
	 * @param folder
	 * 				文件夹名
	 * @return
	 */
	public ObjectListing getObjectList(OSSClient client,String folder){
		// 构造ListObjectsRequest请求
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(this.bucket);
		// "/" 为文件夹的分隔符
		listObjectsRequest.setDelimiter("/");
		// 递归列出fun目录下的所有文件
		listObjectsRequest.setPrefix(folder);
		ObjectListing listing = client.listObjects(listObjectsRequest);
		return listing;
	}
}
