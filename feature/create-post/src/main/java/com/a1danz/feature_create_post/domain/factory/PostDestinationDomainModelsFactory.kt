package com.a1danz.feature_create_post.domain.factory

import android.util.Log
import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.User
import com.a1danz.feature_create_post.domain.model.PostDestinationDomainModel
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import javax.inject.Inject

class PostDestinationDomainModelsFactory @Inject constructor(
    private val user: User
) {
    fun getPostDestinationUiModelsByPlaceType(postPlaceType: PostPlaceType): List<PostDestinationDomainModel> {
        val result = mutableListOf<PostDestinationDomainModel>()

        when (postPlaceType) {
            PostPlaceType.VK_GROUP -> {
                val groups = user.config.vkConfig?.userGroups
                if (groups == null) {
                    Log.e("ERROR", "USER WANT TO CREATE POST IN VK GROUP, BUT VK-CONFIG IS NULL - ${user.config}")
                } else {
                    groups.forEach {
                        result.add(
                            PostDestinationDomainModel(
                                it.groupName,
                                it.img,
                                it.groupId.toString(),
                                postPlaceType
                            )
                        )
                    }
                }
            }

            PostPlaceType.VK_PAGE -> {
                val vkConfig = user.config.vkConfig
                if (vkConfig == null) {
                    Log.e("ERROR", "USER WANT TO CREATE POST IN VK PAGE, BUT VK-CONFIG IS NULL - ${user.config}")
                } else {
                    val userInfo = vkConfig.userInfo
                    result.add(
                        PostDestinationDomainModel(
                            userInfo.fullName,
                            userInfo.userImg ?: "",
                            vkConfig.userId.toString(),
                            postPlaceType
                        )
                    )
                }
            }

            PostPlaceType.TG -> {
                val tgChats = user.config.tgConfig?.selectedChats
                if (tgChats == null) {
                    Log.e("ERROR", "USER WANT TO CREATE POST IN TG CHATS, BUT TG-CONFIG IS NULL - ${user.config}")
                } else {
                    tgChats.forEach {
                        result.add(
                            PostDestinationDomainModel(
                                it.name,
                                it.photo ?: "",
                                it.id.toString(),
                                postPlaceType,
                            )
                        )
                    }
                }
            }
        }

        return result
    }
}