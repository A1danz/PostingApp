package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.domain.interactor.UserInteractor
import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo
import com.a1danz.feature_create_post.domain.model.PostPlaceType
import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model.PostPublishingStatusUiModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.worker.MyWorker
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostPublishingViewModel @Inject constructor(
    private val userInteractor: UserInteractor
) : ViewModel() {
    fun getTgChats(): List<TgChatInfo>? {
        return userInteractor.getTgChats()
    }

    fun getVkConfig(): VkConfig? {
        return userInteractor.getVkUserInfo()
    }

    fun getPostPlaceStaticInfo(postPlaceType: PostPlaceType): PostPlaceStaticInfo? {
        return userInteractor.getPostPlaceStaticInfo(postPlaceType)
    }

    fun getPostPublishingDomainModel(postPlaceType: PostPlaceType): PostPublishingDomainModel? {
        return userInteractor.getPostPublishingModel(postPlaceType)
    }

    suspend fun startPublishingProcess(publisher: PostPublisher, postModel: PostModel) {
        publisher.createPost(postModel)
    }

    fun getStatusUiModel(status: PostPublishingStatus): PostPublishingStatusUiModel {
        return when(status) {
            PostPublishingStatus.INVALID_DATA -> PostPublishingStatusUiModel(
                status,
                "Невалидные данные",
                com.a1danz.common.R.color.error
            )
            PostPublishingStatus.SUCCESS -> PostPublishingStatusUiModel(
                status,
                "Успешно",
                com.a1danz.common.R.color.success
            )
            PostPublishingStatus.RETRYING -> PostPublishingStatusUiModel(
                status,
                "Повторная попытка...",
                com.a1danz.common.R.color.warning
            )
            PostPublishingStatus.FAILURE -> PostPublishingStatusUiModel(
                status,
                "Публикация не удалась",
                com.a1danz.common.R.color.error
            )
            PostPublishingStatus.IN_PROCESS -> PostPublishingStatusUiModel(
                status,
                "В процессе...",
                com.a1danz.common.R.color.primary
            )
            PostPublishingStatus.CANCELLED -> PostPublishingStatusUiModel(
                status,
                "Публикация прервана",
                com.a1danz.common.R.color.error
            )
            else -> {
                Log.e("UNDCATCHABLE STATUS", "UNDEFINED STATUS - $status")
                PostPublishingStatusUiModel(
                    status,
                    "Статус не определен",
                    com.a1danz.common.R.color.warning
                )
            }
        }
    }

    fun startWorking(context: Context) {
        val workManager = WorkManager.getInstance(context)
        val myWorkRequest = OneTimeWorkRequestBuilder<MyWorker>().build()

        workManager.enqueue(myWorkRequest)
        viewModelScope.launch {

        }
    }
}