package com.example.st109_pdf_reader.ui.language

import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseActivity
import com.example.st109_pdf_reader.core.extensions.handleBackLeftToRight
import com.example.st109_pdf_reader.core.extensions.select
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.showToast
import com.example.st109_pdf_reader.core.extensions.startIntent
import com.example.st109_pdf_reader.core.extensions.startIntentFromLeft
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.DataLocal
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.data.model.LanguageModel
import com.example.st109_pdf_reader.databinding.ActivityLanguageBinding
import com.example.st109_pdf_reader.ui.home.HomeActivity
import com.example.st109_pdf_reader.ui.intro.IntroActivity
import kotlin.jvm.java
import kotlin.system.exitProcess

class LanguageActivity : BaseActivity<ActivityLanguageBinding>() {
    private var listLanguage: ArrayList<LanguageModel> = arrayListOf()
    private var codeLang: String = ""
    private var isFirstAccess: Boolean = true
    private val languageAdapter by lazy {
        LanguageAdapter(this)
    }

    override fun setViewBinding(): ActivityLanguageBinding {
        return ActivityLanguageBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        initData()
        initRcv()
    }

    override fun viewListener() {
        binding.apply {
            actionBar.btnActionBarLeft.setOnSingleClick { handleBackLeftToRight() }
            actionBar.btnActionBarRight.setOnSingleClick { handleDone() }
        }
        handleRcv()
    }

    override fun initText() {
    }

    override fun initActionBar() {
        binding.actionBar.apply {
            btnActionBarRight.setImageResource(R.drawable.ic_done)
            btnActionBarRight.visible()
            tvCenter.select()
            tvStart.select()
            tvCenter.text = getString(R.string.language)
            tvCenter.visible()
        }
    }

    private fun initData() {
        binding.apply {
            val intent = intent.getStringExtra(KeyApp.KeyIntent.INTENT_KEY)
            codeLang = SystemUtils.getPreLanguage(this@LanguageActivity)
            if (intent != null) {
                isFirstAccess = false
                actionBar.apply {
                    btnActionBarLeft.setImageResource(R.drawable.ic_back_black)
                    btnActionBarLeft.visible()
                }
            } else {
                isFirstAccess = true
            }
        }
    }

    private fun initRcv() {
        binding.apply {
            listLanguage.clear()
            listLanguage.addAll(DataLocal.getLanguageList())

            val lang = SystemUtils.getPreLanguage(this@LanguageActivity)

            for (i in listLanguage.indices) {
                if (listLanguage[i].code == lang) {
                    val matchingLanguage = listLanguage[i]
                    listLanguage.removeAt(i)
                    listLanguage.add(0, matchingLanguage)

                    if (!SystemUtils.getFirstLang(this@LanguageActivity)) {
                        listLanguage[0].activate = true
                    }
                    break
                }
            }

            if (isFirstAccess) {
                codeLang = "en"
                listLanguage[0].activate = true
            }
            rcv.adapter = languageAdapter
            rcv.itemAnimator = null
            languageAdapter.submitList(listLanguage)
        }
    }

    private fun handleRcv() {
        binding.apply {
            languageAdapter.onItemClick = { item ->
                codeLang = item.code
                languageAdapter.submitItem(item.code)
            }
        }
    }

    private fun handleDoneFirst() {
        if (codeLang == "") {
            showToast(R.string.not_select_lang)
        } else {
            SystemUtils.setPreLanguage(this@LanguageActivity, codeLang)
            SystemUtils.setFirstLang(this, false)
            startIntentFromLeft(IntroActivity::class.java)
            finishAffinity()
        }
    }

    private fun handleChangeSetting() {
        SystemUtils.setPreLanguage(this@LanguageActivity, codeLang)
        startIntent(HomeActivity::class.java)
        finishAffinity()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (!SystemUtils.getFirstLang(this)) {
            handleBackLeftToRight()
        } else {
            exitProcess(0)
        }
    }

    private fun handleDone() {
        if (isFirstAccess) {
            handleDoneFirst()
        } else {
            handleChangeSetting()
        }
    }
}