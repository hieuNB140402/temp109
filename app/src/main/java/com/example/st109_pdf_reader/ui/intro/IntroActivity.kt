package com.example.st109_pdf_reader.ui.intro

import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseActivity
import com.example.st109_pdf_reader.core.extensions.setGradientTextHeightColor
import com.example.st109_pdf_reader.core.extensions.startIntentFromLeft
import com.example.st109_pdf_reader.core.utils.DataLocal.itemIntroList
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.databinding.ActivityIntroBinding
import com.example.st109_pdf_reader.ui.PermissionActivity
import com.example.st109_pdf_reader.ui.home.HomeActivity
import kotlin.jvm.java

class IntroActivity : BaseActivity<ActivityIntroBinding>() {
    private var checkStartHome = false
    private val adapter = IntroAdapter(this, itemIntroList)
    override fun setViewBinding(): ActivityIntroBinding {
        return ActivityIntroBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        initVpg()
    }

    override fun viewListener() {
        binding.btnNext.setOnClickListener { handleNext() }
    }

    override fun initText() {
        setGradientTextHeightColor(binding.btnNext, "#F52C2C", "#B10C0C")
    }

    override fun initActionBar() {}

    private fun initVpg() {
        binding.apply {
            binding.vpgTutorial.adapter = adapter
            binding.dotsIndicator.setViewPager2(binding.vpgTutorial)
        }
    }


    private fun handleNext() {
        binding.apply {
            val nextItem = binding.vpgTutorial.currentItem + 1
            if (nextItem < itemIntroList.size) {
                vpgTutorial.setCurrentItem(nextItem, true)
            } else {
                if (!checkStartHome) {
                    if (SystemUtils.getFirstPermission(this@IntroActivity)) {
                        checkStartHome = true
                        startIntentFromLeft(PermissionActivity::class.java)
                        finishAffinity()
                    } else {
                        checkStartHome = true
//                        layoutLoading.visible()
                        startIntentFromLeft(HomeActivity::class.java)
                        finishAffinity()
                    }
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        finishAffinity()
    }
}