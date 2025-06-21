package com.example.st109_pdf_reader.ui.pdf

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.artifex.mupdfdemo.Annotation
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
import com.artifex.mupdfdemo.MuPDFView
import com.artifex.mupdfdemo.SearchTask
import com.artifex.mupdfdemo.SearchTaskResult
import com.example.st109_pdf_reader.core.extensions.createBimapFromView
import com.example.st109_pdf_reader.core.extensions.dLog
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.showToast
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Locale

class PdfActivity : BaseActivity<ActivityPdfBinding>(), FilePicker.FilePickerSupport, TextToSpeech.OnInitListener {

    private var core: MuPDFCore? = null
    private var mDocView: MuPDFReaderView? = null
    private var mSearchTask: SearchTask? = null

    enum class AcceptMode {
        Highlight, Underline, StrikeOut, Draw, CopyText, Eraser
    }

    private lateinit var textToSpeech: TextToSpeech
    private var textList: List<String> = listOf()
    private var currentIndex = 0
    private var isPaused = false
    private var isCreate = false
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
                btnActionBarRight.setOnSingleClick { onSaveOption(AcceptMode.Draw) }
            }
            btnSpeech.setOnSingleClick {
                handleSpeech()
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
        isCreate = intent.getBooleanExtra(KeyApp.KeyIntent.CREATE_KEY, false)

        if (isCreate) {
            binding.btnSpeech.setImageResource(R.drawable.ic_edit)
            binding.actionBar.btnActionBarRight.setImageResource(R.drawable.ic_save_create)
        }

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
            textToSpeech = TextToSpeech(this, this)

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

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val locale = when (SystemUtils.getPreLanguage(this)) {
                "hi" -> Locale("hi", "IN")
                "es" -> Locale("es", "ES")
                "fr" -> Locale.FRANCE
                "en" -> Locale.ENGLISH
                "pt" -> Locale("pt", "PT")
                "id", "in" -> Locale("id", "ID")
                "de" -> Locale.GERMANY
                else -> Locale.ENGLISH
            }
            val result = textToSpeech.isLanguageAvailable(locale)
            if (result == TextToSpeech.LANG_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                textToSpeech.language = locale
            } else {
                textToSpeech.language = Locale.ENGLISH
            }

            textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) {
                    if (!isPaused) {
                        currentIndex++
                        speakNext()
                    }
                }

                override fun onError(utteranceId: String?) {}
            })

        }
    }

    private fun handleSpeech() {
        if (!isCreate) {
            CoroutineScope(Job() + Dispatchers.IO).launch {
                var bitmap: Bitmap? = null
                val job1 = async {
                    bitmap = createBimapFromView(binding.rlViewPdf)
                    return@async true
                }
                launch(Dispatchers.Main) {
                    if (job1.await()) {
                        recognizeTextFromBitmap(bitmap!!) { text ->
                            dLog("text: $text")
                            speakText(text)
                        }
                    }
                }
            }
        } else {
            handleEdit()
        }
    }

    fun speakText(fullText: String) {
        textList = fullText.split(Regex("(?<=[.!?])\\s+"))
        currentIndex = 0
        isPaused = false
        speakNext()
    }

    private fun speakNext() {
        if (currentIndex < textList.size) {
            val text = textList[currentIndex]
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, currentIndex.toString())
        }
    }

    fun recognizeTextFromBitmap(bitmap: Bitmap, callback: (String) -> Unit) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image).addOnSuccessListener { visionText ->
            // Gọi callback với kết quả văn bản
            callback(visionText.text)
        }.addOnFailureListener { e ->
            e.printStackTrace()
            callback("Lỗi: ${e.message}")
        }
    }

    fun pauseTTS() {
        isPaused = true
        textToSpeech.stop()
    }

    fun resumeTTS() {
        isPaused = false
        speakNext()
    }

    private fun handleEdit() {
        mDocView?.setMode(MuPDFReaderView.Mode.Drawing)
    }

    private fun onSaveOption(mode: AcceptMode) {
        mDocView?.let {
            val muPDFView = it.displayedView as MuPDFView
            val isSuccess: Boolean

            when (mode) {
                AcceptMode.Highlight -> {
                    isSuccess = muPDFView.markupSelection(com.artifex.mupdfdemo.Annotation.Type.HIGHLIGHT)
                    if (!isSuccess) {
                        showToast(getString(R.string.please_select_text))
                    }
                }

                AcceptMode.Underline -> {
                    isSuccess = muPDFView.markupSelection(com.artifex.mupdfdemo.Annotation.Type.UNDERLINE)
                    if (!isSuccess) {
                        showToast(getString(R.string.please_select_text))
                    }
                }

                AcceptMode.StrikeOut -> {
                    isSuccess = muPDFView.markupSelection(Annotation.Type.STRIKEOUT)
                    if (!isSuccess) {
                        showToast(getString(R.string.please_select_text))
                    }
                }

                AcceptMode.Draw -> {
                    isSuccess = muPDFView.saveDraw()
                    if (!isSuccess) {
                        showToast(getString(R.string.please_draw_anything))
                    } else {
//                        viewmodel.setStateUseDraw()
                        mDocView?.setMode(MuPDFReaderView.Mode.Viewing)
                    }
                }

                AcceptMode.CopyText -> {
                    isSuccess = muPDFView.copySelection()
                    if (isSuccess) {
                        showToast(getString(R.string.copied_to_clipboard))
                    } else {
                        showToast(getString(R.string.please_select_text))
                    }
                }

                AcceptMode.Eraser -> {
                    isSuccess = true
                    muPDFView.deleteSelectedAnnotation()
                }

            }

            if (isSuccess) it.refresh(false)
        } ?: showToast("Cannot edit files")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }

    override fun onPause() {
        super.onPause()
        pauseTTS()
    }

    override fun onRestart() {
        super.onRestart()
        resumeTTS()
    }
}