package com.a1danz.feature_create_post.domain.factory

import android.util.Log
import com.a1danz.common.domain.model.Config
import com.a1danz.feature_create_post.presentation.model.PostPlaceDetailInfoUiModel
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceStaticInfo
import javax.inject.Inject

class PostPlacesDetailInfoFactory @Inject constructor(
    private val placesStaticInfo: HashMap<PostPlaceType, PostPlaceStaticInfo>,
    private val config: Config
) {
    fun getPostPlaceDetailInfoModels(postPlaceType: PostPlaceType): List<PostPlaceDetailInfoUiModel> {
        val result = mutableListOf<PostPlaceDetailInfoUiModel>()

        val placeStaticInfo = getStaticInfoAboutPlace(postPlaceType) ?: return result
        when (postPlaceType) {
            PostPlaceType.VK_GROUP -> {
                val groups = config.vkConfig?.userGroups
                if (groups == null) {
                    Log.e("ERROR", "USER WANT TO CREATE POST IN VK GROUP, BUT VK-CONFIG IS NULL")
                } else {
                    groups.forEach {
                        result.add(
                            PostPlaceDetailInfoUiModel(
                                placeStaticInfo,
                                it.groupName,
                                "https://img.mvideo.ru/Pdb/400123281.jpg"
                            )
                        )
                    }
                }
            }

            PostPlaceType.VK_PAGE -> {
                val vkInfo = config.vkConfig?.userInfo
                if (vkInfo == null) {
                    Log.e("ERROR", "USER WANT TO CREATE POST IN VK PAGE, BUT VK-CONFIG IS NULL")
                } else {
                    result.add(
                        PostPlaceDetailInfoUiModel(
                            placeStaticInfo,
                            vkInfo.fullName,
                            vkInfo.userImg ?: ""
                        )
                    )
                }
            }

            PostPlaceType.TG -> {
                val tgChats = config.tgConfig?.selectedChats
                if (tgChats == null) {
                    Log.e("ERROR", "USER WANT TO CREATE POST IN TG CHATS, BUT TG-CONFIG IS NULL")
                } else {
                    tgChats.forEach {
                        result.add(PostPlaceDetailInfoUiModel(
                            placeStaticInfo,
                            it.name,
                            it.photo ?: ""
                        ))
                    }
                }
            }
        }

        return result
    }

    private fun getStaticInfoAboutPlace(postPlaceType: PostPlaceType): PostPlaceStaticInfo? {
        val staticInfo = placesStaticInfo[postPlaceType]
        if (staticInfo == null) Log.e(
            "STATIC INFO IS NULL",
            "STATIC INFO ABOUT $postPlaceType NOT FOUND"
        )

        return staticInfo
    }
}