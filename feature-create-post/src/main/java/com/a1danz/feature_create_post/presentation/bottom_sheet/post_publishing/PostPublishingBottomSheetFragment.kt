package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing

import android.util.Log
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseBottomSheetDialogFragment
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.FragmentPostPublishingBinding
import com.a1danz.feature_create_post.di.CreatePostComponent
import com.a1danz.feature_create_post.domain.model.PostDomainModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.ui.PostPublishingView

class PostPublishingBottomSheetFragment(
    private val postDomainModel: PostDomainModel
) : BaseBottomSheetDialogFragment(R.layout.fragment_create_post) {
    private val viewBinding: FragmentPostPublishingBinding by viewBinding(FragmentPostPublishingBinding::bind)
    private val viewModel: PostPublishingViewModel by viewModels { vmFactory }

    override fun inject() {
        (requireActivity().application as FeatureContainer).getFeature(CreatePostComponent::class.java)
            .inject(this)
    }

    override fun subscribe() {
        TODO("Not yet implemented")
    }

    override fun initViews() {
        with(viewBinding) {
            postDomainModel.postPlaces.forEach { postPlaceType ->
                val postPlaceInfo = viewModel.getPostPlaceStaticInfo(postPlaceType)
                if (postPlaceInfo == null) {
                    Log.e("STATIC INFO", "STATIC INFO ABOUT $postPlaceType NOT FOUND")
                    return
                }

                layout.addView(
                    PostPublishingView(requireContext(), null).apply {
                        setPlaceInfo(postPlaceInfo)
                        if (postPlaceType == )
                    }
                )
            }
        }
    }
}