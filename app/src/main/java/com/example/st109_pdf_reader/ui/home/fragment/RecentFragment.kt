package com.example.st109_pdf_reader.ui.home.fragment

import android.R.attr.type
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseFragment
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.data.model.HomeAllFileModel
import com.example.st109_pdf_reader.databinding.FragmentReaderBinding
import com.example.st109_pdf_reader.databinding.FragmentRecentBinding
import com.example.st109_pdf_reader.ui.home.HomeActivity
import com.example.st109_pdf_reader.ui.home.adapter.TypeFileAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecentFragment : BaseFragment<FragmentRecentBinding>() {
    private val recentAdapter by lazy {
        TypeFileAdapter(requireActivity())
    }
    private val typeList = ArrayList<HomeAllFileModel>()
    private val recentList = ArrayList<FilesModel>()
    private var type = KeyApp.ALL_FILE
    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecentBinding {
        return FragmentRecentBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        initRcv()
    }

    override fun viewListener() {
        handleRcv()
    }

    private fun initRcv() {
        binding.apply {
            rcvTypeFile.adapter = recentAdapter
            rcvTypeFile.itemAnimator = null


        }
    }

    override fun dataObservable() {
        super.dataObservable()
        val homeActivity = (activity as HomeActivity)
        lifecycleScope.launch {
            homeActivity.fileViewModel.filesFlow.collectLatest {
                submitAdapter(it.toCollection(ArrayList<FilesModel>()))
            }
        }
    }

    private fun submitAdapter(fileList: ArrayList<FilesModel>) {
        typeList.clear()
        typeList.addAll(handleConvertFile(fileList))
        typeList[0].isSelected = true
        recentAdapter.submitList(typeList)
    }

    private fun handleRcv() {
        recentAdapter.onItemClick = { typeModel ->
            handleSelectType(typeModel)
        }
    }

    private fun handleSelectType(typeModel: HomeAllFileModel) {
        val homeActivity = (activity as HomeActivity)
        type = typeModel.type
        when (type) {
            KeyApp.ALL_FILE -> {
                homeActivity.binding.actionBar.layoutHeader.setBackgroundResource(R.color.pdf)
            }

            KeyApp.WORD -> {
                homeActivity.binding.actionBar.layoutHeader.setBackgroundResource(R.color.word)
            }

            KeyApp.EXCEL -> {
                homeActivity.binding.actionBar.layoutHeader.setBackgroundResource(R.color.excel)
            }

            KeyApp.PPT -> {
                homeActivity.binding.actionBar.layoutHeader.setBackgroundResource(R.color.ppt)
            }

            KeyApp.PDF -> {
                homeActivity.binding.actionBar.layoutHeader.setBackgroundResource(R.color.pdf)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val homeActivity = (activity as HomeActivity)
        when (type) {
            KeyApp.ALL_FILE -> {
                homeActivity.binding.actionBar.layoutHeader.setBackgroundResource(R.color.pdf)
            }

            KeyApp.WORD -> {
                homeActivity.binding.actionBar.layoutHeader.setBackgroundResource(R.color.word)
            }

            KeyApp.EXCEL -> {
                homeActivity.binding.actionBar.layoutHeader.setBackgroundResource(R.color.excel)
            }

            KeyApp.PPT -> {
                homeActivity.binding.actionBar.layoutHeader.setBackgroundResource(R.color.ppt)
            }

            KeyApp.PDF -> {
                homeActivity.binding.actionBar.layoutHeader.setBackgroundResource(R.color.pdf)
            }
        }
    }
}