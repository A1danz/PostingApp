package com.a1danz.feature_places_info.di

import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.feature_places_info.R
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceStaticInfo
import dagger.Module
import dagger.Provides

@Module
class PostPlacesInfoModule {
    @Provides
    fun providesPostPlacesStaticInfo(resManager: ResourceManager): HashMap<PostPlaceType, PostPlaceStaticInfo> {
        val result = hashMapOf<PostPlaceType, PostPlaceStaticInfo>()

        result[PostPlaceType.VK_PAGE] = PostPlaceStaticInfo(
            resManager.getString(R.string.vk_page_title),
            R.drawable.vk_icon,
            PostPlaceType.VK_PAGE,
            resManager.getString(R.string.page),
            R.drawable.vk_icon_transparent,
            com.a1danz.common.R.color.vk_color
        )

        result[PostPlaceType.VK_GROUP] = PostPlaceStaticInfo(
            resManager.getString(R.string.vk_group_title),
            R.drawable.vk_icon,
            PostPlaceType.VK_GROUP,
            resManager.getString(R.string.groups),
            R.drawable.vk_icon_transparent,
            com.a1danz.common.R.color.vk_color
        )

        result[PostPlaceType.TG] = PostPlaceStaticInfo(
            resManager.getString(R.string.telegram),
            R.drawable.tg_icon,
            PostPlaceType.TG,
            resManager.getString(R.string.telegram),
            R.drawable.tg_icon_transparent,
            com.a1danz.common.R.color.telegram_color
        )

        return result
    }
}