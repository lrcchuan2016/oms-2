package cn.broadin.oms.aop;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import cn.broadin.oms.annotation.DataSource;
import cn.broadin.oms.dataSource.DBHandler;
import cn.broadin.oms.dataSource.DBType;

/**
 * 切换数据源aop
 * (连接点为service层的方法)
 * @author huchanghuan
 *
 */
@Component
@Aspect
public class DataSourceAop {

	@Pointcut("execution(* cn.broadin.oms.service.impl.*Impl.*(..))")
	public void point(){
		
	}
	
	@Before(value="point()")
	public void before(JoinPoint jp){
		
		//获取执行的目标方法的Class
		Class<?> c = jp.getTarget().getClass();
		//判断
		if(c.isAnnotationPresent(DataSource.class)){
			//获得指定的注解对象
			DataSource dataSource = c.getAnnotation(DataSource.class);
			//得到注解的值
			DBType dbType = dataSource.value();
			//数据源切换为注解的值对应的数据源
			DBHandler.setDBType(dbType);
		}else 
			DBHandler.setDBType(DBType.defaultDataSource);
		
	}
}
