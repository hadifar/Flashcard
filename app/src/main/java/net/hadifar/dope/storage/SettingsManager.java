package net.hadifar.dope.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Amir Hadifar on 01/08/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */

public class SettingsManager {

    public static final String DEFAULT_PREFERENCES_NAME = "defaultPreferences";

    private static final String SELECTED_LANGUAGE = "SelectedLanguage";

    public static final String PREFERENCE_FIRST_RUN = "isFirstRun";
    public static final String PREFERENCE_SHOWCASE_AGENTS = "showcaseAgents";
    public static final String PREFERENCE_SHOWCASE_AGENT_DETAIL = "showcaseAgentDetail";
    public static final String PREFERENCE_APP_LATEST_VERSION_CODE = "latestVersionCode";
    public static final String PREFERENCE_APP_MINIMUM_VERSION_CODE = "minimumVersionCode";
    public static final String PREFERENCE_SHOWCASE_LAWBOOK_FAVORITE = "showcaseLawBookFavorite";
    public static final String PREFERENCE_ANIM_ENABLED = "isAnimationEnabled";


    private static SharedPreferences getDefaultPreferences(Context context) {
        return context.getSharedPreferences(DEFAULT_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


    public static int getAppLatestVersionCode(Context context) {
        SharedPreferences preferences = getDefaultPreferences(context);
        return preferences.getInt(PREFERENCE_APP_LATEST_VERSION_CODE, 1);
    }

    public static void setAppLatestVersionCode(Context context, int versionCode) {
        SharedPreferences preferences = getDefaultPreferences(context);
        preferences.edit().putInt(PREFERENCE_APP_LATEST_VERSION_CODE, versionCode).commit();
    }

    public static void setAnimationEnable(Context context, boolean enable) {
        SharedPreferences preferences = getDefaultPreferences(context);
        preferences.edit().putBoolean(PREFERENCE_ANIM_ENABLED, enable).apply();
    }

    public static boolean isAnimationEnabled(Context context) {
        SharedPreferences preferences = getDefaultPreferences(context);
        return preferences.getBoolean(PREFERENCE_ANIM_ENABLED, true);
    }
}
