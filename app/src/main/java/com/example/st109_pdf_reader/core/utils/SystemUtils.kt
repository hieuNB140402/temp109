package com.example.st109_pdf_reader.core.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.ShareCompat
import androidx.lifecycle.MutableLiveData
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.gms.tasks.Task
import com.google.android.play.core.review.ReviewManagerFactory
import com.example.st109_pdf_reader.core.utils.KeyApp.KeyPermission.CAMERA_KEY
import com.example.st109_pdf_reader.core.utils.KeyApp.KeyPermission.FLASH_KEY
import com.example.st109_pdf_reader.core.utils.KeyApp.KeyPermission.IS_NOTIFICATION
import com.example.st109_pdf_reader.core.utils.KeyApp.KeyPermission.IS_STORAGE
import com.example.st109_pdf_reader.core.utils.KeyApp.KeyPermission.NOTIFICATION_KEY
import com.example.st109_pdf_reader.core.utils.KeyApp.KeyPermission.RINGTONE_KEY
import com.example.st109_pdf_reader.core.utils.KeyApp.KeyPermission.RING_TIME_KEY
import com.example.st109_pdf_reader.core.utils.KeyApp.KeyPermission.SOUND_KEY
import com.example.st109_pdf_reader.core.utils.KeyApp.KeyPermission.STORAGE_KEY
import com.example.st109_pdf_reader.core.utils.KeyApp.KeyPermission.STORAGE_KEY_IMAGE
import com.example.st109_pdf_reader.core.utils.KeyApp.KeyPermission.VIBRATION_KEY
import com.example.st109_pdf_reader.core.utils.KeyApp.KeySharePreference.COUNT_BACK
import com.example.st109_pdf_reader.core.utils.KeyApp.KeySharePreference.COUNT_BACK_KEY
import com.example.st109_pdf_reader.core.utils.KeyApp.KeySharePreference.FIRST_LANG
import com.example.st109_pdf_reader.core.utils.KeyApp.KeySharePreference.FIRST_LANG_KEY
import com.example.st109_pdf_reader.core.utils.KeyApp.KeySharePreference.FIRST_PERMISSION
import com.example.st109_pdf_reader.core.utils.KeyApp.KeySharePreference.FIRST_PERMISSION_KEY
import com.example.st109_pdf_reader.core.utils.KeyApp.KeySharePreference.KEY_FIRST_SCAN
import com.example.st109_pdf_reader.core.utils.KeyApp.KeySharePreference.KEY_LANGUAGE
import com.example.st109_pdf_reader.core.utils.KeyApp.KeySharePreference.RATE
import com.example.st109_pdf_reader.core.utils.KeyApp.KeySharePreference.RATE_KEY
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.system.exitProcess

object SystemUtils {
    private var myLocale: Locale? = null

