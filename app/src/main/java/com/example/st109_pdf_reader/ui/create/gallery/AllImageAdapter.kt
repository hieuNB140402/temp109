package com.example.st109_pdf_reader.ui.create.gallery

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.st109_pdf_reader.core.extensions.loadImageGlide
import com.example.st109_pdf_reader.core.utils.SystemUtils.shimmerDrawable
import com.example.st109_pdf_reader.data.model.create.AllImageModel
import com.example.st109_pdf_reader.databinding.ItemAllImageBinding

class AllImageAdapter(val context: Context) :
    RecyclerView.Adapter<AllImageAdapter.AllImageViewHolder>() {
    private val listAllImage: ArrayList<AllImageModel> = arrayListOf()
    var onItemClick: ((AllImageModel, Int) -> Unit)? = null

    inner class AllImageViewHolder(val binding: ItemAllImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AllImageModel, position: Int) {
            loadImageGlide(binding.root, item.imageFirst, binding.imvImage)

            binding.txtNameFolder.text = item.nameFolder

            binding.txtQuantityImage.text = item.size.toString()

            binding.root.setOnClickListener {
                onItemClick?.invoke(item, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllImageViewHolder {
        return AllImageViewHolder(
            ItemAllImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listAllImage.size
    }

    override fun onBindViewHolder(holder: AllImageViewHolder, position: Int) {
        val item = listAllImage[position]
        holder.bind(item, position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<AllImageModel>) {
        listAllImage.clear()
        listAllImage.addAll(list)
        notifyDataSetChanged()
    }
}
