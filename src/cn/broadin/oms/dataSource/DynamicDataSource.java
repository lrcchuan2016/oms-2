package cn.broadin.oms.dataSource;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * 动态数据源切换类
 * @author huchanghuan
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		
		return DBHandler.getDBType();
	}

	

	

}
