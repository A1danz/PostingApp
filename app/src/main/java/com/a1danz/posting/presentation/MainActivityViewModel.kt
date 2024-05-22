package com.a1danz.posting.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.posting.domain.interactor.MainActivityUserInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val interactor: MainActivityUserInteractor
) : ViewModel() {

    fun startPublishingProcess(postPublisher: PostPublisher, postModel: PostModel) {
        viewModelScope.launch {
            interactor.startPublishingProcess(postPublisher, postModel)
        }
    }
}