package com.march1905.dope.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Amir on 4/9/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */


public class FontHelper {

    private static FontHelper instance;
    private static Typeface iconTypeface;
    private static Typeface persianTypeface;
    private static Typeface robotoTypeface;

    private FontHelper(Context context) {
        persianTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/YekanMob-Regular-v4.ttf");
        iconTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Material-Design-Iconic-Font-2.2.0.ttf");
        robotoTypeface = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf");
    }

    public static synchronized FontHelper getInstance(Context context) {
        if (instance == null)
            instance = new FontHelper(context);
        return instance;
    }

    public Typeface getIconTypeface() {
        return iconTypeface;
    }

    public Typeface getPersianTextTypeface() {
        return persianTypeface;
    }

    public Typeface getRobotoTypeface(){
        return robotoTypeface;
    }
}
