package com.henninghall.date_picker;


import android.icu.util.Calendar;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import com.facebook.react.bridge.ReactApplicationContext;
import com.henninghall.date_picker.models.WheelType;

import net.time4j.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

    public static boolean deviceUsesAmPm(){
        return !DateFormat.is24HourFormat(DatePickerPackage.context);
    }

    public static String printToday(Locale locale) {
        return PrettyTime.of(locale).printToday();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static android.icu.util.Calendar isoToCalendar(String dateString, android.icu.util.TimeZone timeZone)  {
        if(dateString == null) return null;
        try {
            android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance(timeZone);
            calendar.setTime(getIsoUTCFormat().parse(dateString));
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String dateToIso(android.icu.util.Calendar date) {
        return getIsoUTCFormat().format(date.getTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean isToday(android.icu.util.Calendar cal){
        return DateUtils.isToday(cal.getTimeInMillis());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Calendar getTruncatedCalendarOrNull(android.icu.util.Calendar cal) {
        try {
            Date date = org.apache.commons.lang3.time.DateUtils.truncate(cal, Calendar.MINUTE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (Exception e){
            return null;
        }
    }

    private static SimpleDateFormat getIsoUTCFormat(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        return format;
    }

    public static ArrayList<String> splitOnSpace(String value){
        String[] array = value.split("\\s+");
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, array);
        return arrayList;
    }

    public static String capitalize(String s){
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static WheelType patternCharToWheelType(char patternChar) throws Exception {
        switch (patternChar){
            case 'y': return WheelType.YEAR;
            case 'M': return WheelType.MONTH;
            case 'd': return WheelType.DATE;
            case 'h':
            case 'H':
                return WheelType.HOUR;
            case 'm': return WheelType.MINUTE;
            case 'a': return WheelType.AM_PM;
            default: throw new Exception("Invalid pattern char: " + patternChar);
        }
    }


    public static int getShortestScrollOption(int from, int to, final int maxValue, boolean isWrapping) {
        int size = maxValue + 1;
        int option1 = to - from;
        int option2 = option1 > 0 ? option1 - size : option1 + size;
        if (isWrapping) {
            return Math.abs(option1) < Math.abs(option2) ? option1 : option2;
        }
        if (from + option1 > maxValue) return option2;
        if (from + option1 < 0) return option2;
        return option1;
    }

    public static String getLocalisedStringFromResources(Locale locale, String tagName) {
        ReactApplicationContext context = DatePickerPackage.context;
        int selectedKey = context.getResources().getIdentifier(tagName,"string", context.getPackageName());
        String localisedText = LocaleUtils.getLocaleStringResource(locale, selectedKey, context);
        return localisedText;
    }

    public static int toDp(int pixels){
        return (int) (pixels * DatePickerPackage.context.getResources().getDisplayMetrics().density);
    }
}
