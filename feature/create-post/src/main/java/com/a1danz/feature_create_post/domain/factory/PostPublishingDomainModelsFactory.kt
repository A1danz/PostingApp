package com.a1danz.feature_create_post.domain.factory

import android.util.Log
import com.a1danz.common.domain.model.Config
import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel
import com.a1danz.feature_create_post.domain.model.PostPublishingItemDomainModel
import com.a1danz.feature_create_post.domain.model.PostDestinationDomainModel
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_telegram_publisher.di.TelegramPublisherComponent
import com.a1danz.vk_publisher.di.VkPublisherComponent
import javax.inject.Inject

class PostPublishingDomainModelsFactory @Inject constructor(
    private val config: Config,
    private val telegramPublisherComponent: TelegramPublisherComponent,
    private val vkPublisherComponent: VkPublisherComponent
) {
    fun createPostPublishingModel(postPlaceType: PostPlaceType): PostPublishingDomainModel? {
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
                            PostDestinationDomainModel(
                                chat.name,
                                chat.photo ?: "",
                                chat.id.toString(),
                                PostPlaceType.TG
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
                        PostDestinationDomainModel(
                            vkConfig.userInfo.fullName,
                            vkConfig.userInfo.userImg ?: "",
                            vkConfig.userId.toString(),
                            PostPlaceType.VK_PAGE
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
                            PostDestinationDomainModel(
                                group.groupName,
                                group.img,
                                group.groupId.toString(),
                                PostPlaceType.VK_GROUP
                            )
                        )
                    )
                }
            }
        }

        return PostPublishingDomainModel(
            postPlaceType,
            postPublishingItems
        )
    }
}