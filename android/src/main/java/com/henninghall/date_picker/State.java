package com.henninghall.date_picker;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import android.os.Build;
import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.models.Is24HourSource;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.models.Variant;
import com.henninghall.date_picker.props.DividerHeightProp;
import com.henninghall.date_picker.props.Is24hourSourceProp;
import com.henninghall.date_picker.props.VariantProp;
import com.henninghall.date_picker.props.DateProp;
import com.henninghall.date_picker.props.FadeToColorProp;
import com.henninghall.date_picker.props.HeightProp;
import com.henninghall.date_picker.props.LocaleProp;
import com.henninghall.date_picker.props.MaximumDateProp;
import com.henninghall.date_picker.props.MinimumDateProp;
import com.henninghall.date_picker.props.MinuteIntervalProp;
import com.henninghall.date_picker.props.ModeProp;
import com.henninghall.date_picker.props.Prop;
import com.henninghall.date_picker.props.TextColorProp;
import com.henninghall.date_picker.props.UtcProp;

import java.util.HashMap;

public class State {

    private Calendar lastSelectedDate = null;
    private final DateProp dateProp = new DateProp();
    private final ModeProp modeProp = new ModeProp();
    private final LocaleProp localeProp = new LocaleProp();
    private final FadeToColorProp fadeToColorProp = new FadeToColorProp();
    private final TextColorProp textColorProp = new TextColorProp();
    private final MinuteIntervalProp minuteIntervalProp = new MinuteIntervalProp();
    private final MinimumDateProp minimumDateProp = new MinimumDateProp();
    private final MaximumDateProp maximumDateProp = new MaximumDateProp();
    private final UtcProp utcProp = new UtcProp();
    private final HeightProp heightProp = new HeightProp();
    private final VariantProp variantProp = new VariantProp();
    private final DividerHeightProp dividerHeightProp = new DividerHeightProp();
    private final Is24hourSourceProp is24hourSourceProp = new Is24hourSourceProp();

    private final HashMap props = new HashMap<String, Prop>() {{
        put(DateProp.name, dateProp);
        put(ModeProp.name, modeProp);
        put(LocaleProp.name, localeProp);
        put(FadeToColorProp.name, fadeToColorProp);
        put(TextColorProp.name, textColorProp);
        put(MinuteIntervalProp.name, minuteIntervalProp);
        put(MinimumDateProp.name, minimumDateProp);
        put(MaximumDateProp.name, maximumDateProp);
        put(UtcProp.name, utcProp);
        put(HeightProp.name, heightProp);
        put(VariantProp.name, variantProp);
        put(DividerHeightProp.name, dividerHeightProp);
        put(Is24hourSourceProp.name, is24hourSourceProp);
    }};
    public DerivedData derived;

    public State() {
        derived = new DerivedData(this);
    }

    private Prop getProp(String name) {
        return (Prop) props.get(name);
    }

    void setProp(String propName, Dynamic value) {
        getProp(propName).setValue(value);
    }

    public Mode getMode() {
        return (Mode) modeProp.getValue();
    }

    public String getFadeToColor() {
        return (String) fadeToColorProp.getValue();
    }

    public String getTextColor() {
        return (String) textColorProp.getValue();
    }

    public int getMinuteInterval() {
        return (int) minuteIntervalProp.getValue();
    }

    public ULocale getLocale() {
        return (ULocale)localeProp.getValue();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public android.icu.util.Calendar getMinimumDate() {
        DateBoundary db = new DateBoundary(getTimeZone(), (String) minimumDateProp.getValue());
        return db.get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public android.icu.util.Calendar getMaximumDate() {
        DateBoundary db = new DateBoundary(getTimeZone(), (String) maximumDateProp.getValue());
        return db.get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public android.icu.util.TimeZone getTimeZone() {
        boolean utc = (boolean) utcProp.getValue();
        return utc ? android.icu.util.TimeZone.getTimeZone("UTC") : TimeZone.getDefault();
    }

    public String getIsoDate() {
        return (String) dateProp.getValue();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public android.icu.util.Calendar getDate() {
        return Utils.isoToCalendar(getIsoDate(), getTimeZone());
    }

    public Integer getHeight() {
        return (Integer) heightProp.getValue();
    }

    public String getLocaleLanguageTag() {
        return localeProp.getLanguageTag();
    }

    public Variant getVariant() {
        return variantProp.getValue();
    }

    public int getDividerHeight() {
        return dividerHeightProp.getValue();
    }

    public Is24HourSource getIs24HourSource() {
        return is24hourSourceProp.getValue();
    }

    public Calendar getLastSelectedDate() {
        return lastSelectedDate;
    }

    public void setLastSelectedDate(Calendar date) {
        lastSelectedDate = date;
    }
}