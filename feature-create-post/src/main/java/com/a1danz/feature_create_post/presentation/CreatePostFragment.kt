package com.a1danz.feature_create_post.presentation

import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
import com.a1danz.feature_create_post.domain.model.PostDomainModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.PostPublishingBottomSheetFragment
import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.SelectedSocialMediaBottomSheetFragment
import com.a1danz.feature_create_post.presentation.model.ImageModel
import com.a1danz.feature_create_post.presentation.model.PostInfoUiModel
import com.a1danz.feature_create_post.presentation.rv.ImagesAdapter
import com.a1danz.feature_create_post.presentation.ui.PostPublishingDialogView
import com.a1danz.feature_create_post.presentation.ui.SocialMediaTagView
import com.a1danz.feature_places_info.domain.model.PostPlaceType
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
        lifecycleScope.launch {
            viewModel.initUpdatesInPublishingInProcessFlow(requireActivity())
            viewModel.publishingInProcessFlow.collect {
                it?.let { inProcess ->
                    if (inProcess) viewBinding.btnPublish.setText(R.string.publication_in_process)
                    else viewBinding.btnPublish.setText(R.string.publish)
                }
            }
        }
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
        } else {
            removePlaceFromLayout(placeType)
            viewModel.removePlace(placeType)
        }

    }

    private fun addPlaceToLayout(postPlace: PostPlaceType) {
        with(viewBinding) {
            val view = SocialMediaTagView(requireContext(), null).apply {
                setStaticInfo(viewModel.getPostPlaceStaticInfo(postPlace))
            }
            addPostPlaceView(view)
        }
    }

    private fun addPostPlaceView(view: View) {
        with(viewBinding) {
            val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            Log.d("ADDING VIEW", "ADDING POST PLACE VIEW")
            fadeIn.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    Log.d("STARTED", "ANIMATION STARTED")
                    checkPlacesAreEmpty()
                }

                override fun onAnimationEnd(animation: Animation?) {}

                override fun onAnimationRepeat(animation: Animation?) {}
            })
            layoutPlaces.addView(view)
            view.startAnimation(fadeIn)

        }
    }

    private fun removePlaceFromLayout(placeType: PostPlaceType) {
        with(viewBinding) {
            val view = layoutPlaces.findViewWithTag<SocialMediaTagView>(placeType)
            removePostPlaceView(view)
        }
    }

    private fun removePostPlaceView(view: View) {
        with(viewBinding) {
            val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            view.startAnimation(fadeOut)
            fadeOut.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    layoutPlaces.removeView(view)
                    checkPlacesAreEmpty()
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
    }

    private fun checkPlacesAreEmpty() {
        with(viewBinding) {
            layoutPlaces.childCount.let { itemsCount ->
                if (itemsCount == 0) {
                    addPostPlaceView(SocialMediaTagView(requireContext(), null).apply {
                        setEmptyTag()
                    })
                } else if (itemsCount == 2) {
                    layoutPlaces.findViewWithTag<View>(SocialMediaTagView.EMPTY_TAG)?.let {
                        removePostPlaceView(it)
                    }
                }
            }
        }
    }

    private fun initMediaInteraction() {
        Log.d("IMAGES", "${viewModel.getImages()}")
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

    private fun selectedImagesCallback(
        images: List<Image>, config: ImagePickerConfig,
        adapter: ImagesAdapter, byClearBtn: Boolean
    ) {
        if (images.isNotEmpty() || byClearBtn) {
            with(viewBinding) {
                config.selectedImages = images
                adapter.submitList(images.map { ImageModel(it.uri) })
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
                    text = editText.text.toString(),
                    mediaSize = tvMediaCount.text.toString().toInt()
                )

                if (postIsValid()) {
                    if (btnPublish.text.toString() == getString(R.string.publish)) {
                        val alertDialog = AlertDialog.Builder(requireContext())
                            .setCancelable(true)
                            .create()

                        alertDialog.setView(PostPublishingDialogView(requireContext(), null).apply {
                            setText(postInfoModel.text)
                            setMediaCount(postInfoModel.mediaSize)
                            setCancelCallback { alertDialog.dismiss() }
                            setContinueCallback {
                                lifecycleScope.launch {
                                    val bshFragment =
                                        PostPublishingBottomSheetFragment(PostDomainModel(
                                            postPlaces = viewModel.selectedPlaces.toList(),
                                            postText = editText.text.toString(),
                                            postImages = viewModel.getImages().map {
                                                viewModel.convertUriToFile(
                                                    it.uri,
                                                    requireContext()
                                                )
                                            }
                                        ))

                                    bshFragment.show(
                                        childFragmentManager,
                                        PostPublishingBottomSheetFragment.TAG
                                    )
                                    alertDialog.dismiss()
                                }
                            }
                            lifecycleScope.launch {
                                setPostPlacesDetailInfo(viewModel.getPostPlaceDetailInfoUiModels())
                            }
                            postInfoModel.getCorrections().forEach { addCorrection(it) }
                        })

                        alertDialog.show()
                    } else {
                        PostPublishingBottomSheetFragment(null).show(
                            childFragmentManager,
                            PostPublishingBottomSheetFragment.TAG
                        )
                    }
                } else {
                    showInvalidDataDialog()
                }

            }
        }
    }

    private fun showInvalidDataDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.invalid_data))
            .setMessage(getString(R.string.invalid_data_description))
            .show()

    }

    override fun onResume() {
        super.onResume()

        with(viewBinding) {
            val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            rvImages.startAnimation(fadeIn)
            rvImages.visibility = if (viewModel.getImages().isEmpty()) View.GONE else View.VISIBLE
        }
    }
}