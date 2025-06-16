package com.example.st109_pdf_reader.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseFragment
import com.example.st109_pdf_reader.core.extensions.setGradientTextHeightColor
import com.example.st109_pdf_reader.core.extensions.startFragmentSlideInFromRight
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.core.utils.KeyApp.ALL_FILE
import com.example.st109_pdf_reader.core.utils.KeyApp.EXCEL
import com.example.st109_pdf_reader.core.utils.KeyApp.PDF
import com.example.st109_pdf_reader.core.utils.KeyApp.PPT
import com.example.st109_pdf_reader.core.utils.KeyApp.WORD
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.data.model.HomeAllFileModel
import com.example.st109_pdf_reader.databinding.FragmentHomeBinding
import com.example.st109_pdf_reader.ui.home.HomeActivity
import com.example.st109_pdf_reader.ui.home.adapter.HomeAllFileAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val homeAllFileAdapter by lazy {
        HomeAllFileAdapter(requireActivity())
    }
    private val allFileList = ArrayList<HomeAllFileModel>()
    override fun setViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        initText()
        initRcv()
        binding.swrTypeHome.setColorSchemeResources(R.color.red_end)

    }

    override fun viewListener() {
        binding.swrTypeHome.setOnRefreshListener {
            (activity as HomeActivity).fileViewModel.refreshScan(requireActivity())
            binding.swrTypeHome.isRefreshing = false
        }
        handleRcv()
    }

    private fun initText() {
        setGradientTextHeightColor(binding.tvCreate, "#F52C2C", "#B10C0C")
    }

    private fun initRcv() {
        binding.rcvTypeHome.adapter = homeAllFileAdapter
        binding.rcvTypeHome.itemAnimator = null
    }

    private fun handleRcv() {
        homeAllFileAdapter.onItemClick = { type ->
            startFragment(type)
        }
    }

    private fun startFragment(type: String) {
        val fragment = ReaderFragment().apply {
            arguments = Bundle().apply {
                putString(KeyApp.KeyIntent.INTENT_KEY, type)
            }
        }
        startFragmentSlideInFromRight((activity as HomeActivity).binding.containerFragment)

        fragment.let {
            requireActivity().supportFragmentManager.beginTransaction().replace((activity as HomeActivity).binding.containerFragment.id, fragment)
                .addToBackStack(null).commit()
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

    private fun submitAdapter(fileList: ArrayList<FilesModel>){
        allFileList.clear()
        allFileList.addAll(handleConvertFile(fileList))
        homeAllFileAdapter.submitList(allFileList)
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).binding.actionBar.layoutHeader.setBackgroundResource(R.color.pdf)
    }
}