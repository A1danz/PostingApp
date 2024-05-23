package com.a1danz.feature_create_post.domain.interactor

import com.a1danz.feature_create_post.data.model.PostPublishingModels
import com.a1danz.feature_create_post.domain.model.PostPlaceType
import com.a1danz.feature_create_post.domain.model.PostPublishingItemInfoDomainModel

interface DatastoreUserInteractor {
    suspend fun writePublishingInProcess(inProcess: Boolean)
    suspend fun getPublishingInProcess(): Boolean

    suspend fun initPostPublishingModels()
    suspend fun getPostPublishingModels(): PostPublishingModels?
    suspend fun addPostPublishingModel(postPlaceType: PostPlaceType, postPublishingItemInfo: PostPublishingItemInfoDomainModel
    )
}