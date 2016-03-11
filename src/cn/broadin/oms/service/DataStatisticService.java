package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.ChannelInstallBean;
import cn.broadin.oms.model.ChannelUserBean;

/**
 * 数据统计类接口
 * @author xiejun
 */
public interface DataStatisticService {
	/**
	 * 查找渠道商新注册用户
	 * @param arg0
	 * 		渠道商ID
	 * @param arg1
	 * 		查询月份
	 * @param arg2
	 * 		查询年份
	 * @return
	 */
	public ChannelUserBean findChannelNewUser(String arg0,int arg1,int arg2);
	
	/**
	 * 查找渠道商新安装数量
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public ChannelInstallBean findChannelInstall(String arg0,int arg1,int arg2);

	/**
	 * 统计各个地区人数（省份）
	 * @param sql
	 * @return
	 */
	public Object findUserDistribution(String sql);

	/**
	 * 执行sql
	 * @param sql
	 * @return
	 */
	public List<Object[]> executeSql(String sql);

	
}
