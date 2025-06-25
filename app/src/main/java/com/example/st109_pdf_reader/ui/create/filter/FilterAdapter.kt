package com.example.st109_pdf_reader.ui.create.filter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.extensions.dp2px
import com.example.st109_pdf_reader.core.extensions.gone
import com.example.st109_pdf_reader.core.extensions.loadImageGlide
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.SystemUtils.shimmerDrawable
import com.example.st109_pdf_reader.data.model.create.FilterModel
import com.example.st109_pdf_reader.databinding.ItemFilterBinding
import kotlin.collections.forEachIndexed

class FilterAdapter(val context: Context) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {
    private val listFilter: ArrayList<FilterModel> = arrayListOf()
    var onItemClick: ((FilterModel, Int) -> Unit)? = null

    inner class FilterViewHolder(val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilterModel, position: Int) {
            loadImageGlide(binding.root, item.image, binding.imvImage)

            binding.apply {
                tvName.text = item.name

                if (item.isSelected) {
                    tvName.setTextColor(context.getColor(R.color.white))
                    tvName.setBackgroundResource(R.color.red_end)
                    viewFocus.visible()
                } else {
                    tvName.setTextColor(context.getColor(R.color.black_2D))
                    tvName.setBackgroundResource(R.color.white)
                    viewFocus.gone()
                }

                root.setOnClickListener {
                    onItemClick?.invoke(item, position)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listFilter.size
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val item = listFilter[position]
        holder.bind(item, position)
    }

    fun submitList(list: ArrayList<FilterModel>) {
        listFilter.clear()
        listFilter.addAll(list)
        notifyDataSetChanged()
    }

    fun submitItem(position: Int) {
        listFilter.forEachIndexed { index, filterModel ->
            filterModel.isSelected = index == position
        }
        notifyDataSetChanged()
    }
}