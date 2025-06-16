package com.example.st109_pdf_reader.core.dialog

import android.R.attr.description
import android.R.attr.name
import android.app.Activity
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseDialog
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.hideSoftKeyboard
import com.example.st109_pdf_reader.core.extensions.setGradientTextHeightColor
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.databinding.DialogRenameBinding


class RenameDialog(val context: Activity, val oldName: String) : BaseDialog<DialogRenameBinding>(context, maxWidth = true, maxHeight = true) {
    override val layoutId: Int = R.layout.dialog_rename
    override val isCancel: Boolean = false
    override val isBack: Boolean = false

    var onNoClick: (() -> Unit)? = null
    var onYesClick: ((String) -> Unit)? = null
    var onDismissClick: (() -> Unit)? = null

    override fun initView() {
        initText()
        context.hideNavigation()
    }

    override fun initAction() {
        binding.apply {
            btnNo.setOnSingleClick {
                context.hideSoftKeyboard()
                onNoClick?.invoke()
            }
            btnYes.setOnSingleClick {
                if (edtRename.text.toString().trim() != "") {
                    onYesClick?.invoke(edtRename.text.toString().trim())
                }
            }
            btnDetele.setOnSingleClick {
                edtRename.setText("")
            }
            edtRename.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    context.hideSoftKeyboard()
                    true
                }
                else {
                    false
                }

            }
        }
    }

    override fun onDismissListener() {

    }

    private fun initText() {
        binding.apply {
            binding.edtRename.setText(oldName)
            setGradientTextHeightColor(btnNo, "#F52C2C", "#B10C0C")
        }
    }
}