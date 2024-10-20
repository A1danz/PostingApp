package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.a1danz.common.resources.ResourceManager
import com.a1danz.common.presentation.base.BaseViewModel
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.a1danz.common.presentation.base.model.ButtonData
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.domain.interactor.PostPublishingInteractor
import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model.PostPublishingUiModel
import com.a1danz.feature_create_post.presentation.model.event.BottomSheetUiEvent
import com.a1danz.feature_create_post.presentation.mapper.toPostPublishingDestinationUiModel
import com.a1danz.feature_create_post.presentation.model.PostUiModel
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.toPostPlaceType
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class PostPublishingViewModel @Inject constructor(
    private val postPublishingInteractor: PostPublishingInteractor,
    private val resourceManager: ResourceManager,
) : BaseViewModel() {

    private val _publishingInProcessFlow: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val publishingInProcessFlow: StateFlow<Boolean?> = _publishingInProcessFlow

    private val _BottomSheet_uiEvent: MutableSharedFlow<BottomSheetUiEvent> = MutableSharedFlow()
    val bottomSheetUiEvent: SharedFlow<BottomSheetUiEvent> = _BottomSheet_uiEvent

    private val postPublishingModels: MutableList<PostPublishingDomainModel> = mutableListOf()

    private var postModel: PostModel? = null


    fun publishingAlreadyInProcess() = publishingInProcessFlow.value == true

    fun getAlreadyStartedPublishingUiModels(): List<PostPublishingUiModel> {
        return postPublishingModels.map { it.toPostPublishingDestinationUiModel(
            viewModelScope
        ) }
    }

    fun onPostUiModelMissing() {
        _baseUiEvent.emitAlertDialogEvent(
            AlertDialogData(
                title = resourceManager.getString(R.string.error_title),
                message = resourceManager.getString(R.string.cant_get_publication_data),
                positiveButton = ButtonData(resourceManager.getString(R.string.ok)) {
                    _BottomSheet_uiEvent.emitDismissUiEvent()
                }
            )
        )
        dismissUiEventWithDelay()
    }

    private fun dismissUiEventWithDelay(delayTime: Long = 1500) {
        viewModelScope.launch {
            delay(delayTime)
            _BottomSheet_uiEvent.emitDismissUiEvent()
        }
    }

    private suspend fun getPostModelByUiModel(postUiModel: PostUiModel, context: Context): PostModel {
        return PostModel(
            text = postUiModel.text,
            images = postUiModel.images.map { postPublishingInteractor.convertUriToFile(it.imgUri, context)}
        )
    }

    fun startPublishingProcess(postUiModel: PostUiModel, context: Context) {
        viewModelScope.launch {
            _publishingInProcessFlow.value = true

            postModel = getPostModelByUiModel(postUiModel, context)
            val publishers = mutableListOf<PostPublisher>()
            postPublishingModels.forEach { publishingModel ->
                publishingModel.publishingItems.forEach { publishingItem ->
                    publishers.add(publishingItem.publisher)
                }
            }

            postPublishingInteractor.startPublishing(
                postPublishers = publishers,
                postModel = postModel!!
            )
            checkForFinishing(publishers, postModel!!)
        }
    }

    private suspend fun checkForFinishing(publishers: List<PostPublisher>, postModel: PostModel) {
        val checkFinishingJob = Job()
        val finishedPublishingCount = AtomicInteger(0)
        withContext(checkFinishingJob + Dispatchers.IO) {
            publishers.forEach { publisher ->
                launch {
                    publisher.resultFlow.collect {
                        it?.let {
                            if (finishedPublishingCount.incrementAndGet() == publishers.size) {
                                Log.e("COUNT", "PUBLISHING COUNT - $finishedPublishingCount")
                                _publishingInProcessFlow.value = false
                                val savingJob = launch {
                                    savePostToFeed(postPublishingModels, postModel)
                                }
                                savingJob.join()
                                postPublishingModels.clear()
                                checkFinishingJob.cancel()
                            }
                        }
                    }
                }
            }
        }

        withContext(Dispatchers.IO) {
            launch {
                delay(PUBLISHING_TIMEOUT_SECONDS * 1000)
                if (publishingInProcessFlow.value != false) {
                    checkFinishingJob.cancel()
                    postPublishingModels.clear()
                    _publishingInProcessFlow.value = null
                    onTimeoutExpired()
                }
            }
        }
    }

    private fun onTimeoutExpired() {
        _baseUiEvent.emitAlertDialogEvent(
            AlertDialogData(
                title = resourceManager.getString(R.string.error_title),
                message = resourceManager.getString(R.string.post_publishing_timeout_expired),
                positiveButton = ButtonData(resourceManager.getString(R.string.ok)) {
                    _BottomSheet_uiEvent.emitDismissUiEvent()
                }
            )
        )

        dismissUiEventWithDelay(3000)
    }

    fun onPostSuccessfullyPublished() {
        _baseUiEvent.emitAlertDialogEvent(
            AlertDialogData(
                title = resourceManager.getString(R.string.post_successfully_published),
                message = resourceManager.getString(R.string.you_can_view_results_in_feed),
                positiveButton = ButtonData(resourceManager.getString(R.string.ok)) {
                    _BottomSheet_uiEvent.emitDismissUiEvent()
                }
            )
        )

        dismissUiEventWithDelay()
    }

    private suspend fun savePostToFeed(
        publishingModelsList: List<PostPublishingDomainModel>,
        postModel: PostModel,
    ) {
        val places = HashSet<PostPlaceType>()
        publishingModelsList.forEach {
            places.add(it.postPlaceType)
        }

        if (places.isNotEmpty()) {
            postPublishingInteractor.savePostToDatabase(postModel, places.toList())
        }
    }

    fun getPostPublishingUiModels(postUiModel: PostUiModel): List<PostPublishingUiModel> {
        val places = postUiModel.destinations.map {
            it.uiInfo.toPostPlaceType()
        }.toHashSet()

        return ArrayList<PostPublishingUiModel>().also { result ->
            places.forEach { place ->
                postPublishingInteractor.getPostPublishingModel(place)?.let {
                    postPublishingModels.add(it)
                    result.add(
                        it.toPostPublishingDestinationUiModel(viewModelScope)
                    )
                }
            }
        }
    }

    private fun MutableSharedFlow<BottomSheetUiEvent>.emitDismissUiEvent() {
        viewModelScope.launch {
            this@emitDismissUiEvent.emit(BottomSheetUiEvent.Dismiss)
        }
    }

    companion object {
        private const val PUBLISHING_TIMEOUT_SECONDS = 60L
    }
}