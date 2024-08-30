package com.a1danz.feature_create_post.presentation

import android.content.Context
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1danz.feature_create_post.domain.interactor.UserSelectedMediaInteractor
import com.a1danz.feature_create_post.presentation.model.PostPlaceDetailInfoUiModel
import com.a1danz.feature_create_post.utils.PostPublishingStarter
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceStaticInfo
import com.esafirm.imagepicker.model.Image
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class CreatePostViewModel @Inject constructor(
    private val userInteractor: UserSelectedMediaInteractor,
    private val placesStaticInfo: HashMap<PostPlaceType, PostPlaceStaticInfo>
) : ViewModel() {
    private val selectedImages: MutableList<Image> = mutableListOf()
    val selectedPlaces: HashSet<PostPlaceType> by lazy {
        getPlaces()
    }

    private val _publishingInProcessStateFlow: MutableStateFlow<Boolean?> =  MutableStateFlow(null)
    val publishingInProcessFlow: StateFlow<Boolean?> get() = _publishingInProcessStateFlow

    fun setImages(images: List<Image>) {
        selectedImages.clear()
        selectedImages.addAll(images)
    }

    fun getImages(): MutableList<Image> = selectedImages

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

    suspend fun convertUriToFile(uri: Uri, context: Context): File {
        return userInteractor.convertUriToFile(uri, context)
    }

    private fun isPublishingInProcess(activity: FragmentActivity): Boolean {
        return (activity as? PostPublishingStarter)?.publishingInProcess() ?: false
    }

    fun initUpdatesInPublishingInProcessFlow(activity: FragmentActivity) {
        viewModelScope.launch {
            while(true) {
                _publishingInProcessStateFlow.value = isPublishingInProcess(activity)
                delay(1000)
            }
        }
    }

    suspend fun getPostPlaceDetailInfoUiModels(): List<PostPlaceDetailInfoUiModel> {
        return userInteractor.getPostPlaceDetailInfoUiModels(selectedPlaces.toList())
    }
}