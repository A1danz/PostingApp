package com.a1danz.feature_create_post.presentation

import com.a1danz.common.resources.ResourceManager
import com.a1danz.common.presentation.base.BaseViewModel
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.domain.interactor.CreatePostInteractor
import com.a1danz.feature_create_post.presentation.mapper.toImageUiModel
import com.a1danz.feature_create_post.presentation.mapper.toPostDestinationUiModel
import com.a1danz.feature_create_post.presentation.model.ImageUiModel
import com.a1danz.feature_create_post.presentation.model.PostDestinationUiModel
import com.a1danz.feature_create_post.presentation.model.PostUiModel
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceUiInfo
import com.a1danz.feature_places_info.presentation.model.getUiInfo
import com.a1danz.feature_places_info.presentation.model.toPostPlaceType
import com.esafirm.imagepicker.model.Image
import javax.inject.Inject

class CreatePostViewModel @Inject constructor(
    private val interactor: CreatePostInteractor,
    private val resManager: ResourceManager,
) : BaseViewModel() {

    val selectedImages: MutableList<Image> = mutableListOf()

    val _selectedPlaces: HashSet<PostPlaceType> by lazy {
        interactor.getPostPlaces()
    }

    val selectedPlaces: List<PostPlaceUiInfo> get() = _selectedPlaces.map { it.getUiInfo() }

    fun setImages(images: List<Image>) {
        selectedImages.clear()
        selectedImages.addAll(images)
    }

    fun getImagesUiModels(): List<ImageUiModel> {
        return selectedImages.map { it.toImageUiModel() }
    }

    fun editPlace(isAdded: Boolean, place: PostPlaceUiInfo) {
        if (isAdded) {
            _selectedPlaces.add(place.toPostPlaceType())
        } else {
            _selectedPlaces.remove(place.toPostPlaceType())
        }
    }

    fun getPostDestinationUiModels(): List<PostDestinationUiModel> {
        return interactor.getPostDestinationDomainModels(_selectedPlaces.toList()).map {
            it.toPostDestinationUiModel()
        }
    }

    fun postIsValid(postUiModel: PostUiModel): Boolean {
        return postUiModel.text.isNotBlank() || postUiModel.images.isNotEmpty()
    }

    fun onPostIsInvalid() {
        _baseUiEvent.emitAlertDialogEvent(
            AlertDialogData(
                title = resManager.getString(R.string.invalid_data),
                message = resManager.getString(R.string.invalid_data_description)
            )
        )
    }

    fun getCorrectionsForPost(postUiModel: PostUiModel): List<String> {
        return mutableListOf<String>().apply {
            if (postUiModel.images.isEmpty()) add(resManager.getString(R.string.you_havent_attached_any_photos))
            if (postUiModel.text.isBlank()) add(resManager.getString(R.string.publication_text_will_be_empty))
        }
    }
}