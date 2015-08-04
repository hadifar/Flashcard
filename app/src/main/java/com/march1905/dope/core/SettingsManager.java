package com.march1905.dope.core;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Amir Hadifar on 01/08/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */

public class SettingsManager {
    public static final String DEFAULT_PREFERENCES_NAME = "defaultPreferences";

    public static final String PREFERENCE_FIRST_RUN = "isFirstRun";
    public static final String PREFERENCE_SHOWCASE_AGENTS = "showcaseAgents";
    public static final String PREFERENCE_SHOWCASE_AGENT_DETAIL = "showcaseAgentDetail";
    public static final String PREFERENCE_APP_LATEST_VERSION_CODE = "latestVersionCode";
    public static final String PREFERENCE_APP_MINIMUM_VERSION_CODE = "minimumVersionCode";
    public static final String PREFERENCE_SHOWCASE_LAWBOOK_FAVORITE = "showcaseLawBookFavorite";


    public static SharedPreferences getDefaultPreferences(Context context) {
        return context.getSharedPreferences(DEFAULT_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static boolean isFirstRun(Context context) {
        SharedPreferences preferences = getDefaultPreferences(context);
        boolean isFirstRun = preferences.getBoolean(PREFERENCE_FIRST_RUN, true);
        preferences.edit().putBoolean(PREFERENCE_FIRST_RUN, false).commit();

        return isFirstRun;
    }

    public static int getAppLatestVersionCode(Context context) {
        SharedPreferences preferences = getDefaultPreferences(context);
        return preferences.getInt(PREFERENCE_APP_LATEST_VERSION_CODE, 1);
    }

    public static void setAppLatestVersionCode(Context context, int versionCode) {
        SharedPreferences preferences = getDefaultPreferences(context);
        preferences.edit().putInt(PREFERENCE_APP_LATEST_VERSION_CODE, versionCode).commit();
    }
}
