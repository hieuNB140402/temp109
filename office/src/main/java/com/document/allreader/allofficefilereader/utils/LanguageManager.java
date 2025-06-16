package com.document.allreader.allofficefilereader.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import java.util.Locale;


public class LanguageManager {
    private static final String LANGUAGE_KEY = "language_key";
    public static final String LANGUAGE_KEY_ARABIC = "ar";
    public static final String LANGUAGE_KEY_CHINES = "zh";
    public static final String LANGUAGE_KEY_ENGLISH = "en";
    public static final String LANGUAGE_KEY_FRENCH = "fr";
    public static final String LANGUAGE_KEY_GERMAN = "de";
    public static final String LANGUAGE_KEY_INDONESIAN = "in";
    public static final String LANGUAGE_KEY_ITALIAN = "it";
    public static final String LANGUAGE_KEY_POLISH = "pl";
    public static final String LANGUAGE_KEY_PORTUGUESE = "pt";
    public static final String LANGUAGE_KEY_RUSSIA = "ru";
    public static final String LANGUAGE_KEY_SPANISH = "es";

    public static Context setLocale(Context context) {
        return updateResources(context, getLanguagePref(context));
    }

    public static Context setNewLocale(Context context, String str) {
        setLanguagePref(context, str);
        return updateResources(context, str);
    }

    public static String getLanguagePref(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(LANGUAGE_KEY, "");
    }

    private static void setLanguagePref(Context context, String str) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(LANGUAGE_KEY, str).commit();
    }

    private static Context updateResources(Context context, String str) {
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            configuration.setLocale(locale);
            return context.createConfigurationContext(configuration);
        }
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    public static Locale getLocale(Resources resources) {
        Configuration configuration = resources.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? configuration.getLocales().get(0) : configuration.locale;
    }
}
