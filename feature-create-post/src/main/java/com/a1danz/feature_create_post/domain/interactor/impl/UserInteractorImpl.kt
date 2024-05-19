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
import com.a1danz.feature_telegram_publisher.di.TelegramPublisherComponent
import com.a1danz.feature_telegram_publisher.domain.TelegramPublisher
import com.a1danz.feature_telegram_publisher.domain.TelegramPublisher_Factory
import com.a1danz.vk_publisher.di.VkPublisherComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserInteractorImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val config: Config,
    private val placesStaticInfo: HashMap<PostPlaceType, PostPlaceStaticInfo>,
    private val telegramPublisherComponent: TelegramPublisherComponent,
    private val vkPublisherComponent: VkPublisherComponent
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

        return postPlaceStaticInfo
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
                            telegramPublisherComponent.telegramPublisherFactory().create(chat.id),
                            PostPublishingItemInfoDomainModel(
                                chat.name,
                                chat.photo ?: ""
                            )
                        )
                    )
                }
            } PostPlaceType.VK_PAGE -> {
                val vkConfig = getVkUserInfo()
                if (vkConfig == null) {
                    Log.e("ERROR", "User chose vk-page, but vk doesnt initialized. [fun getPostPublishingModel()]")
                    return null
                }

                postPublishingItems.add(
                    PostPublishingItemDomainModel(
                        vkPublisherComponent.vkPublisherFactory().create(vkConfig.userId, false),
                        PostPublishingItemInfoDomainModel(
                            vkConfig.userInfo.fullName,
                            vkConfig.userInfo.userImg ?: ""
                        )
                    )
                )
            } PostPlaceType.VK_GROUP -> {
                val vkConfig = getVkUserInfo()
                if (vkConfig == null) {
                    Log.e("ERROR", "User chose vk-groups, but vk doesnt initialized. [fun getPostPublishingModel()]")
                    return null
                }


                val vkPublisherFactory = vkPublisherComponent.vkPublisherFactory()
                vkConfig.userGroups.forEach { group ->
                    Log.e("GROUPID", group.groupId.toString())
                    postPublishingItems.add(
                        PostPublishingItemDomainModel(
                            vkPublisherFactory.create(group.groupId * -1, true),
                            PostPublishingItemInfoDomainModel(
                                group.groupName,
                                "https://mmbuk-rodnik.ru/images/info/PinClipartcom_campin.png"
                            )
                        )
                    )
                }
            }
            else -> {
                Log.e("UNCATCHABLE TYPE", "UNDEFINED $postPlaceType in [fun getPostPublishingModel()]")
                return null
            }
        }
        return PostPublishingDomainModel(
            postPlaceStaticInfo,
            postPublishingItems
        )
    }
}