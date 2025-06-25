package com.example.st109_pdf_reader.ui.create.filter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.loadImageGlide
import com.example.st109_pdf_reader.core.extensions.pxToDpInt
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.data.model.create.CreatePDFModel
import com.example.st109_pdf_reader.databinding.ItemPageBinding

class PageAdapter(val context: Context) :
    RecyclerView.Adapter<PageAdapter.CharacterCatViewHolder>() {
    private val imageList = ArrayList<CreatePDFModel>()

    inner class CharacterCatViewHolder(val binding: ItemPageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CreatePDFModel, position: Int) {
            binding.apply {
                loadImageGlide(binding.root, item.path, imvImage)
                if (item.isSelected) {
                    cvParent.strokeWidth = pxToDpInt(context, 3)
                    cvParent.strokeColor = context.getColor(R.color.red_end)
                    viewBlur.gone()
                } else {
                    cvParent.strokeWidth = pxToDpInt(context, 1)
                    cvParent.strokeColor = context.getColor(R.color.gray_A7)
                    viewBlur.visible()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterCatViewHolder {
        return CharacterCatViewHolder(
            ItemPageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: CharacterCatViewHolder, position: Int) {
        val item = imageList[position]
        holder.bind(item, position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<CreatePDFModel>) {
        imageList.clear()
        imageList.addAll(list)
        notifyDataSetChanged()
    }

    fun selectItem(position: Int) {
        imageList[position].isSelected = true
        notifyItemChanged(position)
        if (position != 0) {
            imageList[position - 1].isSelected = false
            notifyItemChanged(position - 1)
        }
        if (position != imageList.size - 1) {
            imageList[position + 1].isSelected = false
            notifyItemChanged(position + 1)
        }
    }
}