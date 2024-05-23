package com.a1danz.feature_create_post.presentation.model

import java.lang.StringBuilder

data class PostInfoUiModel (
    val mediaSize: Int = 0,
    val text: String
) {
    fun getCorrections(): List<String> {
        val corrections = mutableListOf<String>()
        if (mediaSize == 0) corrections.add("Вы не прикрепили ни одного фото")
        if (text.isBlank()) corrections.add("Текст публикации будет пустой")

        return corrections
    }
}