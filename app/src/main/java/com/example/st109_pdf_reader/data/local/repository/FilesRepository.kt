package com.example.st109_pdf_reader.data.local.repository

import android.R.attr.name
import android.R.attr.type
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.example.st109_pdf_reader.data.local.dao.FileDao
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale


class FileRepository(private val fileDao: FileDao) {
    suspend fun insertFiles(files: List<FilesModel>) = fileDao.insertFiles(files)

    suspend fun getAllFiles(): List<FilesModel> = fileDao.getAllFiles()

    suspend fun updateBookmark(fileId: Int, isBookmark: Boolean) {
        fileDao.updateBookmark(fileId, isBookmark)
    }
    suspend fun updateRecentFile(fileId: Int, isRecent: Boolean) {
        fileDao.updateRecentFile(fileId, isRecent)
    }


    fun getAllFilesFlow(): Flow<List<FilesModel>> = fileDao.getAllFilesFlow()

    suspend fun deleteFiles(files: List<FilesModel>) {
        fileDao.deleteFiles(files)
    }

    suspend fun deleteFileByPath(path: String){
        fileDao.deleteFilesByPath(path)
    }

    suspend fun updateNameByPath(path: String, newName: String, newPath: String){
        fileDao.updateNameByPath(path, newName, newPath)
    }

    fun scanDeviceFiles(context: Context): List<FilesModel> {
        val list = mutableListOf<FilesModel>()
        val uri = MediaStore.Files.getContentUri("external")
        val projection = arrayOf(
            MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns.DISPLAY_NAME)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val indexPath = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            val indexSize = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
            val indexName = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            while (it.moveToNext()) {
                val path = it.getString(indexPath)
                val size = if (indexSize == 0){
                    File(path).length()
                }else{
                    it.getLong(indexSize)
                }
//                Log.i("nbhieu", "size: $size")
                val name = it.getString(indexName).split(".")[0]
//                Log.i("nbhieu", "name: $name")
                if (path.endsWith(".pdf") || path.endsWith(".doc") || path.endsWith(".docx") || path.endsWith(".ppt") || path.endsWith(
                        ".pptx") || path.endsWith(".xls") || path.endsWith(".xlsx")) {
                    val file = File(path)
                    val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(file.lastModified())
                    val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(file.lastModified())
                    val type = when {
                        path.endsWith(".pdf") -> "PDF"
                        path.endsWith(".doc") || path.endsWith(".docx") -> "WORD"
                        path.endsWith(".ppt") || path.endsWith(".pptx") -> "PPT"
                        path.endsWith(".xls") || path.endsWith(".xlsx") -> "EXCEL"
                        else -> "OTHER"
                    }
                    list.add(FilesModel(0, type, name, path, size, date, time))
                }
            }
        }
        return list
    }
    fun searchFilesByName(query: String): Flow<List<FilesModel>> {
        return fileDao.searchFilesByName(query)
    }

    fun searchFilesByNameAndType(query: String, type: String): Flow<List<FilesModel>> {
        return fileDao.searchFilesByNameAndType(query, type)
    }

    suspend fun insertFileIfNotExists(file: FilesModel): Boolean {
        val exists = fileDao.checkFileExists(file.path) > 0
        return if (!exists) {
            fileDao.insertFile(file)
            true
        } else {
            false
        }
    }


}