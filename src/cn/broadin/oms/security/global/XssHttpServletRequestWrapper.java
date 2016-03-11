package cn.broadin.oms.security.global;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import net.sunniwell.tools.string.TextEscapeUtils;

/**
 * 包装http请求
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}
	
	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) { return null; }
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXSS(values[i]);
		}
		return encodedValues;
	}
	
	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) { return null; }
		return cleanXSS(value);
	}
	
	/**
	 * Struts2是通过这个方法来获取参数的。 StrutsPrepareAndExecuteFilter#dofilter() ↓
	 * ExecuteOperations#executeAction() ↓
	 * Dispatcher#serviceAction()->createContextMap()
	 */
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = super.getParameterMap();
		for (String key : map.keySet()) {
			String[] values = map.get(key);
			for (int i = 0; i < values.length; i++) {
				// struts2内部会多次调用此方法，需先将编码后的字符实体进行解码，否则会造成多次编码
				String value = TextEscapeUtils.unEscapeEntities(values[i]);
				values[i] = cleanXSS(value);
			}
		}
		return map;
	}
	
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null) return null;
		return cleanXSS(value);
	}
	
	private String cleanXSS(String value) {
		value = TextEscapeUtils.escapeEntities(value);
		
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		return value;
	}
	
}
