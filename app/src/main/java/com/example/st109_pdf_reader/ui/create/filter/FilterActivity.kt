package com.example.st109_pdf_reader.ui.create.filter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseActivity
import com.example.st109_pdf_reader.core.extensions.convertPathsToBitmaps
import com.example.st109_pdf_reader.core.extensions.eLog
import com.example.st109_pdf_reader.core.extensions.handleBackLeftToRight
import com.example.st109_pdf_reader.core.extensions.iLog
import com.example.st109_pdf_reader.core.extensions.saveBitmapToInternalStorage
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.DataLocal
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.data.model.create.CreatePDFModel
import com.example.st109_pdf_reader.data.model.create.FilterModel
import com.example.st109_pdf_reader.databinding.ActivityFilterBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

class FilterActivity : BaseActivity<ActivityFilterBinding>() {

    private val pageAdapter by lazy {
        PageAdapter(this)
    }
    private val imageList = ArrayList<CreatePDFModel>()
    private var quantityPage = 0

    private var sepiaVintagePaint: Paint? = null
    private var grayscalePaint: Paint? = null
    private var invertColorPaint: Paint? = null
    private var brightContrastPaint: Paint? = null
    private var overexposedPaint: Paint? = null
    private var rgbSwappedPaint: Paint? = null
    private var warmEffectPaint: Paint? = null
    private var coolEffectPaint: Paint? = null
    private var surrealPaint: Paint? = null
    private var subtleTonePaint: Paint? = null
    private var purpleTintPaint: Paint? = null
    private var yellowGreenPaint: Paint? = null
    private var cyanGlowPaint: Paint? = null
    private var highContrastBWPaint: Paint? = null
    private var vintageFadePaint: Paint? = null
    private var icyBluePaint: Paint? = null
    private var richSepiaPaint: Paint? = null
    private var milkyTonePaint: Paint? = null
    private var greenHighlightPaint: Paint? = null
    private var softPeachPaint: Paint? = null
    private var lightBoostPaint: Paint? = null
    private var darkBoostPaint: Paint? = null

    private val INDEX_SATURATION: Int = 7
    private val bitmapOriginList = ArrayList<Bitmap>()

    private var isApplyAll = false

    private val adapterFilter by lazy {
        FilterAdapter(this)
    }
    private val filerList = ArrayList<FilterModel>()
    private val pathOriginList = ArrayList<String>()
    private val positionFilterSelectedList = ArrayList<Int>()

    override fun setViewBinding(): ActivityFilterBinding {
        return ActivityFilterBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        initData()
        initPaints()
    }

    override fun viewListener() {
        binding.apply {
            actionBar.btnActionBarLeft.setOnSingleClick { handleBackLeftToRight() }
            actionBar.btnActionBarRight.setOnSingleClick { handleSave() }
            btnNext.setOnSingleClick { handleNext() }
            btnPre.setOnSingleClick { handlePre() }
            swApplyAll.setOnSingleClick { handleApplyAll() }
        }
        handleRcv()
    }

    override fun initText() {}

    override fun initActionBar() {
        binding.actionBar.apply {
            btnActionBarLeft.setImageResource(R.drawable.ic_back_black)
            btnActionBarLeft.visible()
            tvCenter.text = getString(R.string.filter)
            tvCenter.visible()
            btnActionBarRight.setImageResource(R.drawable.ic_done)
            btnActionBarRight.visible()
        }
    }

    private fun initData() {
        val getModel = intent.getParcelableArrayListExtra<CreatePDFModel>(KeyApp.KeyIntent.INTENT_KEY)
        getModel?.forEach { it.isSelected = false }
        imageList.clear()
        imageList.addAll(getModel ?: return)
        imageList.firstOrNull()?.isSelected = true
        quantityPage = imageList.size
        initRcv()
    }

    private fun initRcv() {
        binding.vpgPage.apply {
            adapter = pageAdapter
            offscreenPageLimit = 3
            currentItem = 0
            setPageTransformer(getPageTransformer())
        }
        pageAdapter.submitList(imageList)
        pageAdapter.selectItem(0)


        // Filter
        binding.rcvFilter.apply {
            adapter = adapterFilter
            itemAnimator = null
        }

        CoroutineScope(Job() + Dispatchers.IO).launch {
            showLoading()
            val differed = async {
                filerList.addAll(DataLocal.getFilterList())
                imageList.forEach { pathOriginList.add(it.path) }

                bitmapOriginList.addAll(convertPathsToBitmaps(this@FilterActivity, pathOriginList))
                repeat(pathOriginList.size) {
                    positionFilterSelectedList.add(0)
                }
                return@async bitmapOriginList.size == pathOriginList.size
            }
            launch(Dispatchers.Main) {
                if (differed.await()) {
                    adapterFilter.submitList(filerList)
                    changePage(0)
                    dismissLoading(true)
                }
            }
        }
    }

