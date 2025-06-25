package com.example.st109_pdf_reader.core.dialog

import android.R.attr.description
import android.R.attr.name
import android.app.Activity
import android.text.InputType
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseDialog
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.hideSoftKeyboard
import com.example.st109_pdf_reader.core.extensions.setGradientTextHeightColor
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.showToast
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.databinding.DialogRenameBinding


class RenameDialog(val context: Activity, val oldName: String, val type: String = KeyApp.RENAME) : BaseDialog<DialogRenameBinding>(context, maxWidth = true, maxHeight = true) {
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
                }else{
                    context.showToast(context.getString(R.string.please_input_text))
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
            when(type){
                KeyApp.SAVE_FILE -> {
                    tvTitle.text = context.getString(R.string.save_file)
                    edtRename.hint = context.getString(R.string.enter_file_name)
                }
                KeyApp.SEARCH_FILE -> {
                    tvTitle.text = context.getString(R.string.search_page)
                    edtRename.hint = context.getString(R.string.enter_page_number)
                    edtRename.inputType = InputType.TYPE_CLASS_NUMBER
                }
                else -> {
                    edtRename.setText(oldName)
                }
            }
            setGradientTextHeightColor(btnNo, "#F52C2C", "#B10C0C")
        }
    }
}