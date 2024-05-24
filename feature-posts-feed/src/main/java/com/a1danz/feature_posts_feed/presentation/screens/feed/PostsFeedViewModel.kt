package com.a1danz.feature_posts_feed.presentation.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1danz.feature_posts_feed.domain.interactor.UserInteractor
import com.a1danz.feature_posts_feed.presentation.model.PostUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsFeedViewModel @Inject constructor(
    private val userInteractor: UserInteractor
) : ViewModel() {

    private val _postsStateFlow: MutableStateFlow<List<PostUiModel>?> = MutableStateFlow(null)
    val postsStateFlow: StateFlow<List<PostUiModel>?> get() = _postsStateFlow

    private val _errorFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorFlow: StateFlow<String?> get() = _errorFlow

    fun getPosts() {

        viewModelScope.launch {
            _postsStateFlow.value = null
            _errorFlow.value = null
            runCatching {
                userInteractor.getPosts()
            }.onSuccess { posts ->
                _postsStateFlow.value = posts
            }.onFailure { error ->
                error.printStackTrace()
                _errorFlow.value = error.message
            }
        }
    }

}