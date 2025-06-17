package com.example.st109_pdf_reader.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.extensions.invisible
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick
import com.example.st109_pdf_reader.core.extensions.visible
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.data.model.HomeAllFileModel
import com.example.st109_pdf_reader.databinding.ItemTypeFileBinding

class TypeFileAdapter(val context: Context) : RecyclerView.Adapter<TypeFileAdapter.TypeFileVH>() {
    val typeList = ArrayList<HomeAllFileModel>()
    var onItemClick: ((HomeAllFileModel) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TypeFileVH {
        return TypeFileVH(
            ItemTypeFileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: TypeFileVH,
        position: Int
    ) {
        holder.bind(typeList[position], position)
    }

    override fun getItemCount(): Int {
        return typeList.size
    }

    inner class TypeFileVH(val binding: ItemTypeFileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(type: HomeAllFileModel, position: Int) {
            when (type.type) {
                KeyApp.ALL_FILE -> {
                    binding.tvType.text = context.getString(R.string.all_count, type.quantity)
                }

                KeyApp.WORD -> {
                    binding.tvType.text = context.getString(R.string.word_count, type.quantity)
                }

                KeyApp.EXCEL -> {
                    binding.tvType.text = context.getString(R.string.excel_count, type.quantity)
                }

                KeyApp.PPT -> {
                    binding.tvType.text = context.getString(R.string.ppt_count, type.quantity)
                }

                KeyApp.PDF -> {
                    binding.tvType.text = context.getString(R.string.pdf_count, type.quantity)
                }
            }

            if (!type.isSelected) {
                binding.tvType.setTextColor(context.getColor(R.color.black_2D))
                binding.tvType.typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
                binding.lineType.invisible()
            } else {
                when (type.type) {
                    KeyApp.ALL_FILE -> {
                        binding.tvType.setTextColor(context.getColor(R.color.pdf))
                        binding.lineType.setBackgroundResource(R.color.pdf)

                    }

                    KeyApp.WORD -> {
                        binding.tvType.setTextColor(context.getColor(R.color.word))
                        binding.lineType.setBackgroundResource(R.color.word)
                    }

                    KeyApp.EXCEL -> {
                        binding.tvType.setTextColor(context.getColor(R.color.excel))
                        binding.lineType.setBackgroundResource(R.color.excel)
                    }

                    KeyApp.PPT -> {
                        binding.tvType.setTextColor(context.getColor(R.color.ppt))
                        binding.lineType.setBackgroundResource(R.color.ppt)
                    }

                    KeyApp.PDF -> {
                        binding.tvType.setTextColor(context.getColor(R.color.pdf))
                        binding.lineType.setBackgroundResource(R.color.pdf)
                    }
                }
                binding.tvType.typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)
                binding.lineType.visible()
            }

            binding.root.setOnSingleClick {
                onItemClick?.invoke(type)
                submitItem(position)
            }
        }
    }

    fun submitList(list: ArrayList<HomeAllFileModel>) {
        typeList.clear()
        typeList.addAll(list)
        notifyDataSetChanged()
    }

    private fun submitItem(position: Int) {
        typeList.forEachIndexed { index, type ->
            type.isSelected = index == position
        }
        notifyDataSetChanged()
    }
}