package com.example.st109_pdf_reader.core.utils

import com.example.st109_pdf_reader.data.model.IntroModel
import com.example.st109_pdf_reader.data.model.LanguageModel
import com.example.st109_pdf_reader.R

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

}

