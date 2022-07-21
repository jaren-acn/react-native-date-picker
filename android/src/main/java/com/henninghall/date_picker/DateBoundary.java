package com.henninghall.date_picker;


import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import androidx.annotation.RequiresApi;

public class DateBoundary {
    private android.icu.util.Calendar date;

    @RequiresApi(api = Build.VERSION_CODES.N)
    DateBoundary(TimeZone timezone, String date) {
        if(date == null) return;
        android.icu.util.Calendar cal = Utils.isoToCalendar(date, timezone);
        this.date = Utils.getTruncatedCalendarOrNull(cal);
    }

    protected Calendar get() {
        return this.date;
    }
}
