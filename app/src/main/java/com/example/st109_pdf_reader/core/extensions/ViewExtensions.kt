package com.example.st109_pdf_reader.core.extensions

import android.R.attr.path
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HeaderFooter.file
import com.document.allreader.allofficefilereader.utils.FileUtils.context
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.dialog.LoadingDialog
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.core.utils.SystemUtils.lastClickTime
import com.example.st109_pdf_reader.ui.home.HomeActivity
import com.example.st109_pdf_reader.ui.home.viewmodel.FileViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

internal fun View.visible() {
    visibility = View.VISIBLE
}

internal fun View.invisible() {
    visibility = View.INVISIBLE
}

internal fun View.gone() {
    visibility = View.GONE
}

internal fun Activity.showToast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, ContextCompat.getString(this, message), duration).show()
}

internal fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

internal fun View.select() {
    isSelected = true
}

fun Activity.showSystemUI(white: Boolean) {
    if (white) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }
    else {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        WindowCompat.setDecorFitsSystemWindows(window, false);
    }
    else {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}

fun Activity.handleDeleteFile(dialog: LoadingDialog, viewModel: FileViewModel, path: String, onFinish: ((Boolean) -> Unit)) {
    dialog.show()
    val handleExceptionCoroutine = CoroutineExceptionHandler { _, throwable ->
        Log.e("nbhieu", "handleDeleteFile: ${throwable.message}")
        onFinish.invoke(false)
    }
    CoroutineScope(SupervisorJob() + Dispatchers.IO + handleExceptionCoroutine).launch {
        val job1 = async {
            val file = File(path)
            if (file.exists()) {
                file.delete()
                viewModel.deleteFileFromPath(path)
            }
            else {
                return@async false
            }
            return@async true
        }
        launch(Dispatchers.Main) {
            if (job1.await() == true) {
                onFinish.invoke(true)
            }
            else if (job1.await() == false) {
                showToast(getString(R.string.file_not_exist))
                onFinish.invoke(false)
            }
        }
    }
}

fun Activity.handleDeleteFile(dialog: LoadingDialog, viewModel: FileViewModel, listPath: ArrayList<String>, onFinish: ((Boolean) -> Unit)) {
    dialog.show()
    val handleExceptionCoroutine = CoroutineExceptionHandler { _, throwable ->
        Log.e("nbhieu", "handleDeleteFile: ${throwable.message}")
    }
    CoroutineScope(SupervisorJob() + Dispatchers.IO + handleExceptionCoroutine).launch {
        val job1 = async {
            val isNotExist = listPath.any { !File(it).exists() }
            if (isNotExist) {
                return@async false
            }
            else {
                listPath.forEach {
                    File(it).delete()
                    viewModel.deleteFileFromPath(it)
                }
                return@async true
            }
        }
        launch(Dispatchers.Main) {
            if (job1.await() == true) {
                Log.e("nbhieu", "job1.await(): true")

                onFinish.invoke(true)
            }
            else if (job1.await() == false) {
                Log.e("nbhieu", "job1.await(): false")
                onFinish.invoke(false)
            }
        }
    }
}

fun Activity.shareFile(filePath: String, isBlack: Boolean = false) {
    val file = File(filePath)
    if (!file.exists()) {
        showToast(getString(R.string.file_not_exist))
        hideNavigation(isBlack)
        return
    }

    val uri = FileProvider.getUriForFile(this, "$packageName.provider", file)

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = getMimeType(filePath) ?: "*/*"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    startActivity(Intent.createChooser(shareIntent, "Share"))
    hideNavigation(isBlack)
}

fun Activity.shareFile(listFilePath: ArrayList<String>, isBlack: Boolean = false) {
    val listFile = ArrayList<File>()
    listFilePath.forEach { file ->
        listFile.add(File(file))
    }
    listFile.forEach { file ->
        if (!file.exists()) {
            val content = "${file.name}" + getString(R.string.content_file_not_exist)
            showToast(content)
            hideNavigation(isBlack)
            return
        }
    }


    val uris = listFile.map { file ->
        FileProvider.getUriForFile(
            this, "$packageName.provider", file)
    }

    val shareIntent = if (uris.size == 1) {
        Intent(Intent.ACTION_SEND).apply {
            type = getMimeType(listFile.first().path) ?: "*/*"
            putExtra(Intent.EXTRA_STREAM, uris.first())
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
    else {
        Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            type = "*/*"
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(uris))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    startActivity(Intent.createChooser(shareIntent, "Share"))
    hideNavigation(isBlack)
}

fun getMimeType(path: String): String? {
    val extension = MimeTypeMap.getFileExtensionFromUrl(path)
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.lowercase())
}

internal fun Activity.saveBitmapToCache(bitmap: Bitmap): File {
    val cachePath = File(cacheDir, "images")
    cachePath.mkdirs()
    val file = File(cachePath, "shared_image.png")
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }
    return file
}

internal fun shareImage(context: Context, imageUri: Uri) {
    val arr = ArrayList<Uri>()
    arr.add(imageUri)

    val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
        type = "*/*"
        putParcelableArrayListExtra(Intent.EXTRA_STREAM, arr)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(intent, "Share Images"))
}

