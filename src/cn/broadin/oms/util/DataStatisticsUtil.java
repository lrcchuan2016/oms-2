package cn.broadin.oms.util;

import java.util.Calendar;

/**
 * 数据统计工具类
 * @author huchanghuan
 *
 */
public class DataStatisticsUtil {

	/**
	 *   获得指定时间的时间范围
	 * @param sDate  如：2015-3
	 * @return
	 */
	public static long[] QueryStartAndEndUtc(String sDate){
		long[] d = new long[2];
		if(null != sDate && !sDate.trim().isEmpty()){
			
			Calendar cal = Calendar.getInstance();
			String[] date = sDate.split("-");
			int year = 0,month = 0,day = 1;
			int len = date.length;
			for(int i=0;i<len;i++){
				if(i == 2) day = Integer.valueOf(date[i]);
				if(i == 1) month = Integer.valueOf(date[i])-1;
				if(i == 0) year = Integer.valueOf(date[i]);
			}
				
			cal.set(year,month,day,0,0,0);
			long startUtc = cal.getTimeInMillis();
			if(len == 1)
				cal.set(year+1, month, day, 0, 0, 0);
			else if(len == 2)
				cal.set(year, month+1, day, 0, 0, 0);
			else 
				cal.set(year, month, day+1, 0, 0, 0);
			
			long endUtc = cal.getTimeInMillis();
			d[0] = startUtc;
			d[1] = endUtc;
		}
		
		return d;
	}

	public static int[] getCategories(String sDate) {
		
		String[] date = sDate.split("-");
		int[] categories = null;
		
		switch (date.length) {
		case 1:
			categories = createArray(12,1);
			break;
		case 2:
			Calendar calendar = Calendar.getInstance();
			calendar.set(Integer.valueOf(date[0]), Integer.valueOf(date[1])-1, 1, 0, 0, 0);
			int dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			categories = createArray(dayOfMonth,1);
			break;
		case 3:
			categories = createArray(24,0);
			break;
		default:
			break;
		}
		
		return categories;
	}
	
	/**
	 * 产生指定大小的数组
	 * @param length
	 * @return
	 */
	private static int[] createArray(int length,int k){
		int[] arrs = new int[length];
		for (int i = 0; i < arrs.length; i++) {
			arrs[i] = i+k;
		}
		return arrs;
	}
	
	/**获取指定时间的utc时间
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static long getUtc(int year,int month,int day,int hour,int minute,int second){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute, second);
		return calendar.getTimeInMillis();
	}
}
