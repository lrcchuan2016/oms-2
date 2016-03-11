package cn.broadin.oms.aop;


import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import cn.broadin.oms.model.ManagerBean;

import com.opensymphony.xwork2.ActionContext;


@Component
@Aspect
public class LoggerAop {
	
	private static Logger log = Logger.getLogger(LoggerAop.class);
	
	@Pointcut("execution(* cn.broadin.oms.service.impl.*Impl.*(..))")
	public void point(){}
	
	@After(value="point()")
	public void after(JoinPoint jp){
		String className = jp.getTarget().getClass().getName();
		String methodName = jp.getSignature().getName();
		ManagerBean manager = ((ManagerBean)ActionContext.getContext().getSession().get("admin"));
		if(null != manager){
			log.info("==>操作员账号："+manager.getAccount()+"  执行的操作==>"+className+":【"+methodName+"】");
		}
		
		
	}
}
