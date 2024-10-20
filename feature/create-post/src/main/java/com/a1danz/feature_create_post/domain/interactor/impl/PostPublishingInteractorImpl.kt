package com.a1danz.feature_create_post.domain.interactor.impl

import android.content.Context
import android.net.Uri
import com.a1danz.common.utils.toFile
import com.a1danz.feature_create_post.domain.factory.PostPublishingDomainModelsFactory
import com.a1danz.feature_create_post.domain.interactor.PostPublishingInteractor
import com.a1danz.feature_create_post.domain.mapper.PostMapper
import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel
import com.a1danz.feature_create_post.domain.repository.PostsLocalRepository
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class PostPublishingInteractorImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val postPublishingFactory: PostPublishingDomainModelsFactory,
    private val postsRepository: PostsLocalRepository,
    private val postMapper: PostMapper,
) : PostPublishingInteractor {

    override fun getPostPublishingModel(postPlaceType: PostPlaceType): PostPublishingDomainModel? {
        return postPublishingFactory.createPostPublishingModel(postPlaceType)
    }


    override suspend fun startPublishing(postPublishers: List<PostPublisher>, postModel: PostModel) {
        withContext(Dispatchers.IO) {
            postPublishers.forEach { publisher ->
                launch {
                    publisher.startCreatingPost(postModel)
                }
            }
        }
    }

    override suspend fun convertUriToFile(uri: Uri, context: Context): File {
        return withContext(dispatcher) {
            uri.toFile(context)
        }
    }

    override suspend fun savePostToDatabase(postModel: PostModel, postPlaces: List<PostPlaceType>) {
        withContext(dispatcher) {
            postsRepository.savePost(
                postMapper.toDomainModel(postModel, postPlaces)
            )
        }
    }
}