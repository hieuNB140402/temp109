package com.example.st109_pdf_reader.core.extensions

import android.os.Build
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun sortByNameAZ(list: List<FilesModel>): List<FilesModel> {
    return list.sortedBy { it.name.lowercase() }
}

fun sortByNameZA(list: List<FilesModel>): List<FilesModel> {
    return list.sortedByDescending { it.name.lowercase() }
}

fun sortByDateOldToNew(list: List<FilesModel>): List<FilesModel> {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        return list.sortedWith(compareBy({
            try {
                LocalDate.parse(it.date, dateFormatter)
            } catch (e: Exception) {
                LocalDate.MIN
            }
        }, {
            try {
                LocalTime.parse(it.time, timeFormatter)
            } catch (e: Exception) {
                LocalTime.MIN
            }
        }))
    }
    else {
        return listOf()
    }
}

fun sortByDateNewToOld(list: List<FilesModel>): List<FilesModel> {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        return list.sortedWith(compareByDescending<FilesModel> {
            try {
                LocalDate.parse(it.date, dateFormatter)
            } catch (e: Exception) {
                LocalDate.MIN
            }
        }.thenByDescending {
            try {
                LocalTime.parse(it.time, timeFormatter)
            } catch (e: Exception) {
                LocalTime.MIN
            }
        })
    }
    else {
        return listOf()
    }
}