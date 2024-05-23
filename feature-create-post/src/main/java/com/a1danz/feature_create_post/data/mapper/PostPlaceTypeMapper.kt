package com.a1danz.feature_create_post.data.mapper

import android.util.Log
import com.a1danz.feature_create_post.domain.model.PostPlaceType
import javax.inject.Inject

class PostPlaceTypeMapper @Inject constructor() {

    fun mapDomainToData(postPlaceType: PostPlaceType): String {
        return when(postPlaceType) {
            PostPlaceType.VK_PAGE -> VK_PAGE_NAME
            PostPlaceType.TG -> VK_GROUP_NAME
            PostPlaceType.VK_GROUP -> TG_NAME
        }
    }

    fun mapDataToDomain(placeString: String): PostPlaceType? {
        return when(placeString) {
            VK_PAGE_NAME -> PostPlaceType.VK_PAGE
            VK_GROUP_NAME -> PostPlaceType.VK_GROUP
            TG_NAME -> PostPlaceType.TG
            else ->  {
                Log.e("ERROR", "UNCATCHABLE PostPlaceType $placeString")
                null
            }
        }
    }
    companion object {
        const val VK_PAGE_NAME = "vk_page"
        const val VK_GROUP_NAME = "vk_group"
        const val TG_NAME = "tg"
    }

}