package com.example.st109_pdf_reader.ui.create.gallery

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseActivity
import com.example.st109_pdf_reader.core.extensions.*
import com.example.st109_pdf_reader.core.utils.DataLocal
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.data.model.create.*
import com.example.st109_pdf_reader.databinding.ActivityGalleryBinding
import com.example.st109_pdf_reader.ui.create.createpdf.CreatePDFActivity

class GalleryActivity : BaseActivity<ActivityGalleryBinding>() {

    private val adapterAllImage by lazy { AllImageAdapter(this) }
    private val adapterImageSub by lazy { ImageSubAdapter(this) }
    private val adapterImageSelected by lazy { ImageSelectedAdapter(this) }

    private val dataAllImage = arrayListOf<AllImageModel>()
    private val dataSelected = arrayListOf<ImageSelectedModel>()

    private var isOpenMore = false
    private var quantitySelected = 0
    private var positionAllImage = 0
    private var isAllowPermission = false
    private var isReplace = false

    override fun setViewBinding() = ActivityGalleryBinding.inflate(LayoutInflater.from(this))

    override fun initView() {
        checkPermission()
    }

    override fun viewListener() {
        binding.apply {
            btnBack.setOnSingleClick { handleBackLeftToRight() }
            txtFolder.setOnClickListener { toggleMoreFolders() }
            btnMore.setOnClickListener { toggleMoreFolders() }
            btnClearAll.setOnSingleClick { clearAllSelected() }
            btnDone.setOnSingleClick { goToCreatePDF() }
        }
        setupRecyclerViewListeners()
    }

    override fun initText() {}

    override fun initActionBar() {}

    private fun checkPermission() {
        val isTiramisuOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        val isBelowR = Build.VERSION.SDK_INT < Build.VERSION_CODES.R

        if (isTiramisuOrAbove || isBelowR) {
            if (!checkPermissions(SystemUtils.storagePermission)) {
                when {
                    SystemUtils.getStoragePermission(this) >= 2 -> goToSettings()
                    else -> requestPermission(
                        SystemUtils.storagePermission, KeyApp.RequestCode.STORAGE_PERMISSION_CODE
                    )
                }
            } else initData()
        } else {
            initData()
        }
    }

    private fun initData() {
        isAllowPermission = true
        dataAllImage.clear()
        dataAllImage.addAll(DataLocal.getAllImageFoldersWithImages(this))

        if (dataAllImage.isEmpty()) {
            binding.txtNoItem.visible()
        } else {
            initRecyclerViews()
        }
        isReplace = intent.getStringExtra(KeyApp.KeyIntent.INTENT_KEY) != null
    }

    private fun initRecyclerViews() = binding.apply {
        rcvAllImage.adapter = adapterAllImage.also { it.submitList(dataAllImage) }
        rcvImageSub.adapter = adapterImageSub.also { it.submitList(dataAllImage[0].listImage) }
        rcvSelected.adapter = adapterImageSelected.also { it.submitList(dataSelected) }

        rcvAllImage.itemAnimator = null
        rcvImageSub.itemAnimator = null
        rcvSelected.itemAnimator = null

        txtFolder.text = dataAllImage[0].nameFolder
    }

    private fun setupRecyclerViewListeners() = binding.apply {
        adapterAllImage.onItemClick = { folder, pos -> onFolderSelected(folder, pos) }
        adapterImageSub.onItemClick = { img, pos -> onImageSelected(img, pos) }
        adapterImageSelected.onDeleteClick = { imgSel -> onSelectedImageDeleted(imgSel) }
    }

    private fun onFolderSelected(folder: AllImageModel, position: Int) = binding.apply {
        adapterImageSub.submitList(folder.listImage)
        layoutAllImage.gone()
        btnMore.rotation = 180f
        txtFolder.text = folder.nameFolder
        positionAllImage = position
    }

    private fun onImageSelected(image: ImageModel, position: Int) = binding.apply {
        if (!isReplace) {
            adapterImageSub.submitItem(position, true)
            dataSelected.add(ImageSelectedModel(image.image, positionAllImage, position))
            adapterImageSelected.submitList(dataSelected)

            quantitySelected++
            txtQuantitySelected.text = getString(R.string.count_item_selected, quantitySelected)

            layoutImageSelected.visible()
            btnDone.visible()
            viewShadow.visible()
        } else {
            val resultIntent = Intent()
            resultIntent.putExtra(KeyApp.KeyIntent.INTENT_KEY, image.image)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun onSelectedImageDeleted(image: ImageSelectedModel) = binding.apply {
        dataSelected.remove(image)

        val stillSelected = dataSelected.any {
            it.positionAllImage == image.positionAllImage && it.positionImageSub == image.positionImageSub
        }

        if (!stillSelected) {
            val list = dataAllImage.getOrNull(image.positionAllImage)?.listImage
            if (list != null && image.positionImageSub in list.indices) {
                list[image.positionImageSub].isSelected = false
                adapterImageSub.submitItem(image.positionImageSub, false)
            } else {
                eLog("Invalid position: ${image.positionAllImage}, ${image.positionImageSub}")
            }
        }

        adapterImageSelected.submitList(dataSelected)
        quantitySelected--
        txtQuantitySelected.text = getString(R.string.count_item_selected, quantitySelected)

        if (dataSelected.isEmpty()) {
            layoutImageSelected.gone()
            viewShadow.gone()
            btnDone.invisible()
        }
    }

    private fun clearAllSelected() = binding.apply {
        dataSelected.clear()
        dataAllImage.clear()
        dataAllImage.addAll(DataLocal.getAllImageFoldersWithImages(this@GalleryActivity))

        adapterAllImage.submitList(dataAllImage)
        adapterImageSub.submitList(dataAllImage[positionAllImage].listImage)
        adapterImageSelected.submitList(dataSelected)

        quantitySelected = 0
        txtQuantitySelected.text = quantitySelected.toString()

        layoutImageSelected.gone()
        viewShadow.gone()
        btnDone.invisible()
    }

    private fun toggleMoreFolders() = binding.apply {
        layoutAllImage.apply { if (isOpenMore) gone() else visible() }
        btnMore.rotation = if (isOpenMore) 180f else 0f
        isOpenMore = !isOpenMore
    }

    private fun goToCreatePDF() {
        val selectedPaths = dataSelected.map { it.image }.also {
            it.forEach { path -> dLog("path: $path") }
        }

        val intent = Intent(this, CreatePDFActivity::class.java).apply {
            putExtra(KeyApp.KeyIntent.INTENT_KEY, ArrayList(selectedPaths))
        }

        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onRestart() {
        super.onRestart()
        if (!isAllowPermission && checkPermissions(SystemUtils.storagePermission)) {
            initData()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == KeyApp.RequestCode.STORAGE_PERMISSION_CODE) {
            val granted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (granted) {
                initData()
            } else {
                SystemUtils.setStoragePermission(this, SystemUtils.getStoragePermission(this) + 1)

                if (!checkPermissions(SystemUtils.storagePermission)) {
                    if (SystemUtils.getStoragePermission(this) < 2) {
                        requestPermission(
                            SystemUtils.storagePermission,
                            KeyApp.RequestCode.STORAGE_PERMISSION_CODE
                        )
                    } else {
                        isAllowPermission = false
                        goToSettings()
                    }
                }
            }
        }
    }
}
