package com.a1danz.feature_create_post.presentation

import android.app.AlertDialog
import android.util.Log
import android.view.View
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
import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.SelectedSocialMediaBottomSheetFragment
import com.a1danz.feature_create_post.presentation.model.ImageModel
import com.a1danz.feature_create_post.presentation.rv.ImagesAdapter
import com.a1danz.feature_create_post.presentation.model.PostInfoUiModel
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
        initPublishPostLogic()
    }

    private fun initSocialMediaSelect() {
        with(viewBinding) {
            viewModel.selectedPlaces.forEach {
                addPlaceToLayout(it)
                checkPlacesAreEmpty()
            }


            layoutEditSocialMedia.setOnClickListener {
                val fragment = SelectedSocialMediaBottomSheetFragment(viewModel.selectedPlaces)
                val stateFlow = fragment.getDataFlow()
                lifecycleScope.launch {
                    stateFlow.collect {
                        it?.let { edit ->
                            processPlaceTypeEdit(edit.first, edit.second)
                        }
                    }
                }

                fragment.show(childFragmentManager, SelectedSocialMediaBottomSheetFragment.TAG)
            }
        }
    }

    private fun processPlaceTypeEdit(isAdded: Boolean, placeType: PostPlaceType) {
        if (isAdded) {
            addPlaceToLayout(placeType)
            viewModel.addPlace(placeType)
        }
        else {
            removePlaceFromLayout(placeType)
            viewModel.removePlace(placeType)
        }
        checkPlacesAreEmpty()
    }

    private fun addPlaceToLayout(postPlace: PostPlaceType) {
        with(viewBinding) {
            val view = SocialMediaTagView(requireContext(), null).apply {
                setStaticInfo(viewModel.getPostPlaceStaticInfo(postPlace))
            }

            layoutPlaces.addView(view)
        }
    }

    private fun removePlaceFromLayout(placeType: PostPlaceType) {
        with(viewBinding) {
            layoutPlaces.removeView(layoutPlaces.findViewWithTag<SocialMediaTagView>(placeType))
        }
    }

    private fun checkPlacesAreEmpty() {
        with(viewBinding) {
            Log.d("CHILD COUNT", "COUNT - ${layoutPlaces.childCount}")
            layoutPlaces.childCount.let { itemsCount ->
                if (itemsCount == 0) {
                    layoutPlaces.addView(SocialMediaTagView(requireContext(), null).apply {
                        setEmptyTag()
                    })
                } else if (itemsCount == 2) {
                    layoutPlaces.removeView(layoutPlaces.findViewWithTag(SocialMediaTagView.EMPTY_TAG))
                }
            }

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
                theme = R.style.AppImagePickerTheme
            }
            val launcher = registerImagePicker {
                selectedImagesCallback(it, baseConfig, adapter, false)
            }

            layoutEditMedia.setOnClickListener {
                launcher.launch(config = baseConfig)
            }

            btnClearMedia.setOnClickListener {
                selectedImagesCallback(emptyList(), baseConfig, adapter, true)
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

    private fun selectedImagesCallback(images: List<Image>, config: ImagePickerConfig,
                                       adapter: ImagesAdapter, byClearBtn: Boolean) {
        if (images.isNotEmpty() || byClearBtn) {
            with(viewBinding) {
                rvImages.visibility = if(images.isEmpty()) View.GONE else View.VISIBLE
                config.selectedImages = images
                adapter.submitList(images.map { ImageModel(it.uri)} )
                tvMediaCount.text = images.size.toString()
                viewModel.setImages(images)
            }

        }
    }

    private fun postIsValid(): Boolean {
        return with(viewBinding) {
            editText.text.isNotBlank() || rvImages.childCount != 0
        }
    }

    private fun initPublishPostLogic() {
        with(viewBinding) {
            btnPublish.setOnClickListener {
                val postInfoModel = PostInfoUiModel(
                    places = viewModel.selectedPlaces.map { viewModel.getPostPlaceStaticInfo(it).title },
                    text = editText.text.toString(),
                    mediaSize = tvMediaCount.text.toString().toInt()
                )
                if (postIsValid()) {

                    AlertDialog.Builder(requireContext())
                        .setTitle("Вы уверены, что хотите опубликовать пост?")
                        .setMessage(postInfoModel.createMessageAboutPost())
                        .setPositiveButton("Опубликовать") { dialog, which ->

                        }
                        .setNegativeButton("Отмена") { dialog, which ->

                        }
                        .show()
                }

            }
        }
    }
}