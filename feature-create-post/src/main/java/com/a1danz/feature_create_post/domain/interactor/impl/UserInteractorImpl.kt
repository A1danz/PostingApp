package com.a1danz.feature_create_post.domain.interactor.impl

import android.util.Log
import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_create_post.domain.interactor.UserInteractor
import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo
import com.a1danz.feature_create_post.domain.model.PostPlaceType
import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel
import com.a1danz.feature_create_post.domain.model.PostPublishingItemDomainModel
import com.a1danz.feature_create_post.domain.model.PostPublishingItemInfoDomainModel
import com.a1danz.feature_telegram_publisher.domain.TelegramPublisher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserInteractorImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val config: Config,
    private val placesStaticInfo: HashMap<PostPlaceType, PostPlaceStaticInfo>
) : UserInteractor {
    override fun getTgChats(): List<TgChatInfo>? {
        return config.tgConfig?.selectedChats
    }

    override fun getVkUserInfo(): VkConfig? {
        return config.vkConfig
    }

    override fun getPostPlaceStaticInfo(postPlaceType: PostPlaceType): PostPlaceStaticInfo? {
        val postPlaceStaticInfo = placesStaticInfo[postPlaceType]
        if (postPlaceStaticInfo == null) {
            Log.e("STATIC INFO IS NULL", "STATIC INFO ABOUT $postPlaceType IS NULL!!!")
        }
    }

    override fun getPostPublishingModel(postPlaceType: PostPlaceType): PostPublishingDomainModel? {
        val postPlaceStaticInfo = getPostPlaceStaticInfo(postPlaceType) ?: return null
        val postPublishingItems: MutableList<PostPublishingItemDomainModel> = mutableListOf()
        when (postPlaceType) {
            PostPlaceType.TG -> {
                val tgChats = getTgChats()
                if (tgChats == null) {
                    Log.e(
                        "ERROR",
                        "User choose tg, but tg not initialized. [fun getPostPublishingModel()]"
                    )
                    return null
                }

                tgChats.forEach { chat ->
                    postPublishingItems.add(
                        PostPublishingItemDomainModel(
                            TelegramPublisher(chat.id),
                            PostPublishingItemInfoDomainModel(
                                chat.name,
                                chat.photo ?: ""
                            )
                        )
                    )
                }
            }
        }
        return PostPublishingDomainModel(
            postPlaceStaticInfo,
            when (postPlaceType) {
                PostPlaceType.TG -> {
                    listOf<PostPublishingItemDomainModel>()
                }

                else -> listOf<PostPublishingItemDomainModel>()
            }
        )
    }
}