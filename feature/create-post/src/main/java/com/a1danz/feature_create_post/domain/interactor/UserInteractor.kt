package com.a1danz.feature_create_post.domain.interactor

import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_create_post.data.model.PostPublishingModels
import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceStaticInfo
import com.a1danz.feature_post_publisher_api.PostPublisher

interface UserInteractor {
    fun getTgChats(): List<TgChatInfo>?
    fun getVkUserInfo(): VkConfig?
    fun getPostPlaceStaticInfo(postPlaceType: PostPlaceType): PostPlaceStaticInfo?
    fun getPostPublishingModel(postPlaceType: PostPlaceType): PostPublishingDomainModel?
    fun getAlreadyCreatedPublishingModels(
        savedPostPublishingModels: PostPublishingModels?,
        postPublishers: HashMap<String, PostPublisher>
    ): List<PostPublishingDomainModel>

}