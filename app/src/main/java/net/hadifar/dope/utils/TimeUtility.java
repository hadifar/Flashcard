package net.hadifar.dope.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Amir on 10/14/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

public class TimeUtility {

    final static Calendar calendar = Calendar.getInstance();

    public static String getHMformat(long millis) {
        return (new SimpleDateFormat("HH:mm").format(new Date(millis)));
    }
    public static String getHMformat(long start, long end) {
        return getHMformat(end) + " - " + getHMformat(start);
    }

    public static String getFullFormat(long startTimestamp) {
        return DateFormat.getDateInstance().format(new Date(startTimestamp));
    }

    public static String getFullDateFormat(long startTimestamp) {
        TimeZone tz = calendar.getTimeZone();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
        sdf.setTimeZone(tz);
        return sdf.format(new Date(startTimestamp));
    }


}
