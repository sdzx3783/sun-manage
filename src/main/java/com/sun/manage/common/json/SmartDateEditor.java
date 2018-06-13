package com.sun.manage.common.json;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.sun.manage.common.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmartDateEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if ((text == null) || (text.length() == 0)) {
			setValue(null);
		} else {
			try {
				setValue(DateUtil.parseDate(text));
			} catch (Exception ex) {
				throw new IllegalArgumentException("转换日期失败: " + ex.getMessage(), ex);
			}
		}
	}

	public static Date parseDate(String dateString) {
		Date date = null;
		try {
			date = DateUtils.parseDate(dateString, new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd",
					"yyyy-MM-dd HH:mm", "HH:mm:ss", "HH:mm", "yyyy-MM-dd HH:mm:ss.SSS" });

		} catch (Exception ex) {

			log.error("Pase the Date(" + dateString + ") occur errors:" + ex.getMessage());
		}

		return date;
	}
	@Override
	public String getAsText() {
		Object obj = getValue();
		if (obj == null) {
			return "";
		}
		Date value = (Date) obj;
		String dateStr = null;
		try {
			dateStr = DateUtil.formatEnDate(value);
			if (dateStr.endsWith(" 00:00:00")) {
				dateStr = dateStr.substring(0, 10);
			} else if (!dateStr.endsWith(":00")) {
			}
			return dateStr.substring(0, 16);
		} catch (Exception ex) {
			throw new IllegalArgumentException("转换日期失败: " + ex.getMessage(), ex);
		}
	}
}
