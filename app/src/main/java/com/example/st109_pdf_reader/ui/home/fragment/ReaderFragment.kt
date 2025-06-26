package com.example.st109_pdf_reader.ui.home.fragment

import android.R.attr.path
import android.content.Intent
import android.content.res.ColorStateList
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseFragment
import com.example.st109_pdf_reader.core.dialog.ConfirmDialog
import com.example.st109_pdf_reader.core.dialog.RenameDialog
import com.example.st109_pdf_reader.core.extensions.backFragmentSlideInFromLeft
import com.example.st109_pdf_reader.core.extensions.dLog
import com.example.st109_pdf_reader.core.extensions.dpToPx
import com.example.st109_pdf_reader.core.extensions.gone
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
import com.example.st109_pdf_reader.ui.pdf.PdfActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import kotlin.jvm.java

class ReaderFragment : BaseFragment<FragmentReaderBinding>() {
    private var type = KeyApp.ALL_FILE
    private var tickList = ArrayList<ImageView>()
    private var buttonTickList = ArrayList<LinearLayout>()
    private val adapter by lazy {
        ReaderAdapter(requireActivity())
    }
    private var fileList = ArrayList<FilesModel>()
    private var isTypeSort = true
    private val homeActivity: HomeActivity
        get() = activity as HomeActivity
    private var typeSort = KeyApp.ValueApp.A_TO_Z
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
                    backFragmentSlideInFromLeft((homeActivity.binding.containerFragment)) {
                        homeActivity.supportFragmentManager.popBackStack()
                        homeActivity.isFragmentOther = false
                    }
                }
                btnActionBarRight.setOnSingleClick {
                    if (isTypeSort) {
                        layoutSort.visible()
                    } else {
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
            tvCenter.setTextColor(homeActivity.getColor(R.color.white))
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
                binding.actionBar.tvCenter.text = homeActivity.getString(R.string.all_file)
            }

            KeyApp.WORD -> {
                binding.actionBar.layoutHeader.setBackgroundResource(R.color.word)
                binding.actionBar.tvCenter.text = homeActivity.getString(R.string.type_reader, "Word")
                tickList[0].imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.word))
                binding.layoutParentSort.setBackgroundResource(R.drawable.bg_10_word)
            }

            KeyApp.EXCEL -> {
                binding.actionBar.layoutHeader.setBackgroundResource(R.color.excel)
                binding.actionBar.tvCenter.text = homeActivity.getString(R.string.type_reader, "Excel")
                tickList[0].imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.excel))
                binding.layoutParentSort.setBackgroundResource(R.drawable.bg_10_excel)
            }

            KeyApp.PPT -> {
                binding.actionBar.layoutHeader.setBackgroundResource(R.color.ppt)
                binding.actionBar.tvCenter.text = homeActivity.getString(R.string.type_reader, "Powerpoint")
                tickList[0].imageTintList =
                    ColorStateList.valueOf(homeActivity.getColor(R.color.ppt))
                binding.layoutParentSort.setBackgroundResource(R.drawable.bg_10_ppt)
            }

            KeyApp.PDF -> {
                binding.actionBar.layoutHeader.setBackgroundResource(R.color.pdf)
                binding.actionBar.tvCenter.text = homeActivity.getString(R.string.type_reader, "PDF")
            }
        }

        initRcv()

        homeActivity.fileViewModel.refreshScan(requireActivity())

        lifecycleScope.launch {
            homeActivity.showLoading()
            homeActivity.fileViewModel.filesFlow.collectLatest {
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
                when(typeSort){
                    KeyApp.ValueApp.A_TO_Z -> {
                        fileList.addAll(sortByNameAZ(temp).toCollection(ArrayList<FilesModel>()))
                    }
                    KeyApp.ValueApp.Z_TO_A -> {
                        fileList.addAll(sortByNameZA(temp).toCollection(ArrayList<FilesModel>()))
                    }
                    KeyApp.ValueApp.OLD_TO_NEW -> {
                        fileList.addAll(sortByDateOldToNew(temp).toCollection(ArrayList<FilesModel>()))
                    }
                    else -> {
                        fileList.addAll(sortByDateNewToOld(temp).toCollection(ArrayList<FilesModel>()))
                    }
                }

                submitAdapter()
                homeActivity.dismissLoading()
            }
        }
    }

    private fun submitAdapter() {
        adapter.submitList(fileList)
        if (fileList.isEmpty()) {
            binding.layoutNoItem.visible()
        } else {
            binding.layoutNoItem.gone()
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
                                tick.imageTintList =
                                    ColorStateList.valueOf(homeActivity.getColor(R.color.word))
                            }

                            KeyApp.EXCEL -> {
                                tick.imageTintList =
                                    ColorStateList.valueOf(homeActivity.getColor(R.color.excel))
                            }

                            KeyApp.PPT -> {
                                tick.imageTintList =
                                    ColorStateList.valueOf(homeActivity.getColor(R.color.ppt))
                            }
                        }
                    } else {
                        tick.setImageResource(R.drawable.ic_not_tick)

                        if (type == KeyApp.WORD || type == KeyApp.EXCEL || type == KeyApp.PPT) {
                            tick.imageTintList =
                                ColorStateList.valueOf(homeActivity.getColor(R.color.gray_CE))
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
                typeSort = indexButton
                fileList.clear()
                fileList.addAll(temp)
                submitAdapter()
            }
        }
    }

    private fun handleRcv() {
        adapter.apply {
            onBookmarkClick = { file, position ->
                handleBookmark(file, position)
            }
            onLongClick = { position ->
                handleLongItemClick(position)
            }
            onItemSelect = { positionTick ->
                handleSelect(positionTick)
            }
            onMoreClick = { file, position, view ->
                handleMoreMyDesign(file, position, view)
            }
            onItemClick = { file ->
                handleClickItem(file)
            }
        }
        binding.rcvFile.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(
                recyclerView: RecyclerView, motionEvent: MotionEvent
            ): Boolean {
                return when {
                    motionEvent.action != MotionEvent.ACTION_UP || recyclerView.findChildViewUnder(
                        motionEvent.x, motionEvent.y
                    ) != null -> false

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
        homeActivity.fileViewModel.updateBookmark(file.id, !file.isBookmark)
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
            } else {
                actionBar.btnActionBarRight.setImageResource(R.drawable.ic_not_select_all)
            }
            fileList[position].isSelected = true
            submitAdapter()
        }
    }

    private fun handleSelect(positionTick: Int) {
        fileList[positionTick].isSelected = !fileList[positionTick].isSelected
        val isNotAll = fileList.any { !it.isSelected }

        if (isNotAll) {
            binding.actionBar.btnActionBarRight.setImageResource(R.drawable.ic_not_select_all)
        } else {
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
        } else {
            fileList.forEach {
                it.isSelected = false
            }
            binding.actionBar.btnActionBarRight.setImageResource(R.drawable.ic_not_select_all)
        }
        submitAdapter()
    }

    private fun resetLongClick() {
        fileList.forEach {
            it.isSelected = false
            it.isShow = false
        }

        submitAdapter()


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
            homeActivity.showToast(getString(R.string.please_choose_a_file))
            return
        }
        homeActivity.shareFile(shareList)
    }

    private fun handleDelete() {
        val confirmDialog = ConfirmDialog(
            requireActivity(),
            R.string.delete,
            R.string.do_you_want_to_delete_this_file
        )
        SystemUtils.setLocale(requireActivity())
        confirmDialog.show()
        confirmDialog.onNoClick = {
            confirmDialog.dismiss()
            homeActivity.hideNavigation()
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
                homeActivity.showToast(getString(R.string.please_choose_a_file))

            } else {
                homeActivity.handleDeleteFile(
                    homeActivity.loadingDialog,
                    homeActivity.fileViewModel,
                    deleteList,
                    onFinish = { status ->
                        if (status) {
                            val updatedList = fileList.filterNot { file ->
                                deleteList.contains(file.path)
                            }
                            fileList.clear()
                            fileList.addAll(updatedList)
                            submitAdapter()

                        } else {
                            homeActivity.showToast(getString(R.string.file_not_exist))
                        }
                        lifecycleScope.launch {
                            homeActivity.dismissLoading()
                        }
                    })
            }

        }

    }

    private fun handleMoreMyDesign(file: FilesModel, position: Int, view: View) {
        val popupBinding = PopupReaderBinding.inflate(LayoutInflater.from(requireActivity()))
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
            popupWindow.dismiss()
            handleClickItem(file)
        }
        popupBinding.btnRename.setOnSingleClick {
            handleRename(file, position, popupWindow)
        }
        popupBinding.btnShare.setOnSingleClick {
            popupWindow.dismiss()
            homeActivity.shareFile(file.path)
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
        } else {
            popupWindow.showAsDropDown(view, xOffset, homeActivity.dpToPx(-135))
        }
    }

    private fun handleDelete(path: String, position: Int, popupWindow: PopupWindow) {
        popupWindow.dismiss()
        val confirmDialog = ConfirmDialog(
            requireActivity(),
            R.string.delete,
            R.string.do_you_want_to_delete_this_file
        )
        SystemUtils.setLocale(requireActivity())
        confirmDialog.show()
        confirmDialog.onNoClick = {
            confirmDialog.dismiss()
            homeActivity.hideNavigation()
        }
        confirmDialog.onYesClick = {
            confirmDialog.dismiss()
            val homeActivity = (activity as HomeActivity)
            if (!File(path).exists()) {
                homeActivity.showToast(getString(R.string.please_choose_a_file))
            } else {
                homeActivity.handleDeleteFile(
                    homeActivity.loadingDialog,
                    homeActivity.fileViewModel,
                    path,
                    onFinish = { status ->
                        if (status) {
                            fileList.removeAt(position)
                            submitAdapter()
                        } else {
                            homeActivity.showToast(getString(R.string.file_not_exist))
                        }
                        lifecycleScope.launch {
                            homeActivity.dismissLoading()
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
            homeActivity.dLog("newNameWithExtension: ${newNameWithExtension}")
            renameFileByPath(
                homeActivity.loadingDialog,
                homeActivity.fileViewModel,
                file.path,
                newNameWithExtension,
                onFinish = { status ->
                    if (status) {
                        homeActivity.dLog("newName: ${newName}")
                        fileList[position].name = newName
                        submitAdapter()
                    } else {
                        homeActivity.showToast(getString(R.string.file_not_exist))
                    }

                    lifecycleScope.launch {
                        homeActivity.dismissLoading()
                    }
                })
        }
    }

    private fun handleClickItem(file: FilesModel) {
        val intent = if (file.type != KeyApp.PDF) {
            Intent(requireActivity(), ViewActivity::class.java)
        } else {
            Intent(requireActivity(), PdfActivity::class.java)
        }
        homeActivity.fileViewModel.updateRecentFile(file.id, true)
        intent.putExtra(KeyApp.KeyIntent.INTENT_KEY, file)
        startActivity(intent)
    }
}