package com.a1danz.feature_create_post.domain.interactor

import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.domain.model.VkUserInfo
import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo
import com.a1danz.feature_create_post.domain.model.PostPlaceType
import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel

interface UserInteractor {
    fun getTgChats(): List<TgChatInfo>?
    fun getVkUserInfo(): VkConfig?
    fun getPostPlaceStaticInfo(postPlaceType: PostPlaceType): PostPlaceStaticInfo?
    fun getPostPublishingModel(postPlaceType: PostPlaceType): PostPublishingDomainModel?

}