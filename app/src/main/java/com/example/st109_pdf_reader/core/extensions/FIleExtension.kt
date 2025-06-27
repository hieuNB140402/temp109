package com.example.st109_pdf_reader.core.extensions

import android.Manifest
import android.R.attr.path
import android.R.attr.text
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.internal.utils.ImageUtil.rotateBitmap
import androidx.core.content.ContextCompat
import androidx.documentfile.provider.DocumentFile
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.lifecycleScope
import com.document.allreader.allofficefilereader.utils.FileUtils.context
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.core.utils.KeyApp.DOWNLOAD_ALBUM
import com.example.st109_pdf_reader.core.utils.KeyApp.RequestCode.PICK_IMAGE_REQUEST_CODE
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.ui.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Random

internal fun Activity.openImagePicker() {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
}


internal fun Activity.saveBitmapToExternalStorage(bitmap: Bitmap) {
    val resolver = contentResolver
    val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    } else {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "image_${System.currentTimeMillis()}.png")
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/$DOWNLOAD_ALBUM")
        } else {
            val directory = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DOWNLOAD_ALBUM
            )
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val filePath = File(directory, "image_${System.currentTimeMillis()}.png").absolutePath
            put(MediaStore.Images.Media.DATA, filePath)
        }
    }

    val imageUri = resolver.insert(imageCollection, contentValues)
    if (imageUri != null) {
        try {
            val outputStream: OutputStream? = resolver.openOutputStream(imageUri)
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun Activity.saveBitmapToInternalStorage(album: String, bitmap: Bitmap): String? {
    val name = generateRandomFileName()

    return try {
        val directory = File(filesDir, album)
        if (!directory.exists()) {
            directory.mkdir()
        }

        val file = File(directory, name)

        val fileOutputStream = FileOutputStream(file)

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)

        fileOutputStream.flush()
        fileOutputStream.close()

        file.absolutePath

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Activity.saveBitmapToInternalStorage(album: String, bitmap: Bitmap, nameInput: Int): String? {
    val name = nameInput.toString() + ".png"

    return try {
        val directory = File(filesDir, album)
        if (!directory.exists()) {
            directory.mkdir()
        }

        val file = File(directory, name)

        val fileOutputStream = FileOutputStream(file)

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)

        fileOutputStream.flush()
        fileOutputStream.close()

        file.absolutePath

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

//internal fun Activity.saveBitmapToInternalStorageZip(bitmap: Bitmap): String? {
//    val name = generateRandomFileName()
//    // Giảm kích thước ảnh xuống 512x512 px
//    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, true)
//
//    return try {
//        val directory = File(filesDir, DOWNLOAD_ALBUM)
//
//        if (!directory.exists()) {
//            directory.mkdir() // Tạo thư mục nếu nó chưa tồn tại
//        }
//
//        // Tạo file mới trong thư mục
//        val file = File(directory, "$name.png")
//
//        // Mở FileOutputStream để ghi dữ liệu vào file
//        val fileOutputStream = FileOutputStream(file)
//
//        // Nén bitmap với chất lượng giảm dần
//        var quality = 100
//        do {
//            fileOutputStream.flush()
//            resizedBitmap.compress(Bitmap.CompressFormat.PNG, quality, fileOutputStream)
//            quality -= 5 // Giảm chất lượng sau mỗi lần nén
//        } while (file.length() > 512 * 1024 && quality > 5) // 512 KB và chất lượng không dưới 5%
//
//        // Đóng luồng
//        fileOutputStream.flush()
//        fileOutputStream.close()
//
//        // Giải phóng bộ nhớ
//        resizedBitmap.recycle() // Giải phóng bitmap sau khi sử dụng
//
//        // Trả về đường dẫn đầy đủ của file đã lưu
//        file.absolutePath
//    } catch (e: Exception) {
//        e.printStackTrace()
//        null
//    }
//}
fun getImageInternal(context: Context, album: String): ArrayList<String> {
    val imagePaths = ArrayList<String>()
    val targetDir = File(context.filesDir, album)

    if (targetDir.exists() && targetDir.isDirectory) {
        targetDir.listFiles()?.filter { isImageFile(it) }                // Chỉ lấy file ảnh
            ?.sortedByDescending { it.lastModified() } // Sắp xếp giảm dần theo thời gian
            ?.forEach { file ->
                imagePaths.add(file.absolutePath)
            }
    }
    return imagePaths
}


private fun isImageFile(file: File): Boolean {
    val imageExtensions = listOf("jpg", "jpeg", "png", "bmp", "webp")
    val extension = file.extension.lowercase()
    return file.isFile && imageExtensions.contains(extension)
}

@Throws(OutOfMemoryError::class)
internal fun createBimapFromView(view: View): Bitmap {
    try {
        val output = Bitmap.createBitmap(
            view.width, view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        view.draw(canvas)
        return output
    } catch (error: OutOfMemoryError) {
        throw error
    }
}

fun Activity.getFilePathFromUri(uri: Uri): String? {
    var filePath: String? = null
    var inputStream: InputStream? = null
    try {
        val documentFile = DocumentFile.fromSingleUri(applicationContext, uri)!!
        val contentResolver = contentResolver
        inputStream = contentResolver.openInputStream(uri)
        if (inputStream != null) {
            val file = createTemporalFileFrom(inputStream, documentFile.name!!)
            filePath = file?.path
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            inputStream?.close()
        } catch (e: IOException) {
            Toast.makeText(
                applicationContext, resources.getText(R.string.file_error), Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }
    return filePath
}

fun Activity.createTemporalFileFrom(inputStream: InputStream, name: String): File? {
    val buffer = ByteArray(8 * 1024)
    val outputDirectory = File(applicationContext.filesDir, "Ringtone")
    if (outputDirectory.exists()) {
        outputDirectory.deleteRecursively()
    }
    var outputDir: File? = null

    try {
        inputStream.use { input ->
            outputDir = File(outputDirectory, name)
            if (outputDir!!.isDirectory) {
                outputDir = File(outputDir, "$name.mp3")
            }

            outputDir!!.parentFile?.mkdirs()

            FileOutputStream(outputDir).use { output ->
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    Log.d(TAG, "createTemporalFileFrom: ${bytesRead}")
                    output.write(buffer, 0, bytesRead)
                }
                output.flush()
            }
        }
    } catch (e: Exception) {
        Log.d(TAG, "createTemporalFileFrom: ${e}")
        e.printStackTrace()
        outputDir = null
    } finally {
        try {
            inputStream.close()
        } catch (e: IOException) {
            Log.d(TAG, "createTemporalFileFrom: Close stream error")
            e.printStackTrace()
        }
    }
    return outputDir
}

fun sortAsset(listFiles: Array<String>?): List<String>? {
    val sortedFiles = listFiles?.sortedWith(compareBy { fileName ->
        val matchResult = Regex("\\d+").find(fileName)
        matchResult?.value?.toIntOrNull() ?: Int.MAX_VALUE
    })
    return sortedFiles
}

fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream).also {
            Log.d("nbhieu", "uriToBitmap: success")
            inputStream?.close()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("nbhieu", "uriToBitmap: ${e.message}")
        null
    }
}

fun convertPathsToBitmaps(context: Context, paths: List<String>): List<Bitmap> {
    val bitmaps = mutableListOf<Bitmap>()
    paths.forEachIndexed { index, path ->
        val uri = Uri.fromFile(File(path))
        var bitmap = uriToBitmap(context, uri)
        if (bitmap != null) {
            val exif = ExifInterface(path)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
            )

            if (orientation != ExifInterface.ORIENTATION_ROTATE_90 && orientation != ExifInterface.ORIENTATION_ROTATE_180 && orientation != ExifInterface.ORIENTATION_ROTATE_270) {
                bitmaps.add(bitmap)
            } else {
                bitmap = when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90f)
                    ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180f)
                    ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270f)
                    else -> bitmap
                }

                bitmaps.add(bitmap)
            }

        }
    }
    return bitmaps
}

fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degrees)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

fun AppCompatActivity.deleteTempDataFolder(context: Context, folder: String) {
    lifecycleScope.launch(Dispatchers.IO) {
        val dataTemp = getImageInternal(context, folder)
        if (dataTemp.isNotEmpty()) {
            dataTemp.forEach {
                val file = File(it)
                file.delete()
            }
        }
    }
}

fun createPdfFromBitmaps(bitmapList: ArrayList<Bitmap>, outputFileName: String): String? {
    val pdfDocument = PdfDocument()

    bitmapList.forEachIndexed { index, bitmap ->
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, index + 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        canvas.drawBitmap(bitmap, 0f, 0f, null)

        pdfDocument.finishPage(page)
    }

    return try {
        val pdfDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), KeyApp.FOLDER_CREATE_PDF
        )
        if (!pdfDir.exists()) pdfDir.mkdirs()

        val file = File(pdfDir, "$outputFileName.pdf")
        val outputStream = FileOutputStream(file)

        pdfDocument.writeTo(outputStream)

        pdfDocument.close()
        outputStream.close()

        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        pdfDocument.close()
        null
    }
}

fun createPdfFromBitmapsInternal(
    context: Context, bitmapList: ArrayList<Bitmap>, outputFileName: String
): String? {
    val pdfDocument = PdfDocument()

    bitmapList.forEachIndexed { index, bitmap ->
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, index + 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        pdfDocument.finishPage(page)
    }

    return try {
        // Tạo thư mục trong internal storage: /data/data/<package>/files/<FOLDER_CREATE_PDF>
        val pdfDir = File(context.filesDir, KeyApp.FOLDER_CREATE_PDF)
        if (!pdfDir.exists()) pdfDir.mkdirs()

        val file = File(pdfDir, "$outputFileName.pdf")
        val outputStream = FileOutputStream(file)

        pdfDocument.writeTo(outputStream)
        pdfDocument.close()
        outputStream.close()

        file.absolutePath // Trả về path nội bộ
    } catch (e: Exception) {
        e.printStackTrace()
        pdfDocument.close()
        null
    }
}

