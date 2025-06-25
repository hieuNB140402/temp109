package com.document.allreader.allofficefilereader.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.document.allreader.allofficefilereader.constant.EventConstant;


public class BaseClass extends AppCompatActivity {
    protected void intentTo(Context context, Activity activity) {
        Intent intent = new Intent(context, activity.getClass());
        intent.setFlags(EventConstant.APP_FIND_ID);
        context.startActivity(intent);
    }
}
