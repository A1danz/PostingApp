package com.a1danz.feature_posts_feed.presentation.screens.feed

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.a1danz.common.resources.ResourceManager
import com.a1danz.common.presentation.base.BaseViewModel
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.a1danz.common.presentation.base.model.ButtonData
import com.a1danz.common.presentation.base.model.ToastData
import com.a1danz.feature_posts_feed.R
import com.a1danz.feature_posts_feed.domain.interactor.UserInteractor
import com.a1danz.feature_posts_feed.presentation.model.PostUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsFeedViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val resourceManager: ResourceManager,
) : BaseViewModel() {

    val postsPagingFlow = userInteractor.getPosts().cachedIn(viewModelScope)

    private fun removePost(postUiModel: PostUiModel) {
        viewModelScope.launch {
            runCatching {
                userInteractor.removePost(postUiModel)
            }.onSuccess {
                _baseUiEvent.emitToastEvent(
                    ToastData(resourceManager.getString(R.string.post_successfully_removed))
                )
            }.onFailure {
                Log.e("POST REMOVING", "Post removing err", it)
                _baseUiEvent.emitToastEvent(
                    ToastData(resourceManager.getString(R.string.cant_remove_post))
                )
            }
        }
    }

    fun onPostRemovedClicked(postUiModel: PostUiModel) {
        _baseUiEvent.emitAlertDialogEvent(
            AlertDialogData(
                title = resourceManager.getString(R.string.post_removing),
                message = resourceManager.getString(R.string.post_removing_description),
                positiveButton = ButtonData(resourceManager.getString(R.string.remove_post)) {
                    removePost(postUiModel)
                },
                negativeButton = ButtonData(resourceManager.getString(R.string.cancel)) {}
            )
        )
    }
}