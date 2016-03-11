package cn.broadin.oms.dao;

import java.util.List;

/**
 * 渠道数据统计DAO层接口
 * @author xiejun
 */
public interface ChannelDataDAO {
	/**
	 * 统计渠道商新注册用户
	 * @param arg0
	 * 			渠道商ID
	 * @param arg1
	 * 			统计月份
	 * @param arg2
	 * 			统计年份
	 * @return
	 */
	public List<Object[]> selectUserCountOfChannel(String arg0,int arg1,int arg2);
	
	/**
	 * 统计渠道商旗下 新安装app数
	 * @param arg0
	 * 			渠道商ID
	 * @param arg1
	 * 			统计月份
	 * @param arg2
	 * 			统计年份
	 * @return
	 */
	public List<Object[]> selectInstallCountOfChannel(String arg0,int arg1,int arg2);
}
