package com.example.st109_pdf_reader.data.model.create

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreatePDFModel(var path: String, var isSelected: Boolean = false) : Parcelable
