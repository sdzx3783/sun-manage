package com.sun.manage.common.util;

import java.util.Locale;

import com.sun.manage.common.api.ICurrentContext;
import com.sun.manage.service.util.AppUtil;

public class ContextUtil {
	private static ICurrentContext context_;

	public static synchronized ICurrentContext getContext() {
		if (context_ == null) {
			context_ = (ICurrentContext) AppUtil.getBean(ICurrentContext.class);
		}
		return context_;
	}

	public static void setLocale(Locale locale) {
		ICurrentContext context = getContext();
		context.setLocale(locale);
	}

	public static Locale getLocale() {
		ICurrentContext context = getContext();
		return context.getLocale();
	}

	public static void clearAll() {
		ICurrentContext context = getContext();
		context.clearAll();
	}
}
