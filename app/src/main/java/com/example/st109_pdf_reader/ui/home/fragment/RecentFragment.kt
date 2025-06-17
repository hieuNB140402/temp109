package com.example.st109_pdf_reader.ui.home.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.util.DisplayMetrics
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
import com.example.st109_pdf_reader.databinding.FragmentRecentBinding
import com.example.st109_pdf_reader.databinding.PopupReaderBinding
import com.example.st109_pdf_reader.ui.home.HomeActivity
import com.example.st109_pdf_reader.ui.home.adapter.ReaderAdapter
import com.example.st109_pdf_reader.ui.home.adapter.TypeFileAdapter
import com.example.st109_pdf_reader.ui.pdf.PdfActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecentFragment : BaseFragment<FragmentRecentBinding>() {
    private val typeAdapter by lazy { TypeFileAdapter(requireActivity()) }
    private val recentAdapter by lazy { ReaderAdapter(requireActivity()) }

    private val typeList = ArrayList<HomeAllFileModel>()
    private val recentList = ArrayList<FilesModel>()
    private val getFileList = ArrayList<FilesModel>()
    private var type = KeyApp.ALL_FILE

    private val homeActivity: HomeActivity
        get() = activity as HomeActivity

    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecentBinding {
        return FragmentRecentBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        initRcv()
    }

    override fun viewListener() {
        handleRcv()
    }

    private fun initRcv() {
        binding.apply {
            rcvTypeFile.apply {
                adapter = typeAdapter
                itemAnimator = null
            }
            rcvFile.apply {
                adapter = recentAdapter
                itemAnimator = null
            }
        }
    }

    override fun dataObservable() {
        super.dataObservable()
        val homeActivity = (activity as HomeActivity)
        lifecycleScope.launch {
            homeActivity.fileViewModel.filesFlow.collectLatest {
                submitAdapter(it.toCollection(ArrayList<FilesModel>()))
            }
        }
        homeActivity.fileList.observe(homeActivity) {
            getFileList.clear()
            getFileList.addAll(it)
            convertToRecent()
        }
    }

    override fun onResume() {
        super.onResume()
        updateHeaderBackground()
    }

    private fun handleRcv() {
        typeAdapter.apply {
            onItemClick = { typeModel ->
                handleSelectType(typeModel)
            }
        }

        recentAdapter.apply {
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

    private fun submitAdapter(fileList: ArrayList<FilesModel>) {
        typeList.clear()
        typeList.addAll(handleConvertFile(fileList, isRecent = true))
        val positionType = typeList.indexOfFirst { it.type == type }
        typeList[positionType].isSelected = true
        typeAdapter.submitList(typeList)
    }

    private fun handleSelectType(typeModel: HomeAllFileModel) {
        type = typeModel.type
        updateHeaderBackground()
        homeActivity.fileViewModel.refreshScan(homeActivity)
        convertToRecent()
    }

    private fun updateHeaderBackground() {
        val colorRes = when (type) {
            KeyApp.WORD -> R.color.word
            KeyApp.EXCEL -> R.color.excel
            KeyApp.PPT -> R.color.ppt
            KeyApp.PDF, KeyApp.ALL_FILE -> R.color.pdf
            else -> R.color.pdf
        }
        homeActivity.binding.actionBar.layoutHeader.setBackgroundResource(colorRes)
    }

    private fun convertToRecent() {
        lifecycleScope.launch(Dispatchers.IO) {
            val temp = getFileList.filter {
                it.isRecentFiles && (type == KeyApp.ALL_FILE || it.type == type)
            }
            recentList.clear()
            recentList.addAll(temp)

            launch(Dispatchers.Main) {
                recentAdapter.submitList(recentList)
                checkListSize()
            }
        }
    }

    private fun checkListSize() {
        if (recentList.isEmpty()) {
            binding.layoutNoItem.visible()
        } else {
            binding.layoutNoItem.gone()
        }
    }

    private fun handleBookmark(file: FilesModel, position: Int) {
        val isRecent = !file.isBookmark
        homeActivity.fileViewModel.updateBookmark(file.id, isRecent)
        recentList[position].isRecentFiles = isRecent
        val positionSelect = getFileList.indexOfFirst { it.id == file.id }
        getFileList[positionSelect].isRecentFiles = isRecent
        recentAdapter.submitItemBookmark(position)
    }

    private fun handleMoreMyDesign(file: FilesModel, position: Int, view: View) {
        val popupBinding = PopupReaderBinding.inflate(LayoutInflater.from(homeActivity))
        val popupWindow = PopupWindow(
            popupBinding.root,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
        popupWindow.elevation = 10f

        when (type) {
            KeyApp.WORD -> {
                popupBinding.layoutParent.setBackgroundResource(R.drawable.bg_10_word)
                popupBinding.imvOpenFile.imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.word))
                popupBinding.imvRename.imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.word))
                popupBinding.imvShare.imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.word))
                popupBinding.imvDelete.imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.word))
            }

            KeyApp.EXCEL -> {
                popupBinding.layoutParent.setBackgroundResource(R.drawable.bg_10_excel)
                popupBinding.imvOpenFile.imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.excel))
                popupBinding.imvRename.imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.excel))
                popupBinding.imvShare.imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.excel))
                popupBinding.imvDelete.imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.excel))
            }

            KeyApp.PPT -> {
                popupBinding.layoutParent.setBackgroundResource(R.drawable.bg_10_ppt)
                popupBinding.imvOpenFile.imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.ppt))
                popupBinding.imvRename.imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.ppt))
                popupBinding.imvShare.imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.ppt))
                popupBinding.imvDelete.imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.ppt))
            }
        }
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
            handleDelete(file.id, position, popupWindow)
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
        } else {
            popupWindow.showAsDropDown(view, xOffset, homeActivity.dpToPx(-135))
        }
    }


    private fun handleDelete(id: Int, position: Int, popupWindow: PopupWindow) {
        popupWindow.dismiss()
        val confirmDialog = ConfirmDialog(
            homeActivity, R.string.delete, R.string.do_you_want_to_remove_this_file_from_recent
        )
        SystemUtils.setLocale(homeActivity)
        confirmDialog.show()
        confirmDialog.onNoClick = {
            confirmDialog.dismiss()
            homeActivity.hideNavigation()
        }
        confirmDialog.onYesClick = {
            confirmDialog.dismiss()
            homeActivity.fileViewModel.updateRecentFile(id, false)
            recentList.removeAt(position)
            recentAdapter.submitList(recentList)
            homeActivity.hideNavigation()
        }
    }

    private fun handleRename(file: FilesModel, position: Int, popupWindow: PopupWindow) {
        popupWindow.dismiss()
        val homeActivity = (activity as HomeActivity)
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
            renameFileByPath(
                homeActivity.loadingDialog,
                homeActivity.fileViewModel,
                file.path,
                newNameWithExtension,
                onFinish = { status ->
                    if (status) {
                        recentList[position].name = newName
                        recentAdapter.notifyItemChanged(position)
                    } else {
                        homeActivity.showToast(getString(R.string.file_not_exist))
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
//        homeActivity.fileViewModel.updateRecentFile(file.id, true)
        intent.putExtra(KeyApp.KeyIntent.INTENT_KEY, file)
        startActivity(intent)
    }
}