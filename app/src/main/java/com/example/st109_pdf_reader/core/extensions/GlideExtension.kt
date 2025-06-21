package com.example.st109_pdf_reader.core.extensions

import android.content.Context
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.facebook.shimmer.ShimmerDrawable

fun loadImageGlide(context: Context, path: String, imageView: ImageView) {
    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(SystemUtils.shimmer)
    }
    Glide.with(context).load(path).placeholder(shimmerDrawable).error(shimmerDrawable).into(imageView)
}
fun loadImageGlide(constraintLayout: ConstraintLayout, path: String, imageView: ImageView) {
    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(SystemUtils.shimmer)
    }
    Glide.with(constraintLayout).load(path).placeholder(shimmerDrawable).error(shimmerDrawable).into(imageView)
}
fun loadImageGlide(constraintLayout: ConstraintLayout, path: Int, imageView: ImageView) {
    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(SystemUtils.shimmer)
    }
    Glide.with(constraintLayout).load(path).placeholder(shimmerDrawable).error(shimmerDrawable).into(imageView)
}
fun loadImageGlide(frameLayout: FrameLayout, path: String, imageView: ImageView) {
    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(SystemUtils.shimmer)
    }
    Glide.with(frameLayout).load(path).placeholder(shimmerDrawable).error(shimmerDrawable).into(imageView)
}