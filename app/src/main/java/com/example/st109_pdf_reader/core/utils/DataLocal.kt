package com.example.st109_pdf_reader.core.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.st109_pdf_reader.data.model.IntroModel
import com.example.st109_pdf_reader.data.model.LanguageModel
import com.example.st109_pdf_reader.R
import com.example.st109_pdf_reader.data.model.create.AllImageModel
import com.example.st109_pdf_reader.data.model.create.ImageModel
import java.io.File
import kotlin.collections.first

object DataLocal {
    fun getLanguageList(): ArrayList<LanguageModel> {
        return arrayListOf(
            LanguageModel("hi", "Hindi", R.drawable.ic_flag_hindi),
            LanguageModel("es", "Spanish", R.drawable.ic_flag_spanish),
            LanguageModel("fr", "French", R.drawable.ic_flag_french),
            LanguageModel("en", "English", R.drawable.ic_flag_english),
            LanguageModel("pt", "Portuguese", R.drawable.ic_flag_portugeese),
            LanguageModel("in", "Indonesian", R.drawable.ic_flag_indo),
            LanguageModel("de", "German", R.drawable.ic_flag_germani),
        )
    }

    val itemIntroList = listOf(
        IntroModel(R.drawable.img_intro_1, R.string.title_1),
        IntroModel(R.drawable.img_intro_2, R.string.title_2),
        IntroModel(R.drawable.img_intro_3, R.string.title_3))

    val imageBottomNotSelectedList = arrayListOf(
        R.drawable.ic_home,
        R.drawable.ic_recent,
        R.drawable.ic_bookmark,
        R.drawable.ic_saved,
    )

    val imageBottomSelectedList = arrayListOf(
        R.drawable.ic_home_selected,
        R.drawable.ic_recent_selected,
        R.drawable.ic_bookmark_selected,
        R.drawable.ic_saved_selected,
    )

    fun getAllImageFoldersWithImages(context: Context): ArrayList<AllImageModel> {
        val folderMap = mutableMapOf<String, ArrayList<ImageModel>>()

        // Query ảnh
        val imageProjection = arrayOf(
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.DATA
        )
        val imageUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val imageCursor: Cursor? = context.contentResolver.query(
            imageUri, imageProjection, null, null, null
        )

        imageCursor?.use {
            val dataColumnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (it.moveToNext()) {
                val imagePath = it.getString(dataColumnIndex)
                val folderPath = File(imagePath).parent

                if (folderPath != null) {
                    // Tạo ImageModel từ imagePath
                    val imageModel = ImageModel(image = imagePath, isSelected = false)

                    // Thêm vào folderMap
                    if (!folderMap.containsKey(folderPath)) {
                        folderMap[folderPath] = arrayListOf()
                    }
                    folderMap[folderPath]?.add(imageModel)
                }
            }
        }

        // Tạo danh sách AllImageModel từ folderMap
        val allImageModels = folderMap.map { (folderPath, images) ->
            AllImageModel(
                imageFirst = images.last().image, // Ảnh đầu tiên trong thư mục
                size = images.size, // Số lượng ảnh trong thư mục
                nameFolder = File(folderPath).name, // Tên thư mục
                listImage = (images.reversed()).toCollection(ArrayList<ImageModel>())
            )
        }
        val dataAllImage: ArrayList<AllImageModel> = arrayListOf()
        allImageModels.forEach {
            dataAllImage.add(it)
        }
        return dataAllImage
    }
}

