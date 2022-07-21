package com.henninghall.date_picker.wheelFunctions;

import android.icu.util.Calendar;
import android.os.Build;
import androidx.annotation.RequiresApi;

import com.henninghall.date_picker.wheels.Wheel;

import java.util.Date;

public class SetDate implements WheelFunction {

    private Calendar date;

    public SetDate(android.icu.util.Calendar date) {
        this.date = date;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void apply(Wheel wheel) {
        wheel.setValue(date);
    }
}


