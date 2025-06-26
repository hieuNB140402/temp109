package com.example.st109_pdf_reader.core.dialog

import android.R.attr.description
import android.app.Activity
import androidx.core.content.ContextCompat
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseDialog
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.setGradientTextHeightColor
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.databinding.DialogConfirmBinding
import com.example.st109_pdf_reader.databinding.DialogScanBinding


class ScanDialog(val context: Activity) :
    BaseDialog<DialogScanBinding>(context, maxWidth = true, maxHeight = true) {
    override val layoutId: Int = R.layout.dialog_scan
    override val isCancel: Boolean = false
    override val isBack: Boolean = false

    var onCameraClick: (() -> Unit)? = null
    var onGalleryClick: (() -> Unit)? = null
    var onDismissClick: (() -> Unit)? = null

    override fun initView() {
        context.hideNavigation()
    }

    override fun initAction() {
        binding.apply {
            btnCamera.setOnSingleClick {
                onCameraClick?.invoke()
            }
            btnGallery.setOnSingleClick {
                onGalleryClick?.invoke()
            }
            main.setOnSingleClick {
                onDismissClick?.invoke()
            }
        }
    }

    override fun onDismissListener() {

    }
    
}