package com.example.st109_pdf_reader.ui.pdf

import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseActivity
import com.example.st109_pdf_reader.core.extensions.eLog
import com.example.st109_pdf_reader.core.extensions.handleBackLeftToRight
import com.example.st109_pdf_reader.core.extensions.select
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.databinding.ActivityPdfBinding
import com.artifex.mupdfdemo.FilePicker
import com.artifex.mupdfdemo.Hit
import com.artifex.mupdfdemo.MuPDFCore
import com.artifex.mupdfdemo.MuPDFPageAdapter
import com.artifex.mupdfdemo.MuPDFReaderView
import com.artifex.mupdfdemo.SearchTask
import com.artifex.mupdfdemo.SearchTaskResult
import com.example.st109_pdf_reader.core.extensions.hideNavigation

class PdfActivity : BaseActivity<ActivityPdfBinding>(), FilePicker.FilePickerSupport {

    //    private val viewmodel: EditViewModel by viewModels()
    private var core: MuPDFCore? = null
    private var mDocView: MuPDFReaderView? = null
    private var mSearchTask: SearchTask? = null

    enum class AcceptMode {
        Highlight,
        Underline,
        StrikeOut,
        Draw,
        CopyText,
        Eraser,
    }

    override fun setViewBinding(): ActivityPdfBinding {
        return ActivityPdfBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        initData()
    }

    override fun viewListener() {
        binding.apply {
            actionBar.apply {
                btnActionBarLeft.setOnSingleClick { handleBackLeftToRight() }
            }
        }
    }

    override fun initText() {
        binding.actionBar.tvCenter.select()
    }

    override fun initActionBar() {
        binding.actionBar.apply {
            btnActionBarLeft.setImageResource(R.drawable.ic_back_white)
            btnActionBarLeft.visible()
            tvCenter.visible()
            tvCenter.setTextColor(getColor(R.color.white))
            btnActionBarRight.setImageResource(R.drawable.ic_more_white)
            btnActionBarRight.visible()
            layoutHeader.setBackgroundResource(R.color.pdf)
        }
    }

    override fun performPickFor(picker: FilePicker?) {

    }

    private fun initData() {
        val file = intent.getParcelableExtra<FilesModel>(KeyApp.KeyIntent.INTENT_KEY)
        try {
            binding.actionBar.tvCenter.text = file!!.name
            core = openFileX(file.path)
            val muPDFCore = core
            if (muPDFCore == null || !muPDFCore.needsPassword()) {
                val muPDFCore2 = core
                if (muPDFCore2 != null && muPDFCore2.countPages() == 0) {
                    core = null
                }
            }
            createUI()
            core?.canProof()
        } catch (e: Exception) {
            eLog("initData: ${e.message}")
        }
    }

    private fun openFileX(str: String): MuPDFCore? {
        val muPDFCore = MuPDFCore(this, str)
        this.core = muPDFCore
        return muPDFCore
    }

    @SuppressLint("SetTextI18n")
    private fun createUI() {
        if (core != null) {
//            binding.tvCount.text = "1 " + getString(R.string.of) + " ${core!!.countPages()}"

            val r0: MuPDFReaderView = object : MuPDFReaderView(this) {
                override fun onDocMotion() {
                }

                override fun onMoveToChild(i: Int) {
                    if (core != null) {
                        val i2 = i + 1
//                        binding.tvCount.text = "$i2 " + getString(R.string.of) + " ${core!!.countPages()}"
                        super.onMoveToChild(i)
                    }
                }

                override fun onTapMainDocArea() {

                }

                override fun onHit(hit: Hit?) {

                }
            }

            mDocView = r0
            r0.adapter = MuPDFPageAdapter(this, this, core)

            mSearchTask = object : SearchTask(this, core) {
                public override fun onTextFound(searchTaskResult: SearchTaskResult) {
                    SearchTaskResult.set(searchTaskResult)
                    mDocView?.displayedViewIndex = searchTaskResult.pageNumber
                    mDocView?.resetupChildren()
                }
            }

            MuPDFCore.gprfSupported()

            binding.rlViewPdf.addView(mDocView)
            mDocView?.setMode(MuPDFReaderView.Mode.Viewing)

//            binding.tvCount.tap {
//                mDocView?.refresh(false)
//            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideNavigation()
    }
}