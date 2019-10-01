package org.fastgrow.teadms.server.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public final class DateUtils {

	public static Date parse(DateFormat format, String date) throws ParseException {
		Objects.requireNonNull(format, "Format can not be null");
		if (StringUtils.isNotBlank(date)) {
			return format.parse(date);
		}
		return null;
	}
	
	private DateUtils() {}
}
