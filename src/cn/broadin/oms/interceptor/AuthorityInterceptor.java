package cn.broadin.oms.interceptor;

import cn.broadin.oms.model.ManagerBean;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorityInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		ManagerBean admin = (ManagerBean) ctx.getSession().get("admin");
		if (admin != null) { return invocation.invoke(); }
		ctx.put("tipMsg", "您还没有登录系统，请先登录！");
		return Action.LOGIN;
		
	}
	
	
}
