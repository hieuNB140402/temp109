package com.document.allreader.allofficefilereader.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import java.io.IOException;


public class Utils {
    public static Bitmap getBitmap(Activity activity, Uri uri) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        return BitmapFactory.decodeFileDescriptor(activity.getContentResolver().openAssetFileDescriptor(uri, "r").getFileDescriptor(), null, options);
    }
}
