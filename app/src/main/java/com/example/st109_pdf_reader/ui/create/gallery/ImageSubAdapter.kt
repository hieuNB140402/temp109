package com.example.st109_pdf_reader.ui.create.gallery

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.st109_pdf_reader.core.extensions.dp2px
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.loadImageGlide
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.SystemUtils.shimmerDrawable
import com.example.st109_pdf_reader.data.model.create.ImageModel
import com.example.st109_pdf_reader.databinding.ItemImageSubBinding

class ImageSubAdapter(val context: Context) : RecyclerView.Adapter<ImageSubAdapter.ImageSubViewHolder>() {
    private val listImageSub: ArrayList<ImageModel> = arrayListOf()
    var onItemClick: ((ImageModel, Int) -> Unit)? = null

    inner class ImageSubViewHolder(val binding: ItemImageSubBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageModel, position: Int) {
            loadImageGlide(binding.root, item.image, binding.imgImage)

            if (item.isSelected) {
                binding.imvTick.visible()
                binding.layoutTick.visible()
                binding.cvImage.strokeWidth = dp2px(context, 2)
            } else {
                binding.imvTick.gone()
                binding.layoutTick.gone()
                binding.cvImage.strokeWidth = dp2px(context, 0)
            }

            binding.root.setOnClickListener {
                onItemClick?.invoke(item, position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSubViewHolder {
        return ImageSubViewHolder(ItemImageSubBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listImageSub.size
    }

    override fun onBindViewHolder(holder: ImageSubViewHolder, position: Int) {
        val item = listImageSub[position]
        holder.bind(item, position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<ImageModel>) {
        listImageSub.clear()
        listImageSub.addAll(list)
        notifyDataSetChanged()
    }

    fun submitItem(position: Int, selected: Boolean) {
        if (position in listImageSub.indices) {
            listImageSub[position].isSelected = selected
            notifyItemChanged(position)
        } else {
            System.err.println("Invalid position: $position in submitItem.")
        }
    }
}