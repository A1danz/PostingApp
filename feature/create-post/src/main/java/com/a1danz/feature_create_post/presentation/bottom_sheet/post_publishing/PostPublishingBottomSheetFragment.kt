package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.ext.getSafeSerializable
import com.a1danz.common.presentation.base.BaseBottomSheetDialogFragment
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.FragmentPostPublishingBinding
import com.a1danz.feature_create_post.di.CreatePostComponent
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model.PostPublishingUiModel
import com.a1danz.feature_create_post.presentation.model.event.BottomSheetUiEvent
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.ui.PostPublishingItemView
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.ui.PostPublishingView
import com.a1danz.feature_create_post.presentation.model.PostUiModel

class PostPublishingBottomSheetFragment : BaseBottomSheetDialogFragment<PostPublishingViewModel>(R.layout.fragment_post_publishing) {

    private val viewBinding: FragmentPostPublishingBinding by viewBinding(FragmentPostPublishingBinding::bind)
    override val viewModel: PostPublishingViewModel by lazy {
        ViewModelProvider(requireActivity(), vmFactory)[PostPublishingViewModel::class.java]
    }

    override fun inject() {
        (requireActivity().application as FeatureContainer).getFeature(CreatePostComponent::class.java)
            .inject(this)
    }

    override fun subscribe() {
        viewModel.bottomSheetUiEvent.observe(::collectUiEvent)
    }

    override fun initViews() {
        val inProcess = arguments?.getBoolean(IN_PROCESS_KEY) ?: false
        if (inProcess || viewModel.publishingAlreadyInProcess()) initAlreadyInProcessState()
        else {
            val postUiModel = arguments?.getSafeSerializable(POST_MODEL_KEY, PostUiModel::class.java)
            if (postUiModel != null) {
                initCreatingState(postUiModel)
            } else {
                viewModel.onPostUiModelMissing()
            }
        }
    }

    private fun initCreatingState(postUiModel: PostUiModel) {
        viewModel.getPostPublishingUiModels(postUiModel).forEach { publishingModel ->
            addPostPublishingModelToLayout(publishingModel)
        }

        viewModel.startPublishingProcess(postUiModel, requireContext())
    }

    private fun initAlreadyInProcessState() {
        val publishingUiModels = viewModel.getAlreadyStartedPublishingUiModels()
        if (publishingUiModels.isEmpty()) {
            viewModel.onPostSuccessfullyPublished()
        }

        publishingUiModels.forEach {
            addPostPublishingModelToLayout(it)
        }
    }

    private fun addPostPublishingModelToLayout(publishingModel: PostPublishingUiModel) {
        with(viewBinding) {
            // add publishing section(example: VK-page etc)
            layout.addView(
                PostPublishingView(requireContext(), null).apply {
                    setPlaceInfo(publishingModel.placeUiInfo)

                    // add items to selected section(for example: add vk-group to VK-group section)
                    publishingModel.postDestinations.forEach { postDestination ->

                        // create view for the certain item(for example: certain vk-group or certain tg-chat)
                        PostPublishingItemView(context, null).apply {
                            setItemModel(postDestination)
                        }.also { postPublishingItemView ->
                            addPostPublishingItemToLayout(postPublishingItemView)

                            // subscribe to post publishing status updates
                            postDestination.creatingStatusFlow.observe {
                                it?.let { status ->
                                    postPublishingItemView.setPublishingStatus(status)
                                }
                            }
                        }
                    }
                }
            )
        }
    }

    private fun collectUiEvent(uiEvent: BottomSheetUiEvent) {
        when(uiEvent) {
            BottomSheetUiEvent.Dismiss -> {
                dismiss()
            }
        }
    }

    companion object {
        const val TAG = "POST_PUBLISHING_FRAGMENT"
        const val POST_MODEL_KEY = "post_model"
        const val IN_PROCESS_KEY = "in_process"

        fun getInstance(postUiModel: PostUiModel): PostPublishingBottomSheetFragment {
            return PostPublishingBottomSheetFragment().apply {
                arguments = bundleOf().apply {
                    putSerializable(POST_MODEL_KEY, postUiModel)
                }
            }
        }

        fun getInProcessInstance(): PostPublishingBottomSheetFragment {
            return PostPublishingBottomSheetFragment().apply {
                arguments = bundleOf(
                    IN_PROCESS_KEY to true
                )
            }
        }
    }
}

