package com.sun.manage.service.util;

import java.util.Locale;

import org.springframework.stereotype.Component;

import com.sun.manage.common.api.ICurrentContext;
@Component
public class CurrentContext implements ICurrentContext {
	private static ThreadLocal<Locale> curLocale = new ThreadLocal<Locale>();
	@Override
	public Locale getLocale() {
		if(curLocale.get()!=null){
			return curLocale.get();
		}
		setLocale(new Locale("zh","CN"));
		return curLocale.get();
	}

	@Override
	public void setLocale(Locale locale) {
		curLocale.set(locale);
	}
	@Override
	public void clearAll() {
		curLocale.remove();
	}
}
