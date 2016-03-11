package cn.broadin.oms.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.broadin.oms.model.DecorateBean;
import cn.broadin.oms.model.DecorateGroupBean;
import cn.broadin.oms.model.MediaBean;
import cn.broadin.oms.model.TemplateBean;
import cn.broadin.oms.model.TemplateGroupBean;
import cn.broadin.oms.service.DecorateService;
import cn.broadin.oms.service.MediaService;
import cn.broadin.oms.service.TemplateService;
import cn.broadin.oms.service.impl.CommonServiceImpl;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.OSSConst;
import cn.broadin.oms.util.PaginationBean;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

/**
 * 阿里云存储OSS平台的图片清理工作
 * 清理由测试或者后台操作上传的无引用的图片
 * 清理空间包括：
 * [template/][templateGroup/]
 * [decorate/][decorateGroup/]
 * [music/][resource/]...
 * 文件夹下的无引用图片（可继续扩展）
 * @author xiejun
 */

@Component("aliyunCleanupTask")
public class AliyunCleanupTask {
	private final Logger log = Logger.getLogger(getClass());
	@Resource
	private CommonServiceImpl commonService;
	@Resource
	private TemplateService templateService;
	@Resource
	private DecorateService decorateService;
	@Resource 
	private MediaService mediaService;
	
	private ExecutorService exe = Executors.newFixedThreadPool(10);
	private final Map<String,List<String>> keyMap = new HashMap<String,List<String>>();
	private OSSClient client = null;
	
	public void execute() {
		log.info("开始扫描阿里云存储bucket目录");
		long st = System.currentTimeMillis();
		List<String> folders = commonService.getFolders();
		client = commonService.createOSSClient();
		//将所有的云存储的图片按文件夹分类扫描,方便批量操作
		final Map<String,ObjectListing> map = new HashMap<String,ObjectListing>();
		OSSClient client = commonService.createOSSClient();
		for(String folder : folders){
			ObjectListing listing = commonService.getObjectList(client, folder);
			map.put(folder, listing);
			initKeyMap(folder);
		}
		// 创建任务,每个分类对应一个任务，这些任务交由线程池处理
		List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
		for (final String key : map.keySet()) {
			tasks.add(new Callable<Void>() {
				public Void call() throws Exception {
					toScan(key, map.get(key));
					return null;
				}
			});
		}
		// 等待完成
		try {
			List<Future<Void>> futures = exe.invokeAll(tasks);
			for (Future<Void> f : futures) {
				f.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("本轮阿里云存储垃圾清理完毕，耗时" + (System.currentTimeMillis() - st) + " ms");
		
	}
	
	private void toScan(String key, ObjectListing listing) {
		// 遍历所有Object
		log.info("key:"+key);
		List<String> keys = keyMap.get(key);
		for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
			String objectKey = objectSummary.getKey();
			int checkStatus=0;
			for(String id : keys) {
				if(objectKey.equals(id)){
					checkStatus = 1;
					break;
				}
			}
			if(checkStatus==0) commonService.deleteFile(client, objectKey);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void initKeyMap(String folder){
		if(folder.equals(OSSConst.TEMPLATE_KEY_HEAD)){
			PaginationBean page = templateService.findTPage(null, null, false, null, null);
			List<TemplateBean> temps = (List<TemplateBean>) page.getRecords();
			keyMap.put(folder, new ArrayList<String>());
			for(TemplateBean bean : temps){
				keyMap.get(folder).add(bean.getId());
			}
		}else if(folder.equals(OSSConst.TEMPLATE_GROUP_KEY_HEAD)){
			PaginationBean page = templateService.findTGPage(null, null);
			List<TemplateGroupBean> tempGroups = (List<TemplateGroupBean>) page.getRecords();
			keyMap.put(folder, new ArrayList<String>());
			for(TemplateGroupBean bean : tempGroups){
				keyMap.get(folder).add(bean.getId());
			}
		}else if(folder.equals(OSSConst.DECORATE_KEY_GROUP_HEAD)){
			PaginationBean page = decorateService.findDGPage(null, null);
			List<DecorateGroupBean> dGroups = (List<DecorateGroupBean>) page.getRecords();
			keyMap.put(folder, new ArrayList<String>());
			for(DecorateGroupBean bean : dGroups){
				keyMap.get(folder).add(bean.getId());
			}
		}else if(folder.equals(OSSConst.DECORATE_KEY_HEAD)){
			PaginationBean page = decorateService.findDPage(null,null,false,null,null);
			List<DecorateBean> ds = (List<DecorateBean>) page.getRecords();
			keyMap.put(folder, new ArrayList<String>());
			for(DecorateBean bean : ds){
				keyMap.get(folder).add(bean.getId());
			}
		}else {
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			if(folder.equals(OSSConst.MEDIA_MUSIC_KEY_HEAD)){
				conditions.add(new ConditionBean("type",2,ConditionBean.EQ));
			}else if(folder.equals(OSSConst.MEDIA_COMM_TEXT_KEY_HEAD)){
				conditions.add(new ConditionBean("type",4,ConditionBean.EQ));
			}else if(folder.equals(OSSConst.MEDIA_PHOTO_KEY_HEAD)){
				conditions.add(new ConditionBean("type",0,ConditionBean.EQ));
			}else if(folder.equals(OSSConst.MEDIA_VIDEO_KEY_HEAD)){
				conditions.add(new ConditionBean("type",1,ConditionBean.EQ));
			}else if(folder.equals(OSSConst.MEDIA_XML_TEXT_KEY_HEAD)){
				conditions.add(new ConditionBean("type",3,ConditionBean.EQ));
			}
			PaginationBean page = mediaService.findPage(conditions, null);
			List<MediaBean> mbs = (List<MediaBean>) page.getRecords();
			keyMap.put(folder, new ArrayList<String>());
			for(MediaBean bean : mbs){
				keyMap.get(folder).add(bean.getId());
			}
		}
	}
	
	@PreDestroy
	public void release() {
		exe.shutdownNow(); // 释放资源
	}
}
