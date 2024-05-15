package com.a1danz.feature_create_post.presentation

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.FragmentCreatePostBinding
import com.a1danz.feature_create_post.di.CreatePostComponent
import com.a1danz.feature_create_post.domain.model.PostPlaceType
import com.a1danz.feature_create_post.presentation.bottom_sheet.SelectedSocialMediaBottomSheetFragment
import com.a1danz.feature_create_post.presentation.model.ImageModel
import com.a1danz.feature_create_post.presentation.rv.ImagesAdapter
import com.a1danz.feature_create_post.presentation.ui.SocialMediaTagView
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import kotlinx.coroutines.launch
import javax.inject.Inject


class CreatePostFragment : BaseFragment(R.layout.fragment_create_post) {
    private val viewBinding: FragmentCreatePostBinding by viewBinding(FragmentCreatePostBinding::bind)
    private val viewModel: CreatePostViewModel by viewModels { vmFactory }
    @Inject lateinit var resManager: ResourceManager

    override fun inject() {
        (requireActivity().application as? FeatureContainer)?.getFeature(CreatePostComponent::class.java)
            ?.inject(this)
    }

    override fun subscribe() {
        return
    }

    override fun initViews() {
        initMediaInteraction()
        initSocialMediaSelect()
    }

    private fun initSocialMediaSelect() {
        with(viewBinding) {
            viewModel.selectedPlaces.forEach {
                addPlaceToLayout(it)
            }


            layoutEditSocialMedia.setOnClickListener {
                val fragment = SelectedSocialMediaBottomSheetFragment(viewModel.selectedPlaces)
                val stateFlow = fragment.getDataFlow()
                fragment.setDismissCallback {
                    reloadSelectedPlaces()
                }
                lifecycleScope.launch {
                    stateFlow.collect {
                        it?.let { edit ->
                            if (edit.first) viewModel.addPlace(edit.second)
                            else viewModel.removePlace(edit.second)
                        }

                    }
                }

                fragment.show(childFragmentManager, SelectedSocialMediaBottomSheetFragment.TAG)
            }
        }
    }

    private fun reloadSelectedPlaces() {
        with(viewBinding) {
            layoutPlaces.removeAllViews()
            viewModel.selectedPlaces.forEach {
                addPlaceToLayout(it)
            }
        }
    }

    private fun addPlaceToLayout(postPlace: PostPlaceType) {
        with(viewBinding) {
            val view = SocialMediaTagView(requireContext(), null).apply {
                setSocialMedia(viewModel.getPostPlaceStaticInfo(postPlace))
            }

            layoutPlaces.addView(view)
        }

    }

    private fun initMediaInteraction() {
        with(viewBinding) {
            val adapter = ImagesAdapter()
            rvImages.adapter = adapter
            adapter.submitList(viewModel.getImages().map { ImageModel(it.uri) })
            initRvDecorations()

            tvMediaCount.text = viewModel.getImages().size.toString()
            val baseConfig = ImagePickerConfig {
                limit = 10
                selectedImages = viewModel.getImages()
            }
            val launcher = registerImagePicker {
                selectedImagesCallback(it, baseConfig, adapter)
            }

            layoutEditMedia.setOnClickListener {
                launcher.launch(config = baseConfig)
            }
        }
    }

    private fun initRvDecorations() {
        with(viewBinding) {
            val dividerItemDecoration = DividerItemDecoration(
                rvImages.context,
                LinearLayoutManager.HORIZONTAL
            )
            resManager.getDrawable(R.drawable.images_divider)
                ?.let { dividerItemDecoration.setDrawable(it) }
            rvImages.addItemDecoration(dividerItemDecoration)
        }
    }

    private fun selectedImagesCallback(images: List<Image>, config: ImagePickerConfig, adapter: ImagesAdapter) {
        if (images.isNotEmpty()) {
            with(viewBinding) {
                config.selectedImages = images
                adapter.submitList(images.map { ImageModel(it.uri)} )
                tvMediaCount.text = images.size.toString()
                viewModel.setImages(images)
            }

        }
    }
}