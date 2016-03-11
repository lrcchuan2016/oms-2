package cn.broadin.oms.util;


/**
 * 查询条件帮助类
 * 
 * @author zhoudeming
 */
public class ConditionBean {
	
	public final static int EQ = 0;
	public final static int NOT_EQ = 1;
	public final static int GT = 2;
	public final static int LT = 3;
	public final static int GE = 4;
	public final static int LE = 5;
	public final static int LIKE = 6;
	public final static int IN = 7;
	public final static int NOT_IN = 8;
	public final static int IS_EMPTY = 9;
	public final static int IS_NOT_EMPTY = 10;
	public final static int IS_NULL = 11;
	public final static int IS_NOT_NULL = 12;
	public final static int AND = 13;
	public final static int OR = 14;
	
	private String key;
	private Object value;
	private int relation = EQ;
	
	
	public ConditionBean() {}
	
	public ConditionBean(String key, Object value, int relation) {
		super();
		this.key = key;
		this.value = value;
		this.relation = relation;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public int getRelation() {
		return relation;
	}
	
	public void setRelation(int relation) {
		this.relation = relation;
	}

	

}
