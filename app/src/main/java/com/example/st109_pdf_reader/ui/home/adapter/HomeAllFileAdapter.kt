package com.example.st109_pdf_reader.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.data.model.HomeAllFileModel
import com.example.st109_pdf_reader.databinding.ItemHomeBinding

class HomeAllFileAdapter(val context: Context) : RecyclerView.Adapter<HomeAllFileAdapter.HomeAllFileVH>() {
    private val allFileList = ArrayList<HomeAllFileModel>()
    var onItemClick: ((String) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): HomeAllFileVH {
        return HomeAllFileVH(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HomeAllFileVH, position: Int) {
        holder.bind(allFileList[position], position)
    }

    override fun getItemCount(): Int {
        return allFileList.size
    }

    inner class HomeAllFileVH(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeAllFileModel, position: Int) {
            when (item.type) {
                KeyApp.ALL_FILE -> {
                    binding.imvImage.setImageResource(R.drawable.img_all_file_home)
                    binding.tvName.text = context.getString(R.string.all_file_count, item.quantity)
                }

                KeyApp.WORD -> {
                    binding.imvImage.setImageResource(R.drawable.img_word_home)
                    binding.tvName.text = context.getString(R.string.word_count, item.quantity)
                }

                KeyApp.EXCEL -> {
                    binding.imvImage.setImageResource(R.drawable.img_excel_home)
                    binding.tvName.text = context.getString(R.string.excel_count, item.quantity)
                }

                KeyApp.PPT -> {
                    binding.imvImage.setImageResource(R.drawable.img_ppt_home)
                    binding.tvName.text = context.getString(R.string.ppt_count, item.quantity)
                }

                KeyApp.PDF -> {
                    binding.imvImage.setImageResource(R.drawable.img_pdf_home)
                    binding.tvName.text = context.getString(R.string.pdf_count, item.quantity)
                }
            }
            binding.root.setOnSingleClick {
                onItemClick?.invoke(item.type)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged") fun submitList(list: ArrayList<HomeAllFileModel>) {
        allFileList.clear()
        allFileList.addAll(list)
        notifyDataSetChanged()
    }
}