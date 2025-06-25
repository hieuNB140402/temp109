package com.document.allreader.allofficefilereader.database;

import android.content.Context;
import android.content.SharedPreferences;


public class MyPreferences {
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String KEY_PREF_IN_APP_IS_ITEM_PURCHASE = "KEY_PREF_IN_APP_IS_ITEM_PURCHASE";
    private static final String KEY_PREF_IN_APP_REVIEW = "KEY_PREF_IN_APP_REVIEW";
    private static final String KEY_PREF_LANGUAGE_SELECTED = "KEY_PREF_LANGUAGE_SELECTED";
    private static final String getOrderId = "getOrderId";
    private static final String getPackageName = "getPackageName";


    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public MyPreferences(Context context) {
        this._context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.allreader.office.allofficefilereader", 0);
        this.pref = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }

    public void setFirstTimeLaunch(boolean z) {
        this.editor.putBoolean(IS_FIRST_TIME_LAUNCH, z);
        this.editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return this.pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setLanguageSelected(boolean z) {
        this.editor.putBoolean(KEY_PREF_LANGUAGE_SELECTED, z);
        this.editor.commit();
    }

    public boolean isLanguageSelected() {
        return this.pref.getBoolean(KEY_PREF_LANGUAGE_SELECTED, false);
    }


    public boolean isItemPurchased() {
        return this.pref.getBoolean(KEY_PREF_IN_APP_IS_ITEM_PURCHASE, false);
    }

    public void setGetOrderId(String str) {
        this.editor.putString(str, str);
        this.editor.commit();
    }

    public String getGetOrderId() {
        return this.pref.getString(getOrderId, null);
    }

    public void setGetPackageName(String str) {
        this.editor.putString(str, str);
        this.editor.commit();
    }

    public String getGetPackageName() {
        return this.pref.getString(getPackageName, null);
    }


    public void setKEY_PREF_IN_APP_REVIEW(boolean z) {
        this.editor.putBoolean(KEY_PREF_IN_APP_REVIEW, z);
        this.editor.commit();
    }

    public boolean isKEY_PREF_IN_APP_REVIEW() {
        return this.pref.getBoolean(KEY_PREF_IN_APP_REVIEW, false);
    }
}
