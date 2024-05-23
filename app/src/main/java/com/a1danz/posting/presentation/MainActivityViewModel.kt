package com.a1danz.posting.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.posting.domain.interactor.MainActivityUserInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val interactor: MainActivityUserInteractor
) : ViewModel() {

    private val publishers: HashMap<String, PostPublisher> = hashMapOf()

    fun startPublishingProcess(uId: String, postPublisher: PostPublisher, postModel: PostModel) {
        addPublisher(uId, postPublisher)
        viewModelScope.launch {
            interactor.startPublishingProcess(postPublisher, postModel)
            delay(1000)
            processAllPublishersFinishWorking()
        }
    }

    fun getPublisher(uId: String): PostPublisher? = publishers[uId]

    private fun addPublisher(uId: String, postPublisher: PostPublisher) {
        publishers[uId] = postPublisher
    }

    fun getPublishers(): HashMap<String, PostPublisher> {
        return publishers
    }

    suspend fun processAllPublishersFinishWorking() {
        val allEnded = true
        publishers.values.forEach {
            if (it.creatingResult == null) return
            else return@forEach
        }

        if (allEnded) {
            publishers.clear()
        }
    }
}