    private fun getPageTransformer(): CompositePageTransformer {
        return CompositePageTransformer().apply {
            addTransformer { page, position ->
                val scale = 0.85f + (1 - abs(position)) * 0.15f
                page.scaleX = scale
                page.scaleY = scale
            }
        }
    }

    private fun handleRcv() {
        binding.apply {
            vpgPage.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    changePage(position)
                }
            })

            adapterFilter.onItemClick = { filter, position ->
                handleSetFilter(position)
            }
        }
    }

    private fun changePage(position: Int) {
        pageAdapter.selectItem(position)
        filerList.forEachIndexed { index, filter ->
            filter.isSelected = index == positionFilterSelectedList[position]
        }
        adapterFilter.submitList(filerList)

        val newPage = "${position + 1}/$quantityPage"
        binding.tvPage.text = newPage

        val isFirst = position == 0
        val isLast = position == imageList.size - 1

        if (isFirst) {
            binding.imvPre.setImageResource(R.drawable.ic_pre)
            binding.btnPre.isEnabled = false
        } else {
            binding.imvPre.setImageResource(R.drawable.ic_pre_selected)
            binding.btnPre.isEnabled = true
        }

        if (isLast) {
            binding.imvNext.setImageResource(R.drawable.ic_pre)
            binding.btnNext.isEnabled = false
        } else {
            binding.imvNext.setImageResource(R.drawable.ic_pre_selected)
            binding.btnNext.isEnabled = true
        }
    }

    private fun handlePre() {
        val nowPosition = binding.vpgPage.currentItem
        if (nowPosition > 0) {
            val newPosition = nowPosition - 1
            binding.vpgPage.setCurrentItem(newPosition, true)
            changePage(newPosition)
        }
    }

    private fun handleNext() {
        val nowPosition = binding.vpgPage.currentItem
        if (nowPosition < imageList.size - 1) {
            val newPosition = nowPosition + 1
            binding.vpgPage.setCurrentItem(newPosition, true)
            changePage(newPosition)
        }
    }

    private fun handleApplyAll() {
        if (!isApplyAll) {
            isApplyAll = true
            binding.swApplyAll.setImageResource(R.drawable.ic_sw_on_filter)
            val positionSelected = imageList.indexOfFirst { it.isSelected == true }
            handleSetFilter(positionFilterSelectedList[positionSelected], true)
        } else {
            isApplyAll = false
            binding.swApplyAll.setImageResource(R.drawable.ic_sw_off_filter)
        }
    }

    private fun initPaints() {

        // 2. Sepia cổ điển
        sepiaVintagePaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        0.393f,
                        0.769f,
                        0.189f,
                        0.0f,
                        0f,
                        0.349f,
                        0.686f,
                        0.168f,
                        0.0f,
                        0f,
                        0.272f,
                        0.534f,
                        0.131f,
                        0.0f,
                        0f,
                        0.0f,
                        0.0f,
                        0.0f,
                        1.0f,
                        0f
                    )
                )
            )
        }

        // 3. Trắng đen (grayscale)
        grayscalePaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
        }

        // 4. Đảo màu
        invertColorPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        -1f, 0f, 0f, 0f, 255f, 0f, -1f, 0f, 0f, 255f, 0f, 0f, -1f, 0f, 255f, 0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }

        // 5. Sáng mạnh, tăng tương phản
        brightContrastPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        2f, 0f, 0f, 0f, -130f, 0f, 2f, 0f, 0f, -130f, 0f, 0f, 2f, 0f, -130f, 0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }

        // 6. Gần như cháy sáng toàn bộ
        overexposedPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        5f, 0f, 0f, 0f, -254f, 0f, 5f, 0f, 0f, -254f, 0f, 0f, 5f, 0f, -254f, 0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }

        // 7. Đảo kênh RGB
        rgbSwappedPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }

        // 8. Hiệu ứng ấm áp (warm)
        warmEffectPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        -0.36f,
                        1.691f,
                        -0.32f,
                        0f,
                        0f,
                        0.325f,
                        0.398f,
                        0.275f,
                        0f,
                        0f,
                        0.79f,
                        0.796f,
                        -0.76f,
                        0f,
                        0f,
                        0f,
                        0f,
                        0f,
                        1f,
                        0f
                    )
                )
            )
        }

        // 9. Hiệu ứng lạnh (cool)
        coolEffectPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        -0.41f,
                        0.539f,
                        -0.873f,
                        0f,
                        0f,
                        0.452f,
                        0.666f,
                        -0.11f,
                        0f,
                        0f,
                        -0.3f,
                        1.71f,
                        -0.4f,
                        0f,
                        0f,
                        0f,
                        0f,
                        0f,
                        1f,
                        0f
                    )
                )
            )
        }

        // 10. Biến dạng màu kỳ lạ
        surrealPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        3.074f,
                        -1.82f,
                        -0.24f,
                        0f,
                        50.8f,
                        -0.92f,
                        2.171f,
                        -0.24f,
                        0f,
                        50.8f,
                        -0.92f,
                        -1.82f,
                        3.754f,
                        0f,
                        50.8f,
                        0f,
                        0f,
                        0f,
                        1f,
                        0f
                    )
                )
            )
        }

        // 11. Nhẹ nhàng, mờ dịu
        subtleTonePaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        0.14f,
                        0.45f,
                        0.05f,
                        0f,
                        0f,
                        0.12f,
                        0.39f,
                        0.04f,
                        0f,
                        0f,
                        0.08f,
                        0.28f,
                        0.03f,
                        0f,
                        0f,
                        0f,
                        0f,
                        0f,
                        1f,
                        0f
                    )
                )
            )
        }

        // 12. Tím nhẹ
        purpleTintPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        1f, -0.2f, 0f, 0f, 0f, 0f, 1f, 0f, -0.1f, 0f, 0f, 1.2f, 1f, 0.1f, 0f, 0f, 0f, 1.7f, 1f, 0f
                    )
                )
            )
        }

        // 13. Vàng/xanh neon
        yellowGreenPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        1f, 0f, 0f, 0f, 0f, -0.2f, 1f, 0.3f, 0.1f, 0f, -3f, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }

        // 14. Ánh cyan
        cyanGlowPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        1f, 0f, 0f, 1.9f, -2.2f, 0f, 1f, 0f, 0.0f, 0.3f, 3f, 0f, 1f, 0f, 0.5f, 0f, 0f, 0f, 1f, 0.2f
                    )
                )
            )
        }

        // 15. Đen trắng đậm (high contrast)
        highContrastBWPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 1f, 0f
                    )
                )
            )
        }

        // 16. Màu cũ nhẹ
        vintageFadePaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        1f, 0f, 0f, 0f, 0f, -0.4f, 1.3f, -0.4f, 0.2f, -0.1f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }

        // 17. Lạnh, ánh xanh
        icyBluePaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        1f, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, -0.2f, 0.2f, 0.1f, 0.4f, 0f, 0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }

        // 18. Sepia đậm
        richSepiaPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        1.3f, -0.3f, 1.1f, 0f, 0f, 0f, 1.3f, 0.2f, 0f, 0f, 0f, 0f, 0.8f, 0.2f, 0f, 0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }

        // 19. Màu sữa dịu
        milkyTonePaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 0.6f, 1f, 0f, 0f, 0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }

        // 20. Xanh sáng
        greenHighlightPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        1f, 0f, 0f, 0f, 0f, 0f, 2f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }

        // 21. Hồng đào pastel
        softPeachPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        1f, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }

        // 22. Tăng sáng
        lightBoostPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        1.5f, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 0f, 0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }

        // 23. Làm tối
        darkBoostPaint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        .5f, 0f, 0f, 0f, 0f, 0f, .5f, 0f, 0f, 0f, 0f, 0f, .5f, 0f, 0f, 0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }
    }

    private fun setFilter(index: Int, btm: Bitmap): Bitmap {
        val bm = Bitmap.createBitmap(btm.width, btm.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)

        if (Build.VERSION.SDK_INT != INDEX_SATURATION && index != -1) {
            when (index) {
                1 ->canvas.drawBitmap(btm, 0f, 0f, sepiaVintagePaint)
                2 -> canvas.drawBitmap(btm, 0f, 0f, grayscalePaint)
                3 -> canvas.drawBitmap(btm, 0f, 0f, invertColorPaint)
                4 -> canvas.drawBitmap(btm, 0f, 0f, brightContrastPaint)
                5 -> canvas.drawBitmap(btm, 0f, 0f, overexposedPaint)
                6 -> canvas.drawBitmap(btm, 0f, 0f, rgbSwappedPaint)
                7 -> canvas.drawBitmap(btm, 0f, 0f, warmEffectPaint)
                8 -> canvas.drawBitmap(btm, 0f, 0f, coolEffectPaint)
                9 -> canvas.drawBitmap(btm, 0f, 0f, surrealPaint)
                10 -> canvas.drawBitmap(btm, 0f, 0f, subtleTonePaint)
                11 -> canvas.drawBitmap(btm, 0f, 0f, purpleTintPaint)
                12 -> canvas.drawBitmap(btm, 0f, 0f, yellowGreenPaint)
                13 -> canvas.drawBitmap(btm, 0f, 0f, cyanGlowPaint)
                14 -> canvas.drawBitmap(btm, 0f, 0f, highContrastBWPaint)
                15 -> canvas.drawBitmap(btm, 0f, 0f, vintageFadePaint)
                16 -> canvas.drawBitmap(btm, 0f, 0f, icyBluePaint)
                17 -> canvas.drawBitmap(btm, 0f, 0f, richSepiaPaint)
                18 -> canvas.drawBitmap(btm, 0f, 0f, milkyTonePaint)
                19 -> canvas.drawBitmap(btm, 0f, 0f, greenHighlightPaint)
                20 -> canvas.drawBitmap(btm, 0f, 0f, softPeachPaint)
                21 -> canvas.drawBitmap(btm, 0f, 0f, lightBoostPaint)
                22 -> canvas.drawBitmap(btm, 0f, 0f, darkBoostPaint)
            }
        }
        return bm
    }

    private fun handleSetFilter(position: Int, isAllPage: Boolean = false) {
        val positionSelected = imageList.indexOfFirst { it.isSelected == true }
        if (positionFilterSelectedList[positionSelected] == position && !isAllPage) {
            return
        }
        else {
            val handleExpectation = CoroutineExceptionHandler { _, throwable ->
                eLog("handleSetFilter: ${throwable.message}")
                lifecycleScope.launch {
                    dismissLoading()
                }
            }
            CoroutineScope(SupervisorJob() + Dispatchers.IO + handleExpectation).launch {
                showLoading()
                val differ = async {
                    if (!isApplyAll) {
                        if (position == 0) {
                            imageList[positionSelected].path = pathOriginList[positionSelected]
                        } else {
                            val path = saveBitmapToInternalStorage(KeyApp.TEMP_IMAGE_FILTER, setFilter(position, bitmapOriginList[positionSelected]))
                            imageList[positionSelected].path = path!!
                        }
                        positionFilterSelectedList[positionSelected] = position
                    } else {
                        if (position == 0) {
                            pathOriginList.forEachIndexed { index, path ->
                                imageList[index].path = path
                            }
                        } else {
                            pathOriginList.forEachIndexed { index, path ->
                                val newPath = saveBitmapToInternalStorage(
                                    KeyApp.TEMP_IMAGE_FILTER, setFilter(position, bitmapOriginList[index])
                                )
                                imageList[index].path = newPath!!
                            }
                        }
                        for (i in 0 until positionFilterSelectedList.size) {
                            positionFilterSelectedList[i] = position
                        }
                    }
                    return@async true
                }
                launch(Dispatchers.Main) {
                    if (differ.await()) {
                        if (!isApplyAll) {
                            pageAdapter.notifyItemChanged(positionSelected)
                            adapterFilter.submitItem(position)
                        } else {
                            pageAdapter.submitList(imageList)
                            adapterFilter.submitItem(position)
                            binding.vpgPage.currentItem = positionSelected - 1
                            delay(100)
                            binding.vpgPage.currentItem = positionSelected
                        }
                        dismissLoading(true)
                    }
                }
            }
        }

    }

    private fun handleSave(){
        val isChange = positionFilterSelectedList.any{it != 0}
        if (!isChange){
            handleBackLeftToRight()
        }else{
            val resultIntent = Intent()
            resultIntent.putParcelableArrayListExtra(KeyApp.KeyIntent.INTENT_KEY, imageList)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

}