package com.a1danz.feature_post_publisher_api

import com.a1danz.feature_post_publisher_api.model.PostCreatingResult
import com.a1danz.feature_post_publisher_api.model.PostCreatingResultType
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class PostPublisher {
    private val _creatingStatusFlow: MutableStateFlow<PostPublishingStatus?> = MutableStateFlow(null)
    val creatingStatusFlow: StateFlow<PostPublishingStatus?> = _creatingStatusFlow

    private val _resultFlow: MutableStateFlow<PostCreatingResult?> = MutableStateFlow(null)
    val resultFlow: StateFlow<PostCreatingResult?> = _resultFlow


    // primary method to creating post with this class
    suspend fun startCreatingPost(post: PostModel) {
        createPostWithAttempts(post)
    }

    // create post with attempts
    private suspend fun createPostWithAttempts(postModel: PostModel, attemptsCount: Int = 0) {
        if (attemptsCount > MAX_ATTEMPTS) {
            _creatingStatusFlow.value = PostPublishingStatus.FAILURE
            _resultFlow.value = PostCreatingResult(PostCreatingResultType.FAILURE)
            return
        } else if (attemptsCount == 0) {
            _creatingStatusFlow.value = PostPublishingStatus.IN_PROCESS
        } else {
            _creatingStatusFlow.value = PostPublishingStatus.RETRYING
        }

        runCatching {
            createPost(postModel)
        }.onSuccess {
            _resultFlow.value = PostCreatingResult(PostCreatingResultType.FAILURE)
            _creatingStatusFlow.value = PostPublishingStatus.SUCCESS
        }.onFailure {
            createPostWithAttempts(postModel, attemptsCount + 1)
        }
    }

    protected abstract suspend fun createPost(post: PostModel)
    abstract fun postDataIsValid(post: PostModel): Boolean

    companion object {
        protected const val MAX_ATTEMPTS = 3
    }
}