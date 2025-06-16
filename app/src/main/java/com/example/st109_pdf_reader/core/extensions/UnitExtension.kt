package com.example.st109_pdf_reader.core.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

internal fun dp2px(context: Context, dpVal: Int): Int {
    return (dpVal * context.resources.displayMetrics.density).toInt()
}

internal fun sp2px(spVal: Float): Int {
    val r = Resources.getSystem()
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, r.displayMetrics).toInt()
}

fun pxToDpInt(context: Context, px: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, px.toFloat(), context.resources.displayMetrics).toInt()
}

fun pxToDpFloat(context: Context, px: Int): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, px.toFloat(), context.resources.displayMetrics).toFloat()
}

fun Activity.dpToPx(dp: Int): Int {
    return (dp * this.resources.displayMetrics.density).toInt()
}
fun formatFileSize(sizeInBytes: Long): String {
    return when {
        sizeInBytes < 1024 -> "$sizeInBytes Bytes"
        sizeInBytes < 1024 * 1024 -> String.format("%.1f KB", sizeInBytes / 1024.0)
        sizeInBytes < 1024 * 1024 * 1024 -> String.format("%.1f MB", sizeInBytes / (1024.0 * 1024))
        else -> String.format("%.1f GB", sizeInBytes / (1024.0 * 1024 * 1024))
    }
}
