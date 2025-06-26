package com.example.st109_pdf_reader.ui.home

import android.R.attr.data
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseActivity
import com.example.st109_pdf_reader.core.dialog.RateDialog
import com.example.st109_pdf_reader.core.extensions.animateLift
import com.example.st109_pdf_reader.core.extensions.backFragmentSlideInFromLeft
import com.example.st109_pdf_reader.core.extensions.checkPermissions
import com.example.st109_pdf_reader.core.extensions.dLog
import com.example.st109_pdf_reader.core.extensions.deleteTempDataFolder
import com.example.st109_pdf_reader.core.extensions.getImageInternal
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.select
import com.example.st109_pdf_reader.core.extensions.setGradientTextHeightColor
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.slideInFromLeft
import com.example.st109_pdf_reader.core.extensions.slideOutToLeft
import com.example.st109_pdf_reader.core.extensions.startFragmentSlideInFromRight
import com.example.st109_pdf_reader.core.extensions.startIntentFromLeft
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.DataLocal
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.core.utils.KeyApp.KeyIntent.INTENT_KEY
import com.example.st109_pdf_reader.core.utils.KeyApp.RequestCode.CAMERA_PERMISSION_CODE
import com.example.st109_pdf_reader.core.utils.KeyApp.RequestCode.STORAGE_PERMISSION_CODE
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.core.utils.SystemUtils.getStoragePermission
import com.example.st109_pdf_reader.core.utils.SystemUtils.policy
import com.example.st109_pdf_reader.core.utils.SystemUtils.setCameraPermission
import com.example.st109_pdf_reader.core.utils.SystemUtils.setStoragePermission
import com.example.st109_pdf_reader.core.utils.SystemUtils.shareApp
import com.example.st109_pdf_reader.core.utils.SystemUtils.storagePermission
import com.example.st109_pdf_reader.data.local.AppDatabase
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.data.local.repository.FileRepository
import com.example.st109_pdf_reader.databinding.ActivityHomeBinding
import com.example.st109_pdf_reader.ui.create.gallery.GalleryActivity
import com.example.st109_pdf_reader.ui.home.adapter.HomeAdapter
import com.example.st109_pdf_reader.ui.home.fragment.ReaderFragment
import com.example.st109_pdf_reader.ui.home.fragment.SearchFragment
import com.example.st109_pdf_reader.ui.home.viewmodel.FileViewModel
import com.example.st109_pdf_reader.ui.home.viewmodel.FileViewModelFactory
import com.example.st109_pdf_reader.ui.language.LanguageActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import kotlin.system.exitProcess

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private var bottomList = ArrayList<View>()
    private var imageBottomList = ArrayList<ImageView>()
    private var textBottomList = ArrayList<TextView>()
    private var textGradientBottomList = ArrayList<TextView>()
    private var currentFragment = KeyApp.ValueApp.HOME
    lateinit var fileViewModel: FileViewModel
    private var isLoadFileSuccess = false
    var fileList = MutableLiveData<ArrayList<FilesModel>>()
    var isFragmentOther = false

    override fun setViewBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        initList()
        initRate()
        countAccess()
        deleteTempDataFolder(this, KeyApp.TEMP_IMAGE_FILTER)
        deleteTempDataFolder(this, KeyApp.DOWNLOAD_ALBUM)
        deleteTempDataFolder(this, KeyApp.FOLDER_CREATE_PDF)
        checkPermissionToInit()
    }

    private fun checkPermissionToInit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            if (!settingsDialog.isShowing) {
                settingsDialog.show()
            }
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R && !checkPermissions(
                storagePermission
            )
        ) {
            if (!settingsDialog.isShowing) {
                settingsDialog.show()
            }
        } else {
            setupViewPager()
            val dao = AppDatabase.getInstance(this).fileDao()
            val repository = FileRepository(dao)

            fileViewModel = ViewModelProvider(this, FileViewModelFactory(repository))[FileViewModel::class.java]
            fileViewModel.scanIfFirstTime(this)

            lifecycleScope.launch {
                fileViewModel.filesFlow.collectLatest {
                    fileList.postValue(it.toCollection(ArrayList<FilesModel>()))
                }
            }

            isLoadFileSuccess = true
        }
    }

    override fun viewListener() {
        binding.apply {
            actionBar.apply {
                btnActionBarLeft.setOnSingleClick { handleSettings() }
                btnActionBarRight.setOnSingleClick { handleSearch() }
            }
            layoutSettings.setOnSingleClick { slideOutToLeft(layoutSettingsShow, layoutSettings) }
            btnLang.setOnSingleClick {
                startIntentFromLeft(
                    LanguageActivity::class.java, INTENT_KEY
                )
            }
            btnShare.setOnSingleClick(1500) { shareApp() }
            btnRate.setOnSingleClick { rateApp() }
            btnPolicy.setOnSingleClick(1500) { policy() }

        }
        handleBottom()
    }

    override fun initText() {
        binding.apply {
            actionBar.tvCenter.select()
            tvAppName.select()
        }
    }

    override fun initActionBar() {
        binding.actionBar.apply {
            btnActionBarLeft.setImageResource(R.drawable.ic_tab)
            btnActionBarLeft.visible()
            tvCenter.text = getString(R.string.pdf_reader)
            tvCenter.setTextColor(getColor(R.color.white))
            tvCenter.visible()
            btnActionBarRight.setImageResource(R.drawable.ic_search)
            btnActionBarRight.visible()
            layoutHeader.setBackgroundResource(R.color.pdf)
        }
    }

    private fun initRate() {
        if (SystemUtils.getIsRate(this)) {
            binding.btnRate.gone()
        } else {
            binding.btnRate.visible()
        }
    }

    private fun countAccess() {
        if (!SystemUtils.getIsRate(this) && !SystemUtils.FIRST_ACCESS) {
            SystemUtils.FIRST_ACCESS = true
            SystemUtils.setCountBack(this, SystemUtils.getCountBack(this) + 1)
        }
    }

    private fun initList() {
        binding.apply {
            bottomList = arrayListOf(
                btnHome, btnRecent, btnBookmark, btnSaved
            )
            imageBottomList = arrayListOf(
                imvHome, imvRecent, imvBookmark, imvSaved
            )
            textBottomList = arrayListOf(
                tvHome, tvRecent, tvBookmark, tvSaved
            )
            textGradientBottomList = arrayListOf(
                tvHomeGradient, tvRecentGradient, tvBookmarkGradient, tvSavedGradient
            )
            textGradientBottomList.forEach { text ->
                setGradientTextHeightColor(text, "#F52C2C", "#B10C0C")
            }
        }
    }

    private fun setupViewPager() {
        binding.apply {
            vpgHome.setUserInputEnabled(false);
            vpgHome.adapter = HomeAdapter(this@HomeActivity)
            vpgHome.setCurrentItem(currentFragment, false)
            handleSelectBottom(currentFragment)


            vpgHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handleSelectBottom(position)
                }
            })
        }
    }

    private fun handleBottom() {
        binding.apply {
            bottomList.forEachIndexed { bottomIndex, bottom ->
                bottom.setOnSingleClick {
                    if (currentFragment == bottomIndex) {
                        return@setOnSingleClick
                    }
                    currentFragment = bottomIndex
                    handleSelectBottom(currentFragment)
                }
            }
        }
    }

    private fun handleSelectBottom(bottomIndex: Int) {
        for (i in 0 until bottomList.size) {
            if (bottomIndex == i) {
                binding.vpgHome.setCurrentItem(bottomIndex, false)
                imageBottomList[i].setImageResource(DataLocal.imageBottomSelectedList[i])
                textBottomList[i].gone()
                textGradientBottomList[i].visible()
            } else {
                imageBottomList[i].setImageResource(DataLocal.imageBottomNotSelectedList[i])
                textBottomList[i].visible()
                textGradientBottomList[i].gone()
            }
        }
    }

    private fun handleSettings() {
        slideInFromLeft(binding.layoutSettingsShow, binding.layoutSettings)
    }

    override fun onResume() {
        super.onResume()
        hideNavigation()
    }

    private fun rateApp() {
        val dialogRate = RateDialog(this)
        SystemUtils.setLocale(this)
        dialogRate.show()

        dialogRate.onRateLess3 = {
            binding.btnRate.gone()
            SystemUtils.setIsRate(this, true)
            Toast.makeText(this, R.string.have_rated, Toast.LENGTH_SHORT).show()
            dialogRate.dismiss()
            hideNavigation()
        }
        dialogRate.onRateGreater3 = {
            binding.btnRate.gone()
            SystemUtils.setIsRate(this, true)
            SystemUtils.reviewApp(this, false)
            Toast.makeText(this, R.string.have_rated, Toast.LENGTH_SHORT).show()
            dialogRate.dismiss()
            hideNavigation()
        }

        dialogRate.onCancel = {
            dialogRate.dismiss()
            hideNavigation()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (!isFragmentOther) {
            if (!SystemUtils.getIsRate(this) && SystemUtils.getCountBack(this) % 2 == 0) {
                val dialogRate = RateDialog(this)
                SystemUtils.setLocale(this)
                dialogRate.show()
                dialogRate.apply {
                    onRateLess3 = {
                        SystemUtils.setIsRate(this@HomeActivity, true)
                        Toast.makeText(this@HomeActivity, R.string.have_rated, Toast.LENGTH_SHORT).show()
                        val handler = Handler()
                        handler.postDelayed({
                            dialogRate.dismiss()
                            exitProcess(0)
                        }, 1000)
                    }
                    onRateGreater3 = {
                        SystemUtils.setIsRate(this@HomeActivity, true)
                        SystemUtils.reviewApp(this@HomeActivity, true)
                    }

                    onCancel = {
                        dialogRate.dismiss()
                        exitProcess(0)
                    }
                }
            } else {
                exitProcess(0)
            }
        } else {
            backFragmentSlideInFromLeft(binding.containerFragment) {
                supportFragmentManager.popBackStack()
            }
            isFragmentOther = false
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (!isLoadFileSuccess) {
            checkPermissionToInit()
        }
    }

    override fun dataObservable() {
        super.dataObservable()

    }

    private fun handleSearch() {
        val fragment = SearchFragment()
        startFragmentSlideInFromRight(binding.containerFragment)

        fragment.let {
            supportFragmentManager.beginTransaction().replace(binding.containerFragment.id, fragment)
                .addToBackStack(null).commit()
        }
        isFragmentOther = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                startIntentFromLeft(GalleryActivity::class.java)
            } else {
                SystemUtils.setStoragePermission(this, (getStoragePermission(this) + 1))
            }
        } else if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setCameraPermission(this, 0)
            } else {
                setCameraPermission(this, (SystemUtils.getCameraPermission(this) + 1))
            }
        }
    }


}