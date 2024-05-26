package com.a1danz.feature_posts_feed.data.mapper

import com.a1danz.feature_places_info.data.mapper.PostPlaceTypeMapper
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_posts_feed_database.data.local.dao.PostPlaceDao
import com.a1danz.feature_posts_feed_database.data.local.entites.PostPlaceEntity
import javax.inject.Inject

class PostPlaceModelDomainMapper @Inject constructor(
    private val postPlaceTypeMapper: PostPlaceTypeMapper,
    private val postPlaceDao: PostPlaceDao
) {

    fun mapToData(postPlaceType: PostPlaceType): PostPlaceEntity? {
        return postPlaceDao.findPostPlaceByName(postPlaceTypeMapper.mapDomainToData(postPlaceType))
    }
}