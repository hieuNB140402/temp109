package com.example.st109_pdf_reader.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.extensions.formatFileSize
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.invisible
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.databinding.ItemReaderBinding

class ReaderAdapter(val context: Context) : RecyclerView.Adapter<ReaderAdapter.ReaderVH>() {
    private val fileList = ArrayList<FilesModel>()
    var onBookmarkClick: ((FilesModel, Int) -> Unit)? = null
    var onLongClick: ((Int) -> Unit)? = null
    var onItemSelect: ((Int) -> Unit)? = null
    var onMoreClick: ((FilesModel, Int, View) -> Unit)? = null
    var onItemClick: ((FilesModel) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ReaderVH {
        return ReaderVH(ItemReaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ReaderVH, position: Int) {
        holder.bind(fileList[position], position)
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    inner class ReaderVH(val binding: ItemReaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(file: FilesModel, position: Int) {
            // Image
            val resourceImage = when (file.type) {
                KeyApp.WORD -> {
                    R.drawable.ic_word_reader
                }

                KeyApp.EXCEL -> {
                    R.drawable.ic_excel_reader
                }

                KeyApp.PPT -> {
                    R.drawable.ic_ppt_reader
                }

                else -> {
                    R.drawable.ic_pdf_reader
                }
            }
            binding.imvType.setImageResource(resourceImage)
            // Name + Description
            val size = formatFileSize(file.sizeInBytes.toLong())
            val fullInformation = "${file.date} | ${file.time} | $size"
            binding.tvName.text = file.name
            binding.tvInformationOther.text = fullInformation
            // Show Long Click
            if (file.isShow) {
                binding.btnSelect.visible()
                binding.btnBookmark.gone()
                binding.btnMore.invisible()
            }
            else {
                binding.btnSelect.gone()
                binding.btnBookmark.visible()
                binding.btnMore.visible()
            }
            // Select
            val resourceSelect = if (file.isSelected) {
                R.drawable.ic_selected
            }
            else {
                R.drawable.ic_not_select
            }
            binding.btnSelect.setImageResource(resourceSelect)
            //  Bookmark
            val resourceBookmark = if (file.isBookmark) {
               R.drawable.ic_bookmark_selected_view
            }
            else {
                R.drawable.ic_not_bookmark
            }
            binding.btnBookmark.setImageResource(resourceBookmark)
            // Event
            binding.btnBookmark.setOnSingleClick { onBookmarkClick?.invoke(file, position) }

            if (!file.isShow) {
                binding.root.setOnLongClickListener {
                    onLongClick?.invoke(position)
                    return@setOnLongClickListener true
                }
            }

            binding.btnSelect.setOnSingleClick {
                onItemSelect?.invoke(position)
            }

            binding.btnMore.setOnSingleClick {
                onMoreClick?.invoke(file, position, it)
            }

            binding.root.setOnSingleClick { onItemClick?.invoke(file) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<FilesModel>) {
        fileList.clear()
        fileList.addAll(list)
        notifyDataSetChanged()
    }

    fun submitItemBookmark(position: Int) {
        notifyItemChanged(position)
    }
    fun submitItemSelected(position: Int, isSelect: Boolean) {
        fileList[position].isSelected = isSelect
        notifyItemChanged(position)
    }
}