package com.a1danz.feature_create_post.presentation.bottom_sheet.select_places

import android.content.DialogInterface
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseBottomSheetDialogFragment
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.FragmentSelectedSocialMediaBinding
import com.a1danz.feature_create_post.di.CreatePostComponent
import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.ui.SocialMediaSelectView
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceStaticInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SelectedSocialMediaBottomSheetFragment(
    private val selectedPlaces: HashSet<PostPlaceType>
) : BaseBottomSheetDialogFragment(R.layout.fragment_selected_social_media) {
    private val viewBinding: FragmentSelectedSocialMediaBinding by viewBinding(FragmentSelectedSocialMediaBinding::bind)
    private val viewModel: SelectedSocialMediaViewModel by viewModels { vmFactory }
    private val _stateFlow: MutableStateFlow<Pair<Boolean, PostPlaceType>?> = MutableStateFlow(null)
    private var dismissCallback: (() -> Unit)? = null

    override fun inject() {
        (requireActivity().application as? FeatureContainer)?.getFeature(CreatePostComponent::class.java)
            ?.inject(this)
    }

    override fun subscribe() {
        return
    }

    override fun initViews() {
        lifecycleScope.launch {
            val placesUiModel = viewModel.getPostPlaces(selectedPlaces)

            placesUiModel.forEach { postPlace ->
                val postPlaceView = SocialMediaSelectView(requireContext(), null)
                postPlaceView.setUiModel(postPlace)
                postPlaceView.setSelectCallback(::selectCallback)
                viewBinding.layout.addView(postPlaceView)
            }
        }
    }

    private fun selectCallback(isChecked: Boolean, staticInfo: PostPlaceStaticInfo) {
        _stateFlow.value = isChecked to staticInfo.placeType
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissCallback?.invoke()
    }

    fun setDismissCallback(callback: () -> Unit) {
        dismissCallback = callback
    }

    fun getDataFlow(): StateFlow<Pair<Boolean, PostPlaceType>?> {
        return _stateFlow
    }

    companion object {
        const val TAG = "select_social_media_bsheet"
    }
}