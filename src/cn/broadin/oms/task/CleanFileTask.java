package cn.broadin.oms.task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;



/**
 * 定时清除FileResource文件夹下的文件
 * @author huchanghuan
 *
 */
@Component("cleanFileTask")
public class CleanFileTask {

	private final Logger log = Logger.getLogger(getClass());
	private final String filePath = "FileResource";
	
	/**
	 * 执行定时任务的方法
	 */
	protected void execute(){
		log.info("开始扫描FileResource文件夹");
		long startUtc = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info("定时任务开始时间"+sdf.format(new Date(startUtc)));
		//不能通过ServletActionContext获得路径
		String contextPath = this.getClass().getResource("/").getPath().split("WEB-INF")[0];
		//获得文件路径得到的文件对象，并执行cleanFile方法
		File file = new File(contextPath+filePath); 
		if(file.isDirectory()) this.cleanFile(file);
		else log.info("不是文件夹");
		
		long endUtc = System.currentTimeMillis();
		log.info("任务耗时"+(endUtc-startUtc)+"ms");
	}

	/**
	 * 清除文件夹下的文件
	 * @param file
	 */
	private void cleanFile(File file) {
		File[] files = file.listFiles();
		if(files.length>0){
			for (File f : files) {
				//是文件或文件夹（文件夹递归）
				if(f.isFile()){
					f.delete();
//					if(f.delete()) log.info("文件"+f.getPath()+"已删除");
//					else log.info("文件"+f.getPath()+"删除失败！！！");
				}else{
					this.cleanFile(f);
					f.delete();
//					if(f.delete()) log.info("文件夹"+f.getPath()+"已删除");
//					else log.info("文件夹"+f.getPath()+"删除失败！！！");
				}
			}
		}
	}
}
