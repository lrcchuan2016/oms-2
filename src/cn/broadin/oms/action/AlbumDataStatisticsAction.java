package cn.broadin.oms.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.AlbumClickRecordBean;
import cn.broadin.oms.model.AlbumSurferRecordBean;
import cn.broadin.oms.model.PhotoSurfedRecordBean;
import cn.broadin.oms.model.TerminalTypeBean;
import cn.broadin.oms.service.AlbumDataStatisticsService;
import cn.broadin.oms.service.TerminalService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.DataStatisticsUtil;
import cn.broadin.oms.util.PaginationBean;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 相册统计处理类
 * @author huchanghuan
 *
 */
@Controller("albumDataStatisticsAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AlbumDataStatisticsAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> paramMap = new HashMap<String, String>();
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private PaginationBean page;
	
	@Resource
	private AlbumDataStatisticsService albumDataStatisticsService;
	@Resource
	private TerminalService terminalService;

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public Map<String, Object> getResultJson() {
		return resultJson;
	}

	public void setResultJson(Map<String, Object> resultJson) {
		this.resultJson = resultJson;
	}
	
	public PaginationBean getPage() {
		return page;
	}

	public void setPage(PaginationBean page) {
		this.page = page;
	}

	/**
	 * 相册点击量统计
	 * @return
	 */
	public String QueryAlbumClickDataStatistics(){
		
		if(paramMap.containsKey("date")){
			//切换数据库
			//DBHandler.setDBType(DBType.collectionDataSource);
			
			String sDate = paramMap.get("date");
			long[] utcs = DataStatisticsUtil.QueryStartAndEndUtc(sDate);
			//添加查询条件
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			conditions.add(new ConditionBean("clickUtc", utcs[0], ConditionBean.GE));
			conditions.add(new ConditionBean("clickUtc", utcs[1], ConditionBean.LT));
			if(paramMap.containsKey("albumType")) conditions.add(new ConditionBean("albumType", Integer.valueOf(paramMap.get("albumType")), ConditionBean.EQ));
			if(paramMap.containsKey("terminalType")) conditions.add(new ConditionBean("terminalType", Integer.valueOf(paramMap.get("terminalType")), ConditionBean.EQ));
			
			List<AlbumClickRecordBean> albumClickRecords = albumDataStatisticsService.findAlbumClickRecords(conditions,null,true,null,null);
			
			int[] categories = DataStatisticsUtil.getCategories(sDate);
			
			Object obj = packagingData(categories,sDate.split("-").length,albumClickRecords,AlbumClickRecordBean.class,paramMap.get("terminalType"));
			
			if(null != obj){
				resultJson.put("result", "00000000");
				resultJson.put("data", obj);
				return Action.SUCCESS;
			}
			
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 相册分享统计
	 * @return
	 */
	public String QueryAlbumShareDataStatistics(){
		if(paramMap.containsKey("date")){
			//切换数据库
			//DBHandler.setDBType(DBType.collectionDataSource);
			
			long[] l = DataStatisticsUtil.QueryStartAndEndUtc(paramMap.get("date"));
			String sql = "select album_type,count(*) from album_share_record where share_utc>="+l[0]+" and share_utc<"+l[1]+" group by album_type";
			List<Object[]> albumTypeDatas = albumDataStatisticsService.executeSql(sql);
		
			sql = "select platform,count(*) from album_share_record where share_utc>="+l[0]+" and share_utc<"+l[1]+" and album_type = 0 group by platform";
			List<Object[]> commonAlbums = albumDataStatisticsService.executeSql(sql);
			sql = "select platform,count(*) from album_share_record where share_utc>="+l[0]+" and share_utc<"+l[1]+" and album_type = 1 group by platform";
			List<Object[]> themeAlbums = albumDataStatisticsService.executeSql(sql);
			sql = "select platform,count(*) from album_share_record where share_utc>="+l[0]+" and share_utc<"+l[1]+" and album_type = 2 group by platform";
			List<Object[]> timerAlbums = albumDataStatisticsService.executeSql(sql);
		
			JSONObject platformDatas = new JSONObject();
			platformDatas.element("commonAlbums", commonAlbums);
			platformDatas.element("themeAlbums", themeAlbums);
			platformDatas.element("timerAlbums", timerAlbums);
			
			JSONObject data = new JSONObject();
			data.element("albumTypeDatas", albumTypeDatas);
			data.element("platformDatas", platformDatas);
			
			resultJson.put("result", "00000000");
			resultJson.put("data", data);
			return Action.SUCCESS;
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 相册浏览时间统计
	 * @return
	 */
	public String QueryAlbumSurfedDatatistics(){
		
		if(paramMap.containsKey("date")){
			//切换数据库
			//DBHandler.setDBType(DBType.collectionDataSource);
			
			String sDate = paramMap.get("date");
			long[] l = DataStatisticsUtil.QueryStartAndEndUtc(sDate);
			//添加查询条件
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			conditions.add(new ConditionBean("surfUtc", l[0], ConditionBean.GE));
			conditions.add(new ConditionBean("surfUtc", l[1], ConditionBean.LT));
			if(paramMap.containsKey("albumType")) 
				conditions.add(new ConditionBean("albumType", Integer.valueOf(paramMap.get("albumType")), ConditionBean.EQ));
			if(paramMap.containsKey("terminalType")) 
				conditions.add(new ConditionBean("terminalType", Integer.valueOf(paramMap.get("terminalType")), ConditionBean.EQ));
			
			List<AlbumSurferRecordBean> albumSurferRecords = albumDataStatisticsService.findAlbumSurferRecords(conditions, null, true, null, null);
			int[] categories = DataStatisticsUtil.getCategories(sDate);
		
			Object obj = packagingData(categories, sDate.split("-").length, albumSurferRecords,AlbumSurferRecordBean.class,paramMap.get("terminalType"));
		
			if(null != obj){
				resultJson.put("result", "00000000");
				resultJson.put("data", obj);
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 相册制作统计
	 * @return
	 */
	public String QueryAlbumMakeDatatistics(){
		if(paramMap.containsKey("date")){
			//切换数据库
			//DBHandler.setDBType(DBType.collectionDataSource);
			
			String sDate = paramMap.get("date");
			long[] l = DataStatisticsUtil.QueryStartAndEndUtc(sDate);
			//①、加入查询条件(仅针对主题相册)    页数
			String sql = null;
			if(!paramMap.containsKey("terminalType"))
				sql = "select pages,count(*) from album_make_record where build_start_utc>="+l[0]+" and build_start_utc<"+l[1]+" and album_type = 1 group by pages";
			else sql = "select pages,count(*) from album_make_record where build_start_utc>="+l[0]+" and build_start_utc<"+l[1]+" and album_type = 1 and terminal_type = "+Integer.valueOf(paramMap.get("terminalType"))+" group by pages";
			//查出各个页数所占的比例
			List<Object[]> objs = albumDataStatisticsService.executeSql(sql);
			
			//②、    最长、最短、平均
			int[] categories = DataStatisticsUtil.getCategories(sDate);
			int length = categories.length;
			String[] date = sDate.split("-");
			
			JSONArray albumMakeTimes = new JSONArray();
			List<Object[]> albumMakeTime = null;
			
			long startUtc = 0,endUtc = 0;
			if(!paramMap.containsKey("terminalType"))
				sql = "select ?,max(build_complete_utc-build_start_utc),min(build_complete_utc-build_start_utc),avg(build_complete_utc-build_start_utc) from album_make_record where album_type =1" +
						" and build_start_utc>=? and build_start_utc<?";
			else{
				int terminalType = Integer.valueOf(paramMap.get("terminalType"));
				sql = "select ?,max(build_complete_utc-build_start_utc),min(build_complete_utc-build_start_utc) from album_make_record where album_type =1" +
					" and terminal_type="+terminalType+" and build_start_utc>=? and build_start_utc<?";
			}
			
			//获得时间区间的最大、最小、平均
			if(date.length == 1){
				int year = Integer.valueOf(date[0]);
				for (int i = 0; i < length; i++) {
					startUtc = DataStatisticsUtil.getUtc(year, i, 1, 0, 0, 0);
					endUtc = DataStatisticsUtil.getUtc(year, i+1, 1, 0, 0, 0);
					//填满?占位符后执行sql
					albumMakeTime = albumDataStatisticsService.executeSql(fillSql(sql,"?",startUtc,endUtc));
					//查出的数据放入jsonArray
					albumMakeTimes.element(albumMakeTime);
				}
			}else if(date.length == 3){
				int year = Integer.valueOf(date[0]);
				int month = Integer.valueOf(date[1]);
				int day = Integer.valueOf(date[2]);	
				for (int i = 0; i < length; i++) {
					startUtc = DataStatisticsUtil.getUtc(year, month-1, day, i, 0, 0);
					endUtc = DataStatisticsUtil.getUtc(year, month-1, day, i+1, 0, 0);
					albumMakeTime = albumDataStatisticsService.executeSql(fillSql(sql,"?",startUtc,endUtc));
					//查出的数据放入jsonArray
					albumMakeTimes.element(albumMakeTime);
				}
			}else{
				int year = Integer.valueOf(date[0]);
				int month = Integer.valueOf(date[1]);	
				for (int i = 1; i <= length; i++) {
					startUtc = DataStatisticsUtil.getUtc(year, month-1, i, 0, 0, 0);
					endUtc = DataStatisticsUtil.getUtc(year, month-1, i+1, 0, 0, 0);
					albumMakeTime = albumDataStatisticsService.executeSql(fillSql(sql,"?",startUtc,endUtc));
					//查出的数据放入jsonArray
					albumMakeTimes.element(albumMakeTime);
				}
			}
			
			resultJson.put("result", "00000000");
			resultJson.put("albumPageNums", objs);
			resultJson.put("albumMakeTimes", albumMakeTimes);
			return Action.SUCCESS;
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	

	/**
	 * 相片点击统计
	 * @return
	 */
	public String QueryPhotoClickDatatistics(){
		//切换数据库
		//DBHandler.setDBType(DBType.collectionDataSource);
		
		String hql = null,totalCountHql = null;
		int startIndex = page.getStartIndex();
		int pageSize = page.getPageSize();
		int totalCount = 0;
		if(!paramMap.containsKey("type") && !paramMap.containsKey("keyWords")){
			hql = "select id,albumName,photoUrl,albumType,clubNum,clubName,count(*) from PhotoClickRecordBean " +
					"group by albumId order by count(*) desc limit "+startIndex+","+(startIndex+pageSize);
			totalCountHql = "select count(*) from PhotoClickRecordBean group by albumId";
					
		}else if(paramMap.containsKey("type") && !paramMap.containsKey("keyWords")){
			int type = Integer.valueOf(paramMap.get("type"));
			hql = "select id,albumName,photoUrl,albumType,clubNum,clubName,count(*) from PhotoClickRecordBean " +
					"where albumType = "+type+" group by albumId order by count(*) desc limit "+startIndex+","+(startIndex+pageSize);
			totalCountHql = "select count(*) from PhotoClickRecordBean where albumType = "+type+" group by albumId";
					
		}else if(!paramMap.containsKey("type") && paramMap.containsKey("keyWords")){
			String keyWords = paramMap.get("keyWords");
			hql = "select id,albumName,photoUrl,albumType,clubNum,clubName,count(*) from PhotoClickRecordBean " +
					"where clubNum like '%"+keyWords+"%' or clubName like '%"+keyWords+"%' " +
							"group by albumId order by count(*) desc limit "+startIndex+","+(startIndex+pageSize);
			totalCountHql = "select count(*) from PhotoClickRecordBean where clubNum like '%"+keyWords+"%' or clubName like '%"+keyWords+"%' group by albumId";
					
		}else{
			int type = Integer.valueOf(paramMap.get("type"));
			String keyWords = paramMap.get("keyWords");
			hql = "select id,albumName,photoUrl,albumType,clubNum,clubName,count(*) from PhotoClickRecordBean " +
					"where albumType = "+type+" and (clubNum like '%"+keyWords+"%' or clubName like '%"+keyWords+"%') " +
							"group by albumId order by count(*) desc limit "+startIndex+","+(startIndex+pageSize);
			totalCountHql = "select count(*) from PhotoClickRecordBean where albumType = "+type+" and (clubNum like '%"+keyWords+"%' or clubName like '%"+keyWords+"%') group by albumId";
		}
		List<Object[]> objs = albumDataStatisticsService.executeHql(hql);
		List<Object[]> totalCounts = albumDataStatisticsService.executeHql(totalCountHql);
		if(totalCounts == null || totalCounts.isEmpty())
			totalCount = 0;
		else
			totalCount = totalCounts.size();
		page.setTotalCount(totalCount);
		page.setRecords(objs);
		resultJson.put("list", page);
		return Action.SUCCESS;
	}
	
	/**
	 * 相片浏览时间统计
	 * @return
	 */
	public String QueryPhotoSurfedDatatistics(){
		if(paramMap.containsKey("date")){
			//切换数据库
			//DBHandler.setDBType(DBType.collectionDataSource);
			
			String sDate = paramMap.get("date");
			long[] l = DataStatisticsUtil.QueryStartAndEndUtc(sDate);
			//添加条件
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			conditions.add(new ConditionBean("clickUtc", l[0], ConditionBean.GE));
			conditions.add(new ConditionBean("clickUtc", l[1], ConditionBean.LT));
			if(paramMap.containsKey("terminalType"))
				conditions.add(new ConditionBean("terminalType", Integer.valueOf(paramMap.get("terminalType")), ConditionBean.EQ));
		
			List<PhotoSurfedRecordBean> records = albumDataStatisticsService.findPhotoSurfedRecords(conditions,null,false,null,null);
			int[] categories = DataStatisticsUtil.getCategories(sDate);
			
			Object obj = packagingData(categories, sDate.split("-").length, records, PhotoSurfedRecordBean.class, paramMap.get("terminalType"));
			
			if(null != obj){
				resultJson.put("result", "00000000");
				resultJson.put("data", obj);
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 封装数据
	 * @param categories
	 * @param length
	 * @param albumRecords
	 * @param c
	 * @param terminalType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object packagingData(int[] categories,int length,
			List<?> albumRecords,Class<?> c,String terminalType) {
		JSONObject data = new JSONObject();
		JSONArray series = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		int[] nums = new int[categories.length];
		if(null != albumRecords && !albumRecords.isEmpty()){
			int field = 0;
			if(length == 1) 
				field = Calendar.MONTH;
			else if(length == 2) 
				field = Calendar.DAY_OF_MONTH;
			else 
				field = Calendar.HOUR_OF_DAY;
			int size = albumRecords.size();
			Calendar calendar = Calendar.getInstance();
			
			System.out.println(c == AlbumClickRecordBean.class);

			if(c == AlbumClickRecordBean.class){
				List<AlbumClickRecordBean> records = (List<AlbumClickRecordBean>)albumRecords;
				for(int i=0;i < size;i++){
					long utc = records.get(i).getClickUtc();
					calendar.setTimeInMillis(utc);
					int index = calendar.get(field);
					nums[index] += 1;
				}
			}else if(c == AlbumSurferRecordBean.class){
				List<AlbumSurferRecordBean> records = (List<AlbumSurferRecordBean>)albumRecords;
				for(int i=0;i < size;i++){
					long utc = records.get(i).getSurfUtc();
					calendar.setTimeInMillis(utc);
					int index = calendar.get(field);
					nums[index] += 1;
				}
			}else{
				//PhotoSurfedRecordBean
				List<PhotoSurfedRecordBean> records = (List<PhotoSurfedRecordBean>) albumRecords;
				for(int i=0;i < size;i++){
					long utc = records.get(i).getClickUtc();
					calendar.setTimeInMillis(utc);
					int index = calendar.get(field);
					nums[index] += 1;
				}
			}
		}
		if(null != terminalType){
			TerminalTypeBean type = terminalService.findTerminalById(Integer.valueOf(terminalType));
			jsonObject.element("name", type.getDescription());
		}else jsonObject.element("name", "All");	
		
		jsonObject.element("data", nums);
		
		series.add(jsonObject);
		
		data.element("series", series);
		data.element("categories", categories);
		
		return data;
	}
	
	private String fillSql(String sql,String str,long startUtc, long endUtc) {
		int index1 = sql.indexOf(str);
		sql = sql.substring(0, index1)+startUtc+sql.substring(index1+1);
		
		int index2 = sql.indexOf(str);
		sql = sql.substring(0, index2)+startUtc+sql.substring(index2+1);
		
		int index3 = sql.lastIndexOf(str);
		sql = sql.substring(0, index3)+endUtc+sql.substring(index3+1);
		return sql;
	}
}
