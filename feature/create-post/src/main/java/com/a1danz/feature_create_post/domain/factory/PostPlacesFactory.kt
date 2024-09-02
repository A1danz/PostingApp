package com.a1danz.feature_create_post.domain.factory

import com.a1danz.common.domain.model.Config
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import javax.inject.Inject

class PostPlacesFactory @Inject constructor(
    val config: Config
) {

    fun getPostPlaces(): HashSet<PostPlaceType> {
        return hashSetOf<PostPlaceType>().also { result ->
            with(config) {
                if (vkConfig != null) {
                    result.add(PostPlaceType.VK_PAGE)
                }

                if (vkConfig != null && vkConfig!!.userGroups.isNotEmpty()) {
                    result.add(PostPlaceType.VK_GROUP)
                }

                if (tgConfig != null && tgConfig!!.selectedChats.isNotEmpty()) {
                    result.add(PostPlaceType.TG)
                }
            }
        }
    }
}