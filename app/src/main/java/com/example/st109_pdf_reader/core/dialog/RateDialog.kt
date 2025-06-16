package com.example.st109_pdf_reader.core.dialog

import android.app.Activity
import android.widget.Toast
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.base.BaseDialog2
import com.example.st109_pdf_reader.databinding.DialogRateBinding
import com.example.st109_pdf_reader.core.extensions.setOnSingleClick

class RateDialog(context: Activity) : BaseDialog2<DialogRateBinding>(context, false) {
    var i = 0
    var onRateGreater3: (() -> Unit)? = null
    var onRateLess3: (() -> Unit)? = null
    var onCancel: (() -> Unit)? = null
    override fun getContentView(): Int = R.layout.dialog_rate

    override fun initView() {
    }


    override fun bindView() {
        binding.btnCancel.setOnSingleClick {
            onCancel?.invoke()
        }
        binding.btnVote.setOnClickListener {
            when (i) {
                0 -> {
                    Toast.makeText(context, context.getText(R.string.rate_us_0), Toast.LENGTH_SHORT).show()
                }

                in 1..3 -> {
                    onRateLess3?.invoke()
                }

                else -> {
                    onRateGreater3?.invoke()
                }
            }
        }
        binding.ll1.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            i = rating.toInt()
            when (i) {
                0 -> {
                    setView(R.string.zero_star_title, R.string.zero_star, R.drawable.ic_rate_zero)
                }

                1 -> {
                    setView(R.string.one_start_title, R.string.one_start, R.drawable.ic_rate_one)
                }

                2 -> {
                    setView(R.string.one_start_title, R.string.one_start, R.drawable.ic_rate_two)
                }

                3 -> {
                    setView(R.string.one_start_title, R.string.one_start, R.drawable.ic_rate_three)
                }

                4 -> {
                    setView(R.string.four_start_title, R.string.four_start, R.drawable.ic_rate_four)
                }

                5 -> {
                    setView(R.string.four_start_title, R.string.four_start, R.drawable.ic_rate_five)
                }
            }
        }
    }

    fun setView(tv1: Int, tv2: Int, img: Int) {
        binding.tv1.text = (context.resources.getString(tv1))
        binding.tv2.text = (context.resources.getString(tv2))
        binding.imvAvtRate.setImageResource(img)
    }
}