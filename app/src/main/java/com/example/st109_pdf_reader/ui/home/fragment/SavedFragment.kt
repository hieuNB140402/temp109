package com.example.st109_pdf_reader.ui.home.fragment

import android.R.attr.type
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.lifecycle.lifecycleScope
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseFragment
import com.example.st109_pdf_reader.core.dialog.ConfirmDialog
import com.example.st109_pdf_reader.core.dialog.RenameDialog
import com.example.st109_pdf_reader.core.extensions.dpToPx
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.handleDeleteFile
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.renameFileByPath
import com.example.st109_pdf_reader.core.extensions.select
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.shareFile
import com.example.st109_pdf_reader.core.extensions.showToast
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.data.model.HomeAllFileModel
import com.example.st109_pdf_reader.databinding.FragmentSavedBinding
import com.example.st109_pdf_reader.databinding.PopupReaderBinding
import com.example.st109_pdf_reader.ui.home.HomeActivity
import com.example.st109_pdf_reader.ui.home.adapter.ReaderAdapter
import com.example.st109_pdf_reader.ui.home.adapter.TypeFileAdapter
import com.example.st109_pdf_reader.ui.pdf.PdfActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

class SavedFragment : BaseFragment<FragmentSavedBinding>() {
    private val savedAdapter by lazy { ReaderAdapter(requireActivity()) }
    private val savedList = ArrayList<FilesModel>()
    private val getFileList = ArrayList<FilesModel>()

    private val homeActivity: HomeActivity
        get() = activity as HomeActivity

    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSavedBinding {
        return FragmentSavedBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        initRcv()
        convertToSaved()
    }

    override fun viewListener() {
        handleRcv()
    }

    private fun initRcv() {
        binding.apply {
            rcvFile.apply {
                adapter = savedAdapter
                itemAnimator = null
            }
        }
    }

    override fun dataObservable() {
        super.dataObservable()
        val homeActivity = (activity as HomeActivity)
        homeActivity.fileList.observe(homeActivity) {
            getFileList.clear()
            getFileList.addAll(it)
            convertToSaved()
        }
    }

    override fun onResume() {
        super.onResume()
        updateHeaderBackground()
        homeActivity.binding.actionBar.tvCenter.text = getString(R.string.saved)
    }

    private fun handleRcv() {
        savedAdapter.apply {
            onItemClick = { file ->
                handleOpenFile(file)
            }
            onBookmarkClick = { file, position ->
                handleBookmark(file, position)
            }
            onMoreClick = { file, position, view ->
                handleMoreMyDesign(file, position, view)
            }
        }
    }

    private fun handleBookmark(file: FilesModel, position: Int) {
        homeActivity.fileViewModel.updateBookmark(file.id, !file.isBookmark)
        savedAdapter.submitItemBookmark(position)
    }

    private fun updateHeaderBackground() {
        homeActivity.binding.actionBar.layoutHeader.setBackgroundResource(R.color.pdf)
    }


    private fun convertToSaved() {
        lifecycleScope.launch(Dispatchers.IO) {
            val temp = getFileList.filter { it.isSaved }
            savedList.clear()
            savedList.addAll(temp)

            launch(Dispatchers.Main) {
                savedAdapter.submitList(savedList)
                checkListSize()
            }
        }
    }

    private fun checkListSize() {
        if (savedList.isEmpty()) {
            binding.layoutNoItem.visible()
        } else {
            binding.layoutNoItem.gone()
        }
    }

