package com.a1danz.feature_create_post.presentation.bottom_sheet.select_places

import androidx.lifecycle.viewModelScope
import com.a1danz.common.resources.ResourceManager
import com.a1danz.common.presentation.base.BaseViewModel
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.a1danz.common.presentation.base.model.ButtonData
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.domain.interactor.UserSelectedMediaInteractor
import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.model.PostPlaceUiModel
import com.a1danz.feature_create_post.presentation.model.event.BottomSheetUiEvent
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceUiInfo
import com.a1danz.feature_places_info.presentation.model.getUiInfo
import com.a1danz.feature_places_info.presentation.model.toPostPlaceType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectedSocialMediaViewModel @Inject constructor(
    private val userInteractor: UserSelectedMediaInteractor,
    private val resourceManager: ResourceManager,
) : BaseViewModel() {

    private val _placesEditingState: MutableSharedFlow<Pair<Boolean, PostPlaceUiInfo>> = MutableSharedFlow()
    val placesEditingState = _placesEditingState

    private val _uiEvent: MutableSharedFlow<BottomSheetUiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<BottomSheetUiEvent> = _uiEvent

    fun getPostPlaces(alreadySelected: List<PostPlaceUiInfo>): List<PostPlaceUiModel> {
        val places: ArrayList<PostPlaceUiModel> = arrayListOf()
        val alreadySelectedTypes = alreadySelected.map { it.toPostPlaceType() }.toHashSet()

        // vk
        val vkConfig = userInteractor.getVkConfig()
        if (vkConfig != null) {
            places.add(
                PostPlaceUiModel(
                    PostPlaceType.VK_PAGE.getUiInfo(),
                    vkConfig.userInfo.fullName,
                    alreadySelectedTypes.contains(PostPlaceType.VK_PAGE)
                ),
            )
        }
        if (vkConfig != null && vkConfig.userGroups.isNotEmpty()) {
            places.add(
                PostPlaceUiModel(
                    PostPlaceType.VK_GROUP.getUiInfo(),
                    vkConfig.userGroups.joinToString(", ") { it.groupName },
                    alreadySelectedTypes.contains(PostPlaceType.VK_GROUP)
                )
            )
        }

        // tg
        val tgConfig = userInteractor.getTgConfig()
        if (tgConfig != null && tgConfig.selectedChats.isNotEmpty()) {
            places.add(
                PostPlaceUiModel(
                    PostPlaceType.TG.getUiInfo(),
                    tgConfig.selectedChats.joinToString(", ") { it.name },
                    alreadySelectedTypes.contains(PostPlaceType.TG)
                )
            )
        }

        return places
    }

    fun placeEdited(placeEdit: Pair<Boolean, PostPlaceUiInfo>) {
        viewModelScope.launch {
            _placesEditingState.emit(placeEdit)
        }
    }

    fun onSelectedPlacesMissing() {
        _baseUiEvent.emitAlertDialogEvent(
            AlertDialogData(
                resourceManager.getString(R.string.error_title),
                resourceManager.getString(R.string.cant_get_data),
                positiveButton = ButtonData(resourceManager.getString(R.string.ok)) {
                    viewModelScope.launch {
                        _uiEvent.emit(BottomSheetUiEvent.Dismiss)
                    }
                }
            )
        )
    }
}