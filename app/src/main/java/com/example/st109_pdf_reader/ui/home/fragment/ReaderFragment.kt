package com.example.st109_pdf_reader.ui.home.fragment

import android.R.attr.path
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.document.allreader.allofficefilereader.fc.dom4j.dom.DOMNodeHelper.getData
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseFragment
import com.example.st109_pdf_reader.core.dialog.ConfirmDialog
import com.example.st109_pdf_reader.core.dialog.RenameDialog
import com.example.st109_pdf_reader.core.extensions.backFragmentSlideInFromLeft
import com.example.st109_pdf_reader.core.extensions.dLog
import com.example.st109_pdf_reader.core.extensions.dpToPx
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.handleBackFragmentFromRight
import com.example.st109_pdf_reader.core.extensions.handleDeleteFile
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.renameFileByPath
import com.example.st109_pdf_reader.core.extensions.select
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.shareFile
import com.example.st109_pdf_reader.core.extensions.showToast
import com.example.st109_pdf_reader.core.extensions.sortByDateNewToOld
import com.example.st109_pdf_reader.core.extensions.sortByDateOldToNew
import com.example.st109_pdf_reader.core.extensions.sortByNameAZ
import com.example.st109_pdf_reader.core.extensions.sortByNameZA
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.databinding.FragmentReaderBinding
import com.example.st109_pdf_reader.databinding.PopupReaderBinding
import com.example.st109_pdf_reader.ui.home.HomeActivity
import com.example.st109_pdf_reader.ui.home.adapter.ReaderAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ReaderFragment : BaseFragment<FragmentReaderBinding>() {
    private var type = KeyApp.ALL_FILE
    private var tickList = ArrayList<ImageView>()
    private var buttonTickList = ArrayList<LinearLayout>()
    private val adapter by lazy {
        ReaderAdapter(requireActivity())
    }
    private var fileList = ArrayList<FilesModel>()
    private var isTypeSort = true
    override fun setViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentReaderBinding {
        return FragmentReaderBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        initActionBar()
        initData()
    }

    override fun viewListener() {
        binding.apply {
            actionBar.apply {
                btnActionBarLeft.setOnSingleClick {
                    backFragmentSlideInFromLeft(
                        ((activity as HomeActivity).binding.containerFragment)) {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
                btnActionBarRight.setOnSingleClick {
                    if (isTypeSort) {
                        layoutSort.visible()
                    }
                    else {
                        handleSelectAll()
                    }
                }
            }
            layoutSort.setOnSingleClick { it.gone() }
            layoutActionBar.setOnSingleClick { resetLongClick() }
            btnShare.setOnSingleClick { handleShare() }
            btnDelete.setOnSingleClick { handleDelete() }
        }

        handleSort()
        handleRcv()
    }

    private fun initActionBar() {
        binding.actionBar.apply {
            btnActionBarLeft.setImageResource(R.drawable.ic_back_white)
            btnActionBarLeft.visible()
            tvCenter.text = requireActivity().getString(R.string.pdf_reader)
            tvCenter.setTextColor(requireActivity().getColor(R.color.white))
            tvCenter.visible()
            btnActionBarRight.setImageResource(R.drawable.ic_sort)
            btnActionBarRight.visible()
        }
    }

    private fun initRcv() {
        binding.rcvFile.adapter = adapter
        binding.rcvFile.itemAnimator = null
    }

    private fun initData() {
        tickList = arrayListOf(
            binding.imvTickAtoZ,
            binding.imvTickZtoA,
            binding.imvTickOldToNew,
            binding.imvTickNewToOld,
        )
        buttonTickList = arrayListOf(
            binding.btnSortAtoZ,
            binding.btnSortZtoA,
            binding.btnOldToNew,
            binding.btnNewToOld,
        )

        val getType = arguments?.getString(KeyApp.KeyIntent.INTENT_KEY)
        type = getType!!

        when (type) {
            KeyApp.ALL_FILE -> {
                binding.actionBar.layoutHeader.setBackgroundResource(R.color.pdf)
            }

            KeyApp.WORD -> {
                binding.actionBar.layoutHeader.setBackgroundResource(R.color.word)
                tickList[0].imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.word))
                binding.layoutParentSort.setBackgroundResource(R.drawable.bg_10_word)
            }

            KeyApp.EXCEL -> {
                binding.actionBar.layoutHeader.setBackgroundResource(R.color.excel)
                tickList[0].imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.excel))
                binding.layoutParentSort.setBackgroundResource(R.drawable.bg_10_excel)
            }

            KeyApp.PPT -> {
                binding.actionBar.layoutHeader.setBackgroundResource(R.color.ppt)
                tickList[0].imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.ppt))
                binding.layoutParentSort.setBackgroundResource(R.drawable.bg_10_ppt)
            }

            KeyApp.PDF -> {
                binding.actionBar.layoutHeader.setBackgroundResource(R.color.pdf)
            }
        }

        initRcv()

        (activity as HomeActivity).fileViewModel.refreshScan(requireActivity())

        lifecycleScope.launch {
            (activity as HomeActivity).showLoading()
            (activity as HomeActivity).fileViewModel.filesFlow.collectLatest {
                var temp = ArrayList<FilesModel>()
                when (type) {
                    KeyApp.ALL_FILE -> {
                        temp.addAll(it.toCollection(ArrayList<FilesModel>()))
                    }

                    KeyApp.WORD -> {
                        it.forEach {
                            if (it.type == KeyApp.WORD) {
                                temp.add(it)
                            }
                        }
                    }

                    KeyApp.EXCEL -> {
                        it.forEach {
                            if (it.type == KeyApp.EXCEL) {
                                temp.add(it)
                            }
                        }
                    }

                    KeyApp.PPT -> {
                        it.forEach {
                            if (it.type == KeyApp.PPT) {
                                temp.add(it)
                            }
                        }
                    }

                    KeyApp.PDF -> {
                        it.forEach {
                            if (it.type == KeyApp.PDF) {
                                temp.add(it)
                            }
                        }
                    }
                }
                fileList.clear()
                fileList.addAll(sortByNameAZ(temp).toCollection(ArrayList<FilesModel>()))
                adapter.submitList(fileList)
                (activity as HomeActivity).dismissLoading()
            }
        }
    }

    private fun handleSort() {
        buttonTickList.forEachIndexed { indexButton, button ->
            button.setOnSingleClick {
                tickList.forEachIndexed { tickIndex, tick ->
                    if (indexButton == tickIndex) {
                        tick.setImageResource(R.drawable.ic_tick)
                        when (type) {
                            KeyApp.WORD -> {
                                tick.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.word))
                            }

                            KeyApp.EXCEL -> {
                                tick.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.excel))
                            }

                            KeyApp.PPT -> {
                                tick.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.ppt))
                            }
                        }
                    }
                    else {
                        tick.setImageResource(R.drawable.ic_not_tick)

                        if (type == KeyApp.WORD || type == KeyApp.EXCEL || type == KeyApp.PPT) {
                            tick.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.gray_CE))
                        }

                    }
                }
                var temp = ArrayList<FilesModel>()
                when (indexButton) {
                    0 -> {
                        temp = sortByNameAZ(fileList).toCollection(ArrayList<FilesModel>())
                    }

                    1 -> {
                        temp = sortByNameZA(fileList).toCollection(ArrayList<FilesModel>())
                    }

                    2 -> {
                        temp = sortByDateOldToNew(fileList).toCollection(ArrayList<FilesModel>())
                    }

                    3 -> {
                        temp = sortByDateNewToOld(fileList).toCollection(ArrayList<FilesModel>())
                    }
                }
                fileList.clear()
                fileList.addAll(temp)
                adapter.submitList(fileList)
            }
        }
    }

    private fun handleRcv() {
        adapter.onBookmarkClick = { file, position ->
            handleBookmark(file, position)

        }
        adapter.onLongClick = { position ->
            handleLongItemClick(position)
        }
        adapter.onItemSelect = { positionTick ->
            handleSelect(positionTick)
        }
        adapter.onMoreClick = { file, position, view ->
            handleMoreMyDesign(file, position, view)
        }

        binding.rcvFile.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(
                recyclerView: RecyclerView, motionEvent: MotionEvent
            ): Boolean {
                return when {
                    motionEvent.action != MotionEvent.ACTION_UP || recyclerView.findChildViewUnder(
                        motionEvent.x, motionEvent.y) != null -> false

                    else -> {
                        resetLongClick()
                        true
                    }
                }
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
            override fun onTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent) {}
        })
    }

    private fun handleBookmark(file: FilesModel, position: Int) {
        (activity as HomeActivity).fileViewModel.updateBookmark(file.id, !file.isBookmark)
        adapter.submitItemBookmark(position)
    }

    private fun handleLongItemClick(position: Int) {
        binding.apply {
            fileList.forEach {
                it.isShow = true
            }
            layoutBottom.visible()
            isTypeSort = false
            if (fileList.size == 1) {
                actionBar.btnActionBarRight.setImageResource(R.drawable.ic_select_all)
            }
            else {
                actionBar.btnActionBarRight.setImageResource(R.drawable.ic_not_select_all)
            }
            fileList[position].isSelected = true
            adapter.submitList(fileList)
        }
    }

    private fun handleSelect(positionTick: Int) {
        fileList[positionTick].isSelected = !fileList[positionTick].isSelected
        val isNotAll = fileList.any { !it.isSelected }

        if (isNotAll) {
            binding.actionBar.btnActionBarRight.setImageResource(R.drawable.ic_not_select_all)
        }
        else {
            binding.actionBar.btnActionBarRight.setImageResource(R.drawable.ic_select_all)
        }
        adapter.submitItemSelected(positionTick, fileList[positionTick].isSelected)
    }

    private fun handleSelectAll() {
        var isNotSelectAll = fileList.any { !it.isSelected }

        if (isNotSelectAll) {
            fileList.forEach {
                it.isSelected = true
            }
            binding.actionBar.btnActionBarRight.setImageResource(R.drawable.ic_select_all)
        }
        else {
            fileList.forEach {
                it.isSelected = false
            }
            binding.actionBar.btnActionBarRight.setImageResource(R.drawable.ic_not_select_all)
        }
        adapter.submitList(fileList)
    }

    private fun resetLongClick() {
        fileList.forEach {
            it.isSelected = false
            it.isShow = false
        }

        adapter.submitList(fileList)
        if (fileList.isEmpty()) {
            binding.layoutNoItem.visible()
        }
        else {
            binding.layoutNoItem.gone()
        }

        binding.actionBar.btnActionBarRight.setImageResource(R.drawable.ic_sort)
        binding.layoutBottom.gone()
        isTypeSort = true
    }

    private fun handleShare() {
        val shareList = ArrayList<String>()
        fileList.forEach {
            if (it.isSelected) {
                shareList.add(it.path)
            }
        }
        if (shareList.isEmpty()) {
            requireActivity().showToast(getString(R.string.please_choose_a_file))
            return
        }
        (activity as HomeActivity).shareFile(shareList)
    }

    private fun handleDelete() {
        val confirmDialog = ConfirmDialog(requireActivity(), R.string.delete, R.string.do_you_want_to_delete_this_file)
        SystemUtils.setLocale(requireActivity())
        confirmDialog.show()
        confirmDialog.onNoClick = {
            confirmDialog.dismiss()
            requireActivity().hideNavigation()
        }
        confirmDialog.onYesClick = {
            confirmDialog.dismiss()
            val deleteList = ArrayList<String>()
            fileList.forEach {
                if (it.isSelected) {
                    deleteList.add(it.path)
                }
            }
            if (deleteList.isEmpty()) {
                requireActivity().showToast(getString(R.string.please_choose_a_file))

            }
            else {
                (activity as HomeActivity).handleDeleteFile(
                    (activity as HomeActivity).loadingDialog, (activity as HomeActivity).fileViewModel,
                    deleteList, onFinish = { status ->
                        if (status) {
                            val updatedList = fileList.filterNot { file ->
                                deleteList.contains(file.path)
                            }
                            fileList.clear()
                            fileList.addAll(updatedList)
                            adapter.submitList(fileList)

                        }
                        else {
                            requireActivity().showToast(getString(R.string.file_not_exist))
                        }
                        lifecycleScope.launch {
                            (activity as HomeActivity).dismissLoading()
                        }
                    })
            }

        }

    }

    private fun handleMoreMyDesign(file: FilesModel, position: Int, view: View) {
        val popupBinding = PopupReaderBinding.inflate(LayoutInflater.from(requireActivity()))
        val popupWindow = PopupWindow(
            popupBinding.root, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindow.elevation = 10f

        when (type) {
            KeyApp.WORD -> {
                popupBinding.layoutParent.setBackgroundResource(R.drawable.bg_10_word)
                popupBinding.imvOpenFile.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.word))
                popupBinding.imvRename.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.word))
                popupBinding.imvShare.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.word))
                popupBinding.imvDelete.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.word))
            }

            KeyApp.EXCEL -> {
                popupBinding.layoutParent.setBackgroundResource(R.drawable.bg_10_excel)
                popupBinding.imvOpenFile.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.excel))
                popupBinding.imvRename.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.excel))
                popupBinding.imvShare.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.excel))
                popupBinding.imvDelete.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.excel))
            }

            KeyApp.PPT -> {
                popupBinding.layoutParent.setBackgroundResource(R.drawable.bg_10_ppt)
                popupBinding.imvOpenFile.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.ppt))
                popupBinding.imvRename.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.ppt))
                popupBinding.imvShare.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.ppt))
                popupBinding.imvDelete.imageTintList = ColorStateList.valueOf(requireActivity().getColor(R.color.ppt))
            }
        }
        popupBinding.tvOpenFile.select()
        popupBinding.tvRename.select()
        popupBinding.tvShare.select()
        popupBinding.tvDelete.select()

