package cn.broadin.oms.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.broadin.oms.dataSource.DBType;


/**
 * 注解类
 * @author huchanghuan
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface DataSource {

	public DBType value() default DBType.defaultDataSource;
}
