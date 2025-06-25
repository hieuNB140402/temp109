package com.example.st109_pdf_reader.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseActivity
import com.example.st109_pdf_reader.core.extensions.startIntentFromLeft
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.databinding.ActivitySplashBinding
import com.example.st109_pdf_reader.ui.intro.IntroActivity
import com.example.st109_pdf_reader.ui.language.LanguageActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private var check = false
    override fun setViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        if (!isTaskRoot && intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intent.action != null && intent.action.equals(Intent.ACTION_MAIN)) {
            finish(); return;
        }

        CoroutineScope(Job() + Dispatchers.Main).launch{
            delay(3000)
            if (SystemUtils.getFirstLang(this@SplashActivity)) {
                startIntentFromLeft(LanguageActivity::class.java)
                check = true
                finishAffinity()
            } else {
                startIntentFromLeft(IntroActivity::class.java)
                check = true
                finishAffinity()
            }
        }
    }

    override fun viewListener() {
    }

    override fun initText() {
        binding.apply {

        }
    }

    override fun initActionBar() {
    }

    override fun onBackPressed() {
        if (check) {
            super.onBackPressed()
        } else {
            check = false
        }
    }

}