fun copyFileInternalToExternal(context: Context, fileName: String): Boolean {
    val internalFile = File(context.filesDir, "${KeyApp.FOLDER_CREATE_PDF}/${fileName}")
    Log.i("nbhieu", "internalFile: $internalFile")
    val externalFile = File(context.getExternalFilesDir(null), fileName) // thư mục /Android/data/<package>/files

    return try {
        if (!internalFile.exists()) return false

        internalFile.inputStream().use { input ->
            externalFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        Log.i("nbhieu", "File copied to external: ${externalFile.absolutePath}")
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun Activity.copyFileToExternal(fileName: String, status: ((String, Boolean) -> Unit)) {
    // Kiểm tra quyền cho Android 9 trở xuống
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P && ContextCompat.checkSelfPermission(
            this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // Yêu cầu quyền (cần xử lý trong Activity)
        status.invoke("", false)
        return
    }

    // File nguồn: /data/user/0/com.example.st109_pdf_reader/files/My PDF/{fileName}
    val sourceFile = File(filesDir, "${KeyApp.FOLDER_CREATE_PDF}/$fileName")
    if (!sourceFile.exists() || !sourceFile.isFile) {
        status.invoke("", false)
        return
    }

    // Thư mục đích: /storage/emulated/0/Documents/MyPDF
    val destDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
    if (!destDir.exists()) {
        destDir.mkdirs() // Tạo thư mục đích nếu chưa tồn tại
    }

    // File đích
    val destFile = File(destDir, fileName)

    // Sao chép file
    try {
        FileInputStream(sourceFile).use { fis ->
            FileOutputStream(destFile).use { fos ->
                val buffer = ByteArray(1024)
                var length: Int
                while (fis.read(buffer).also { length = it } > 0) {
                    fos.write(buffer, 0, length)
                }
            }
        }
        status.invoke(destFile.absolutePath, true)
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun createPdfFromTextInternal(context: Context, text: String): FilesModel {
    val fileName = generateRandomString()
    val fileNameWithExtension = "$fileName.pdf"

    val pdfDocument = PdfDocument()

    // Kích thước trang PDF A4
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas: Canvas = page.canvas

    // Vẽ nền trắng
    canvas.drawColor(Color.WHITE)

    // Vẽ nội dung text
    val paint = Paint().apply {
        color = Color.BLACK
        textSize = 14f
        isAntiAlias = true
    }

    val lines = text.split("\n")
    var x = 40f
    var y = 50f
    val lineHeight = paint.descent() - paint.ascent()

    for (line in lines) {
        canvas.drawText(line, x, y, paint)
        y += lineHeight + 10f
    }

    pdfDocument.finishPage(page)
    val pdfDir = File(context.filesDir, KeyApp.FOLDER_CREATE_PDF)
    if (!pdfDir.exists()) pdfDir.mkdirs()
    // 🔸 Tạo file trong internal storage
    val file = File(context.filesDir, "${KeyApp.FOLDER_CREATE_PDF}/${fileNameWithExtension}")
    FileOutputStream(file).use { out ->
        pdfDocument.writeTo(out)
    }

    pdfDocument.close()

    return FilesModel(
        kotlin.random.Random.nextInt(9999, 999999), KeyApp.PDF, fileName, file.absolutePath, file.length(), "", ""
    )

}