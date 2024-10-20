package com.a1danz.feature_posts_feed.data.mapper

import android.net.Uri
import com.a1danz.core_data.database.entites.PostEntity
import com.a1danz.feature_places_info.data.mapper.PostPlaceTypeMapper
import com.a1danz.feature_posts_feed.domain.model.PostDomainModel
import javax.inject.Inject

class PostEntityMapper @Inject constructor(
    private val postPlaceTypeMapper: PostPlaceTypeMapper,
) {
    fun postToDomainModel(postEntity: PostEntity): PostDomainModel {
        return postEntity.run {
            PostDomainModel(
                id = id,
                text = text,
                imgs = imgs.map { Uri.parse(it) },
                date = date,
                postPlaces = postPlaces.mapNotNull { postPlaceTypeMapper.mapDataToDomain(it.name) }
            )
        }
    }
}