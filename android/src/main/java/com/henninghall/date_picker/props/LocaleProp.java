package com.henninghall.date_picker.props;

import android.icu.util.ULocale;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.Dynamic;


import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class LocaleProp extends Prop<ULocale> {
    public static final String name = "locale";
    private String languageTag = getDefaultLanguageTag();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LocaleProp(){
        super(getDefaultLocale());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static private ULocale getDefaultLocale(){
        return ULocale.getDefault();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static private String getDefaultLanguageTag(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? ULocale.getDefault().toLanguageTag()
                : "en";
    }

    public String getLanguageTag(){
        return languageTag;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)



    @Override
    public ULocale toValue(Dynamic value){
        this.languageTag = value.asString().replace('-','_');
        return new ULocale(value.asString());
    }

}

