package com.henninghall.date_picker.wheels;

import android.graphics.Paint;
import android.icu.util.Calendar;
import android.icu.util.IslamicCalendar;
import android.os.Build;
import androidx.annotation.RequiresApi;

import com.henninghall.date_picker.LocaleUtils;
import com.henninghall.date_picker.pickers.Picker;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.models.Mode;

import java.util.ArrayList;
import java.util.Objects;

public class YearWheel extends Wheel
{
    private int defaultStartYear;
    private int defaultEndYear;

    public YearWheel(final Picker picker, final State id) {
        super(picker, id);
        this.defaultStartYear = 1900;
        this.defaultEndYear = 2100;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
       Calendar cal = Calendar.getInstance();

        final int startYear = getStartYear();
        final int endYear = getEndYear();
        int max = endYear - startYear;

        cal.set(IslamicCalendar.YEAR, startYear);

        for (int i = 0; i <= max; ++i) {
            values.add(getLocaleString(cal));

            if(Objects.equals(getLocaleString(cal), "1348")) {
                values.add("1349");
            }
            if(Objects.equals(getLocaleString(cal), "1449")) {
                values.add("1450");
            }
            cal.add(Calendar.YEAR, 1);
        }

        return values;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int getEndYear() {
        if (state.getMaximumDate() == null) {
            return this.defaultEndYear;
        }
        return state.getMaximumDate().get(IslamicCalendar.YEAR);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int getStartYear() {
        if (state.getMinimumDate() == null) {
            return this.defaultStartYear;
        }
        return state.getMinimumDate().get(IslamicCalendar.YEAR);
    }

    @Override
    public boolean visible() {
        return state.getMode() == Mode.date;
    }

    @Override
    public boolean wrapSelectorWheel() {
        return false;
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }

    @Override
    public String getFormatPattern() {
        return LocaleUtils.getYear(state.getLocaleLanguageTag());
    }

}

