package com.a1danz.feature_create_post.domain.mapper

import com.a1danz.feature_create_post.domain.model.PostDomainModel
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_post_publisher_api.model.PostModel
import javax.inject.Inject

class PostMapper @Inject constructor() {

    fun toDomainModel(postModel: PostModel, postPlaces: List<PostPlaceType>): PostDomainModel {
        return PostDomainModel(
            text = postModel.text,
            images = postModel.images,
            places = postPlaces
        )
    }
}