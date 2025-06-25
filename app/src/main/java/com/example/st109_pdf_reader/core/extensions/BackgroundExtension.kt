package com.example.st109_pdf_reader.core.extensions

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

fun setBackgroundButtonSolidBlue(context: Context, button: TextView) {
    val drawableButtonBlue = MaterialShapeDrawable().apply {
        setTint(Color.parseColor("#002DB3"))
        setStroke(pxToDpFloat(context, 2), Color.WHITE)
        shapeAppearanceModel =
            ShapeAppearanceModel.builder().setTopLeftCorner(CornerFamily.CUT, pxToDpFloat(context, 6)).setTopRightCorner(CornerFamily.CUT,
                pxToDpFloat(context, 6))
                .setBottomLeftCorner(CornerFamily.CUT, pxToDpFloat(context, 6)).setBottomRightCorner(CornerFamily.CUT, pxToDpFloat(context, 6)).build()
    }
    button.background = drawableButtonBlue
}

fun setBackgroundButtonSolidWhite(context: Context, button: TextView) {
    val drawableButtonWhite = MaterialShapeDrawable().apply {
        setStroke(pxToDpFloat(context, 2), Color.parseColor("#002DB3"))
        setTint(Color.WHITE)
        shapeAppearanceModel =
            ShapeAppearanceModel.builder().setTopLeftCorner(CornerFamily.CUT, pxToDpFloat(context, 6)).setTopRightCorner(CornerFamily.CUT,
                pxToDpFloat(context, 6))
                .setBottomLeftCorner(CornerFamily.CUT, pxToDpFloat(context, 6)).setBottomRightCorner(CornerFamily.CUT, pxToDpFloat(context, 6)).build()
    }
    button.background = drawableButtonWhite
}

fun setBackgroundSolidTransparent(context: Context, view: View) {
    val drawableTransparent = MaterialShapeDrawable().apply {
        setStroke(pxToDpFloat(context, 2), Color.WHITE)
        setTint(Color.TRANSPARENT)
        shapeAppearanceModel =
            ShapeAppearanceModel.builder()
                .setTopLeftCorner(CornerFamily.CUT, pxToDpFloat(context, 12))
                .setTopRightCorner(CornerFamily.CUT, pxToDpFloat(context, 12))
                .setBottomLeftCorner(CornerFamily.CUT, pxToDpFloat(context, 12))
                .setBottomRightCorner(CornerFamily.CUT, pxToDpFloat(context, 12))
                .build()
    }
    view.background = drawableTransparent
}

fun setBackgroundButtonSolidBlue(context: Context, button: ViewGroup) {
    val drawableButtonBlue = MaterialShapeDrawable().apply {
        setTint(Color.parseColor("#002DB3"))
        setStroke(pxToDpFloat(context, 2), Color.WHITE)
        shapeAppearanceModel =
            ShapeAppearanceModel.builder().setTopLeftCorner(CornerFamily.CUT, pxToDpFloat(context, 6)).setTopRightCorner(CornerFamily.CUT,
                pxToDpFloat(context, 6))
                .setBottomLeftCorner(CornerFamily.CUT, pxToDpFloat(context, 6)).setBottomRightCorner(CornerFamily.CUT, pxToDpFloat(context, 6)).build()
    }
    button.background = drawableButtonBlue
}

fun setBackgroundButtonSolidBlueCadet(context: Context, button: RelativeLayout) {
    val drawableButtonBlue = MaterialShapeDrawable().apply {
        setTint(Color.parseColor("#B3253257"))
        setStroke(pxToDpFloat(context, 2), Color.parseColor("#787272"))
        shapeAppearanceModel =
            ShapeAppearanceModel.builder().setTopLeftCorner(CornerFamily.CUT, pxToDpFloat(context, 6)).setTopRightCorner(CornerFamily.CUT,
                pxToDpFloat(context, 6))
                .setBottomLeftCorner(CornerFamily.CUT, pxToDpFloat(context, 6)).setBottomRightCorner(CornerFamily.CUT, pxToDpFloat(context, 6)).build()
    }
    button.background = drawableButtonBlue
}