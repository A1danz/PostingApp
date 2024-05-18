package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing

import androidx.lifecycle.ViewModel
import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_create_post.domain.interactor.UserInteractor
import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo
import com.a1danz.feature_create_post.domain.model.PostPlaceType
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
}