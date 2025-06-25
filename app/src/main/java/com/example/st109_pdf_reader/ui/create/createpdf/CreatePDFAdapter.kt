package com.example.st109_pdf_reader.ui.create.createpdf

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.extensions.loadImageGlide
import com.example.st109_pdf_reader.core.extensions.pxToDpInt
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.data.model.create.CreatePDFModel
import com.example.st109_pdf_reader.databinding.ItemCreatePdfBinding

class CreatePDFAdapter(val context: Context) :
    RecyclerView.Adapter<CreatePDFAdapter.CreatePDFVH>() {
    private val listImage: ArrayList<CreatePDFModel> = arrayListOf()
    var onItemClick: ((CreatePDFModel, Int) -> Unit)? = null
    var onDragEnd: ((List<CreatePDFModel>) -> Unit)? = null

    inner class CreatePDFVH(val binding: ItemCreatePdfBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CreatePDFModel, position: Int) {
            loadImageGlide(binding.root, item.path, binding.imvImage)
            val positionText = position + 1
            binding.tvPosition.text = "$positionText"

            if (item.isSelected) {
                binding.cvParent.strokeColor = context.getColor(R.color.red_end)
                binding.cvParent.strokeWidth = pxToDpInt(context, 2)
            }else{
                binding.cvParent.strokeColor = context.getColor(R.color.gray_A7)
                binding.cvParent.strokeWidth = pxToDpInt(context, 1)
            }

            binding.root.setOnSingleClick {
                onItemClick?.invoke(item, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatePDFVH {
        return CreatePDFVH(
            ItemCreatePdfBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listImage.size
    }

    override fun onBindViewHolder(holder: CreatePDFVH, position: Int) {
        val item = listImage[position]
        holder.bind(item, position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<CreatePDFModel>) {
        listImage.clear()
        listImage.addAll(list)
        notifyDataSetChanged()
    }
    fun moveItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition < 0 || toPosition < 0 || fromPosition >= listImage.size || toPosition >= listImage.size) return

        val movedItem = listImage.removeAt(fromPosition)
        listImage.add(toPosition, movedItem)
        notifyItemMoved(fromPosition, toPosition)
    }
    fun getCurrentList(): List<CreatePDFModel> {
        return listImage
    }

}
