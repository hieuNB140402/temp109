package com.example.st109_pdf_reader.core.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.st109_pdf_reader.core.dialog.GoToSettingsDialog
import com.example.st109_pdf_reader.core.dialog.LoadingDialog
import com.example.st109_pdf_reader.core.extensions.checkPermissions
import com.example.st109_pdf_reader.core.extensions.handleBackLeftToRight
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.core.utils.SystemUtils.storagePermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    lateinit var binding: T

    protected abstract fun setViewBinding(): T

    protected abstract fun initView()

    protected abstract fun viewListener()

    open fun dataObservable() {}

    protected abstract fun initText()

    protected abstract fun initActionBar()

    open fun initAds() {}

    lateinit var loadingDialog: LoadingDialog

    lateinit var settingsDialog: GoToSettingsDialog

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemUtils.setLocale(this)
        binding = setViewBinding()
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        loadingDialog = LoadingDialog(this)
        settingsDialog = GoToSettingsDialog(this)

        initView()
        viewListener()
        dataObservable()
        initText()
        initActionBar()
        initAds()
    }

    override fun onResume() {
        super.onResume()
        hideNavigation(true)
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        handleBackLeftToRight()
    }

    suspend fun showLoading() {
        withContext(Dispatchers.Main) {
            if (loadingDialog.isShowing.not()) {
                SystemUtils.setLocale(this@BaseActivity)
                loadingDialog.show()
            }
        }
    }

    suspend fun dismissLoading(isBlack: Boolean = false) {
        withContext(Dispatchers.Main) {
            if (loadingDialog.isShowing) {
                loadingDialog.dismiss()
                hideNavigation(isBlack)
            }
        }
    }
}
