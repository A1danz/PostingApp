package com.a1danz.feature_create_post.domain.factory

import android.util.Log
import com.a1danz.common.domain.model.User
import com.a1danz.feature_create_post.domain.model.PostDestinationDomainModel
import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel
import com.a1danz.feature_create_post.domain.model.PostPublishingItemDomainModel
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_telegram_publisher.di.TelegramPublisherComponent
import com.a1danz.vk_publisher.di.VkPublisherComponent
import javax.inject.Inject
import dagger.Lazy

class PostPublishingDomainModelsFactory @Inject constructor(
    private val user: User,
    private val telegramPublisherComponent: Lazy<TelegramPublisherComponent>,
    private val vkPublisherComponent: Lazy<VkPublisherComponent>
) {
    fun createPostPublishingModel(postPlaceType: PostPlaceType): PostPublishingDomainModel? {
        val postPublishingItems: MutableList<PostPublishingItemDomainModel> = mutableListOf()

        when (postPlaceType) {

            PostPlaceType.TG -> {
                val tgChats = user.config.tgConfig?.selectedChats
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
                            telegramPublisherComponent.get().telegramPublisherFactory().create(chat.id),
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
                val vkConfig = user.config.vkConfig
                if (vkConfig == null) {
                    Log.e(
                        "ERROR",
                        "User chose vk-page, but vk doesnt initialized. [fun getPostPublishingModel()]"
                    )
                    return null
                }

                postPublishingItems.add(
                    PostPublishingItemDomainModel(
                        vkPublisherComponent.get().vkPublisherFactory().create(vkConfig.userId, false),
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
                val vkConfig = user.config.vkConfig
                if (vkConfig == null) {
                    Log.e(
                        "ERROR",
                        "User chose vk-groups, but vk doesnt initialized. [fun getPostPublishingModel()]"
                    )
                    return null
                }


                val vkPublisherFactory = vkPublisherComponent.get().vkPublisherFactory()
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