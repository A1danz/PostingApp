package com.a1danz.feature_create_post.domain.interactor

import com.a1danz.feature_create_post.domain.model.PostDestinationDomainModel
import com.a1danz.feature_create_post.presentation.model.PostDestinationUiModel
import com.a1danz.feature_places_info.domain.model.PostPlaceType

interface CreatePostInteractor {
    fun getPostDestinationDomainModels(postPlaces: List<PostPlaceType>): List<PostDestinationDomainModel>
    fun getPostPlaces(): HashSet<PostPlaceType>
}