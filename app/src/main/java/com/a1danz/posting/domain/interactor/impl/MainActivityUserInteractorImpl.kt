package com.a1danz.posting.domain.interactor.impl

import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.posting.domain.interactor.MainActivityUserInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityUserInteractorImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) : MainActivityUserInteractor {

    override suspend fun startPublishingProcess(postPublisher: PostPublisher, postModel: PostModel) {
        withContext(dispatcher) {
            postPublisher.createPost(postModel)
        }
    }
}