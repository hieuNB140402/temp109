package com.example.st109_pdf_reader.ui.home.viewmodel

import android.R.attr.type
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.st109_pdf_reader.core.utils.KeyApp
import com.example.st109_pdf_reader.core.utils.SystemUtils
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import com.example.st109_pdf_reader.data.local.repository.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.map

class FileViewModel(private val repository: FileRepository) : ViewModel() {
    val filesFlow: StateFlow<List<FilesModel>> =
        repository.getAllFilesFlow().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val getFilesFlow: StateFlow<List<FilesModel>> = filesFlow

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized


    fun scanIfFirstTime(context: Context) {
        if (SystemUtils.isFirstScan(context)) {
            viewModelScope.launch {
                scanAndSaveFiles(context)
                SystemUtils.setFirstScanDone(context)
            }
        }
    }

    fun refreshScan(context: Context) {
        viewModelScope.launch {
            scanAndSaveFiles(context)
        }
    }

    private suspend fun scanAndSaveFiles(context: Context) {
        val scanned = withContext(Dispatchers.IO) {
            repository.scanDeviceFiles(context)
        }

        val roomList = repository.getAllFiles()

        // Tạo danh sách các path hiện tại trên thiết bị
        val scannedPaths = scanned.map { it.path }.toSet()

        // Xác định các file trong Room nhưng đã bị xóa khỏi thiết bị
        val deletedFiles = roomList.filter { it.path !in scannedPaths }

        // Xóa file không còn tồn tại khỏi Room
        if (deletedFiles.isNotEmpty()) {
            repository.deleteFiles(deletedFiles)
        }

        // Merge dữ liệu: giữ nguyên id, isBookmark, isRecentFiles
        val merged = mergeScannedWithRoom(scanned, roomList)

        // Lưu danh sách mới vào Room
        repository.insertFiles(merged)

        // Cập nhật trạng thái
        _isInitialized.value = true
    }

    private fun mergeScannedWithRoom(
        scanned: List<FilesModel>,
        roomList: List<FilesModel>
    ): List<FilesModel> {
        val roomMap = roomList.associateBy { it.path }
        return scanned.map { scannedFile ->
            roomMap[scannedFile.path]?.let { roomFile ->
                scannedFile.copy(
                    id = roomFile.id,
                    isBookmark = roomFile.isBookmark,
                    isRecentFiles = roomFile.isRecentFiles,
                    isSaved = roomFile.isSaved
                )
            } ?: scannedFile
        }
    }

    fun updateBookmark(fileId: Int, isBookmark: Boolean) {
        viewModelScope.launch {
            try {
                repository.updateBookmark(fileId, isBookmark)
            } catch (e: Exception) {
                Log.e("nbhieu", "updateBookmark: ${e.message}")
            }
        }
    }

    fun updateRecentFile(fileId: Int, isRecent: Boolean) {
        viewModelScope.launch {
            try {
                repository.updateRecentFile(fileId, isRecent)
            } catch (e: Exception) {
                Log.e("nbhieu", "updateRecentFile: ${e.message}")
            }
        }
    }

    fun deleteFileFromPath(path: String) {
        viewModelScope.launch {
            try {
                repository.deleteFileByPath(path)
            } catch (e: Exception) {
                Log.e("nbhieu", "deleteFileFromPath: ${e.message}")
            }
        }
    }

    fun renameFileByPath(path: String, newName: String, newPath: String) {
        viewModelScope.launch {
            try {
                val nameList = newName.split(".")

                var nameWithoutExtension = ""
                for (i in 0 until nameList.size){
                    if (i < nameList.size - 1){
                        nameWithoutExtension += nameList[i]
                    }
                }
                repository.updateNameByPath(path, nameWithoutExtension, newPath)
            } catch (e: Exception) {
                Log.e("nbhieu", "renameFileByPath: ${e.message}")
            }
        }
    }

    fun searchFiles(query: String, type: String): Flow<List<FilesModel>> {
        return if (type == KeyApp.ALL_FILE) {
            repository.searchFilesByName(query)
        } else {
            repository.searchFilesByNameAndType(query, type)
        }
    }

    fun insertFileIfNotExists(file: FilesModel, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val result = repository.insertFileIfNotExists(file)
                onResult(result)
            } catch (e: Exception) {
                Log.e("nbhieu", "insertFileIfNotExists: ${e.message}")
                onResult(false)
            }
        }
    }
}