package cn.broadin.oms.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;


public class TokenInterceptor extends MethodFilterInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("static-access")
	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {
		String sToken = (String) arg0.getInvocationContext().getSession().get("token");
		Map<String, Object> map = arg0.getInvocationContext().getParameters();
		Object o = map.get("token");
		String token = null;
		if(null!=o) token = ((String[])map.get("token"))[0];
		if(null!=token && null!=sToken && token.equals(sToken)){
			return arg0.invoke();
		}
		arg0.getInvocationContext().getContext().put("tipMsg", "缺少参数token，或不匹配");
		return Action.LOGIN;
	}

}
