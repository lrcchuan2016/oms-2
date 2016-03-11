package cn.broadin.oms.dataSource;

/**
 * 获得或设置数据源KEY
 * @author huchanghuan
 *
 */
public class DBHandler {
	
	//当前线程环境
	private static ThreadLocal<DBType> holder = new ThreadLocal<DBType>();

	public static void setDBType(DBType dbType){
		holder.set(dbType);
	}
	
	public static DBType getDBType(){
		return holder.get();
	}

	public static void clearDBType(){
		holder.remove();
	}

	
}
