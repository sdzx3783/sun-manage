package com.sun.manage.web.il8n;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import com.sun.manage.common.util.ContextUtil;
@Component
public class MessageUtil {

	private MessageSourceAccessor messages;

	@Autowired
	public void setMessages(MessageSource messageSource) {
		messages = new MessageSourceAccessor(messageSource);
	}

	public String getText(String msgKey, Locale locale) {
		return messages.getMessage(msgKey, locale);
	}

	public String getText(String msgKey, String arg, Locale locale) {
		return getText(msgKey, new Object[] { arg }, locale);
	}

	public String getText(String msgKey, Object[] args, Locale locale) {
		return messages.getMessage(msgKey, args, locale);
	}

	public String getText(String msgKey, Object... args) {
		return messages.getMessage(msgKey, args, ContextUtil.getLocale());
	}

	public String getText(String msgKey) {
		return messages.getMessage(msgKey, ContextUtil.getLocale());
	}

	protected String getText(String msgKey, String arg, HttpServletRequest request) {
		Locale locale = ContextUtil.getLocale();
		return getText(msgKey, arg, locale);
	}

	protected String getText(String msgKey, Object[] args, HttpServletRequest request) {
		Locale locale = ContextUtil.getLocale();
		return getText(msgKey, args, locale);
	}
}
