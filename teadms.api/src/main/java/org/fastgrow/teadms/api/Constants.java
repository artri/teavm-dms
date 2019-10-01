package org.fastgrow.teadms.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class Constants {
	
    public static DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    }

	private Constants() {}
}
