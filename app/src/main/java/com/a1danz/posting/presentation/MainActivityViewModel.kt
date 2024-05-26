package com.a1danz.posting.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1danz.feature_create_post.domain.model.PostPublishingItemDomainModel
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.posting.domain.interactor.MainActivityUserInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val interactor: MainActivityUserInteractor
) : ViewModel() {

    private val publishers: HashMap<String, PostPublishingItemDomainModel> = hashMapOf()

    fun startPublishingProcess(postPublishingItem: PostPublishingItemDomainModel, postModel: PostModel) {
        addPublisher(postPublishingItem.itemInfo.uId, postPublishingItem)
        viewModelScope.launch {
            interactor.startPublishingProcess(postPublishingItem.publisher, postModel)
            delay(1000)
            processAllPublishersFinishWorking()
        }
    }

    private fun addPublisher(uId: String, postPublishingItem: PostPublishingItemDomainModel) {
        publishers[uId] = postPublishingItem
    }

    fun getPublishers(): HashMap<String, PostPublisher> {
        return HashMap(publishers.mapValues { it.value.publisher })
    }

    suspend fun processAllPublishersFinishWorking() {
        val allEnded = true
        publishers.values.forEach {
            if (it.publisher.creatingResult == null) return
            else return@forEach
        }

        if (allEnded) {
            publishers.clear()
        }
    }
}