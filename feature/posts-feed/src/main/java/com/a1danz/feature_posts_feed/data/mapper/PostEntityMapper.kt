package com.a1danz.feature_posts_feed.data.mapper

import android.net.Uri
import com.a1danz.core_data.database.entites.PostEntity
import com.a1danz.feature_places_info.data.mapper.PostPlaceTypeMapper
import com.a1danz.feature_posts_feed.domain.model.PostDomainModel
import javax.inject.Inject

class PostEntityMapper @Inject constructor(
    private val postPlaceTypeMapper: PostPlaceTypeMapper
) {
    fun postToDomainModel(postEntity: PostEntity): PostDomainModel {
        val imgs = postEntity.imgs.map { Uri.parse(it) }
        val postPlaces = postEntity.postPlaces.mapNotNull { postPlaceTypeMapper.mapDataToDomain(it.name) }

        return PostDomainModel(
            id = postEntity.id,
            text = postEntity.text,
            imgs = imgs,
            date = postEntity.date,
            postPlaces = postPlaces
        )
    }
}