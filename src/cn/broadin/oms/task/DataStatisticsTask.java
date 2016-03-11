package cn.broadin.oms.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import cn.broadin.oms.model.ChannelBean;
import cn.broadin.oms.model.ChannelInstallBean;
import cn.broadin.oms.model.ChannelUserBean;
import cn.broadin.oms.service.ChannelService;
import cn.broadin.oms.service.DataStatisticService;
import cn.broadin.oms.util.Tools;

/**
 * 每个月的第一天开启统计线程，此统计并不要求时间效率，采用单线程分步骤处理模式
 * 
 * @author xiejun
 */
@Component("dataStatisticsTask")
public class DataStatisticsTask {
	private final Log log = LogFactory.getLog(DataStatisticsTask.class);
	@Resource
	private ChannelService channelService;
	@Resource
	private DataStatisticService dataStatisticService;
	
	@SuppressWarnings("deprecation")
	public synchronized void execute() {
		long st = System.currentTimeMillis();
		log.info("----开启渠道注册用户统计程序，开始时间："+new Date().toLocaleString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		String lastMonthDate = sdf.format(Tools.getLastMonth(date));
		int year = Integer.parseInt(lastMonthDate.split("-")[0]);
		int month = Integer.parseInt(lastMonthDate.split("-")[1]);
		List<ChannelBean> cbs = channelService.findAll();
		for(ChannelBean bean : cbs){
			ChannelUserBean cub = dataStatisticService.findChannelNewUser(bean.getId(), month, year);
			ChannelInstallBean cib = dataStatisticService.findChannelInstall(bean.getId(), month, year);
			bean.getInstalls().add(cib);
			bean.getUsers().add(cub);
			//cub.setChannelUser(bean);
			//cib.setChannelInstall(bean);
			//channelService.update(cub);
			//channelService.update(cib);
			channelService.update(bean);
		}
		
		log.info("----统计渠道注册用户程序结束，耗时: " + (System.currentTimeMillis() - st) + " ms");
	}
}