fun renameFileByPath(
    dialog: LoadingDialog,
    viewModel: FileViewModel,
    path: String,
    newNameWithExtension: String,
    onFinish: (String) -> Unit
) {
    dialog.show()

    val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("nbhieu", "renameFileByPath: ${throwable.message}", throwable)
        CoroutineScope(Dispatchers.Main).launch {
            dialog.dismiss()
            onFinish(KeyApp.FILE_NOT_EXIST)
        }
    }

    CoroutineScope(Dispatchers.IO + SupervisorJob() + handler).launch {
        val resultCode = try {
            val oldFile = File(path)
            val parentDir = oldFile.parent
            if (parentDir == null || !oldFile.exists()) {
                KeyApp.FILE_NOT_EXIST
            } else {
                val newPath = "$parentDir/$newNameWithExtension"
                val newFile = File(newPath)

                if (newFile.exists()) {
                    KeyApp.FILE_NAME_EXIST
                } else {
                    val renameSuccess = oldFile.renameTo(newFile)
                    if (renameSuccess) {
                        viewModel.renameFileByPath(path, newNameWithExtension, newPath)
                        KeyApp.RENAME_SUCCESS
                    } else {
                        KeyApp.RENAME_FAIL
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("nbhieu", "renameFileByPath exception", e)
            KeyApp.FILE_NOT_EXIST
        }

        withContext(Dispatchers.Main) {
            dialog.dismiss()
            onFinish(resultCode)
        }
    }
}



internal fun Activity.handleBackLeftToRight() {
    finish()
    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
}

internal fun Activity.handleBackRightToLeft() {
    finish()
    overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
}

fun Context.handleBackFragmentFromRight() {
    if (this is FragmentActivity) {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        supportFragmentManager.popBackStack()
    }
}


//fun Activity.handleMusic(view: ImageView, pref: SharedPrefsHelper) {
//    if (statusSound) {
//        mediaPlayer?.stop()
//        mediaPlayer = null
//        statusSound = false
//        pref.setSettings(MUSIC, false)
//        view.setImageResource(R.drawable.ic_music_off)
//    } else {
//        mediaPlayer = MediaPlayer.create(this, R.raw.theme)
//        mediaPlayer?.start()
//        safeSetVolume(mediaPlayer, 0.3f)
//        statusSound = true
//        pref.setSettings(MUSIC, true)
//        mediaPlayer?.isLooping = true
//        view.setImageResource(R.drawable.ic_music_on)
//    }
//}

//fun Activity.initMusic(view: ImageView) {
//    if (statusSound) {
//        view.setImageResource(R.drawable.ic_music_on)
//    } else {
//        view.setImageResource(R.drawable.ic_music_off)
//    }
//}

//fun Activity.stopActivity() {
//    if (!isFromOtherScreen) {
//        mediaPlayer?.pause()
//    } else {
//        isFromOtherScreen = false
//    }
//}
//
//fun Activity.restartActivity() {
//    if (statusSound) {
//        mediaPlayer?.start()
//    }
//}

internal fun Activity.hideNavigation(isBlack: Boolean = false) {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    if (!isBlack) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
    else {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}

//@SuppressLint("ClickableViewAccessibility")
//@RequiresApi(Build.VERSION_CODES.O)
//fun View.setOnClickListenerWithVibration(
//    vibrationDuration: Long = 100,
//    onClick: (View, Float, Float) -> Unit
//) {
//    var touchX = 0f
//    var touchY = 0f
//    this.setOnTouchListener { _, event ->
//        if (event.action == MotionEvent.ACTION_DOWN) {
//            touchX = event.x
//            touchY = event.y
//        }
//        false
//    }
//    this.setOnClickListener {
//        if (isVibrate) {
//            val vibrator = this.context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
//            vibrator?.let {
//                if (it.hasVibrator()) {
//                    val effect = VibrationEffect.createOneShot(
//                        vibrationDuration,
//                        VibrationEffect.DEFAULT_AMPLITUDE
//                    )
//                    it.vibrate(effect)
//                }
//            }
//        }
//        if (isEffect) {
//            val effect = MediaPlayer.create(context, R.raw.click_fail)
//            effect.start()
//            effect.setOnCompletionListener {
//                it.release()
//            }
//        }
//        onClick(it, touchX, touchY)
//    }
//
//}

//fun Activity.handleComeBackHome(address: Activity) {
//    val intent = Intent(address, HomeActivity::class.java)
//    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
//}

fun View.setOnSingleClick(time: Long = 500, action: (View) -> Unit) {
    this.setOnClickListener {
        if (System.currentTimeMillis() - lastClickTime >= time) {
            action(it)
            lastClickTime = System.currentTimeMillis()
        }
    }
}


//@SuppressLint("ClickableViewAccessibility")
//fun View.onSingleClick(action: (View) -> Unit) {
//    this.setOnClickListener {
//        if (System.currentTimeMillis() - lastClickTime >= 500) {
//            animateScaleEffect()
//            lastClickTime = System.currentTimeMillis()
//            action(it)
//        }
//    }
//}
//
//@SuppressLint("ClickableViewAccessibility")
//fun View.onSingleClickOut(time: Int = 500, action: (View) -> Unit) {
//    this.setOnClickListener {
//        if (System.currentTimeMillis() - lastClickTime >= time) {
//            if (isEffect) {
//                val effect = MediaPlayer.create(context, R.raw.touch)
//                effect.start()
//                effect.setOnCompletionListener {
//                    it.release()
//                }
//            }
//            animateScaleOutEffect()
//            lastClickTime = System.currentTimeMillis()
//            action(it)
//        }
//    }
//}
fun Activity.handleComeBackHome(address: Activity) {
    val intent = Intent(address, HomeActivity::class.java)
    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
}

