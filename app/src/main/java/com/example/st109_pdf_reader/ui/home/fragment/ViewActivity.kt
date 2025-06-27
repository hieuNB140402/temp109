package com.example.st109_pdf_reader.ui.home.fragment

import android.R.attr.path
import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.document.allreader.allofficefilereader.common.IOfficeToPicture
import com.document.allreader.allofficefilereader.constant.EventConstant
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HeaderFooter.file
import com.document.allreader.allofficefilereader.officereader.AppFrame
import com.document.allreader.allofficefilereader.officereader.beans.AImageButton
import com.document.allreader.allofficefilereader.officereader.beans.AImageCheckButton
import com.document.allreader.allofficefilereader.officereader.beans.AToolsbar
import com.document.allreader.allofficefilereader.ss.sheetbar.SheetBar
import com.document.allreader.allofficefilereader.system.IMainFrame
import com.document.allreader.allofficefilereader.system.MainControl
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseActivity
import com.example.st109_pdf_reader.core.dialog.ConfirmDialog
import com.example.st109_pdf_reader.core.dialog.RenameDialog
import com.example.st109_pdf_reader.core.extensions.checkPermissions
import com.example.st109_pdf_reader.core.extensions.dpToPx
import com.example.st109_pdf_reader.core.extensions.goToManageSettings
import com.example.st109_pdf_reader.core.extensions.goToSettings
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.handleBackLeftToRight
import com.example.st109_pdf_reader.core.extensions.handleBackLeftToRight
import com.example.st109_pdf_reader.core.extensions.handleDeleteFile
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.renameFileByPath
import com.example.st109_pdf_reader.core.extensions.select
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.shareFile
import com.example.st109_pdf_reader.core.extensions.showToast
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.core.utils.SystemUtils.storagePermission
import com.example.st109_pdf_reader.data.local.AppDatabase
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.data.local.repository.FileRepository
import com.example.st109_pdf_reader.databinding.ActivityViewBinding

import com.example.st109_pdf_reader.databinding.PopupReaderBinding
import com.example.st109_pdf_reader.ui.home.HomeActivity
import com.example.st109_pdf_reader.ui.home.viewmodel.FileViewModel
import com.example.st109_pdf_reader.ui.home.viewmodel.FileViewModelFactory
import kotlinx.coroutines.launch
import java.io.File
import kotlin.text.get

