package com.tys.netty.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

public class MsgSource implements MessageSourceAware {
    @Autowired
	private MessageSource messageSource;
	
	private Locale locale = Locale.CHINA; // 暂时写死，要实现即时切换时，可存于session中
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public String getMessage(String code) {
		String msg = messageSource.getMessage(code, null, locale);
		return msg;
	}
	
	public String getMessage(String code, String[] params) {
		String msg;
		if (params == null || params.length == 0) {
			msg = getMessage(code);
		} else {
			msg = messageSource.getMessage(code, params, locale);
		}
		return msg;
	}
	
	public String getMessage(String code, String param) {
		String msg;
		if (param == null) {
			msg = getMessage(code);
		} else {
			msg = messageSource.getMessage(code, new String[] {param}, locale);
		}
		return msg;
	}
}
