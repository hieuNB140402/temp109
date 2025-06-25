package com.example.st109_pdf_reader.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "file")
@Parcelize
data class FilesModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var type: String = "",
    var name: String = "",
    var path: String = "",
    val sizeInBytes: Long = 0,
    var date: String = "",
    var time: String = "",
    var isBookmark: Boolean = false,
    var isRecentFiles: Boolean = false,
    var isSaved: Boolean = false,
    var isShow: Boolean = false,
    var isSelected: Boolean = false
) : Parcelable {
    constructor() : this(0, "", "", "", 0, "", "",false, false, false, false, false)
}