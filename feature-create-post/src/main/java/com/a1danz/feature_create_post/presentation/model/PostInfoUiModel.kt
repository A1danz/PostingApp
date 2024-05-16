package com.a1danz.feature_create_post.presentation.model

import java.lang.StringBuilder

data class PostInfoUiModel (
    val places: List<String>,
    val mediaSize: Int = 0,
    val text: String
) {
    fun createMessageAboutPost(): String {
        val message = StringBuilder()
        message.append("Пост будет опубликован в следующих местах").append(":\n")
        places.forEach { place -> message.append("\t").append(place).append("\n") }
        val corrections = mutableListOf<String>()
        if (mediaSize == 0) corrections.add("вы не выбрали ни одного медиа для публикации")
        else {
            corrections.add("количество медиа - $mediaSize")
        }
        if (text.isBlank()) corrections.add("текст публикации будет пустой")
        corrections.forEach { corr ->
            message.append("Уточнение").append(": ").append(corr).append("\n")
        }

        return message.toString()
    }
}