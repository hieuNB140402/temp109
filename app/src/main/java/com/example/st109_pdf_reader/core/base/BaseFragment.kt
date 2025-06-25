package com.example.st109_pdf_reader.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.st109_pdf_reader.core.utils.KeyApp.ALL_FILE
import com.example.st109_pdf_reader.core.utils.KeyApp.EXCEL
import com.example.st109_pdf_reader.core.utils.KeyApp.PDF
import com.example.st109_pdf_reader.core.utils.KeyApp.PPT
import com.example.st109_pdf_reader.core.utils.KeyApp.WORD
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.data.model.HomeAllFileModel

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected lateinit var binding: T

    protected abstract fun setViewBinding(inflater: LayoutInflater, container: ViewGroup?): T

    protected abstract fun initView()

    protected abstract fun viewListener()

    open fun dataObservable() {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        SystemUtils.setLocale(requireContext())
        binding = setViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewListener()
        dataObservable()
    }

    fun handleConvertFile(fileList: ArrayList<FilesModel>, isRecent: Boolean = false, isBookmark: Boolean = false, isSaved: Boolean = false): ArrayList<HomeAllFileModel> {
        val typeList = arrayListOf(ALL_FILE, WORD, EXCEL, PPT, PDF)
        val allFileList = ArrayList<HomeAllFileModel>()
        allFileList.clear()
        when{
            isRecent ->{
                typeList.forEach { type ->
                    var countFile = 0
                    fileList.forEach { file ->
                        if (file.type == type && file.isRecentFiles) {
                            countFile++
                        }
                    }
                    allFileList.add(HomeAllFileModel(type, countFile))
                }
            }
            isBookmark -> {
                typeList.forEach { type ->
                    var countFile = 0
                    fileList.forEach { file ->
                        if (file.type == type && file.isBookmark) {
                            countFile++
                        }
                    }
                    allFileList.add(HomeAllFileModel(type, countFile))
                }
            }
            isSaved -> {
                typeList.forEach { type ->
                    var countFile = 0
                    fileList.forEach { file ->
                        if (file.type == type && file.isSaved) {
                            countFile++
                        }
                    }
                    allFileList.add(HomeAllFileModel(type, countFile))
                }
            }
            else -> {
                typeList.forEach { type ->
                    var countFile = 0
                    fileList.forEach { file ->
                        if (file.type == type) {
                            countFile++
                        }
                    }
                    allFileList.add(HomeAllFileModel(type, countFile))
                }
            }
        }

        allFileList[0].quantity =
            allFileList[1].quantity + allFileList[2].quantity + allFileList[3].quantity + allFileList[4].quantity
        return allFileList
    }
}