    val storagePermission = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES
        )

        else -> arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    val notificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        emptyArray()
    }

    val cameraPermission = arrayOf(Manifest.permission.CAMERA)

    var FIRST_ACCESS = false

    var lastClickTime = 0L

    fun setLocale(context: Context) {
        val language = getPreLanguage(context)
        if (language.isEmpty()) {
            val config = Configuration()
            val locale = Locale.getDefault()
            Locale.setDefault(locale)
            config.setLocale(locale)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        } else {
            changeLang(language, context)
        }
    }

    fun changeLang(lang: String, context: Context) {
        if (lang.equals("", ignoreCase = true)) return
        myLocale = Locale(lang)
        saveLocale(context, lang)
        Locale.setDefault(myLocale!!)
        val config = Configuration()
        config.setLocale(myLocale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun saveLocale(context: Context, lang: String) {
        setPreLanguage(context, lang)
    }

    fun setPreLanguage(context: Context, language: String) {
        if (language.isNotEmpty()) {
            val preferences: SharedPreferences =
                context.getSharedPreferences("data", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(KEY_LANGUAGE, language)
            editor.apply()
        }
    }

    fun getPreLanguage(context: Context): String {
        val preferences: SharedPreferences =
            context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return preferences.getString(KEY_LANGUAGE, "en") ?: "en"
    }

    fun getFirstLang(context: Context): Boolean {
        val preferences: SharedPreferences =
            context.getSharedPreferences(FIRST_LANG, Context.MODE_PRIVATE)
        return preferences.getBoolean(FIRST_LANG_KEY, true)
    }

    fun setFirstLang(context: Context, isFirstAccess: Boolean) {
        val preferences: SharedPreferences =
            context.getSharedPreferences(FIRST_LANG, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean(FIRST_LANG_KEY, isFirstAccess)
        editor.apply()
    }

    fun getFirstPermission(context: Context): Boolean {
        val preferences: SharedPreferences =
            context.getSharedPreferences(FIRST_PERMISSION, Context.MODE_PRIVATE)
        return preferences.getBoolean(FIRST_PERMISSION_KEY, true)
    }

    fun setFirstPermission(context: Context, isFirstAccess: Boolean) {
        val preferences: SharedPreferences =
            context.getSharedPreferences(FIRST_PERMISSION, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean(FIRST_PERMISSION_KEY, isFirstAccess)
        editor.apply()
    }

    fun getIsRate(context: Context): Boolean {
        val preferences: SharedPreferences =
            context.getSharedPreferences(RATE, Context.MODE_PRIVATE)
        return preferences.getBoolean(RATE_KEY, false)
    }

    fun setIsRate(context: Context, isFirstAccess: Boolean) {
        val preferences: SharedPreferences =
            context.getSharedPreferences(RATE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean(RATE_KEY, isFirstAccess)
        editor.apply()
    }

    fun setCountBack(context: Context, countBack: Int) {
        val preferences: SharedPreferences =
            context.getSharedPreferences(COUNT_BACK, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt(COUNT_BACK_KEY, countBack)
        editor.apply()
    }

    fun getCountBack(context: Context): Int {
        val preferences: SharedPreferences =
            context.getSharedPreferences(COUNT_BACK, Context.MODE_PRIVATE)
        return preferences.getInt(COUNT_BACK_KEY, 0)
    }

    fun Activity.shareApp() {
        ShareCompat.IntentBuilder.from(this).setType("text/plain").setChooserTitle("Chooser title")
            .setText("http://play.google.com/store/apps/details?id=" + (this).packageName)
            .startChooser()
    }

    fun Activity.policy() {
        val url = "https://sites.google.com/view/tung-sahur-fake-video-call/home"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    fun reviewApp(context: Activity, isBackPress: Boolean) {
        val manager = ReviewManagerFactory.create(context)
        val request = manager.requestReviewFlow();
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                Log.e("ReviewInfo", "" + reviewInfo.toString())
                val flow = (context as Activity?)?.let { manager.launchReviewFlow(it, reviewInfo) }
                flow?.addOnCompleteListener { task2: Task<Void> ->
                    if (isBackPress) {
                        exitProcess(0)
                    }
                }
            } else {
                if (isBackPress) {
                    exitProcess(0)
                }
            }
        }
    }

    fun getStoragePermission(context: Context): Int {
        val preferences: SharedPreferences =
            context.getSharedPreferences(IS_STORAGE, Context.MODE_PRIVATE)
        return preferences.getInt(STORAGE_KEY, 0)
    }

    fun setStoragePermission(context: Context, count: Int) {
        val preferences: SharedPreferences =
            context.getSharedPreferences(IS_STORAGE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt(STORAGE_KEY, count)
        editor.apply()
    }

    fun getStoragePermissionImage(context: Context): Int {
        val preferences: SharedPreferences =
            context.getSharedPreferences(STORAGE_KEY_IMAGE, Context.MODE_PRIVATE)
        return preferences.getInt(STORAGE_KEY_IMAGE, 0)
    }

    fun setStoragePermissionImage(context: Context, count: Int) {
        val preferences: SharedPreferences =
            context.getSharedPreferences(STORAGE_KEY_IMAGE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt(STORAGE_KEY_IMAGE, count)
        editor.apply()
    }

    fun getNotificationPermission(context: Context): Int {
        val preferences: SharedPreferences =
            context.getSharedPreferences(IS_NOTIFICATION, Context.MODE_PRIVATE)
        return preferences.getInt(NOTIFICATION_KEY, 0)
    }

    fun setNotificationPermission(context: Context, count: Int) {
        val preferences: SharedPreferences =
            context.getSharedPreferences(IS_NOTIFICATION, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt(NOTIFICATION_KEY, count)
        editor.apply()
    }

    fun getCameraPermission(context: Context): Int {
        val preferences: SharedPreferences =
            context.getSharedPreferences(CAMERA_KEY, Context.MODE_PRIVATE)
        return preferences.getInt(CAMERA_KEY, 0)
    }

    fun setCameraPermission(context: Context, count: Int) {
        val preferences: SharedPreferences =
            context.getSharedPreferences(CAMERA_KEY, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt(CAMERA_KEY, count)
        editor.apply()
    }

    val shimmer =
        Shimmer.AlphaHighlightBuilder().setDuration(1800).setBaseAlpha(0.7f).setHighlightAlpha(0.6f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT).setAutoStart(true).build()

    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(Date())
    }

    var CURRENT_DATE = ""

    fun isFirstScan(context: Context): Boolean {
        val pref = context.getSharedPreferences(KEY_FIRST_SCAN, Context.MODE_PRIVATE)
        return pref.getBoolean(KEY_FIRST_SCAN, true)
    }

    fun setFirstScanDone(context: Context) {
        val pref = context.getSharedPreferences(KEY_FIRST_SCAN, Context.MODE_PRIVATE)
        pref.edit().putBoolean(KEY_FIRST_SCAN, false).apply()
    }
}
