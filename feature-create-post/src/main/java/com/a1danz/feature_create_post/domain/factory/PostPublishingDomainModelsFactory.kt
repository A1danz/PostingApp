package com.a1danz.feature_create_post.domain.factory

import android.util.Log
import com.a1danz.common.domain.model.Config
import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo
import com.a1danz.feature_create_post.domain.model.PostPlaceType
import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel
import com.a1danz.feature_create_post.domain.model.PostPublishingItemDomainModel
import com.a1danz.feature_create_post.domain.model.PostPublishingItemInfoDomainModel
import com.a1danz.feature_telegram_publisher.di.TelegramPublisherComponent
import com.a1danz.vk_publisher.di.VkPublisherComponent
import javax.inject.Inject

class PostPublishingDomainModelsFactory @Inject constructor(
    private val config: Config,
    private val placesStaticInfo: HashMap<PostPlaceType, PostPlaceStaticInfo>,
    private val telegramPublisherComponent: TelegramPublisherComponent,
    private val vkPublisherComponent: VkPublisherComponent
) {
    private fun getPostPlaceStaticInfo(postPlaceType: PostPlaceType): PostPlaceStaticInfo? {
        val postPlaceStaticInfo = placesStaticInfo[postPlaceType]
        if (postPlaceStaticInfo == null) {
            Log.e("STATIC INFO IS NULL", "STATIC INFO ABOUT $postPlaceType IS NULL!!!")
        }

        return postPlaceStaticInfo
    }


    fun createPostPublishingModel(postPlaceType: PostPlaceType): PostPublishingDomainModel? {
        val postPlaceStaticInfo = getPostPlaceStaticInfo(postPlaceType) ?: return null
        val postPublishingItems: MutableList<PostPublishingItemDomainModel> = mutableListOf()

        when (postPlaceType) {
            PostPlaceType.TG -> {
                val tgChats = config.tgConfig?.selectedChats
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
                                chat.photo ?: "",
                                chat.id.toString()
                            )
                        )
                    )
                }
            }

            PostPlaceType.VK_PAGE -> {
                val vkConfig = config.vkConfig
                if (vkConfig == null) {
                    Log.e(
                        "ERROR",
                        "User chose vk-page, but vk doesnt initialized. [fun getPostPublishingModel()]"
                    )
                    return null
                }

                postPublishingItems.add(
                    PostPublishingItemDomainModel(
                        vkPublisherComponent.vkPublisherFactory().create(vkConfig.userId, false),
                        PostPublishingItemInfoDomainModel(
                            vkConfig.userInfo.fullName,
                            vkConfig.userInfo.userImg ?: "",
                            vkConfig.userId.toString()
                        )
                    )
                )
            }

            PostPlaceType.VK_GROUP -> {
                val vkConfig = config.vkConfig
                if (vkConfig == null) {
                    Log.e(
                        "ERROR",
                        "User chose vk-groups, but vk doesnt initialized. [fun getPostPublishingModel()]"
                    )
                    return null
                }


                val vkPublisherFactory = vkPublisherComponent.vkPublisherFactory()
                vkConfig.userGroups.forEach { group ->
                    postPublishingItems.add(
                        PostPublishingItemDomainModel(
                            vkPublisherFactory.create(group.groupId * -1, true),
                            PostPublishingItemInfoDomainModel(
                                group.groupName,
                                "https://mmbuk-rodnik.ru/images/info/PinClipartcom_campin.png",
                                group.groupId.toString()
                            )
                        )
                    )
                }
            }

            else -> {
                Log.e(
                    "UNCATCHABLE TYPE",
                    "UNDEFINED $postPlaceType in [fun getPostPublishingModel()]"
                )
                return null
            }
        }
        return PostPublishingDomainModel(
            postPlaceStaticInfo,
            postPublishingItems
        )
    }
}