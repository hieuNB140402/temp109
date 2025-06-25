package com.example.st109_pdf_reader.core.extensions

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.widget.TextView

fun setGradientHeightTitleColor(textView: TextView) {
    textView.post {
        val height = textView.height.toFloat()

        val textShader = LinearGradient(
            0f, 0f, 0f, height, intArrayOf(
                Color.parseColor("#30E9FF"), Color.parseColor("#118FFF")
            ), null, Shader.TileMode.CLAMP
        )

        textView.paint.shader = textShader
        textView.invalidate()
    }
}

fun setGradientTextHeightColor(textView: TextView, startColor: Int, endColor: Int) {
    textView.post {
        val height = textView.height.toFloat()

        val textShader = LinearGradient(
            0f, 0f, 0f, height, intArrayOf(startColor, endColor), null, Shader.TileMode.CLAMP
        )

        textView.paint.shader = textShader
        textView.invalidate()
    }
}
fun setGradientTextHeightColor(textView: TextView, startColor: String, endColor: String) {
    textView.post {
        val height = textView.height.toFloat()

        val textShader = LinearGradient(
            0f, 0f, 0f, height, intArrayOf(Color.parseColor(startColor), Color.parseColor(endColor)), null, Shader.TileMode.CLAMP
        )

        textView.paint.shader = textShader
        textView.invalidate()
    }
}

fun setGradientWidthTextColor(textView: TextView, startColor: Int, endColor: Int) {
    val paint = textView.paint
    val width = paint.measureText(textView.text.toString())
    val textShader = LinearGradient(
        0f, 0f, width, textView.textSize, intArrayOf(
            startColor, endColor
        ), null, Shader.TileMode.CLAMP
    )

    textView.paint.shader = textShader
}

