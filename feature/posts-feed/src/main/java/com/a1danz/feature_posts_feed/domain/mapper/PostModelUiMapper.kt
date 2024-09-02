package com.a1danz.feature_posts_feed.domain.mapper

import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceUiInfo
import com.a1danz.feature_places_info.presentation.model.getUiInfo
import com.a1danz.feature_posts_feed.domain.model.PostDomainModel
import com.a1danz.feature_posts_feed.presentation.model.PostUiModel
import java.util.Calendar
import javax.inject.Inject

class PostModelUiMapper @Inject constructor() {

    fun mapToUiModel(domainModel: PostDomainModel): PostUiModel {
        return PostUiModel(
            id = domainModel.id,
            text = domainModel.text,
            imgs = domainModel.imgs,
            date = convertDateToString(domainModel.date),
            postPlaces = domainModel.postPlaces.map {
                it.getUiInfo()
            }
        )
    }

    private fun convertDateToString(date: Calendar): String {
        val sb = StringBuilder()
        sb.append(convertNumber(date.get(Calendar.DAY_OF_MONTH))).append(".")
            .append(convertNumber(date.get(Calendar.MONTH) + 1)).append(".")
            .append(convertNumber(date.get(Calendar.YEAR))).append(" ")
            .append(convertNumber(date.get(Calendar.HOUR_OF_DAY))).append(":")
            .append(convertNumber(date.get(Calendar.MINUTE)))

        return sb.toString()
    }

    private fun convertNumber(number: Int): String {
        if (number >= 10) return number.toString()
        else return "0" + number
    }
}