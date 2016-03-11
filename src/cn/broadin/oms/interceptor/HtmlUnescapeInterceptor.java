package cn.broadin.oms.interceptor;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.xwork.StringEscapeUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class HtmlUnescapeInterceptor extends AbstractInterceptor{

	/**
	 * 反转义页面特殊字符
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		ActionContext act = arg0.getInvocationContext();
		Map<String, Object> map = act.getParameters();
		if(null != map && map.entrySet().size() != 0){
			for (Entry<String, Object> entry : map.entrySet()) {
				String[] s = (String[])entry.getValue();
				int len = s.length;
				if(null != s && len != 0)
				for (int i = 0;i<len;i++) {s[i] = StringEscapeUtils.unescapeHtml(s[i]);};
			}
		}
		return arg0.invoke();
		
	}

}
