package cn.broadin.oms.security.struts;

import java.util.Enumeration;

import net.sunniwell.tools.string.TextEscapeUtils;

import org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequest;

/**
 * 基于Struts2文件域的防注入实现
 */
public class JakartaMultiPartRequestExt extends JakartaMultiPartRequest {
	
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		return TextEscapeUtils.escapeEntities(value);
	}
	
	@Override
	public Enumeration<String> getParameterNames() {
		return super.getParameterNames();
	}
	
	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		for (int i = 0; values != null && i < values.length; i++) {
			values[i] = TextEscapeUtils.escapeEntities(values[i]);
		}
		return values;
	}
}
