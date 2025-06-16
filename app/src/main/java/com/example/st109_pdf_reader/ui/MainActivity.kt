package com.example.st109_pdf_reader.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.document.allreader.allofficefilereader.R
import com.document.allreader.allofficefilereader.common.IOfficeToPicture
import com.document.allreader.allofficefilereader.constant.EventConstant
import com.document.allreader.allofficefilereader.officereader.AppFrame
import com.document.allreader.allofficefilereader.officereader.beans.AImageButton
import com.document.allreader.allofficefilereader.officereader.beans.AImageCheckButton
import com.document.allreader.allofficefilereader.officereader.beans.AToolsbar
import com.document.allreader.allofficefilereader.ss.sheetbar.SheetBar
import com.document.allreader.allofficefilereader.system.IMainFrame
import com.document.allreader.allofficefilereader.system.MainControl
import com.example.st109_pdf_reader.core.base.BaseActivity
import com.example.st109_pdf_reader.databinding.ActivityMainBinding
import java.io.File

class MainActivity : BaseActivity<ActivityMainBinding>(), IMainFrame {
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
    private lateinit var filePath: String
    private lateinit var fileName: String
    override fun setViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    @RequiresApi(Build.VERSION_CODES.R) override fun initView() {
        if (!Environment.isExternalStorageManager()) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data = ("package:$packageName").toUri()
            startActivity(intent)
        }
        else {
            filePath = "/storage/emulated/0/Download/Nghi quyet cua chinh phu.doc"
            fileName = "Nghi quyet cua chinh phu.doc"

            control = MainControl(this)
            appFrame = AppFrame(applicationContext)
            appFrame?.post {
                try {
                    this.control?.openFile(this.filePath)
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
                        this.bitmap = Bitmap.createBitmap(visibleWidth, visibleHeight, Bitmap.Config.ARGB_8888)
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

            binding.tvName.text = fileName
            if (this.appFrame == null) {
                Log.i("nbhieu", "appFrame: null")
            }
            else {
                Log.i("nbhieu", "appFrame: not null")
                binding.appFrame.addView(this.appFrame)
            }
        }


    }

    override fun viewListener() {

    }

    override fun initText() {

    }

    override fun initActionBar() {

    }

    override fun getActivity(): AppCompatActivity? {
        return this
    }

    override fun doActionEvent(actionID: Int, obj: Any?): Boolean {
        if (actionID == 0) {
            onBackPressed()
        }
        else if (actionID == 15) {
            startActivity(
                Intent(
                    "android.intent.action.VIEW", Uri.parse("https://my.oschina.net/wxiwei")))
        }
        else if (actionID == 20) {
            updateToolsbarStatus()
        }
        else if (actionID == 25) {
            title = obj as String
        }
        else if (actionID == 1073741828) {
            this.bottomBar?.setFocusSheetButton(obj as Int)
        }
        else if (actionID != 536870913) {
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
                    }
                    else {
                        this.control!!.getSysKit().calloutManager.drawingMode = 1
                        this.appFrame?.post {
                            this.control!!.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, null)
                        }
                    }
                }

                EventConstant.APP_ERASER_ID -> {
                    if (!(obj as Boolean)) {
                        this.control!!.getSysKit().calloutManager.drawingMode = 0
                    }
                    else {
                        this.control!!.getSysKit().calloutManager.drawingMode = 1
                    }
                }

                else -> {
                    when (actionID) {
                        EventConstant.APP_FINDING -> {
                            val trim = (obj as String).trim()
                            if (trim.isNotEmpty() && this.control!!.find.find(trim)) {
                                setFindBackForwardState(true)
                            }
                            else {
                                setFindBackForwardState(false)
                                Toast.makeText(
                                    this, getLocalString("DIALOG_FIND_NOT_FOUND"), Toast.LENGTH_SHORT).show()
                            }
                        }

                        EventConstant.APP_FIND_BACKWARD -> {
                            if (!this.control!!.find.findBackward()) {
                                Toast.makeText(
                                    this, getLocalString("DIALOG_FIND_TO_BEGIN"), Toast.LENGTH_SHORT).show()
                            }
                        }

                        EventConstant.APP_FIND_FORWARD -> {
                            if (!this.control!!.find.findForward()) {
                                Toast.makeText(
                                    this, getLocalString("DIALOG_FIND_TO_END"), Toast.LENGTH_SHORT).show()
                            }
                        }

                        else -> return false
                    }
                }
            }
        }
        else {
            val arrayList = ArrayList<Uri>()
            arrayList.add(Uri.fromFile(File(this.filePath)))

            val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayList)
                type = "application/octet-stream"
            }

            startActivity(
                Intent.createChooser(
                    intent, getString(R.string.sys_share_title)))
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
        return getString(com.example.st109_pdf_reader.R.string.app_name)
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

    @SuppressLint("WrongConstant", "ResourceType") override fun fullScreen(fullscreen: Boolean) {
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

}