package com.example.st109_pdf_reader.core.dialog

import android.app.Activity
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseDialog
import com.example.st109_pdf_reader.databinding.DialogLoadingBinding

class LoadingDialog(val context: Activity) :
    BaseDialog<DialogLoadingBinding>(context, maxWidth = true, maxHeight = true) {
    override val layoutId: Int = R.layout.dialog_loading
    override val isCancel: Boolean = false
    override val isBack: Boolean = false
    override fun initView() {
        initRotation()
    }

    override fun initAction() {}

    override fun onDismissListener() {}

    private fun initRotation(){
    }
}