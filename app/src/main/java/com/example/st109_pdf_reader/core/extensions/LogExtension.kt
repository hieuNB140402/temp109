package com.example.st109_pdf_reader.core.extensions

import android.app.Activity
import android.util.Log

fun Activity.dLog(content: String) {
    Log.d("nbhieu", content)
}

fun Activity.eLog(content: String) {
    Log.e("nbhieu", content)
}

fun Activity.iLog(content: String) {
    Log.i("nbhieu", content)
}