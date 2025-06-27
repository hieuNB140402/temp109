package com.example.st109_pdf_reader.ui.pdf

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HeaderFooter.file
import com.example.st109_pdf_reader.core.dialog.ConfirmDialog
import com.example.st109_pdf_reader.core.dialog.RenameDialog
import com.example.st109_pdf_reader.core.extensions.copyFileInternalToExternal
import com.example.st109_pdf_reader.core.extensions.copyFileToExternal
import com.example.st109_pdf_reader.core.extensions.createBimapFromView
import com.example.st109_pdf_reader.core.extensions.dLog
import com.example.st109_pdf_reader.core.extensions.dpToPx
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.handleDeleteFile
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.invisible
import com.example.st109_pdf_reader.core.extensions.pxToDpInt
import com.example.st109_pdf_reader.core.extensions.recognizeTextFromBitmap
import com.example.st109_pdf_reader.core.extensions.renameFileByPath
import com.example.st109_pdf_reader.core.extensions.shareFile
import com.example.st109_pdf_reader.core.extensions.showToast
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.data.local.AppDatabase
import com.example.st109_pdf_reader.data.local.repository.FileRepository
import com.example.st109_pdf_reader.databinding.PopupEditBinding
import com.example.st109_pdf_reader.databinding.PopupReaderBinding
import com.example.st109_pdf_reader.ui.SuccessfulActivity
import com.example.st109_pdf_reader.ui.home.viewmodel.FileViewModel
import com.example.st109_pdf_reader.ui.home.viewmodel.FileViewModelFactory
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class PdfActivity : BaseActivity<ActivityPdfBinding>(), FilePicker.FilePickerSupport, TextToSpeech.OnInitListener {

    private var core: MuPDFCore? = null
    private var mDocView: MuPDFReaderView? = null
    private var mSearchTask: SearchTask? = null

    enum class AcceptMode {
        Highlight, Underline, StrikeOut, Draw, CopyText, Eraser, View
    }

    lateinit var fileViewModel: FileViewModel
    private lateinit var textToSpeech: TextToSpeech
    private var textList: List<String> = listOf()
    private var currentIndex = 0
    private var isPaused = false
    private lateinit var file: FilesModel
    private var isCreate = false
    private var isHighlight = false
    private var isDraw = false
    private var isEdit = false

    override fun setViewBinding(): ActivityPdfBinding {
        return ActivityPdfBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        initData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun viewListener() {
        binding.apply {
            actionBar.apply {
                btnActionBarLeft.setOnSingleClick { handleBack() }
                btnActionBarRight.setOnSingleClick { handleTopRight(it) }
            }
            btnSpeech.setOnSingleClick {
                handleSpeech()
            }
            btnEdit.setOnSingleClick { handlePopupBottom(it) }
            btnHighlight.setOnSingleClick { onSaveOption(AcceptMode.Highlight) }
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
            btnActionBarRight.visible()
            layoutHeader.setBackgroundResource(R.color.pdf)
        }
    }

    override fun performPickFor(picker: FilePicker?) {

    }

    private fun initData() {
        val dao = AppDatabase.getInstance(this).fileDao()
        val repository = FileRepository(dao)
        fileViewModel = ViewModelProvider(this, FileViewModelFactory(repository))[FileViewModel::class.java]

        val getFile = intent.getParcelableExtra<FilesModel>(KeyApp.KeyIntent.INTENT_KEY)
        dLog("getFile: ${getFile}")
        file = getFile!!

        isCreate = intent.getBooleanExtra(KeyApp.KeyIntent.CREATE_KEY, false)
        dLog("isCreate: ${isCreate}")
        if (isCreate) {
            binding.apply {
                actionBar.btnActionBarRight.setImageResource(R.drawable.ic_save_create)
                btnSpeech.gone()
            }
        } else {
            binding.apply {
                actionBar.btnActionBarRight.setImageResource(R.drawable.ic_more_white)
            }
        }

        try {
            binding.actionBar.tvCenter.text = file.name
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


    fun pauseTTS() {
        isPaused = true
        textToSpeech.stop()
    }

    fun resumeTTS() {
        isPaused = false
        speakNext()
    }

    private fun handlePopupBottom(view: View) {
        val popupBinding = PopupEditBinding.inflate(LayoutInflater.from(this))
        val popupWindow = PopupWindow(
            popupBinding.root, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true
        ).apply {
            elevation = 10f
            isFocusable = true
            isOutsideTouchable = true
        }

        popupBinding.apply {
            tvHighLight.select()
            tvDraw.select()
            btnHighLight.setOnSingleClick {
                handleHighLight()
                popupWindow.dismiss()
            }
            btnDraw.setOnSingleClick {
                handleDraw()
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
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        val finalX = screenWidth - popupWidth - marginPx
        val finalY = y - popupHeight - marginPx

        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, finalX, finalY)
    }

    private fun handleHighLight(isClose: Boolean = false) {
        binding.apply {
            if (!isClose) {
                actionBar.tvCenter.text = getString(R.string.highlight)
                actionBar.btnActionBarRight.invisible()
                btnEdit.gone()
                btnSpeech.gone()
                btnHighlight.visible()
                isHighlight = true
                pauseTTS()
                lifecycleScope.launch {
                    binding.tvHelpHighlight.visible()
                    delay(5000)
                    binding.tvHelpHighlight.gone()
                }
            } else {
                onSaveOption(AcceptMode.View)
            }
        }
    }

    private fun handleBack() {
        when {
            isHighlight -> {
                handleHighLight(isClose = true)
            }

            isDraw -> {
                onCancelDraw()
            }

            else -> {
                handleBackLeftToRight()
            }
        }
    }

    private fun handleDraw(isClose: Boolean = false) {
        if (!isClose) {
            pauseTTS()
            isDraw = true
            binding.apply {
                actionBar.btnActionBarRight.setImageResource(R.drawable.ic_done_white)
                btnEdit.gone()
                btnSpeech.gone()
            }
            mDocView?.setMode(MuPDFReaderView.Mode.Drawing)
        } else {
            isDraw = false
            isEdit = true
            binding.apply {
                actionBar.btnActionBarRight.setImageResource(R.drawable.ic_save_create)
                btnEdit.visible()
                if (!isCreate) {
                    btnSpeech.visible()
                }
            }
        }
    }

    private fun onCancelDraw() {
        mDocView?.let {
            val muPDFView = it.displayedView as MuPDFView
            muPDFView.deselectText()
            muPDFView.cancelDraw()
            mDocView?.setMode(MuPDFReaderView.Mode.Viewing)
        }
        handleDraw(isClose = true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleTopRight(view: View) {
        pauseTTS()
        when {
            isDraw -> {
                onSaveOption(AcceptMode.Draw)
            }

            isCreate -> {
                handleSetName()
            }

            !isEdit -> {
                handlePopupTop(view)
            }

            isEdit && !isCreate -> {
                dLog("in")
                val muPDFCore: MuPDFCore? = core
                if (muPDFCore != null && muPDFCore.hasChanges()) {
                    lifecycleScope.launch {
                        core?.save()
                        dLog("Success")
                    }
                }
            }

            else -> {}
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleSetName() {
        val dialog = RenameDialog(this, "", KeyApp.SAVE_FILE)
        SystemUtils.setLocale(this)

        dialog.apply {
            show()
            onNoClick = {
                dismiss()
                hideNavigation()
            }
            onYesClick = { name ->
                CoroutineScope(Job() + Dispatchers.IO).launch {
                    showLoading()
                    core?.save()
                    val nameSplitOld = file.path.split(".")
                    val extensionOld = nameSplitOld.last()
                    val oldFile = File(filesDir, "${KeyApp.FOLDER_CREATE_PDF}/${file.name}.${extensionOld}")
                    dLog("oldFile: ${oldFile}")
                    if (oldFile.exists()) {
                        val nameSplit = file.path.split(".")
                        val extension = nameSplit.last()
                        dLog("extension: ${extension}")
                        val cleanName = if (name.endsWith(".$extension")) name else "$name.$extension"
                        dLog("cleanName: ${cleanName}")
                        val newFileInternal = File(filesDir, "${KeyApp.FOLDER_CREATE_PDF}/${cleanName}")
                        val newFileExternal = File(getExternalFilesDir(null), cleanName)
                        dLog("newFileExternal: ${newFileExternal}")
                        if (!newFileExternal.exists()) {
                            val renamed = oldFile.renameTo(newFileInternal)
                            dLog("renamed: ${renamed}")
                            if (renamed) {
                                dLog("Success")
                                var getPath = ""
                                var getStatus = false
                                copyFileToExternal(cleanName, status = { path, status ->
                                    getPath = path
                                    getStatus = status
                                })

                                dLog("getStatus: ${getStatus}")
                                dLog("getPath: ${getPath}")

                                withContext(Dispatchers.Main) {
                                    dismissLoading()
                                    if (getStatus) {
                                        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                                        val time = LocalTime.now()
                                            .format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))
                                        dLog("size: ${File(getPath).length()}")
                                        val fileModel = FilesModel(
                                            Random.nextInt(9999, 999999),
                                            KeyApp.PDF,
                                            name,
                                            getPath,
                                            File(getPath).length(),
                                            date,
                                            time,
                                            isSaved = true
                                        )
                                        fileViewModel.insertFileIfNotExists(fileModel) { onResult ->
                                            if (onResult) {
                                                file.name = name
                                                val newPathInternal = File(
                                                    filesDir,
                                                    "${KeyApp.FOLDER_CREATE_PDF}/${file.name}.${extensionOld}"
                                                )
                                                file.path = newPathInternal.toString()
                                                dLog("newPathInternal: ${newPathInternal}")
                                                dismiss()
                                                showToast(getString(R.string.save_success_to_my_pdf))
                                                val intent = Intent(this@PdfActivity, SuccessfulActivity::class.java)
                                                intent.putExtra(KeyApp.KeyIntent.INTENT_KEY, fileModel)
                                                startActivity(intent)
//                                                fileViewModel.refreshScan(this@PdfActivity)
                                            } else {
                                                showToast(getString(R.string.save_fail))
                                            }
                                        }

                                    } else {
                                        showToast(getString(R.string.save_fail))
                                    }
                                }

                            } else {
                                withContext(Dispatchers.Main) {
                                    dismissLoading()
                                    showToast(getString(R.string.save_fail))
                                }
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                dismissLoading()
                                showToast(getString(R.string.name_already_exists, name))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onSaveOption(mode: AcceptMode) {
        mDocView?.let {
            val muPDFView = it.displayedView as MuPDFView
            val isSuccess: Boolean

            when (mode) {
                AcceptMode.Highlight -> {
                    isSuccess = muPDFView.markupSelection(Annotation.Type.HIGHLIGHT)
                    if (!isSuccess) {
                        showToast(getString(R.string.please_select_text))
                    } else {
                        isEdit = true
                    }
                }

                AcceptMode.Underline -> {
                    isSuccess = muPDFView.markupSelection(Annotation.Type.UNDERLINE)
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
                        mDocView?.setMode(MuPDFReaderView.Mode.Viewing)
                        handleDraw(isClose = true)
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

                AcceptMode.View -> {
                    isSuccess = true
                    mDocView?.setMode(MuPDFReaderView.Mode.Viewing)
                    binding.apply {
                        actionBar.tvCenter.text = file.name
                        actionBar.btnActionBarRight.visible()
                        btnEdit.visible()
                        if (!isCreate) {
                            btnSpeech.visible()
                        }
                        btnHighlight.gone()
                        tvHelpHighlight.gone()
                        isHighlight = false
                    }
                }
            }

            if (isSuccess) it.refresh(false)
        } ?: showToast("Cannot edit files")
    }

    private fun handlePopupTop(view: View) {
        val popupBinding = PopupReaderBinding.inflate(LayoutInflater.from(this))
        val popupWindow = PopupWindow(
            popupBinding.root, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true
        )
        popupWindow.elevation = 10f
        popupBinding.btnOpenFile.gone()
        when (file.type) {
            KeyApp.WORD -> {
                popupBinding.layoutParent.setBackgroundResource(R.drawable.bg_10_word)
                popupBinding.imvRename.imageTintList = ColorStateList.valueOf(
                    getColor(
                        R.color.word
                    )
                )
                popupBinding.imvShare.imageTintList = ColorStateList.valueOf(
                    getColor(
                        R.color.word
                    )
                )
                popupBinding.imvDelete.imageTintList = ColorStateList.valueOf(
                    getColor(
                        R.color.word
                    )
                )
            }

            KeyApp.EXCEL -> {
                popupBinding.layoutParent.setBackgroundResource(R.drawable.bg_10_excel)
                popupBinding.imvOpenFile.imageTintList = ColorStateList.valueOf(
                    getColor(
                        R.color.excel
                    )
                )
                popupBinding.imvRename.imageTintList = ColorStateList.valueOf(
                    getColor(
                        R.color.excel
                    )
                )
                popupBinding.imvShare.imageTintList = ColorStateList.valueOf(
                    getColor(
                        R.color.excel
                    )
                )
                popupBinding.imvDelete.imageTintList = ColorStateList.valueOf(
                    getColor(
                        R.color.excel
                    )
                )
            }

            KeyApp.PPT -> {
                popupBinding.layoutParent.setBackgroundResource(R.drawable.bg_10_ppt)
                popupBinding.imvOpenFile.imageTintList = ColorStateList.valueOf(
                    getColor(
                        R.color.ppt
                    )
                )
                popupBinding.imvRename.imageTintList = ColorStateList.valueOf(
                    getColor(
                        R.color.ppt
                    )
                )
                popupBinding.imvShare.imageTintList = ColorStateList.valueOf(
                    getColor(
                        R.color.ppt
                    )
                )
                popupBinding.imvDelete.imageTintList = ColorStateList.valueOf(
                    getColor(
                        R.color.ppt
                    )
                )
            }
        }
        popupBinding.tvRename.select()
        popupBinding.tvShare.select()
        popupBinding.tvDelete.select()

        popupBinding.btnRename.setOnSingleClick {
            handleRename(popupWindow)
        }
        popupBinding.btnShare.setOnSingleClick {
            popupWindow.dismiss()
            shareFile(file.path)
        }
        popupBinding.btnDelete.setOnSingleClick {
            handleDelete(popupWindow)
        }

        val xOffset = dpToPx(-100)
        val yOffset = dpToPx(6)

        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val viewY = location[1]
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val distanceToBottom = screenHeight - viewY - view.height
        if (distanceToBottom >= dpToPx(180)) {
            popupWindow.showAsDropDown(view, xOffset, yOffset)
        } else {
            popupWindow.showAsDropDown(view, xOffset, dpToPx(-135))
        }
    }

    private fun handleRename(popupWindow: PopupWindow) {
        popupWindow.dismiss()
        val renameDialog = RenameDialog(this, file.name)
        SystemUtils.setLocale(this)
        renameDialog.show()
        renameDialog.onNoClick = {
            renameDialog.dismiss()
            hideNavigation()
        }
        renameDialog.onYesClick = { newName ->
            renameDialog.dismiss()
            val extensionArray = file.path.split(".")
            val extension = extensionArray[extensionArray.size - 1]
            val newNameWithExtension = "${newName}.${extension}"
            renameFileByPath(
                loadingDialog, fileViewModel, file.path, newNameWithExtension, onFinish = { status ->
                    when(status){
                        KeyApp.FILE_NOT_EXIST -> {
                            showToast(getString(R.string.file_not_exist))
                        }
                        KeyApp.FILE_NAME_EXIST -> {
                            showToast(getString(R.string.new_name_already_exists))
                        }
                        KeyApp.RENAME_SUCCESS -> {
                            binding.actionBar.tvCenter.text = newName
                        }
                        else -> {
                            showToast(getString(R.string.rename_failed_please_try_again))
                        }
                    }

                    lifecycleScope.launch {
                        dismissLoading()
                    }
                })
        }
    }

    private fun handleDelete(popupWindow: PopupWindow) {
        popupWindow.dismiss()
        val confirmDialog = ConfirmDialog(
            this, R.string.delete, R.string.do_you_want_to_delete_this_file
        )
        SystemUtils.setLocale(this)
        confirmDialog.show()
        confirmDialog.onNoClick = {
            confirmDialog.dismiss()
            hideNavigation()
        }
        confirmDialog.onYesClick = {
            confirmDialog.dismiss()
            if (!File(file.path).exists()) {
                showToast(getString(R.string.file_not_exist))
            } else {
                handleDeleteFile(loadingDialog, fileViewModel, file.path, onFinish = { status ->
                    if (status) {
                        handleBackLeftToRight()
                    } else {
                        showToast(getString(R.string.file_not_exist))
                    }
                    lifecycleScope.launch {
                        dismissLoading()
                    }
                })
            }

        }
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