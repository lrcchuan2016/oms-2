package cn.broadin.oms.security.global;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 过滤get、post之外的http方法
 */
public class HttpMethodFilter extends OncePerRequestFilter {
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Override
	protected void initFilterBean() throws ServletException {}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
			IOException {
		// 只支持get/post的http method
		String method = request.getMethod();
		if (!method.equalsIgnoreCase("get") && (!method.equalsIgnoreCase("post"))) {
			log.warn("非常规http请求:{" + method + "}，响应403.");
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		filterChain.doFilter(request, response);
	}
	
}