class ViewActivity : BaseActivity<ActivityViewBinding>(), IMainFrame {
    private var control: MainControl? = null
    private var appFrame: AppFrame? = null
    private var bottomBar: SheetBar? = null
    private lateinit var gapView: View
    private var isDispose: Boolean = false
    private var wmParams: WindowManager.LayoutParams? = null
    private var f269wm: WindowManager? = null
    private var penButton: AImageCheckButton? = null
    private var eraserButton: AImageCheckButton? = null
    private var settingsButton: AImageButton? = null
    private var pageDown: AImageButton? = null
    private var pageUp: AImageButton? = null
    private var toolsbar: AToolsbar? = null
    private var writeLog = true
    private var isThumbnail = false
    private var f268bg: Any = -7829368
    private lateinit var file: FilesModel
    lateinit var fileViewModel: FileViewModel
    override fun setViewBinding(): ActivityViewBinding {
        return ActivityViewBinding.inflate(LayoutInflater.from(this))
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = ("package:$packageName").toUri()
                startActivity(intent)
            }
            else {
                initData()
            }
        }else{
            if (checkPermissions(storagePermission)){
                initData()
            }else{
                goToSettings()
            }
        }
    }
    private fun initData(){
        val dao = AppDatabase.getInstance(this).fileDao()
        val repository = FileRepository(dao)

        fileViewModel = ViewModelProvider(this, FileViewModelFactory(repository))[FileViewModel::class.java]

        val file = intent.getParcelableExtra<FilesModel>(KeyApp.KeyIntent.INTENT_KEY)
        this.file = file!!

        changeHeader(file.type)
        control = MainControl(this)
        appFrame = AppFrame(applicationContext)
        appFrame?.post {
            try {
                this.control?.openFile(this.file.path)
                Log.i("nbhieu", "openfile")
            } catch (e: Exception) {
                Log.i("nbhieu", e.toString())
            }
        }
        this.control?.setOffictToPicture(object : IOfficeToPicture {

            private var bitmap: Bitmap? = null

            override fun setModeType(modeType: Byte) {

            }

            override fun getModeType(): Byte {
                return 1
            }

            override fun getBitmap(visibleWidth: Int, visibleHeight: Int): Bitmap {
                if (visibleHeight != 0 && visibleWidth != 0) {
                    val bitmap = this.bitmap
                    if (!(bitmap != null && bitmap.width == visibleWidth && this.bitmap?.height == visibleHeight)) {
                        val bitmap2 = this.bitmap
                        bitmap2?.recycle()
                    }
                    this.bitmap = Bitmap.createBitmap(
                        visibleWidth, visibleHeight, Bitmap.Config.ARGB_8888
                    )
                }
                return this.bitmap!!
            }

            override fun callBack(bitmap: Bitmap?) {

            }

            override fun isZoom(): Boolean {
                return false
            }

            override fun dispose() {

            }
        })

        binding.actionBar.tvCenter.text = this.file.name
        if (this.appFrame == null) {
            Log.i("nbhieu", "appFrame: null")
        } else {
            Log.i("nbhieu", "appFrame: not null")
            binding.appFrame.addView(this.appFrame)
        }
    }

    override fun viewListener() {
        binding.actionBar.apply {
            btnActionBarLeft.setOnSingleClick { handleBackLeftToRight() }
            btnActionBarRight.setOnSingleClick { handlePopUp(it) }
        }
    }

    override fun initText() {

    }

    override fun initActionBar() {
        binding.actionBar.apply {
            btnActionBarLeft.setImageResource(R.drawable.ic_back_white)
            btnActionBarLeft.visible()
            tvCenter.visible()
            tvCenter.setTextColor(getColor(com.document.allreader.allofficefilereader.R.color.white))
            tvCenter.select()
            btnActionBarRight.setImageResource(R.drawable.ic_more_white)
            btnActionBarRight.visible()
        }
    }

    private fun changeHeader(type: String) {
        val colorRes = when (type) {
            KeyApp.WORD -> R.color.word
            KeyApp.EXCEL -> R.color.excel
            KeyApp.PPT -> R.color.ppt
            KeyApp.PDF, KeyApp.ALL_FILE -> R.color.pdf
            else -> R.color.pdf
        }
        binding.actionBar.layoutHeader.setBackgroundResource(colorRes)
    }

    override fun getActivity(): AppCompatActivity? {
        return this
    }

    override fun doActionEvent(actionID: Int, obj: Any?): Boolean {
        if (actionID == 0) {
            onBackPressed()
        } else if (actionID == 15) {
            startActivity(
                Intent(
                    "android.intent.action.VIEW", "https://my.oschina.net/wxiwei".toUri()
                )
            )
        } else if (actionID == 20) {
            updateToolsbarStatus()
        } else if (actionID == 25) {
            title = obj as String
        } else if (actionID == 1073741828) {
            this.bottomBar?.setFocusSheetButton(obj as Int)
        } else if (actionID != 536870913) {
            when (actionID) {
                EventConstant.APP_DRAW_ID -> {
                    this.control!!.getSysKit().calloutManager.drawingMode = 1
                    this.appFrame?.post {
                        this.control!!.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, null)
                    }
                }

                EventConstant.APP_BACK_ID -> {
                    this.control!!.getSysKit().calloutManager.drawingMode = 0
                }

                EventConstant.APP_PEN_ID -> {
                    if (!(obj as Boolean)) {
                        this.control!!.getSysKit().calloutManager.drawingMode = 0
                    } else {
                        this.control!!.getSysKit().calloutManager.drawingMode = 1
                        this.appFrame?.post {
                            this.control!!.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, null)
                        }
                    }
                }

                EventConstant.APP_ERASER_ID -> {
                    if (!(obj as Boolean)) {
                        this.control!!.getSysKit().calloutManager.drawingMode = 0
                    } else {
                        this.control!!.getSysKit().calloutManager.drawingMode = 1
                    }
                }

                else -> {
                    when (actionID) {
                        EventConstant.APP_FINDING -> {
                            val trim = (obj as String).trim()
                            if (trim.isNotEmpty() && this.control!!.find.find(trim)) {
                                setFindBackForwardState(true)
                            } else {
                                setFindBackForwardState(false)
                                Toast.makeText(
                                    this, getLocalString("DIALOG_FIND_NOT_FOUND"), Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        EventConstant.APP_FIND_BACKWARD -> {
                            if (!this.control!!.find.findBackward()) {
                                Toast.makeText(
                                    this, getLocalString("DIALOG_FIND_TO_BEGIN"), Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        EventConstant.APP_FIND_FORWARD -> {
                            if (!this.control!!.find.findForward()) {
                                Toast.makeText(
                                    this, getLocalString("DIALOG_FIND_TO_END"), Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        else -> return false
                    }
                }
            }
        } else {
            val arrayList = ArrayList<Uri>()
            arrayList.add(Uri.fromFile(File(file.path)))

            val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayList)
                type = "application/octet-stream"
            }

            startActivity(
                Intent.createChooser(
                    intent, getString(com.document.allreader.allofficefilereader.R.string.sys_share_title)
                )
            )
        }
        return true
    }

    override fun openFileFinish() {
        Log.i("nbhieu", "on")
        this.gapView = View(applicationContext)
        this.appFrame?.addView(this.gapView, LinearLayout.LayoutParams(-1, 1))
        this.appFrame?.addView(this.control!!.view, LinearLayout.LayoutParams(-1, -1))
    }

    override fun updateToolsbarStatus() {
        val appFrame = this.appFrame
        if (!(appFrame == null || this.isDispose)) {
            val childCount = appFrame.childCount
            for (i in 0 until childCount) {
                val childAt = this.appFrame?.getChildAt(i)
                if (childAt is AToolsbar) {
                    childAt.updateStatus()
                }
            }
        }
    }

    override fun setFindBackForwardState(state: Boolean) {

    }

    override fun getBottomBarHeight(): Int {
        val sheetBar = this.bottomBar
        if (sheetBar != null) {
            return sheetBar.sheetbarHeight
        }
        return 0
    }

    override fun getTopBarHeight(): Int {
        return 0
    }

    override fun getAppName(): String {
        return getString(R.string.app_name)
    }

    override fun getTemporaryDirectory(): File {
        val externalFilesDir = getExternalFilesDir(null)
        if (externalFilesDir != null) {
            return externalFilesDir
        }
        return filesDir
    }

    override fun onEventMethod(
        v: View?, e1: MotionEvent?, e2: MotionEvent?, xValue: Float, yValue: Float, eventMethodType: Byte
    ): Boolean {
        return false
    }

    override fun isDrawPageNumber(): Boolean {
        return true
    }

    override fun isShowZoomingMsg(): Boolean {
        return true
    }

    override fun isPopUpErrorDlg(): Boolean {
        return true
    }

    override fun isShowPasswordDlg(): Boolean {
        return true
    }

    override fun isShowProgressBar(): Boolean {
        return true
    }

    override fun isShowFindDlg(): Boolean {
        return true
    }

    override fun isShowTXTEncodeDlg(): Boolean {
        return true
    }

    override fun getTXTDefaultEncode(): String {
        return "GBK"
    }

    override fun isTouchZoom(): Boolean {
        return true
    }

    override fun isZoomAfterLayoutForWord(): Boolean {
        return true
    }

    override fun getWordDefaultView(): Byte {
        return 0
    }

    override fun getLocalString(resName: String?): String {
        return "DIALOG_LOADING"
    }

    override fun changeZoom() {

    }

    override fun changePage() {
    }

    override fun completeLayout() {

    }

    override fun error(errorCode: Int) {

    }

    @SuppressLint("WrongConstant", "ResourceType")
    override fun fullScreen(fullscreen: Boolean) {
        if (fullscreen) {
            this.wmParams?.gravity = 53
            this.wmParams?.x = 5
            this.f269wm?.addView(this.penButton, this.wmParams)
            this.wmParams?.gravity = 53
            this.wmParams?.x = 5
            val layoutParams = this.wmParams
            layoutParams!!.y = layoutParams.height
            f269wm!!.addView(this.eraserButton, this.wmParams)
            wmParams!!.gravity = 53
            wmParams!!.x = 5
            val layoutParams2 = this.wmParams
            layoutParams2!!.y = layoutParams2.height * 2
            f269wm!!.addView(this.settingsButton, this.wmParams)
            wmParams!!.gravity = 19
            wmParams!!.x = 5
            wmParams!!.y = 0
            f269wm!!.addView(this.pageUp, this.wmParams)
            wmParams!!.gravity = 21
            f269wm!!.addView(this.pageDown, this.wmParams)
            (window.findViewById<View>(16908310)?.parent as? View)?.visibility = View.GONE
            this.toolsbar?.visibility = View.GONE
            gapView.visibility = View.GONE
            penButton!!.state = 2.toShort()
            this.eraserButton?.setState(2.toShort())
            val attributes = window.attributes
            attributes.flags = attributes.flags or 1024
            window.attributes = attributes
            window.addFlags(512)
            requestedOrientation = 0
            return
        }
        f269wm!!.removeView(this.pageUp)
        f269wm!!.removeView(this.pageDown)
        f269wm!!.removeView(this.penButton)
        f269wm!!.removeView(this.eraserButton)
        f269wm!!.removeView(this.settingsButton)
        (window.findViewById<View>(16908310)?.parent as? View)?.visibility = View.VISIBLE
        toolsbar!!.visibility = View.VISIBLE
        gapView.visibility = View.VISIBLE
        val attributes2 = window.attributes
        attributes2.flags = attributes2.flags and -1025
        window.attributes = attributes2
        window.clearFlags(512)
        requestedOrientation = 4
    }

    override fun showProgressBar(visible: Boolean) {
        setProgressBarIndeterminateVisibility(visible)
    }

    override fun updateViewImages(viewList: MutableList<Int>?) {

    }

    override fun isChangePage(): Boolean {
        return true
    }

    override fun setWriteLog(saveLog: Boolean) {
        this.writeLog = saveLog
    }

    override fun isWriteLog(): Boolean {
        return this.writeLog
    }

    override fun setThumbnail(isThumbnail: Boolean) {
        this.isThumbnail = isThumbnail
    }

    override fun isThumbnail(): Boolean {
        return this.isThumbnail
    }

    override fun getViewBackground(): Any {
        return this.f268bg
    }

    override fun setIgnoreOriginalSize(ignoreOriginalSize: Boolean) {

    }

    override fun isIgnoreOriginalSize(): Boolean {
        return false
    }

    override fun getPageListViewMovingPosition(): Byte {
        return 0
    }

    override fun dispose() {
        this.isDispose = true
        val mainControl = this.control
        if (mainControl != null) {
            mainControl.dispose()
            this.control = null
        }
        this.toolsbar = null
        this.bottomBar = null
        val appFrame = this.appFrame
        if (appFrame != null) {
            val childCount = appFrame.childCount
            for (i in 0 until childCount) {
                val childAt = this.appFrame!!.getChildAt(i)
                if (childAt is AToolsbar) {
                    childAt.dispose()
                }
            }
            this.appFrame = null
        }
        if (this.f269wm != null) {
            this.f269wm = null
            this.wmParams = null
            pageUp!!.dispose()
            pageDown!!.dispose()
            penButton!!.dispose()
            eraserButton!!.dispose()
            settingsButton!!.dispose()
            this.pageUp = null
            this.pageDown = null
            this.penButton = null
            this.eraserButton = null
            this.settingsButton = null
        }
    }

    override fun onResume() {
        super.onResume()
        hideNavigation()
    }

    private fun handlePopUp(view: View) {
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
                    when (status) {
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
}