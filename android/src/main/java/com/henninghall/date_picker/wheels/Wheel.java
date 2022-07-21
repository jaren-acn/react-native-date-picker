package com.henninghall.date_picker.wheels;

import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.IslamicCalendar;
import android.icu.util.ULocale;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.view.View;

import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.pickers.Picker;
import com.henninghall.date_picker.State;

import java.util.ArrayList;
import java.util.Locale;

public abstract class Wheel {

    protected final State state;
    private android.icu.util.Calendar userSetValue;

    public abstract boolean visible();
    public abstract boolean wrapSelectorWheel();
    public abstract Paint.Align getTextAlign();
    public abstract String getFormatPattern();
    public abstract ArrayList<String> getValues();

    public String toDisplayValue(String value) {
        return value;
    }

    private ArrayList<String> values = new ArrayList<>();
    public Picker picker;
    public android.icu.text.SimpleDateFormat format;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Wheel(Picker picker, State state) {
        this.state = state;
        this.picker = picker;
        this.format = new android.icu.text.SimpleDateFormat(getFormatPattern(), state.getLocale());
        picker.setTextAlign(getTextAlign());
        picker.setWrapSelectorWheel(wrapSelectorWheel());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int getIndexOfDate(android.icu.util.Calendar date){
        format.setTimeZone(state.getTimeZone());
        return values.indexOf(format.format(date.getTime()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void animateToDate(Calendar date) {
        picker.smoothScrollToValue(getIndexOfDate(date));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getValue() {
        if(!visible()) return format.format(userSetValue.getTime());
        return getValueAtIndex(getIndex());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getPastValue(int subtractIndex) {
        if(!visible()) return format.format(userSetValue.getTime());
        int size = values.size();
        int pastValueIndex = (getIndex() + size - subtractIndex) % size;
        return getValueAtIndex(pastValueIndex);
    }


    private int getIndex() {
        return picker.getValue();
    }

    public String getValueAtIndex(int index) {
        return values.get(index);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setValue(android.icu.util.Calendar date) {
        format.setTimeZone(state.getTimeZone());
        this.userSetValue = date;
        int index = getIndexOfDate(date);

        if(index > -1) {
            // Set value directly during initializing. After init, always smooth scroll to value
            if(picker.getValue() == 0) picker.setValue(index);
            else picker.smoothScrollToValue(index);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void refresh() {
        this.format = new android.icu.text.SimpleDateFormat(getFormatPattern(), state.getLocale());
        if (!this.visible()) return;
        init();
    }

    public String getDisplayValue(){
        return toDisplayValue(getValueAtIndex(getIndex()));
    }

    private String[] getDisplayValues(ArrayList<String> values){
        ArrayList<String> displayValues = new ArrayList<>();
        for (String value: values) {
            displayValues.add(this.toDisplayValue(value));
        }
        return displayValues.toArray(new String[0]);
    }

    private void init(){
        picker.setMinValue(0);
        picker.setMaxValue(0);
        values = getValues();
        picker.setDisplayedValues(getDisplayValues(values));
        picker.setMaxValue(values.size() -1);
    }

    public void updateVisibility(){
        int visibility = visible() ? View.VISIBLE: View.GONE;
        picker.setVisibility(visibility);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private SimpleDateFormat getFormat(ULocale locale) {
        return new android.icu.text.SimpleDateFormat(this.getFormatPattern(), locale);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    String getLocaleString(Calendar cal) {
        return getString(cal, this.state.getLocale());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getString(Calendar cal, ULocale locale){
        return getFormat(locale).format(cal.getTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setHorizontalPadding(){
        picker.setItemPaddingHorizontal(getHorizontalPadding());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int getHorizontalPadding() {
        Mode mode = state.getMode();
        if(state.derived.hasOnly2Wheels()) return 10;
        switch (mode){
            case date: return 15;
            case time:
            case datetime:
            default:
                return 5;
        }
    }
}
