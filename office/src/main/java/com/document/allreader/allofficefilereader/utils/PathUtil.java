package com.document.allreader.allofficefilereader.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import com.document.allreader.allofficefilereader.fc.openxml4j.opc.PackagingURIHelper;

import java.net.URISyntaxException;


public class PathUtil {
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        String[] strArr;
        String str;
        Uri uri2;
        if ((Build.VERSION.SDK_INT >= 19) && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                return Environment.getExternalStorageDirectory() + PackagingURIHelper.FORWARD_SLASH_STRING + split[1];
            } else if (isDownloadsDocument(uri)) {
                uri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue());
            } else if (isMediaDocument(uri)) {
                String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                String str2 = split2[0];
                if ("image".equals(str2)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(str2)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(str2)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                uri2 = uri;
                str = "_id=?";
                strArr = new String[]{split2[1]};
                if (!"content".equalsIgnoreCase(uri2.getScheme())) {
                    try {
                        Cursor query = context.getContentResolver().query(uri2, new String[]{"_data"}, str, strArr, null);
                        int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
                        if (query.moveToFirst()) {
                            return query.getString(columnIndexOrThrow);
                        }
                    } catch (Exception unused) {
                    }
                } else if ("file".equalsIgnoreCase(uri2.getScheme())) {
                    return uri2.getPath();
                }
                return null;
            }
        }
        uri2 = uri;
        str = null;
        strArr = null;
        if (!"content".equalsIgnoreCase(uri2.getScheme())) {
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
