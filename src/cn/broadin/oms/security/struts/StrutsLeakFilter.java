package cn.broadin.oms.security.struts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 通过该过滤器屏蔽疑似struts安全漏洞的攻击.
 * 详见：{@link http://struts.apache.org/release/2.3.x/docs/security-bulletins.html}
 * 
 * 也可以通过拦截器的方式：
 * {@code
 * <interceptor-ref name="params">
 *  <param name="excludeParams">dojo\..*,^struts\..*,.*\\u0023.*,.*\\x5Cu0023.*,.*\\x5cu0023.*</param>
 * </interceptor-ref>
 * }
 * 或重写org.apache.struts2.dispatcher.mapper.ActionMapper
 * </pre>
 */
public class StrutsLeakFilter implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(StrutsLeakFilter.class);
	private List<String> keywords = new ArrayList<String>();
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@SuppressWarnings("unused")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (!isBugInvade(req)) {
			chain.doFilter(request, response);
		} else {
			log.warn("检测到疑似攻击的请求.参数中包含关键字.该请求被丢弃!");
		}
	}
	
	private boolean isBugInvade(HttpServletRequest request) {
		// get/post参数
		Enumeration<String> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parName = e.nextElement();
			for (String keyword : keywords) {
				if (parName.indexOf(keyword) != -1) { return true; }
			}
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		String keys = config.getInitParameter("keywords");
		if (StringUtils.isBlank(keys)) return;
		for (String key : keys.split(",")) {
			if (StringUtils.isNotBlank(key)) {
				keywords.add(key.trim());
			}
		}
	}
	
}
