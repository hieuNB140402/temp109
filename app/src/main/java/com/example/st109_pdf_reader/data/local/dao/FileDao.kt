package com.example.st109_pdf_reader.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.st109_pdf_reader.data.local.entity.FilesModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiles(files: List<FilesModel>)

    @Update
    suspend fun updateFiles(file: FilesModel)

    @Delete
    suspend fun deleteFiles(files: List<FilesModel>)

    @Query("DELETE FROM file WHERE path = :path")
    suspend fun deleteFilesByPath(path: String)

    @Query("SELECT * FROM file")
    suspend fun getAllFiles(): List<FilesModel>

    @Query("SELECT * FROM file")
    fun getAllFilesFlow(): Flow<List<FilesModel>>

    @Query("UPDATE file SET isBookmark = :isBookmark WHERE id = :id")
    suspend fun updateBookmark(id: Int, isBookmark: Boolean)

    @Query("UPDATE file SET name = :newName WHERE path = :path")
    suspend fun updateNameByPath(path: String, newName: String)

    @Query("SELECT * FROM file WHERE type = :type AND isBookmark = 1")
    fun getFileBookmarkByType(type: String): Flow<List<FilesModel>>


}
