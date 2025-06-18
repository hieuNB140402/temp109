package com.example.st109_pdf_reader.ui.create.gallery

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.st109_pdf_reader.core.extensions.loadImageGlide
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.utils.SystemUtils.shimmerDrawable
import com.example.st109_pdf_reader.data.model.create.ImageSelectedModel
import com.example.st109_pdf_reader.databinding.ItemImageSelectedBinding

class ImageSelectedAdapter(val context: Context) :
    RecyclerView.Adapter<ImageSelectedAdapter.ImageSelectedViewHolder>() {

    private val listImageSelected: ArrayList<ImageSelectedModel> = arrayListOf()
    var onDeleteClick: ((ImageSelectedModel) -> Unit)? = null

    inner class ImageSelectedViewHolder(val binding: ItemImageSelectedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageSelectedModel, position: Int) {
            loadImageGlide(binding.root, item.image, binding.imvImage)
            binding.root.setOnSingleClick {
                onDeleteClick?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSelectedViewHolder {
        return ImageSelectedViewHolder(
            ItemImageSelectedBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return listImageSelected.size
    }

    override fun onBindViewHolder(holder: ImageSelectedViewHolder, position: Int) {
        val item = listImageSelected[position]
        holder.bind(item, position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<ImageSelectedModel>) {
        listImageSelected.clear()
        listImageSelected.addAll(list)
        notifyDataSetChanged()
    }
}