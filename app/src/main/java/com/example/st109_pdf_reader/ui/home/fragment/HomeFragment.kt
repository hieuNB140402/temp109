package com.example.st109_pdf_reader.ui.home.fragment

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.lifecycle.lifecycleScope
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseFragment
import com.example.st109_pdf_reader.core.dialog.ScanDialog
import com.example.st109_pdf_reader.core.extensions.checkPermissions
import com.example.st109_pdf_reader.core.extensions.goToSettings
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.requestPermission
import com.example.st109_pdf_reader.core.extensions.select
import com.example.st109_pdf_reader.core.extensions.setGradientTextHeightColor
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.startFragmentSlideInFromRight
import com.example.st109_pdf_reader.core.extensions.startIntentFromLeft
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.core.utils.KeyApp.ALL_FILE
import com.example.st109_pdf_reader.core.utils.KeyApp.EXCEL
import com.example.st109_pdf_reader.core.utils.KeyApp.PDF
import com.example.st109_pdf_reader.core.utils.KeyApp.PPT
import com.example.st109_pdf_reader.core.utils.KeyApp.RequestCode.STORAGE_PERMISSION_CODE
import com.example.st109_pdf_reader.core.utils.KeyApp.RequestCode.STORAGE_PERMISSION_CODE_IMAGE
import com.example.st109_pdf_reader.core.utils.KeyApp.WORD
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.core.utils.SystemUtils.cameraPermission
import com.example.st109_pdf_reader.core.utils.SystemUtils.getStoragePermission
import com.example.st109_pdf_reader.core.utils.SystemUtils.getStoragePermissionImage
import com.example.st109_pdf_reader.core.utils.SystemUtils.setStoragePermission
import com.example.st109_pdf_reader.core.utils.SystemUtils.setStoragePermissionImage
import com.example.st109_pdf_reader.core.utils.SystemUtils.storagePermission
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.data.model.HomeAllFileModel
import com.example.st109_pdf_reader.databinding.FragmentHomeBinding
import com.example.st109_pdf_reader.databinding.PopupCreateBinding
import com.example.st109_pdf_reader.databinding.PopupEditBinding
import com.example.st109_pdf_reader.ui.camera.CameraActivity
import com.example.st109_pdf_reader.ui.create.gallery.GalleryActivity
import com.example.st109_pdf_reader.ui.home.HomeActivity
import com.example.st109_pdf_reader.ui.home.adapter.HomeAllFileAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.compareTo

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val homeAllFileAdapter by lazy {
        HomeAllFileAdapter(requireActivity())
    }
    private val allFileList = ArrayList<HomeAllFileModel>()

    private val homeActivity: HomeActivity
        get() = activity as HomeActivity


    override fun setViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        initText()
        initRcv()
        binding.swrTypeHome.setColorSchemeResources(R.color.red_end)

    }

    override fun viewListener() {
        binding.swrTypeHome.setOnRefreshListener {
            homeActivity.fileViewModel.refreshScan(requireActivity())
            binding.swrTypeHome.isRefreshing = false
        }
        binding.btnAdd.setOnSingleClick { handlePopup(it) }
        binding.btnCreate.setOnSingleClick { handleCreate() }
        handleRcv()
    }

    private fun initText() {
        setGradientTextHeightColor(binding.tvCreate, "#F52C2C", "#B10C0C")
    }

    private fun initRcv() {
        binding.rcvTypeHome.adapter = homeAllFileAdapter
        binding.rcvTypeHome.itemAnimator = null
    }

    private fun handleRcv() {
        homeAllFileAdapter.onItemClick = { type ->
            startFragment(type)
        }
    }

    private fun startFragment(type: String) {
        val fragment = ReaderFragment().apply {
            arguments = Bundle().apply {
                putString(KeyApp.KeyIntent.INTENT_KEY, type)
            }
        }
        startFragmentSlideInFromRight(homeActivity.binding.containerFragment)

        fragment.let {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(homeActivity.binding.containerFragment.id, fragment).addToBackStack(null).commit()
        }

        homeActivity.isFragmentOther = true
    }

    override fun dataObservable() {
        super.dataObservable()
        val homeActivity = homeActivity
        lifecycleScope.launch {
            homeActivity.fileViewModel.filesFlow.collectLatest {
                submitAdapter(it.toCollection(ArrayList<FilesModel>()))
            }
        }

    }

    private fun checkPermissionStorage(isScan: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU || Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            if (!homeActivity.checkPermissions(storagePermission)) {
                if (SystemUtils.getStoragePermission(homeActivity) >= 2) {
                    homeActivity.goToSettings()
                } else {
                    homeActivity.requestPermission(storagePermission, STORAGE_PERMISSION_CODE)
                }
            } else {
                if (!isScan) {
                    homeActivity.startIntentFromLeft(GalleryActivity::class.java)
                } else {
                    homeActivity.startIntentFromLeft(GalleryActivity::class.java, true)
                }
            }
        } else {
            if (!isScan) {
                homeActivity.startIntentFromLeft(GalleryActivity::class.java)
            } else {
                homeActivity.startIntentFromLeft(GalleryActivity::class.java, KeyApp.KeyIntent.SCAN_KEY, true)
            }
        }
    }

    private fun checkPermissionCamera(isScan: Boolean = false) {
        if (!homeActivity.checkPermissions(cameraPermission)) {
            if (SystemUtils.getCameraPermission(homeActivity) < 2) {
                homeActivity.requestPermission(cameraPermission, KeyApp.RequestCode.CAMERA_PERMISSION_CODE)
            } else {
                homeActivity.goToSettings()
            }
        } else {
            if (!isScan) {
                homeActivity.startIntentFromLeft(CameraActivity::class.java)
            } else {
                homeActivity.startIntentFromLeft(CameraActivity::class.java, true)
            }
        }
    }

    private fun submitAdapter(fileList: ArrayList<FilesModel>) {
        allFileList.clear()
        allFileList.addAll(handleConvertFile(fileList))
        homeAllFileAdapter.submitList(allFileList)
    }

    override fun onResume() {
        super.onResume()
        homeActivity.binding.actionBar.layoutHeader.setBackgroundResource(R.color.pdf)
        homeActivity.binding.actionBar.tvCenter.text = getString(R.string.pdf_reader)
    }

    private fun handlePopup(view: View) {
        val popupBinding = PopupCreateBinding.inflate(LayoutInflater.from(homeActivity))
        val popupWindow = PopupWindow(
            popupBinding.root, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true
        ).apply {
            elevation = 10f
            isFocusable = true
            isOutsideTouchable = true
        }

        popupBinding.apply {
            tvCamera.select()
            tvGallery.select()
            btnGallery.setOnSingleClick {
                checkPermissionStorage()
                popupWindow.dismiss()
            }
            btnCamera.setOnSingleClick {
                checkPermissionCamera()
                popupWindow.dismiss()
            }
        }

        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1]

        // Đo trước khi lấy kích thước
        popupBinding.root.measure(
            View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED
        )
        val popupHeight = popupBinding.root.measuredHeight
        val popupWidth = popupBinding.root.measuredWidth

        // Chuyển 16dp thành pixel
        val marginPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics
        ).toInt()

        val displayMetrics = DisplayMetrics()
        homeActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        val finalX = screenWidth - popupWidth - marginPx
        val finalY = y - popupHeight - marginPx

        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, finalX, finalY)
    }

    private fun handleCreate() {
        val dialogScan = ScanDialog(homeActivity)
        SystemUtils.setLocale(homeActivity)
        dialogScan.apply {
            show()

            onCameraClick = {
                checkPermissionCamera(true)
            }

            onGalleryClick = {
                checkPermissionStorage(true)
            }
            onDismissClick = {
                dismiss()
                homeActivity.hideNavigation()
            }
        }

    }

}