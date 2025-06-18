package com.example.st109_pdf_reader.ui.create.createpdf

import android.R.attr.bitmap
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseActivity
import com.example.st109_pdf_reader.core.extensions.createBimapFromView
import com.example.st109_pdf_reader.core.extensions.dLog
import com.example.st109_pdf_reader.core.extensions.eLog
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.saveBitmapToInternalStorage
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.core.utils.KeyApp.DOWNLOAD_ALBUM
import com.example.st109_pdf_reader.data.model.create.CreatePDFModel
import com.example.st109_pdf_reader.databinding.ActivityCreatePdfBinding
import com.example.st109_pdf_reader.ui.create.gallery.GalleryActivity
import java.io.File
import java.util.UUID
import kotlin.jvm.java
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class CreatePDFActivity : BaseActivity<ActivityCreatePdfBinding>() {
    private val imageAdapter by lazy {
        CreatePDFAdapter(this)
    }
    private val imageList = ArrayList<CreatePDFModel>()

    private var pathSelected = ""

    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        0
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            imageAdapter.moveItem(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            imageAdapter.onDragEnd?.invoke(imageAdapter.getCurrentList())
        }

    }

    private val replaceImageSelected =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val newPath = data?.getStringExtra(KeyApp.KeyIntent.INTENT_KEY)
                val positionSelected = imageList.indexOfFirst { it.isSelected == true }
                imageList[positionSelected].path = newPath!!
                submitAdapter()
            }
        }

    override fun setViewBinding(): ActivityCreatePdfBinding {
        return ActivityCreatePdfBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        initData()
    }

    override fun viewListener() {
        binding.apply {
            btnReplace.setOnSingleClick { handleReplace() }
            btnCutout.setOnSingleClick { handleCutout() }
        }
        handleRcv()
    }

    override fun initText() {

    }

    override fun initActionBar() {
        binding.actionBar.apply {
            btnActionBarLeft.setImageResource(R.drawable.ic_back_black)
            btnActionBarLeft.visible()
            tvCenter.text = getString(R.string.create_pdf)
            tvCenter.visible()
            btnActionBarRight.setImageResource(R.drawable.ic_done)
            btnActionBarRight.visible()
        }
    }

    private fun initData() {
        val getListPathImage: ArrayList<String>? =
            intent.getStringArrayListExtra(KeyApp.KeyIntent.INTENT_KEY)
        getListPathImage?.let {
            it.forEach { path ->
                imageList.add(CreatePDFModel(path))
            }
            imageList.first().isSelected = true
            pathSelected = imageList.first().path
            initRcv()
        }
    }

    private fun initRcv() {
        binding.rcvImage.apply {
            adapter = imageAdapter
            itemAnimator = null
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rcvImage)

        submitAdapter()
    }

    private fun submitAdapter() {
        imageAdapter.submitList(imageList)
    }

    private fun handleRcv() {
        imageAdapter.onItemClick = { create, position ->
            imageList.forEach { it.isSelected = false }
            imageList[position].isSelected = true
            pathSelected = create.path
            submitAdapter()
        }
        imageAdapter.onDragEnd = { newList ->
            imageList.clear()
            imageList.addAll(newList)
            submitAdapter()
        }
    }

    private fun handleReplace() {
        val intent = Intent(this, GalleryActivity::class.java).apply {
            putExtra(KeyApp.KeyIntent.INTENT_KEY, KeyApp.KeyIntent.INTENT_KEY)
        }
        replaceImageSelected.launch(intent)
    }

    private fun handleCutout() {
        val sourceUri: Uri = Uri.fromFile(File(imageList.first { it.isSelected == true }.path))
        val destinationUri = StringBuilder(UUID.randomUUID().toString()).append(".png").toString()

        val options = UCrop.Options().apply {
            setToolbarColor(ContextCompat.getColor(this@CreatePDFActivity, R.color.transparent))
            setStatusBarColor(ContextCompat.getColor(this@CreatePDFActivity, R.color.black))
            setHideBottomControls(false)    // Hiển thị thanh công cụ chỉnh tỷ lệ
            setFreeStyleCropEnabled(true)   // Cho phép người dùng chỉnh khung cắt tự do
            setCropGridColor(getColor(R.color.gray_CA))
            setCropFrameColor(getColor(R.color.red_end))
            setCropGridStrokeWidth(1)
            setCropFrameStrokeWidth(5)
            setLogoColor(ContextCompat.getColor(this@CreatePDFActivity, R.color.red_end))
            setActiveControlsWidgetColor(
                ContextCompat.getColor(
                    this@CreatePDFActivity, R.color.red_end
                )
            )
        }

        UCrop.of(sourceUri, Uri.fromFile(File(cacheDir, destinationUri)))
            .withAspectRatio(9f, 16f)                // Cắt theo tỷ lệ 1:1
            .withMaxResultSize(1000, 1000)   // Kích thước tối đa
            .withOptions(options).start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            if (resultUri != null) {
                val inputStream = contentResolver.openInputStream(resultUri)
                val originalBitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
                handleSaveToInternal(originalBitmap)

            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            eLog("cropError: $cropError")
            cropError?.printStackTrace()
        }
    }

    private fun handleSaveToInternal(bitmap: Bitmap) {
        var pathInternal = ""
        loadingDialog.show()
        val handleExceptionCoroutine = CoroutineExceptionHandler { _, throwable ->
            eLog("e: ${throwable.message}")
            loadingDialog.dismiss()
            hideNavigation(true)
        }
        CoroutineScope(SupervisorJob() + Dispatchers.IO + handleExceptionCoroutine).launch {
            val job1 = async {
                pathInternal = saveBitmapToInternalStorage(
                    DOWNLOAD_ALBUM, bitmap
                ).toString()
                return@async true
            }

            launch(Dispatchers.Main) {
                if (job1.await()) {
                    imageList.first { it.isSelected == true }.path = pathInternal
                    submitAdapter()
                    loadingDialog.dismiss()
                    hideNavigation(true)
                }
            }
        }

    }
}