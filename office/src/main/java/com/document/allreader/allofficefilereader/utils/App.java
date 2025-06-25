package com.document.allreader.allofficefilereader.utils;

import android.app.Application;
import android.os.Build;


public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= 26) {
            createChannel();
        }
    }

    public void createChannel() {
      //  ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(getString(R.string.default_notification_channel_id), getString(R.string.default_notification_channel_name), 2));
    }
}
