package com.a1danz.feature_places_info.presentation.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.a1danz.feature_places_info.R
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.Serializable

sealed class PostPlaceUiInfo(
    @StringRes val title: Int,
    @StringRes val shortTitle: Int,
    @DrawableRes val img: Int?,
    @DrawableRes val transparentIcon: Int?,
    @ColorRes val color: Int
) : Serializable {

    class VkPage : PostPlaceUiInfo(
        title = R.string.vk_page_title,
        shortTitle = R.string.page,
        img = R.drawable.vk_icon,
        transparentIcon = R.drawable.vk_icon_transparent,
        color = com.a1danz.common.R.color.vk_color
    )

    class VkGroup : PostPlaceUiInfo(
        title = R.string.vk_group_title,
        shortTitle = R.string.groups,
        img = R.drawable.vk_icon,
        transparentIcon = R.drawable.vk_icon_transparent,
        color = com.a1danz.common.R.color.vk_color
        )

    class Telegram : PostPlaceUiInfo(
        title = R.string.telegram,
        shortTitle = R.string.telegram,
        img = R.drawable.tg_icon,
        transparentIcon = R.drawable.tg_icon_transparent,
        color = com.a1danz.common.R.color.telegram_color
    )
}

fun PostPlaceType.getUiInfo(): PostPlaceUiInfo {
    return when (this) {
        PostPlaceType.VK_PAGE -> PostPlaceUiInfo.VkPage()
        PostPlaceType.VK_GROUP -> PostPlaceUiInfo.VkGroup()
        PostPlaceType.TG -> PostPlaceUiInfo.Telegram()
    }
}

fun PostPlaceUiInfo.toPostPlaceType(): PostPlaceType {
    return when(this) {
        is PostPlaceUiInfo.Telegram -> PostPlaceType.TG
        is PostPlaceUiInfo.VkGroup -> PostPlaceType.VK_GROUP
        is PostPlaceUiInfo.VkPage -> PostPlaceType.VK_PAGE
    }
}