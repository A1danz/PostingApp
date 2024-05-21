package com.a1danz.feature_create_post.domain.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostCreatingResult
import com.a1danz.feature_post_publisher_api.model.PostCreatingResultType
import com.a1danz.feature_post_publisher_api.model.PostModel
import kotlinx.coroutines.coroutineScope

class PostPublishingWorker(
    context: Context,
    params: WorkerParameters,
    private val publisher: PostPublisher,
    private val postModel: PostModel
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        publisher.createPost(postModel)
        if (publisher.creatingResult == null || publisher.creatingResult!!.resultType == PostCreatingResultType.FAILURE) {
            return Result.failure()
        }
        return Result.success()
    }
}