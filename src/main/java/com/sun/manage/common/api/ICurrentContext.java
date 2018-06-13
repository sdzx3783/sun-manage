package com.sun.manage.common.api;

import java.util.Locale;


public interface ICurrentContext {

	Locale getLocale();

	void setLocale(Locale locale);
	
	void clearAll();
}
