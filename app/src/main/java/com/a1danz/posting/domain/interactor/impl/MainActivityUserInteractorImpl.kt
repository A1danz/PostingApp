package com.a1danz.posting.domain.interactor.impl

import android.util.Log
import androidx.core.net.toUri
import com.a1danz.feature_create_post.domain.model.PostPublishingItemDomainModel
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostCreatingResult
import com.a1danz.feature_post_publisher_api.model.PostCreatingResultType
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.feature_posts_feed.domain.model.PostDomainModel
import com.a1danz.posting.domain.interactor.MainActivityUserInteractor
import com.a1danz.posting.domain.repository.PostsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class MainActivityUserInteractorImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val postsRepository: PostsRepository
) : MainActivityUserInteractor {

    override suspend fun startPublishingProcess(postPublisher: PostPublisher, postModel: PostModel) {
        withContext(dispatcher) {
            postPublisher.createPost(postModel)
        }
    }

    override suspend fun savePostToFeed(postModel: PostModel, items: List<PostPublishingItemDomainModel>) {
        withContext(dispatcher) {
            val successFinished = items.filter {
                it.publisher.creatingResult != null && it.publisher.creatingResult?.resultType == PostCreatingResultType.SUCCESS
            }


            val places = HashSet<PostPlaceType>()
            successFinished.forEach {
                it.itemInfo.postPlaceType?.let { place ->
                    places.add(place)
                }
            }

            Log.d("PLACES", places.toString())
            if (places.isNotEmpty()) {
                val postDomainModel = PostDomainModel(
                    id = -1,
                    text = postModel.text,
                    imgs = postModel.images.map { it.toUri() },
                    date = Calendar.getInstance().apply { timeInMillis = System.currentTimeMillis() },
                    postPlaces = places.toList()
                )

                postsRepository.savePost(postDomainModel)
            }

        }
    }
}