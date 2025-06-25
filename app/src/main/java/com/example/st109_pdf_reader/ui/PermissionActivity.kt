package com.example.st109_pdf_reader.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseActivity
import com.example.st109_pdf_reader.core.extensions.checkPermissions
import com.example.st109_pdf_reader.core.extensions.goToManageSettings
import com.example.st109_pdf_reader.core.extensions.goToSettings
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.requestPermission
import com.example.st109_pdf_reader.core.extensions.select
import com.example.st109_pdf_reader.core.extensions.setGradientTextHeightColor
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.showToast
import com.example.st109_pdf_reader.core.extensions.startIntentFromLeft
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.KeyApp.RequestCode.NOTIFICATION_PERMISSION_CODE
import com.example.st109_pdf_reader.core.utils.KeyApp.RequestCode.STORAGE_PERMISSION_CODE
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.core.utils.SystemUtils.notificationPermission
import com.example.st109_pdf_reader.core.utils.SystemUtils.setNotificationPermission
import com.example.st109_pdf_reader.core.utils.SystemUtils.setStoragePermission
import com.example.st109_pdf_reader.core.utils.SystemUtils.storagePermission
import com.example.st109_pdf_reader.databinding.ActivityPermissionBinding
import com.example.st109_pdf_reader.ui.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PermissionActivity : BaseActivity<ActivityPermissionBinding>() {
    override fun setViewBinding(): ActivityPermissionBinding {
        return ActivityPermissionBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        initData()
    }

    override fun viewListener() {
        binding.apply {
            switchStoragePermission.setOnSingleClick {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (!Environment.isExternalStorageManager()) {
                        goToManageSettings()
                    }
                    else {
                        showToast(R.string.granted_storage)
                    }
                }
                else {
                    if (checkPermissions(storagePermission)) {
                        showToast(R.string.granted_storage)
                    }
                    else {
                        if (SystemUtils.getStoragePermission(this@PermissionActivity) >= 2 && !checkPermissions(storagePermission)) {
                            goToSettings()
                        }
                        else {
                            requestPermission(storagePermission, STORAGE_PERMISSION_CODE)
                        }
                    }
                }
            }

            switchNotificationPermission.setOnSingleClick {
                if (checkPermissions(notificationPermission)) {
                    Toast.makeText(
                        this@PermissionActivity, getString(R.string.granted_notification), Toast.LENGTH_SHORT).show()
                }
                else {
                    if (SystemUtils.getNotificationPermission(this@PermissionActivity) >= 2) {
                        goToSettings()
                    }
                    else {
                        requestPermission(notificationPermission, NOTIFICATION_PERMISSION_CODE)
                    }
                }
            }

            tvContinue.setOnSingleClick(1500) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
                    goToManageSettings()
                }
                else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R && !checkPermissions(storagePermission)) {
                    goToManageSettings()
                }
                else {
                    lifecycleScope.launch(Dispatchers.IO) {
                        showLoading()
                        val job1 = async {
                            SystemUtils.setFirstPermission(this@PermissionActivity, false)
                            this@PermissionActivity.deleteDatabase("app_database")
                            return@async true
                        }
                        launch(Dispatchers.Main) {
                            if (job1.await()) {
                                startIntentFromLeft(HomeActivity::class.java)
                                dismissLoading(true)
                                finishAffinity()
                            }
                        }
                    }
                }

            }
        }
    }

    override fun initText() {
        binding.apply {
            setGradientTextHeightColor(binding.tvContinue, "#F52C2C", "#B10C0C")
            actionBar.tvCenter.select()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                txtPer.text = TextUtils.concat(
                    changeColor(
                        this@PermissionActivity, resources.getString(R.string.allow), R.color.black_24, R.font.roboto_medium), " ", changeColor(
                        this@PermissionActivity, resources.getString(R.string.app_name), R.color.red_end, R.font.roboto_medium), " ",

                    changeColor(
                        this@PermissionActivity, resources.getString(R.string.to_access_13), R.color.black_24, R.font.roboto_medium))
            }
            else {
                txtPer.text = TextUtils.concat(
                    changeColor(
                        this@PermissionActivity, resources.getString(R.string.allow), R.color.black_24, R.font.roboto_medium), " ", changeColor(
                        this@PermissionActivity, resources.getString(R.string.app_name), R.color.red_end, R.font.roboto_medium), " ", changeColor(
                        this@PermissionActivity, resources.getString(R.string.to_access), R.color.black_24, R.font.roboto_medium))
            }
        }
    }

    private fun initData() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            binding.btnStorage.visible()
            binding.btnNotification.gone()
        }
    }

    private fun changeColor(
        context: Context,
        text: String,
        color: Int,
        fontfamily: Int,
    ): SpannableString {
        val spannableString = SpannableString(text)
        spannableString.setSpan(
            ForegroundColorSpan(context.getColor(color)), 0, text.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        val font = ResourcesCompat.getFont(context, fontfamily)
        val typefaceSpan = CustomTypefaceSpan("", font)
        spannableString.setSpan(
            typefaceSpan, 0, text.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }

    class CustomTypefaceSpan(private val family: String, private val typeface: Typeface?) : TypefaceSpan(family) {

        override fun updateDrawState(ds: TextPaint) {
            applyCustomTypeFace(ds, typeface)
        }

        override fun updateMeasureState(paint: TextPaint) {
            applyCustomTypeFace(paint, typeface)
        }

        private fun applyCustomTypeFace(paint: Paint, tf: Typeface?) {
            if (tf != null) {
                paint.typeface = tf
            }
            else {
                paint.typeface = Typeface.DEFAULT
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                binding.switchStoragePermission.setImageResource(R.drawable.ic_sw_on)
                Toast.makeText(this, R.string.granted_storage, Toast.LENGTH_SHORT).show()
            }
            else {
                binding.switchStoragePermission.setImageResource(R.drawable.ic_sw_off)
                setStoragePermission(this, (SystemUtils.getStoragePermission(this) + 1))
            }
        }

        else if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                binding.switchNotificationPermission.setImageResource(R.drawable.ic_sw_on)
                Toast.makeText(this, R.string.granted_notification, Toast.LENGTH_SHORT).show()
            }
            else {
                binding.switchNotificationPermission.setImageResource(R.drawable.ic_sw_off)
                setNotificationPermission(this, (SystemUtils.getNotificationPermission(this) + 1))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M) override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                binding.switchStoragePermission.setImageResource(R.drawable.ic_sw_on)
            }
            else {
                binding.switchStoragePermission.setImageResource(R.drawable.ic_sw_off)
            }
        }
        else {
            if (checkPermissions(storagePermission)) {
                binding.switchStoragePermission.setImageResource(R.drawable.ic_sw_on)
                setStoragePermission(this, 0)
            }
            else {
                binding.switchStoragePermission.setImageResource(R.drawable.ic_sw_off)
            }
        }


        if (checkPermissions(notificationPermission)) {
            binding.switchNotificationPermission.setImageResource(R.drawable.ic_sw_on)
            setNotificationPermission(this, 0)
        }
        else {
            binding.switchNotificationPermission.setImageResource(R.drawable.ic_sw_off)
        }
    }

    override fun initActionBar() {
        binding.actionBar.apply {
            tvStart.text = getString(R.string.permission)
            tvStart.visible()
        }
    }
}