package com.a1danz.feature_create_post.presentation.bottom_sheet.select_places

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.ext.getSafeSerializable
import com.a1danz.common.presentation.base.BaseBottomSheetDialogFragment
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.FragmentSelectedSocialMediaBinding
import com.a1danz.feature_create_post.di.CreatePostComponent
import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.ui.SocialMediaSelectView
import com.a1danz.feature_create_post.presentation.model.event.BottomSheetUiEvent
import com.a1danz.feature_places_info.presentation.model.PostPlaceUiInfo

class SelectedSocialMediaBottomSheetFragment : BaseBottomSheetDialogFragment<SelectedSocialMediaViewModel>(R.layout.fragment_selected_social_media) {

    private val viewBinding: FragmentSelectedSocialMediaBinding by viewBinding(FragmentSelectedSocialMediaBinding::bind)

    override val viewModel: SelectedSocialMediaViewModel by lazy {
        ViewModelProvider(requireParentFragment(), vmFactory)[SelectedSocialMediaViewModel::class.java]
    }

    override fun inject() {
        (requireActivity().application as? FeatureContainer)?.getFeature(CreatePostComponent::class.java)
            ?.inject(this)
    }

    override fun subscribe() {
        viewModel.uiEvent.observe(::collectUiEvent)
    }

    override fun initViews() {
        val selectedPlaces = arguments?.getSafeSerializable(POST_PLACES_KEY, Array<PostPlaceUiInfo>::class.java)
            ?: return viewModel.onSelectedPlacesMissing()

        val placesUiModel = viewModel.getPostPlaces(selectedPlaces.toList())
        placesUiModel.forEach { postPlace ->
            viewBinding.layout.addView(
                SocialMediaSelectView(requireContext(), null).apply {
                    setupData(
                        postPlace = postPlace,
                        selectCallback = ::selectCallback
                    )
                }
            )
        }
    }

    private fun selectCallback(isChecked: Boolean, placeUiInfo: PostPlaceUiInfo) {
        viewModel.placeEdited(isChecked to placeUiInfo)
    }

    private fun collectUiEvent(uiEvent: BottomSheetUiEvent) {
        when(uiEvent) {
            BottomSheetUiEvent.Dismiss -> {
                dismiss()
            }
        }
    }

    companion object {
        const val TAG = "select_social_media_bsheet"
        private const val POST_PLACES_KEY = "post_places"

        fun getInstance(postPlaces: List<PostPlaceUiInfo>): SelectedSocialMediaBottomSheetFragment {
            return SelectedSocialMediaBottomSheetFragment().apply {
                arguments = Bundle().also { bundle ->
                    bundle.putSerializable(POST_PLACES_KEY, postPlaces.toTypedArray())
                }
            }
        }
    }
}