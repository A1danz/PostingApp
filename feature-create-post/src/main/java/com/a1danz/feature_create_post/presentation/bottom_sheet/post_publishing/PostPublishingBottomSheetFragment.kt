package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseBottomSheetDialogFragment
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.FragmentPostPublishingBinding
import com.a1danz.feature_create_post.di.CreatePostComponent
import com.a1danz.feature_create_post.domain.model.PostDomainModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.ui.PostPublishingView
import com.a1danz.feature_post_publisher_api.model.PostModel
import kotlinx.coroutines.launch

class PostPublishingBottomSheetFragment(
    private val postDomainModel: PostDomainModel
) : BaseBottomSheetDialogFragment(R.layout.fragment_post_publishing) {
    private val viewBinding: FragmentPostPublishingBinding by viewBinding(FragmentPostPublishingBinding::bind)
    private val viewModel: PostPublishingViewModel by viewModels { vmFactory }

    override fun inject() {
        (requireActivity().application as FeatureContainer).getFeature(CreatePostComponent::class.java)
            .inject(this)
    }

    override fun subscribe() {
        return
    }

    override fun initViews() {
        with(viewBinding) {
            postDomainModel.postPlaces.forEach { postPlaceType ->
                val postPlaceInfo = viewModel.getPostPlaceStaticInfo(postPlaceType)
                if (postPlaceInfo == null) {
                    Log.e("STATIC INFO", "STATIC INFO ABOUT $postPlaceType NOT FOUND")
                    return
                }

                viewModel.getPostPublishingDomainModel(postPlaceType)?.let {
                    layout.addView(
                        PostPublishingView(requireContext(), null).apply {
                            setPlaceInfo(it.postPlaceStaticInfo)
                            it.postPublishingItems.forEach { postPublishingItem ->
                                val postPublishingItemView = addItem(postPublishingItem)
                                lifecycleScope.launch {
                                    postPublishingItem.publisher.creatingStatusFlow.collect { _status ->
                                        _status?.let { status ->
                                            postPublishingItemView.setPublishingStatus(
                                                viewModel.getStatusUiModel(status)
                                            )
                                        }
                                    }
                                }

                                val creatingJob = lifecycleScope.launch {
                                    viewModel.startPublishingProcess(postPublishingItem.publisher, PostModel(
                                        postDomainModel.postImages,
                                        postDomainModel.postText
                                    ))
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    companion object {
        const val TAG = "POST_PUBLISHING_FRAGMENT"
    }
}