package com.example.st109_pdf_reader.data.model.create

data class AllImageModel(
    val imageFirst: String,
    val size: Int,
    val nameFolder: String,
    val listImage: ArrayList<ImageModel> = arrayListOf()
)
