package com.example.st109_pdf_reader.core.extensions

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun generateRandomFileName(): String {
    val randomNumber = (100000000000..999999999999).random()
    return "IMG_$randomNumber.png"
}

fun generateRandomString(length: Int = 12): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    return (1..length).map { chars.random() }.joinToString("")
}

fun formatNumber(input: String): String {
    val number = input.toLongOrNull() ?: return input
    val formatter = NumberFormat.getInstance(Locale.GERMANY) as DecimalFormat
    return formatter.format(number)
}
internal fun upperFirstCharacter(str: String): String {
    return str.capitalize(Locale.ROOT)
}

internal fun convertToLowerCase(input: String): String {
    return input.lowercase()
}

internal fun formatDecimal(number: Double, decimalPlaces: Int): String {
    val pattern = "#." + "0".repeat(decimalPlaces)
    val decimalFormat = DecimalFormat(pattern)
    return decimalFormat.format(number)
}