package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_create_post.domain.interactor.UserInteractor
import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo
import com.a1danz.feature_create_post.domain.model.PostPlaceType
import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model.PostPublishingStatusUiModel
import com.a1danz.feature_create_post.utils.PostPublishingStarter
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus
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

    fun startPublishingProcess(activity: FragmentActivity, publisher: PostPublisher, postModel: PostModel) {
        val postPublishingStarter: PostPublishingStarter? = (activity as? PostPublishingStarter)
        if (postPublishingStarter == null) {
            Log.e("CAST EXCEPTION", "Can not cast activity to PostPublishingStarter $activity")
            return
        }
        postPublishingStarter.startPublishingProcess(publisher, postModel)
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
}