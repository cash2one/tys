package com.tys.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 	使用此工具，需要在Spring的配置文件中配置此bean
 * <bean class="XXX.SpringContextUtil"/>
 * @author Administrator
 */
public class SpringContextUtil implements ApplicationContextAware  {

	private static ApplicationContext ctx;
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		SpringContextUtil.ctx = ctx;
	}

	
	/**
	 * 在普通类中获取Spring管理的bean
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName){
    	return ctx.getBean(beanName);
    }

}