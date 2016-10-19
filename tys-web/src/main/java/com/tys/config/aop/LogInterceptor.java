package com.tys.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.tys.util.MUtil;

@Aspect
@Component
public class LogInterceptor {


	@Around("execution(public com.tys.base.BaseSpiRsp+ com.tys.spi.app.controller.*.*(..))")
	public Object printfLog(ProceedingJoinPoint pj) throws Throwable {

		long time = System.currentTimeMillis();
		Object obj = pj.proceed();
		if (obj != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("------- use time=").append(System.currentTimeMillis() - time);
			sb.append(" ").append(pj.toShortString());
			sb.append(" ").append(JSONObject.toJSONString(obj));
			MUtil.log(sb.toString());
		}
		return obj;
	}

}