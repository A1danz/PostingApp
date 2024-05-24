package com.a1danz.feature_create_post.domain.interactor.impl

import android.util.Log
import com.a1danz.feature_create_post.data.model.PostPublishingModel
import com.a1danz.feature_create_post.data.model.PostPublishingModels
import com.a1danz.feature_create_post.domain.interactor.DatastoreUserInteractor
import com.a1danz.feature_create_post.domain.model.PostPublishingItemInfoDomainModel
import com.a1danz.feature_create_post.domain.repository.DataStoreRepository
import com.a1danz.feature_places_info.data.mapper.PostPlaceTypeMapper
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataStoreUserInteractorImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val dataStoreRepository: DataStoreRepository,
    private val postPlaceTypeMapper: PostPlaceTypeMapper
) : DatastoreUserInteractor {
    override suspend fun writePublishingInProcess(inProcess: Boolean) {
        withContext(dispatcher) {
            dataStoreRepository.writeBoolean(PUBLISHING_IN_PROCESS_KEY, inProcess)
        }
    }

    override suspend fun getPublishingInProcess(): Boolean {
        return withContext(dispatcher) {
            val result = dataStoreRepository.readBoolean(PUBLISHING_IN_PROCESS_KEY)
            if (result == null) Log.e("DATASTORE NULL", "PUBLISHING PROCESS IS NULL")
            result ?: false
        }
    }

    override suspend fun initPostPublishingModels() {
        withContext(dispatcher) {
            dataStoreRepository.initPostPublishingModels(POST_PUBLISHING_MODELS)
        }
    }

    override suspend fun getPostPublishingModels(): PostPublishingModels? {
        return withContext(dispatcher) {
            dataStoreRepository.getPostPublishingModels(POST_PUBLISHING_MODELS)
        }
    }

    override suspend fun addPostPublishingModel(
        postPlaceType: PostPlaceType,
        postPublishingItemInfo: PostPublishingItemInfoDomainModel
    ) {
        val postPlaceTypeName = postPlaceTypeMapper.mapDomainToData(postPlaceType)


        withContext(dispatcher) {
            dataStoreRepository.addPostPublishingModel(POST_PUBLISHING_MODELS, PostPublishingModel(
                postPlaceTypeName,
                postPublishingItemInfo.uId,
                postPublishingItemInfo.name,
                postPublishingItemInfo.img
            ))
        }
    }

    companion object {
        const val PUBLISHING_IN_PROCESS_KEY = "publishing_in_process"
        const val POST_PUBLISHING_MODELS = "post_publishing_models"
    }
}