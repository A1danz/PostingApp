package com.a1danz.feature_create_post.domain.interactor.impl

import com.a1danz.feature_create_post.domain.factory.PostDestinationDomainModelsFactory
import com.a1danz.feature_create_post.domain.factory.PostPlacesFactory
import com.a1danz.feature_create_post.domain.interactor.CreatePostInteractor
import com.a1danz.feature_create_post.domain.model.PostDestinationDomainModel
import com.a1danz.feature_create_post.presentation.model.PostDestinationUiModel
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import javax.inject.Inject

class CreatePostInteractorImpl @Inject constructor(
    private val postDestinationsFactory: PostDestinationDomainModelsFactory,
    private val postPlacesFactory: PostPlacesFactory,
) : CreatePostInteractor {
    override fun getPostDestinationDomainModels(postPlaces: List<PostPlaceType>): List<PostDestinationDomainModel> {
        return postPlaces.flatMap {
            postDestinationsFactory.getPostDestinationUiModelsByPlaceType(it)
        }
    }

    override fun getPostPlaces(): HashSet<PostPlaceType> {
        return postPlacesFactory.getPostPlaces()
    }
}