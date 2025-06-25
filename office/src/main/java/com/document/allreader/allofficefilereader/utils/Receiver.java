package com.document.allreader.allofficefilereader.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.document.allreader.allofficefilereader.database.MyPreferences;


public class Receiver extends BroadcastReceiver {
    public MyPreferences myPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        MyPreferences myPreferences = new MyPreferences(context);
        this.myPreferences = myPreferences;
        myPreferences.setKEY_PREF_IN_APP_REVIEW(true);
    }
}
