package cn.broadin.oms.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据分页帮助类
 * 
 * @author zhoudeming
 */
public class PaginationBean implements Serializable {
	
	private static final long serialVersionUID = -6045811471583108665L;
	private int startIndex;
	private int pageSize;
	private int totalCount;
	private List<?> records = new ArrayList<Object>();
	
	public PaginationBean() {}
	
	public PaginationBean(int startIndex, int pageSize, int totalCount, List<?> records) {
		super();
		this.startIndex = startIndex;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.records = records;
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public List<?> getRecords() {
		return records;
	}
	
	public void setRecords(List<?> records) {
		this.records = records;
	}
	
}
