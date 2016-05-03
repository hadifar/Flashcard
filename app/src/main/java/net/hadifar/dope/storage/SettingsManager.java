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


    //
    public static void onCreate(Context context) {
        String lang = getPersistedData(context, Locale.getDefault().getLanguage());
        setLocale(context, lang);
    }

    public static void onCreate(Context context, String defaultLanguage) {
        String lang = getPersistedData(context, defaultLanguage);
        setLocale(context, lang);
    }

    public static String getLanguage(Context context) {
        return getPersistedData(context, Locale.getDefault().getLanguage());
    }

    public static void setLocale(Context context, String language) {
        persist(context, language);
        updateResources(context, language);
    }

    private static String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
    }

    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }

    private static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
