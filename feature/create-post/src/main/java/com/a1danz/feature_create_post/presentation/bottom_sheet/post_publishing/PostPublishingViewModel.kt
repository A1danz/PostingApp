package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_create_post.domain.interactor.DatastoreUserInteractor
import com.a1danz.feature_create_post.domain.interactor.UserInteractor
import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel
import com.a1danz.feature_create_post.domain.model.PostPublishingItemDomainModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model.PostPublishingStatusUiModel
import com.a1danz.feature_create_post.utils.PostPublishingStarter
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceStaticInfo
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.feature_post_publisher_api.model.PostPublishingStatus
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostPublishingViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val dataStoreUserInteractor: DatastoreUserInteractor
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

    fun startPublishingProcess(
        activity: FragmentActivity,
        postPlaceType: PostPlaceType,
        postPublishingItem: PostPublishingItemDomainModel,
        postModel: PostModel
    ) {
        val postPublishingStarter: PostPublishingStarter? = (activity as? PostPublishingStarter)
        if (postPublishingStarter == null) {
            Log.e("CAST EXCEPTION", "Can not cast activity to PostPublishingStarter $activity")
            return
        }
        viewModelScope.launch {
            dataStoreUserInteractor.addPostPublishingModel(postPlaceType, postPublishingItem.itemInfo)
        }

        postPublishingStarter.startPublishingProcess(postPublishingItem, postModel)
    }

    fun getStatusUiModel(status: PostPublishingStatus): PostPublishingStatusUiModel {
        return when(status) {
            PostPublishingStatus.INVALID_DATA -> PostPublishingStatusUiModel(
                status,
                "Невалидные данные",
                com.a1danz.common.R.color.error,
                "Завершена"
            )
            PostPublishingStatus.SUCCESS -> PostPublishingStatusUiModel(
                status,
                "Успешно",
                com.a1danz.common.R.color.success,
                "Завершена"
            )
            PostPublishingStatus.RETRYING -> PostPublishingStatusUiModel(
                status,
                "Повторная попытка...",
                com.a1danz.common.R.color.warning,
                "Публикация..."
            )
            PostPublishingStatus.FAILURE -> PostPublishingStatusUiModel(
                status,
                "Публикация не удалась",
                com.a1danz.common.R.color.error,
                "Завершена"
            )
            PostPublishingStatus.IN_PROCESS -> PostPublishingStatusUiModel(
                status,
                "В процессе...",
                com.a1danz.common.R.color.primary,
                "Публикация..."
            )
            PostPublishingStatus.CANCELLED -> PostPublishingStatusUiModel(
                status,
                "Публикация прервана",
                com.a1danz.common.R.color.error,
                "Завершена"
            )
            else -> {
                Log.e("UNDCATCHABLE STATUS", "UNDEFINED STATUS - $status")
                PostPublishingStatusUiModel(
                    status,
                    "Статус не определен",
                    com.a1danz.common.R.color.warning,
                    "Неизвестный статус"
                )
            }
        }
    }

    fun initPostPublishingSaving() {
        viewModelScope.launch {
            dataStoreUserInteractor.initPostPublishingModels()
        }
    }

    fun publishingAlreadyInProcess(activity: FragmentActivity): Boolean {
        return (activity as? PostPublishingStarter)?.publishingInProcess() ?: false
    }

    suspend fun getPostPublishingDomainModels(activity: FragmentActivity): List<PostPublishingDomainModel> {

        val result = listOf<PostPublishingDomainModel>()
        val savedPostPublishingModels = dataStoreUserInteractor.getPostPublishingModels()
        val postPublishers = getPublishersMap(activity)
        if (postPublishers == null) {
            Log.e("POST PUBLISHER", "POST PUBLISHERS IS NULL [PostPublishingViewModel]")
            return result
        }

        return userInteractor.getAlreadyCreatedPublishingModels(savedPostPublishingModels, postPublishers).sortedBy {
            it.postPlaceStaticInfo.placeType
        }
    }

    fun getPublishersMap(activity: FragmentActivity): HashMap<String, PostPublisher>? {
        val publishingStarter = (activity as? PostPublishingStarter)
        if (publishingStarter == null) {
            Log.e("ERROR", "CAN NOT CAST ACTIVITY")
        }

        return publishingStarter?.getPublishersMap()
    }
}