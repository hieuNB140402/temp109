package com.example.st109_pdf_reader.ui.camera

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseActivity
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.databinding.ActivityCameraBinding
import androidx.core.graphics.toColorInt
import com.example.st109_pdf_reader.core.extensions.setGradientTextHeightColor
import androidx.window.layout.WindowMetricsCalculator
import com.example.st109_pdf_reader.core.dialog.LoadingDialog
import com.example.st109_pdf_reader.core.extensions.animateScaleEffect
import com.example.st109_pdf_reader.core.extensions.checkPermissions
import com.example.st109_pdf_reader.core.extensions.createBimapFromView
import com.example.st109_pdf_reader.core.extensions.dLog
import com.example.st109_pdf_reader.core.extensions.goToSettings
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.handleBackLeftToRight
import com.example.st109_pdf_reader.core.extensions.hideNavigation
import com.example.st109_pdf_reader.core.extensions.loadImageGlide
import com.example.st109_pdf_reader.core.extensions.recognizeTextFromBitmap
import com.example.st109_pdf_reader.core.extensions.requestPermission
import com.example.st109_pdf_reader.core.extensions.saveBitmapToInternalStorage
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.startIntentFromLeft
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.core.utils.KeyApp.RequestCode.CAMERA_PERMISSION_CODE
import com.example.st109_pdf_reader.core.utils.KeyApp.RequestCode.STORAGE_PERMISSION_CODE
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.core.utils.SystemUtils.cameraPermission
import com.example.st109_pdf_reader.core.utils.SystemUtils.getStoragePermission
import com.example.st109_pdf_reader.core.utils.SystemUtils.setCameraPermission
import com.example.st109_pdf_reader.ui.create.createpdf.CreatePDFActivity
import com.example.st109_pdf_reader.ui.create.gallery.GalleryActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.jvm.java

class CameraActivity : BaseActivity<ActivityCameraBinding>() {
    private var imageCapture: ImageCapture? = null
    private val imageList = ArrayList<String>()
    private var countImage = 0

    private var camera: androidx.camera.core.Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var isFlashOn = false
    private var isScan = false

    override fun setViewBinding(): ActivityCameraBinding {
        return ActivityCameraBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        isScan = intent.getBooleanExtra(KeyApp.KeyIntent.INTENT_KEY, false)
        checkPermission()

    }

    override fun viewListener() {
        binding.apply {
            actionBar.apply {
                btnActionBarLeft.setOnSingleClick { handleBackLeftToRight() }
                btnActionBarTextRight.setOnSingleClick { handleCreatePDF() }
            }
            btnShot.setOnSingleClick {
                handleTakePhoto(it)
            }
            binding.btnFlash.setOnSingleClick {
                isFlashOn = !isFlashOn
                updateFlashState()
            }

        }
    }

    override fun initText() {

    }

    override fun initActionBar() {
        binding.actionBar.apply {
            btnActionBarLeft.setImageResource(R.drawable.ic_back_white)
            btnActionBarLeft.visible()
            setGradientTextHeightColor(btnActionBarTextRight, "#F52C2C", "#B10C0C")
            layoutHeader.setBackgroundColor("#66000000".toColorInt())
        }
    }

    private fun checkPermission() {
        if (!checkPermissions(cameraPermission)) {
            if (SystemUtils.getCameraPermission(this) < 2) {
                requestPermission(cameraPermission, KeyApp.RequestCode.CAMERA_PERMISSION_CODE)
            } else {
                goToSettings()
            }
        } else {
            startCamera()
        }
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            val screenAspectRatio = AspectRatio.RATIO_4_3

            val preview = Preview.Builder()
                .setTargetAspectRatio(screenAspectRatio)
                .build()
                .also {
                    it.setSurfaceProvider(binding.cmrPreview.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .setTargetAspectRatio(screenAspectRatio)
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider?.unbindAll()
                camera = cameraProvider?.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

                // Khởi tạo trạng thái flash khi startCamera
                updateFlashState()

            } catch (exc: Exception) {
                Log.e("nbhieu", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))

    }

    private fun handleTakePhoto(view: View) {
        view.animateScaleEffect(0.8f, 150L)
        imageCapture?.targetRotation = binding.cmrPreview.display.rotation

        val imageCapture = imageCapture ?: return

        // Hiển thị dialog loading nếu cần
        val dialogLoading = LoadingDialog(this)
        dialogLoading.show()

        // Capture ảnh
        imageCapture.takePicture(ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageCapturedCallback() {
            @OptIn(ExperimentalGetImage::class)
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                val rotationDegrees = image.imageInfo.rotationDegrees


                val bitmap = imageProxyToBitmap(image)
                val rotatedBitmap = rotateBitmap(bitmap, rotationDegrees)
//                binding.imvImageTest.rotationY = 180f
                binding.imvImageTest.setImageBitmap(rotatedBitmap)
                val bit = createBimapFromView(binding.layoutExport)
                image.close() // Dóng ImageProxy

                CoroutineScope(Dispatchers.IO).launch {
                    var getText = ""
                    var currentPath = ""
                    try {
                        if (!isScan){
                            currentPath = saveBitmapToInternalStorage(KeyApp.DOWNLOAD_ALBUM, bit).toString()
                            imageList.add(currentPath.toString())
                        }else{
                            recognizeTextFromBitmap(bit, { text ->
                                getText = text
                            })
                        }


                        launch(Dispatchers.Main) {
                            if (!isScan) {
                                binding.imvImageTest.setImageBitmap(null)
//                            binding.imvImageTest.rotationY = 0f
                                countImage++
                                binding.tvCountImage.text = countImage.toString()
                                binding.tvCountImage.visible()
                                loadImageGlide(this@CameraActivity, currentPath, binding.imvImageShot)
                                binding.actionBar.btnActionBarTextRight.visible()
                            } else {
                                dLog("text: $getText")
                            }

                            dialogLoading.dismiss()
                            hideNavigation()
                        }
                    } catch (e: Exception) {
                        Log.e("nbhieu", "Lỗi lưu ảnh", e)
                        launch(Dispatchers.Main) {
                            dialogLoading.dismiss()
                            hideNavigation()
                        }
                    }
                }
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                dialogLoading.dismiss()
                Log.e("nbhieu", "Lỗi chụp ảnh", exception)
            }
        })

    }

    fun imageProxyToBitmap(image: ImageProxy): Bitmap {
        val planeProxy = image.planes[0]
        val buffer = planeProxy.buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    fun rotateBitmap(bitmap: Bitmap, rotationDegrees: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(rotationDegrees.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    override fun onResume() {
        super.onResume()
        hideNavigation()
    }

    private fun handleCreatePDF() {
        val intent = Intent(this, CreatePDFActivity::class.java).apply {
            putExtra(KeyApp.KeyIntent.INTENT_KEY, ArrayList(imageList))
        }

        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun updateFlashState() {
        camera?.cameraControl?.enableTorch(isFlashOn)
        // Cập nhật icon nếu cần
        val icon = if (isFlashOn) R.drawable.ic_flash_on else R.drawable.ic_flash_off
        binding.btnFlash.setImageResource(icon)
    }

    override fun onPause() {
        super.onPause()
        cameraProvider?.unbindAll()
    }

    override fun onRestart() {
        super.onRestart()
        if (checkPermissions(cameraPermission)) {
            checkPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
                setCameraPermission(this, 0)
            } else {
                setCameraPermission(this, (SystemUtils.getCameraPermission(this) + 1))
                checkPermission()
            }
        }
    }

}