//        popupBinding.btnOpenFile.setOnSingleClick {
//            handleDeleteItemPopup(path, popupWindow)
//        }
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

        val xOffset = requireActivity().dpToPx(-100)
        val yOffset = requireActivity().dpToPx(6)

        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val viewY = location[1]
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val distanceToBottom = screenHeight - viewY - view.height
        if (distanceToBottom >= requireActivity().dpToPx(180)) {
            popupWindow.showAsDropDown(view, xOffset, yOffset)
        }
        else {
            popupWindow.showAsDropDown(view, xOffset, requireActivity().dpToPx(-135))
        }
    }

    private fun handleDelete(path: String, position: Int, popupWindow: PopupWindow) {
        popupWindow.dismiss()
        val confirmDialog = ConfirmDialog(requireActivity(), R.string.delete, R.string.do_you_want_to_delete_this_file)
        SystemUtils.setLocale(requireActivity())
        confirmDialog.show()
        confirmDialog.onNoClick = {
            confirmDialog.dismiss()
            requireActivity().hideNavigation()
        }
        confirmDialog.onYesClick = {
            confirmDialog.dismiss()
            val homeActivity = (activity as HomeActivity)
            if (!File(path).exists()) {
                requireActivity().showToast(getString(R.string.please_choose_a_file))
            }
            else {
                homeActivity.handleDeleteFile(homeActivity.loadingDialog, homeActivity.fileViewModel, path, onFinish = { status ->
                    if (status) {
                        fileList.removeAt(position)
                        adapter.submitList(fileList)
                    }
                    else {
                        requireActivity().showToast(getString(R.string.file_not_exist))
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
            renameFileByPath(homeActivity.loadingDialog, homeActivity.fileViewModel, file.path, newNameWithExtension, onFinish = { status ->
                if (status) {
                    fileList[position].name = newName
                    adapter.notifyItemChanged(position)
                }
                else {
                    requireActivity().showToast(getString(R.string.file_not_exist))
                }

                lifecycleScope.launch {
                    homeActivity.dismissLoading()
                }
            })
        }
    }
}