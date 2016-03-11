package cn.broadin.oms.util;

import java.util.HashMap;
import java.util.Map;

/**
 * jquery jstree数据结构类
 * 
 * @author zhoudeming
 */
public class JSTreeNodeBean {
	
	private String id;
	private String icon;
	private String text;
	private String type;
	private Map<String, Object> data = new HashMap<String, Object>();
	private Map<String, Object> state = new HashMap<String, Object>();
	private Map<String, Object> li_attr = new HashMap<String, Object>();
	private Map<String, Object> a_attr = new HashMap<String, Object>();
	private Object children = new Object();
	
	public JSTreeNodeBean() {};
	
	public JSTreeNodeBean(String arg0, String arg1, String arg2, String arg3) {
		super();
		this.id = arg0;
		this.icon = arg1;
		this.text = arg2;
		this.type = arg3;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Map<String, Object> getData() {
		return data;
	}
	
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public Map<String, Object> getState() {
		return state;
	}
	
	public void setState(Map<String, Object> state) {
		this.state = state;
	}
	
	public Map<String, Object> getLi_attr() {
		return li_attr;
	}
	
	public void setLi_attr(Map<String, Object> li_attr) {
		this.li_attr = li_attr;
	}
	
	public Map<String, Object> getA_attr() {
		return a_attr;
	}
	
	public void setA_attr(Map<String, Object> a_attr) {
		this.a_attr = a_attr;
	}
	
	public Object getChildren() {
		return children;
	}
	
	public void setChildren(Object children) {
		this.children = children;
	}
	
}
