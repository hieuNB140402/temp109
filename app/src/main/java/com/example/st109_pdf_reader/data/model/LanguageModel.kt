package com.example.st109_pdf_reader.data.model

data class LanguageModel(
    val code: String,
    val name: String,
    val flag: Int,
    var activate: Boolean = false
)
