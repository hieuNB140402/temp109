package com.document.allreader.allofficefilereader.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import com.document.allreader.allofficefilereader.fc.openxml4j.opc.PackagingURIHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;


public class TinyDB {
    private String DEFAULT_APP_IMAGEDATA_DIRECTORY;
    private String lastImagePath = "";
    private SharedPreferences preferences;

    public TinyDB(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isExternalStorageWritable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static boolean isExternalStorageReadable() {
        String externalStorageState = Environment.getExternalStorageState();
        return "mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState);
    }

    public Bitmap getImage(String str) {
        try {
            return BitmapFactory.decodeFile(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSavedImagePath() {
        return this.lastImagePath;
    }

    public String putImage(String str, String str2, Bitmap bitmap) {
        if (str == null || str2 == null || bitmap == null) {
            return null;
        }
        this.DEFAULT_APP_IMAGEDATA_DIRECTORY = str;
        String str3 = setupFullPath(str2);
        if (!str3.equals("")) {
            this.lastImagePath = str3;
            saveBitmap(str3, bitmap);
        }
        return str3;
    }

    public boolean putImageWithFullPath(String str, Bitmap bitmap) {
        return (str == null || bitmap == null || !saveBitmap(str, bitmap)) ? false : true;
    }

    private String setupFullPath(String str) {
        File file = new File(Environment.getExternalStorageDirectory(), this.DEFAULT_APP_IMAGEDATA_DIRECTORY);
        if (!isExternalStorageReadable() || !isExternalStorageWritable() || file.exists() || file.mkdirs()) {
            return file.getPath() + PackagingURIHelper.FORWARD_SLASH_CHAR + str;
        }
        Log.e("ERROR", "Failed to setup folder");
        return "";
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x004c A[SYNTHETIC, Splitter:B:30:0x004c] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x005d A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0065 A[SYNTHETIC, Splitter:B:43:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    private boolean saveBitmap(String str, Bitmap bitmap) {
        boolean z;
        Throwable th;
        boolean z2 = false;
        boolean z3 = false;
        Exception e;
        if (str == null || bitmap == null) {
            return false;
        }
        File file = new File(str);
        if (file.exists() && !file.delete()) {
            return false;
        }
        try {
            z = file.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
            z = false;
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                z2 = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                try {
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                    z3 = true;
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            } catch (Exception e4) {
                e = e4;
                fileOutputStream = fileOutputStream2;
                try {
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            z2 = false;
                            z3 = true;
                        } catch (IOException e5) {
                            e5.printStackTrace();
                            z2 = false;
                            z3 = false;
                            return !z ? false : false;
                        }
                        if (!z) {
                        }
                    }
                    z2 = false;
                    z3 = false;
                    if (!z) {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }

                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                }

            }
        } catch (Exception e7) {
            e = e7;
            e.printStackTrace();
            if (fileOutputStream != null) {
            }
            z2 = false;
            z3 = false;
            if (!z) {
            }
        }
        if (!z || !z2 || !z3) {
            return false;
        }
        return true;
    }

    public int getInt(String str) {
        return this.preferences.getInt(str, 0);
    }

    public ArrayList<Integer> getListInt(String str) {
        ArrayList arrayList = new ArrayList(Arrays.asList(TextUtils.split(this.preferences.getString(str, ""), "\u201a\u2017\u201a")));
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(Integer.valueOf(Integer.parseInt((String) it.next())));
        }
        return arrayList2;
    }

    public long getLong(String str, long j) {
        return this.preferences.getLong(str, j);
    }

    public float getFloat(String str) {
        return this.preferences.getFloat(str, 0.0f);
    }

    public double getDouble(String str, double d) {
        try {
            return Double.parseDouble(getString(str));
        } catch (NumberFormatException unused) {
            return d;
        }
    }

    public ArrayList<Double> getListDouble(String str) {
        ArrayList arrayList = new ArrayList(Arrays.asList(TextUtils.split(this.preferences.getString(str, ""), "\u201a\u2017\u201a")));
        ArrayList<Double> arrayList2 = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(Double.valueOf(Double.parseDouble((String) it.next())));
        }
        return arrayList2;
    }

    public ArrayList<Long> getListLong(String str) {
        ArrayList arrayList = new ArrayList(Arrays.asList(TextUtils.split(this.preferences.getString(str, ""), "\u201a\u2017\u201a")));
        ArrayList<Long> arrayList2 = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(Long.valueOf(Long.parseLong((String) it.next())));
        }
        return arrayList2;
    }

    public String getString(String str) {
        return this.preferences.getString(str, "");
    }

    public ArrayList<String> getListString(String str) {
        return new ArrayList<>(Arrays.asList(TextUtils.split(this.preferences.getString(str, ""), "\u201a\u2017\u201a")));
    }

    public boolean getBoolean(String str) {
        return this.preferences.getBoolean(str, false);
    }

    public ArrayList<Boolean> getListBoolean(String str) {
        ArrayList<String> listString = getListString(str);
        ArrayList<Boolean> arrayList = new ArrayList<>();
        Iterator<String> it = listString.iterator();
        while (it.hasNext()) {
            if (it.next().equals("true")) {
                arrayList.add(true);
            } else {
                arrayList.add(false);
            }
        }
        return arrayList;
    }

    public void putInt(String str, int i) {
        checkForNullKey(str);
        this.preferences.edit().putInt(str, i).apply();
    }

    public void putListInt(String str, ArrayList<Integer> arrayList) {
        checkForNullKey(str);
        this.preferences.edit().putString(str, TextUtils.join("\u201a\u2017\u201a", (Integer[]) arrayList.toArray(new Integer[arrayList.size()]))).apply();
    }

    public void putLong(String str, long j) {
        checkForNullKey(str);
        this.preferences.edit().putLong(str, j).apply();
    }

    public void putListLong(String str, ArrayList<Long> arrayList) {
        checkForNullKey(str);
        this.preferences.edit().putString(str, TextUtils.join("\u201a\u2017\u201a", (Long[]) arrayList.toArray(new Long[arrayList.size()]))).apply();
    }

    public void putFloat(String str, float f) {
        checkForNullKey(str);
        this.preferences.edit().putFloat(str, f).apply();
    }

    public void putDouble(String str, double d) {
        checkForNullKey(str);
        putString(str, String.valueOf(d));
    }

    public void putListDouble(String str, ArrayList<Double> arrayList) {
        checkForNullKey(str);
        this.preferences.edit().putString(str, TextUtils.join("\u201a\u2017\u201a", (Double[]) arrayList.toArray(new Double[arrayList.size()]))).apply();
    }

    public void putString(String str, String str2) {
        checkForNullKey(str);
        checkForNullValue(str2);
        this.preferences.edit().putString(str, str2).apply();
    }

    public void putListString(String str, ArrayList<String> arrayList) {
        checkForNullKey(str);
        this.preferences.edit().putString(str, TextUtils.join("\u201a\u2017\u201a", (String[]) arrayList.toArray(new String[arrayList.size()]))).apply();
    }

    public void putBoolean(String str, boolean z) {
        checkForNullKey(str);
        this.preferences.edit().putBoolean(str, z).apply();
    }

    public void putListBoolean(String str, ArrayList<Boolean> arrayList) {
        checkForNullKey(str);
        ArrayList<String> arrayList2 = new ArrayList<>();
        Iterator<Boolean> it = arrayList.iterator();
        while (it.hasNext()) {
            if (it.next().booleanValue()) {
                arrayList2.add("true");
            } else {
                arrayList2.add("false");
            }
        }
        putListString(str, arrayList2);
    }

    public void remove(String str) {
        this.preferences.edit().remove(str).apply();
    }

    public boolean deleteImage(String str) {
        return new File(str).delete();
    }

    public void clear() {
        this.preferences.edit().clear().apply();
    }

    public Map<String, ?> getAll() {
        return this.preferences.getAll();
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.preferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.preferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public void checkForNullKey(String str) {
        Objects.requireNonNull(str);
    }

    public void checkForNullValue(String str) {
        Objects.requireNonNull(str);
    }
}
