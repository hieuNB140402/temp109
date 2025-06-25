package com.example.st109_pdf_reader.ui.language

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.data.model.LanguageModel
import com.example.st109_pdf_reader.databinding.ItemLanguageBinding

class LanguageAdapter(val context: Context) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {
    private val languageList = ArrayList<LanguageModel>()
    var onItemClick: ((LanguageModel) -> Unit)? = null
    private var currentActive = 0
    inner class LanguageViewHolder(private val binding: ItemLanguageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LanguageModel, position: Int) {
            binding.apply {
                imvFlag.setImageResource(item.flag)
                txtLang.text = item.name
                if (item.activate) {
                    itemLang.background = ContextCompat.getDrawable(context, R.drawable.bg_8_gradient_ver)
                    rdbLang.setImageResource(R.drawable.ic_tick_lang)
                    txtLang.setTextColor(ContextCompat.getColor(context, R.color.white))
                    txtLang.typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)

                } else {
                    itemLang.background = ContextCompat.getDrawable(context, R.drawable.bg_8_stroke_gray_solid_white)
                    rdbLang.setImageResource(R.drawable.ic_not_tick_lang)
                    txtLang.setTextColor(ContextCompat.getColor(context, R.color.black))
                    txtLang.typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
                }

                itemLang.setOnClickListener {
                    languageList[currentActive].activate = false
                    notifyItemChanged(currentActive)
                    currentActive = position
                    languageList[currentActive].activate = true
                    notifyItemChanged(currentActive)
                    onItemClick?.invoke(item)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return languageList.size
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val item = languageList[position]
        holder.bind(item, position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<LanguageModel>) {
        languageList.clear()
        languageList.addAll(list)
        notifyDataSetChanged()
    }

    fun submitItem(code: String) {
        languageList.forEach {
            it.activate = it.code == code
        }
    }
}