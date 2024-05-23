package com.a1danz.feature_create_post.domain.repository

import com.a1danz.feature_create_post.data.model.PostPublishingModel
import com.a1danz.feature_create_post.data.model.PostPublishingModels

interface DataStoreRepository {
    suspend fun writeBoolean(key: String, value: Boolean)
    suspend fun readBoolean(key: String): Boolean?

    suspend fun getPostPublishingModels(key: String): PostPublishingModels?
    suspend fun addPostPublishingModel(key: String, postPublishingModel: PostPublishingModel)
    suspend fun initPostPublishingModels(key: String)
}