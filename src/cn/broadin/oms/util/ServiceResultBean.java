package cn.broadin.oms.util;

/**
 * 处理结果类
 * 
 * @author xiejun
 */
public class ServiceResultBean {
	
	private boolean success = true; // 处理结果成功或者失败
	
	private int code = 0; // 结果码
	
	private String description = ""; // 结果详情
	
	public ServiceResultBean() {}
	
	public ServiceResultBean(boolean success, int code, String description) {
		super();
		this.success = success;
		this.code = code;
		this.description = description;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
