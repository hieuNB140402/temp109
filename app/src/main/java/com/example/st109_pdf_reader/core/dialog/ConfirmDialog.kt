package com.example.st109_pdf_reader.core.dialog

import android.app.Activity
import androidx.core.content.ContextCompat
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseDialog
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.setGradientTextHeightColor
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.databinding.DialogConfirmBinding


class ConfirmDialog(val context: Activity, val title: Int, val description: Int) :
    BaseDialog<DialogConfirmBinding>(context, maxWidth = true, maxHeight = true) {
    override val layoutId: Int = R.layout.dialog_confirm
    override val isCancel: Boolean = false
    override val isBack: Boolean = false

    var onNoClick: (() -> Unit)? = null
    var onYesClick: (() -> Unit)? = null
    var onDismissClick: (() -> Unit)? = null

    override fun initView() {
        initText()
        context.hideNavigation()
    }

    override fun initAction() {
        binding.apply {
            btnNo.setOnSingleClick {
                onNoClick?.invoke()
            }
            btnYes.setOnSingleClick {
                onYesClick?.invoke()
            }
            main.setOnSingleClick {
                onDismissClick?.invoke()
            }
        }
    }

    override fun onDismissListener() {

    }

    private fun initText() {
        binding.apply {
            tvTitle.text = ContextCompat.getString(context, title)
            tvDescription.text = ContextCompat.getString(context, description)
            setGradientTextHeightColor(btnNo, "#F52C2C", "#B10C0C")
        }
    }
}