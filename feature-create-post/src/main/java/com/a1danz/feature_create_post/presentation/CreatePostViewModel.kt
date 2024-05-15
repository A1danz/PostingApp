package com.a1danz.feature_create_post.presentation

import androidx.lifecycle.ViewModel
import com.a1danz.feature_create_post.domain.interactor.UserSelectedMediaInteractor
import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo
import com.a1danz.feature_create_post.domain.model.PostPlaceType
import com.esafirm.imagepicker.model.Image
import javax.inject.Inject

class CreatePostViewModel @Inject constructor(
    private val userInteractor: UserSelectedMediaInteractor,
    private val placesStaticInfo: HashMap<PostPlaceType, PostPlaceStaticInfo>
) : ViewModel() {
    private val selectedImages: MutableList<Image> = mutableListOf()
    val selectedPlaces: HashSet<PostPlaceType> by lazy {
        getPlaces()
    }

    fun setImages(images: List<Image>) {
        selectedImages.clear()
        selectedImages.addAll(images)
    }

    fun getImages(): List<Image> = selectedImages

    private fun getPlaces(): HashSet<PostPlaceType> {
        val result = hashSetOf<PostPlaceType>()

        val vkConfig = userInteractor.getVkConfig()
        if (vkConfig != null) {
            result.add(PostPlaceType.VK_PAGE)
            if (vkConfig.userGroups.isNotEmpty()) {
                result.add(PostPlaceType.VK_GROUP)
            }
        }

        val tgConfig = userInteractor.getTgConfig()
        if (tgConfig != null && tgConfig.selectedChats.isNotEmpty()) {
            result.add(PostPlaceType.TG)
        }

        return result
    }

    fun getPostPlaceStaticInfo(postPlaceType: PostPlaceType): PostPlaceStaticInfo {
        return placesStaticInfo[postPlaceType] ?: throw IllegalStateException("Static info about $postPlaceType not found")
    }

    fun addPlace(placeType: PostPlaceType) {
        selectedPlaces.add(placeType)
    }

    fun removePlace(placeType: PostPlaceType) {
        selectedPlaces.remove(placeType)
    }
}