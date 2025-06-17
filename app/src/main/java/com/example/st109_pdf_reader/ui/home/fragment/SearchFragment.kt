package com.example.st109_pdf_reader.ui.home.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseFragment
import com.example.st109_pdf_reader.core.extensions.*
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.data.model.HomeAllFileModel
import com.example.st109_pdf_reader.databinding.FragmentSearchBinding
import com.example.st109_pdf_reader.ui.home.HomeActivity
import com.example.st109_pdf_reader.ui.home.adapter.SearchAdapter
import com.example.st109_pdf_reader.ui.home.adapter.TypeFileSearchAdapter
import com.example.st109_pdf_reader.ui.pdf.PdfActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val typeAdapter by lazy { TypeFileSearchAdapter(requireActivity()) }
    private val searchAdapter by lazy { SearchAdapter(requireActivity()) }

    private val typeList = ArrayList<HomeAllFileModel>()
    private val searchList = ArrayList<FilesModel>()

    private var type = KeyApp.ALL_FILE
    private var query = ""

    private val homeActivity: HomeActivity
        get() = activity as HomeActivity

    override fun setViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        initRecyclerViews()
        searchFiles(query)
    }

    override fun viewListener() {
        binding.btnBack.setOnSingleClick {
            backFragmentSlideInFromLeft(homeActivity.binding.containerFragment) {
                homeActivity.supportFragmentManager.popBackStack()
                homeActivity.isFragmentOther = false
            }
        }
        handleRcv()
        handleSearch()
    }

    override fun dataObservable() {
        super.dataObservable()
        lifecycleScope.launch {
            homeActivity.fileViewModel.filesFlow.collectLatest {
                submitAdapter(ArrayList(it))
            }
        }
    }

    private fun initRecyclerViews() = binding.apply {
        rcvType.apply {
            adapter = typeAdapter
            itemAnimator = null
        }
        rcvFile.apply {
            adapter = searchAdapter
            itemAnimator = null
        }
    }

    private fun handleRcv() {
        typeAdapter.onItemClick = { typeModel -> handleSelectType(typeModel) }

        searchAdapter.onItemClick = { file -> handleOpenFile(file) }
        binding.rcvFile.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return if (e.action == MotionEvent.ACTION_UP &&
                    rv.findChildViewUnder(e.x, e.y) == null
                ) {
                    hideKeyBoard()
                    true
                } else {
                    false
                }
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleSearch() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                query = s.toString()
                searchFiles(query)
            }
        })

        binding.edtSearch.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = binding.edtSearch.compoundDrawables[2]
                drawableEnd?.let {
                    val touchX = event.rawX
                    val viewRight = binding.edtSearch.right
                    val drawableWidth = it.bounds.width()
                    val paddingEnd = binding.edtSearch.paddingEnd

                    if (touchX >= (viewRight - drawableWidth - paddingEnd)) {
                        searchAdapter.submitList(searchList)
                        hideKeyBoard()
                        Log.d("nbhieu", "DrawableEnd clicked")
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }

    private fun handleSelectType(typeModel: HomeAllFileModel) {
        type = typeModel.type
        updateHeaderBackground()
    }

    private fun updateHeaderBackground() {
        val colorRes = when (type) {
            KeyApp.WORD -> R.color.word
            KeyApp.EXCEL -> R.color.excel
            KeyApp.PPT -> R.color.ppt
            KeyApp.PDF, KeyApp.ALL_FILE -> R.color.pdf
            else -> R.color.pdf
        }
        binding.header.setBackgroundResource(colorRes)
        searchFiles(query)
        hideKeyBoard()
    }

    private fun searchFiles(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            homeActivity.fileViewModel.searchFiles(query.trim(), type).collectLatest { resultList ->
                searchList.clear()
                searchList.addAll(resultList)
                searchAdapter.submitList(searchList)
                requireActivity().dLog("searchList: $searchList")
                checkListSize()
            }
        }
    }

    private fun submitAdapter(fileList: ArrayList<FilesModel>) {
        typeList.clear()
        typeList.addAll(handleConvertFile(fileList, isBookmark = true))
        val positionType = typeList.indexOfFirst { it.type == type }
        if (positionType != -1) {
            typeList[positionType].isSelected = true
        }
        typeAdapter.submitList(typeList)
    }

    private fun checkListSize() = binding.run {
        if (searchList.isEmpty()) {
            layoutNoItem.visible()
            rcvFile.gone()
        } else {
            layoutNoItem.gone()
            rcvFile.visible()
        }
    }

    private fun hideKeyBoard() {
        homeActivity.hideSoftKeyboard()
        binding.edtSearch.clearFocus()
    }

    private fun handleOpenFile(file: FilesModel) {
        val intent = if (file.type != KeyApp.PDF) {
            Intent(homeActivity, ViewActivity::class.java)
        } else {
            Intent(homeActivity, PdfActivity::class.java)
        }
        homeActivity.fileViewModel.updateRecentFile(file.id, true)
        intent.putExtra(KeyApp.KeyIntent.INTENT_KEY, file)
        startActivity(intent)
    }
}
