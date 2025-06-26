package com.example.st109_pdf_reader.core.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.utils.SystemUtils.setLocale
import androidx.core.net.toUri
import com.example.st109_pdf_reader.core.extensions.goToSettings


internal fun Context.checkPermissions(listPermission: Array<String>): Boolean {
    return listPermission.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
}


internal fun Activity.requestPermission(permissions: Array<String>, requestCode: Int) {
    ActivityCompat.requestPermissions(this, permissions, requestCode)
}

@RequiresApi(Build.VERSION_CODES.M)
internal fun Activity.goToSettings() {
    setLocale(this)
    val dialog = AlertDialog.Builder(this).setTitle(R.string.go_to_setting_title)
        .setMessage(R.string.go_to_setting_message)
        .setPositiveButton(R.string.settings) { dialog, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = "package:${this@goToSettings.packageName}".toUri()
            }
            this.startActivity(intent)
            dialog.dismiss()
            hideNavigation()
        }.setNegativeButton(R.string.cancel1) { dialog, _ ->
            dialog.dismiss()
            hideNavigation()
        }.setCancelable(false).create()

    dialog.show()
    val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
    val negativeButton: Button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
    positiveButton.setTextColor(getColor(R.color.red_end))
    negativeButton.setTextColor(getColor(R.color.black))
}

internal fun Activity.goToManageSettings() {
    setLocale(this)
    val dialog = AlertDialog.Builder(this).setTitle(R.string.go_to_setting_title)
        .setMessage(R.string.this_app_needs_read_storage_permission_to_use_all_functions)
        .setPositiveButton(R.string.settings) { dialog, _ ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = ("package:$packageName").toUri()
                startActivity(intent)
            }else{
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = "package:${this@goToManageSettings.packageName}".toUri()
                }
                startActivity(intent)
            }
            dialog.dismiss()
            hideNavigation()
        }.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
            hideNavigation()
        }.setCancelable(false).create()

    dialog.show()
    val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
    val negativeButton: Button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
    positiveButton.setTextColor(getColor(R.color.red_end))
    negativeButton.setTextColor(getColor(R.color.black))
}