    private fun handleMoreMyDesign(file: FilesModel, position: Int, view: View) {
        val popupBinding = PopupReaderBinding.inflate(LayoutInflater.from(homeActivity))
        val popupWindow = PopupWindow(
            popupBinding.root, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindow.elevation = 10f

        popupBinding.tvOpenFile.select()
        popupBinding.tvRename.select()
        popupBinding.tvShare.select()
        popupBinding.tvDelete.select()

        popupBinding.btnOpenFile.setOnSingleClick {
            handleOpenFile(file)
        }
        popupBinding.btnRename.setOnSingleClick {
            handleRename(file, position, popupWindow)
        }
        popupBinding.btnShare.setOnSingleClick {
            popupWindow.dismiss()
            (activity as HomeActivity).shareFile(file.path)
        }
        popupBinding.btnDelete.setOnSingleClick {
            handleDelete(file.path, position, popupWindow)
        }

        val xOffset = homeActivity.dpToPx(-100)
        val yOffset = homeActivity.dpToPx(6)

        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val viewY = location[1]
        val displayMetrics = DisplayMetrics()
        homeActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val distanceToBottom = screenHeight - viewY - view.height
        if (distanceToBottom >= homeActivity.dpToPx(180)) {
            popupWindow.showAsDropDown(view, xOffset, yOffset)
        }
        else {
            popupWindow.showAsDropDown(view, xOffset, homeActivity.dpToPx(-135))
        }
    }

    private fun handleDelete(path: String, position: Int, popupWindow: PopupWindow) {
        popupWindow.dismiss()
        val confirmDialog = ConfirmDialog(
            homeActivity, R.string.delete, R.string.do_you_want_to_delete_this_file)
        SystemUtils.setLocale(homeActivity)
        confirmDialog.show()
        confirmDialog.onNoClick = {
            confirmDialog.dismiss()
            homeActivity.hideNavigation()
        }
        confirmDialog.onYesClick = {
            confirmDialog.dismiss()
            if (!File(path).exists()) {
                homeActivity.showToast(getString(R.string.file_not_exist))
            }
            else {
                homeActivity.handleDeleteFile(homeActivity.loadingDialog, homeActivity.fileViewModel, path, onFinish = { status ->
                    if (status) {
                        savedList.removeAt(position)
                        savedAdapter.submitList(savedList)
                    }
                    else {
                        homeActivity.showToast(getString(R.string.file_not_exist))
                    }
                    lifecycleScope.launch {
                        (activity as HomeActivity).dismissLoading()
                    }
                })
            }

        }
    }

    private fun handleRename(file: FilesModel, position: Int, popupWindow: PopupWindow) {
        popupWindow.dismiss()
        val renameDialog = RenameDialog(homeActivity, file.name)
        SystemUtils.setLocale(homeActivity)
        renameDialog.show()
        renameDialog.onNoClick = {
            renameDialog.dismiss()
            homeActivity.hideNavigation()
        }
        renameDialog.onYesClick = { newName ->
            renameDialog.dismiss()
            val extensionArray = file.path.split(".")
            val extension = extensionArray[extensionArray.size - 1]
            val newNameWithExtension = "${newName}.${extension}"
            renameFileByPath(homeActivity.loadingDialog, homeActivity.fileViewModel, file.path, newNameWithExtension, onFinish = { status ->
                when(status){
                    KeyApp.FILE_NOT_EXIST -> {
                        homeActivity.showToast(getString(R.string.file_not_exist))
                    }
                    KeyApp.FILE_NAME_EXIST -> {
                        homeActivity.showToast(getString(R.string.new_name_already_exists))
                    }
                    KeyApp.RENAME_SUCCESS -> {
                        savedList[position].name = newName
                        savedAdapter.notifyItemChanged(position)
                    }
                    else -> {
                        homeActivity.showToast(getString(R.string.rename_failed_please_try_again))
                    }
                }

                lifecycleScope.launch {
                    homeActivity.dismissLoading()
                }
            })
        }
    }

    private fun handleOpenFile(file: FilesModel) {
        val intent = if (file.type != KeyApp.PDF) {
            Intent(homeActivity, ViewActivity::class.java)
        } else {
            Intent(homeActivity, PdfActivity::class.java)
        }
        homeActivity.fileViewModel.updateRecentFile(file.id, true)
        intent.putExtra(KeyApp.KeyIntent.INTENT_KEY, file)
        startActivity(intent)
    }
}