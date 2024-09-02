package com.a1danz.feature_create_post.presentation.bottom_sheet.select_places

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.ext.getSafeSerializable
import com.a1danz.common.presentation.base.BaseBottomSheetDialogFragment
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.a1danz.common.presentation.base.model.ButtonData
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.FragmentSelectedSocialMediaBinding
import com.a1danz.feature_create_post.di.CreatePostComponent
import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.ui.SocialMediaSelectView
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceUiInfo
import com.a1danz.feature_places_info.presentation.model.toPostPlaceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SelectedSocialMediaBottomSheetFragment : BaseBottomSheetDialogFragment(R.layout.fragment_selected_social_media) {

    private val viewBinding: FragmentSelectedSocialMediaBinding by viewBinding(FragmentSelectedSocialMediaBinding::bind)

    private val viewModel: SelectedSocialMediaViewModel by lazy {
        ViewModelProvider(requireParentFragment(), vmFactory)[SelectedSocialMediaViewModel::class.java]
    }

    override fun inject() {
        (requireActivity().application as? FeatureContainer)?.getFeature(CreatePostComponent::class.java)
            ?.inject(this)
    }

    override fun subscribe() {
        return
    }

    override fun initViews() {
        lifecycleScope.launch {
            val selectedPlaces = arguments?.getSafeSerializable(POST_PLACES_KEY, Array<PostPlaceUiInfo>::class.java)
            if (selectedPlaces == null) {
                showErrorDialog()
                return@launch
            }
            val placesUiModel = viewModel.getPostPlaces(selectedPlaces.toList())

            placesUiModel.forEach { postPlace ->
                viewBinding.layout.addView(
                    SocialMediaSelectView(requireContext(), null).apply {
                        setupData(
                            postPlace = postPlace,
                            selectCallback = ::selectCallback
                        )
                    })
            }
        }
    }

    private fun showErrorDialog() {
        showAlertDialog(AlertDialogData(
            getString(R.string.error_title),
            getString(R.string.cant_get_data),
            positiveButton = ButtonData(getString(R.string.ok), this::dismiss)
        ))
    }

    private fun selectCallback(isChecked: Boolean, placeUiInfo: PostPlaceUiInfo) {
        viewModel.placeEdited(isChecked to placeUiInfo)
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