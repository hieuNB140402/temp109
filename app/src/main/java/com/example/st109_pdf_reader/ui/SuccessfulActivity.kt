package com.example.st109_pdf_reader.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseActivity
import com.example.st109_pdf_reader.core.extensions.handleBackLeftToRight
import com.example.st109_pdf_reader.core.extensions.handleComeBackHome
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.shareFile
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.data.local.AppDatabase
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.data.local.repository.FileRepository
import com.example.st109_pdf_reader.databinding.ActivitySuccessfulBinding
import com.example.st109_pdf_reader.ui.home.viewmodel.FileViewModel
import com.example.st109_pdf_reader.ui.home.viewmodel.FileViewModelFactory
import com.example.st109_pdf_reader.ui.pdf.PdfActivity
import java.io.File

class SuccessfulActivity : BaseActivity<ActivitySuccessfulBinding>() {
    private lateinit var file: FilesModel
    lateinit var fileViewModel: FileViewModel
    override fun setViewBinding(): ActivitySuccessfulBinding {
        return ActivitySuccessfulBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        val dao = AppDatabase.getInstance(this).fileDao()
        val repository = FileRepository(dao)
        fileViewModel = ViewModelProvider(this, FileViewModelFactory(repository))[FileViewModel::class.java]
        initData()
    }

    override fun viewListener() {
        binding.apply {
            actionBar.apply {
                btnActionBarLeft.setOnSingleClick { handleBackLeftToRight() }
                btnActionBarRight.setOnSingleClick { handleComeBackHome(this@SuccessfulActivity) }
            }
            btnShare.setOnSingleClick { shareFile(file.path) }
            btnView.setOnSingleClick { handleView() }
        }
    }

    override fun initText() {

    }

    override fun initActionBar() {
        binding.actionBar.apply {
            btnActionBarLeft.setImageResource(R.drawable.ic_back_white)
            btnActionBarLeft.visible()
            tvCenter.text = getString(R.string.successful)
            tvCenter.setTextColor(getColor(R.color.white))
            tvCenter.visible()
            btnActionBarRight.setImageResource(R.drawable.ic_home_white)
            btnActionBarRight.visible()
            layoutHeader.setBackgroundResource(R.color.pdf)
        }
    }

    private fun initData(){
        file = intent.getParcelableExtra<FilesModel>(KeyApp.KeyIntent.INTENT_KEY)!!
        val bit = renderFirstPageFromPdf(file.path)
        Glide.with(this).load(bit).into(binding.imvImage)
        val name = file.name + ".pdf"
        binding.tvName.text = name
        binding.tvPath.text = getString(R.string.path_success, file.path)
    }
    fun renderFirstPageFromPdf(filePath: String): Bitmap? {
        val file = File(filePath)
        val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        val pdfRenderer = PdfRenderer(fileDescriptor)
        val page = pdfRenderer.openPage(0) // Trang đầu

        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close()
        pdfRenderer.close()
        fileDescriptor.close()

        return bitmap
    }

    private fun handleView(){
        val intent = Intent(this, PdfActivity::class.java)
        fileViewModel.updateRecentFile(file.id, true)
        intent.putExtra(KeyApp.KeyIntent.INTENT_KEY, file)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        hideNavigation()
    }
}