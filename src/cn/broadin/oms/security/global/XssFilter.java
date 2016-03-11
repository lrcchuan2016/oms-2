package cn.broadin.oms.security.global;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 跨站脚本（Cross site script，简称xss）是一种HTML注入， 由于攻击的脚本多数时候是跨域的，所以称之为"跨域脚本"。
 */
public class XssFilter implements Filter {
	
	FilterConfig filterConfig = null;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	
	public void destroy() {
		this.filterConfig = null;
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) req), response);
	}
}
