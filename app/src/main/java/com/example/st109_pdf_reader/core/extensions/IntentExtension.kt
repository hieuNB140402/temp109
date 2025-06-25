package com.example.st109_pdf_reader.core.extensions

import android.app.Activity
import android.content.Intent
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.core.utils.KeyApp.KeyIntent.INTENT_KEY

internal fun Activity.startIntent(targetActivity: Class<*>) {
    val intent = Intent(this, targetActivity)
    startActivity(intent)
}

internal fun Activity.startIntent(targetActivity: Class<*>, value: String) {
    val intent = Intent(this, targetActivity)
    intent.putExtra(INTENT_KEY, value)
    startActivity(intent)
}

internal fun Activity.startIntent(targetActivity: Class<*>, key: String, value: String) {
    val intent = Intent(this, targetActivity)
    intent.putExtra(key, value)
    startActivity(intent)
}

internal fun Activity.startIntentFromLeft(targetActivity: Class<*>) {
    val intent = Intent(this, targetActivity)
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

internal fun Activity.startIntentFromLeft(targetActivity: Class<*>, value: String) {
    val intent = Intent(this, targetActivity)
    intent.putExtra(INTENT_KEY, value)
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

internal fun Activity.startIntentFromLeft(targetActivity: Class<*>, value: Int) {
    val intent = Intent(this, targetActivity)
    intent.putExtra(INTENT_KEY, value)
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

internal fun Activity.startIntentFromLeft(targetActivity: Class<*>, key: String, value: String) {
    val intent = Intent(this, targetActivity)
    intent.putExtra(key, value)
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

internal fun Activity.startIntentFromRight(targetActivity: Class<*>) {
    val intent = Intent(this, targetActivity)
    startActivity(intent)
    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right)
}

internal fun Activity.startIntentFromRight(targetActivity: Class<*>, value: String) {
    val intent = Intent(this, targetActivity)
    intent.putExtra(INTENT_KEY, value)
    startActivity(intent)
    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right)
}

internal fun Activity.startIntentFromRight(targetActivity: Class<*>, value: Int) {
    val intent = Intent(this, targetActivity)
    intent.putExtra(INTENT_KEY, value)
    startActivity(intent)
    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right)
}

internal fun Activity.startIntentFromRight(targetActivity: Class<*>, key: String, value: String) {
    val intent = Intent(this, targetActivity)
    intent.putExtra(key, value)
    startActivity(intent)
    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right)
}