package com.a1danz.feature_create_post.presentation.bottom_sheet

import androidx.lifecycle.ViewModel
import com.a1danz.feature_create_post.domain.interactor.UserSelectedMediaInteractor
import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo
import com.a1danz.feature_create_post.domain.model.PostPlaceType
import com.a1danz.feature_create_post.presentation.bottom_sheet.model.PostPlaceUiModel
import javax.inject.Inject

class SelectedSocialMediaViewModel @Inject constructor(
    private val userInteractor: UserSelectedMediaInteractor,
    private val placesStaticInfo: HashMap<PostPlaceType, PostPlaceStaticInfo>
) : ViewModel() {

    fun getPostPlaces(alreadySelected: HashSet<PostPlaceType>): List<PostPlaceUiModel> {
        val places: ArrayList<PostPlaceUiModel> = arrayListOf()

        // vk
        val vkConfig = userInteractor.getVkConfig()
        if (vkConfig != null) {
            places.add(
                PostPlaceUiModel(
                    placesStaticInfo[PostPlaceType.VK_PAGE] ?: throw IllegalStateException("Static info about VK_PAGE not found"),
                    vkConfig.userInfo.fullName,
                    alreadySelected.contains(PostPlaceType.VK_PAGE)
                ),
            )
        }
        if (vkConfig != null && vkConfig.userGroups.isNotEmpty()) {
            places.add(
                PostPlaceUiModel(
                    placesStaticInfo[PostPlaceType.VK_GROUP] ?: throw IllegalStateException("Static info about VK_GROUP not found"),
                    vkConfig.userGroups.joinToString(", ") { it.groupName },
                    alreadySelected.contains(PostPlaceType.VK_GROUP)
                )
            )
        }

        // tg
        val tgConfig = userInteractor.getTgConfig()
        if (tgConfig != null && tgConfig.selectedChats.isNotEmpty()) {
            places.add(
                PostPlaceUiModel(
                    placesStaticInfo[PostPlaceType.TG] ?: throw IllegalStateException("Static info about TG_GROUP not found"),
                    tgConfig.selectedChats.joinToString(", ") { it.name },
                    alreadySelected.contains(PostPlaceType.TG)
                )
            )
        }

        return places
    }
}