package com.example.st109_pdf_reader.core.dialog

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseDialog
import com.example.st109_pdf_reader.core.extensions.goToManageSettings
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.setGradientTextHeightColor
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.databinding.DialogBottomSettingsBinding


class GoToSettingsDialog(val context: Activity) :
    BaseDialog<DialogBottomSettingsBinding>(context, maxWidth = true, maxHeight = true) {
    override val layoutId: Int = R.layout.dialog_bottom_settings
    override val isCancel: Boolean = false
    override val isBack: Boolean = false

    var onYesClick: (() -> Unit)? = null

    override fun initView() {
        initText()
        context.hideNavigation()
    }

    override fun initAction() {
        binding.apply {
            btnGoToSettings.setOnSingleClick {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.data = ("package:$context.packageName").toUri()
                    context.startActivity(intent)
                }else{
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = "package:${context.packageName}".toUri()
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onDismissListener() {

    }

    private fun initText() {
        binding.apply {

        }
    }
}