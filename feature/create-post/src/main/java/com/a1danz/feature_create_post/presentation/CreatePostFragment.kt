package com.a1danz.feature_create_post.presentation

import android.app.AlertDialog
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.ext.observe
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.data.config.CreatingPostConfig
import com.a1danz.feature_create_post.databinding.FragmentCreatePostBinding
import com.a1danz.feature_create_post.di.CreatePostComponent
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.PostPublishingBottomSheetFragment
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.PostPublishingViewModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.SelectedSocialMediaBottomSheetFragment
import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.SelectedSocialMediaViewModel
import com.a1danz.feature_create_post.presentation.model.PostUiModel
import com.a1danz.feature_create_post.presentation.rv.images.ImagesAdapter
import com.a1danz.feature_create_post.presentation.rv.images.decoration.ImagesItemDecoration
import com.a1danz.feature_create_post.presentation.ui.PostPublishingDialogView
import com.a1danz.feature_create_post.presentation.ui.SocialMediaTagView
import com.a1danz.feature_places_info.presentation.model.PostPlaceUiInfo
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import javax.inject.Inject


class CreatePostFragment : BaseFragment(R.layout.fragment_create_post) {

    private val viewBinding: FragmentCreatePostBinding by viewBinding(FragmentCreatePostBinding::bind)

    private val viewModel: CreatePostViewModel by viewModels { vmFactory }

    private val postPublishingViewModel by lazy {
        ViewModelProvider(requireActivity(), vmFactory)[PostPublishingViewModel::class.java]
    }

    private val selectedPlacesViewModel: SelectedSocialMediaViewModel by viewModels { vmFactory }

    @Inject lateinit var resManager: ResourceManager

    private val imagesAdapter: ImagesAdapter by lazy {
        ImagesAdapter()
    }

    override fun inject() {
        (requireActivity().application as? FeatureContainer)?.getFeature(CreatePostComponent::class.java)
            ?.inject(this)
    }

    override fun subscribe() {
        postPublishingViewModel.publishingInProcessFlow.observe {
            it?.let { inProcess ->
                viewBinding.btnPublish.setText(
                    if (inProcess) R.string.publication_in_process
                    else R.string.publish
                )
            }
        }
    }

    override fun initViews() {
        initMediaInteraction()
        initSocialMediaSelect()
        initPublishPostLogic()
    }

    private fun initMediaInteraction() {
        with(viewBinding) {
            initImagesRecyclerView()

            tvMediaCount.text = viewModel.selectedImages.size.toString()

            val imagePickerConfig = ImagePickerConfig {
                limit = CreatingPostConfig.IMAGES_LIMIT_COUNT
                selectedImages = viewModel.selectedImages
                theme = R.style.AppImagePickerTheme
            }
            val launcher = registerImagePicker {
                selectedImagesCallback(it, imagePickerConfig)
            }

            layoutEditMedia.setOnClickListener {
                launcher.launch(config = imagePickerConfig)
            }

            btnClearMedia.setOnClickListener {
                selectedImagesCallback(emptyList(), imagePickerConfig, true)
            }

            btnClearText.setOnClickListener {
                viewBinding.editText.text.clear()
            }
        }
    }

    private fun initImagesRecyclerView() {
        with(viewBinding) {
            rvImages.addItemDecoration(ImagesItemDecoration(
                resManager.getDimen(R.dimen.spacing_between_images).toInt()
            ))

            rvImages.adapter = imagesAdapter

            imagesAdapter.submitList(viewModel.getImagesUiModels())
        }
    }

    private fun selectedImagesCallback(
        images: List<Image>,
        imagePickerConfig: ImagePickerConfig,
        byClearBtn: Boolean = false
    ) {
        if (images.isNotEmpty() || byClearBtn) {
            with(viewBinding) {
                imagePickerConfig.selectedImages = images
                viewModel.setImages(images)
                imagesAdapter.submitList(viewModel.getImagesUiModels())
                tvMediaCount.text = images.size.toString()
            }
        }
    }

    private fun initSocialMediaSelect() {
        with(viewBinding) {
            viewModel.selectedPlaces.forEach { postPlace ->
                addPlaceToLayout(postPlace)
            }

            layoutEditSocialMedia.setOnClickListener {
                val fragment = SelectedSocialMediaBottomSheetFragment.getInstance(
                    viewModel.selectedPlaces
                )
                selectedPlacesViewModel.placesEditingState.observe(fragment) { edit ->
                    processPlaceTypeEdit(edit.first, edit.second)
                }

                fragment.show(childFragmentManager, SelectedSocialMediaBottomSheetFragment.TAG)
            }
        }
    }

    private fun processPlaceTypeEdit(isAdded: Boolean, place: PostPlaceUiInfo) {
        if (isAdded) {
            addPlaceToLayout(place)
        } else {
            removePlaceFromLayout(place)
        }

        viewModel.editPlace(isAdded, place)

    }

    private fun addPlaceToLayout(postPlace: PostPlaceUiInfo) {
        addPostPlaceView(
            SocialMediaTagView(requireContext(), null).apply {
                setPlaceUiInfo(postPlace)
            }
        )
    }

    private fun addPostPlaceView(view: View) {
        with(viewBinding) {
            val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            fadeIn.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    checkPlacesAreEmpty()
                }

                override fun onAnimationEnd(animation: Animation?) {}

                override fun onAnimationRepeat(animation: Animation?) {}
            })
            layoutPlaces.addView(view)
            view.startAnimation(fadeIn)
        }
    }

    private fun removePlaceFromLayout(place: PostPlaceUiInfo) {
        with(viewBinding) {
            removePostPlaceView(
                layoutPlaces.findViewWithTag<SocialMediaTagView>(place.javaClass)
            )
        }
    }

    private fun removePostPlaceView(view: View) {
        with(viewBinding) {
            val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)

            fadeOut.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    layoutPlaces.removeView(view)
                    checkPlacesAreEmpty()
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })

            view.startAnimation(fadeOut)
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

    private fun initPublishPostLogic() {
        with(viewBinding) {
            btnPublish.setOnClickListener {
                // check publishing already in process state
                if (postPublishingViewModel.publishingAlreadyInProcess()) {
                    PostPublishingBottomSheetFragment.getInProcessInstance().apply {
                        show(childFragmentManager, PostPublishingBottomSheetFragment.TAG)
                    }
                    return@setOnClickListener
                }

                val postUiModel = PostUiModel(
                    destinations = viewModel.getPostDestinationUiModels(),
                    text = editText.text.toString(),
                    images = viewModel.getImagesUiModels(),
                )

                if (!viewModel.postIsValid(postUiModel)) {
                    showInvalidDataDialog()
                } else {
                    showPostPreviewDialog(postUiModel)
                }
            }
        }
    }

    private fun showPostPreviewDialog(postUiModel: PostUiModel) {
        AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .create()
            .also { dialog ->
                dialog.setView(
                    PostPublishingDialogView(requireContext(), null).apply {
                        setupData(
                            postInfoUiModel = postUiModel,
                            corrections = viewModel.getCorrectionsForPost(postUiModel),
                            cancelCallback = {
                                dialog.dismiss()
                            },
                            continueCallback = {
                                continuePublishingCallback(dialog, postUiModel)
                            },
                        )
                    })
            }
            .show()
    }

    private fun continuePublishingCallback(alertDialog: AlertDialog, postUiModel: PostUiModel) {
        PostPublishingBottomSheetFragment.getInstance(postUiModel).also { bottomSheet ->
            bottomSheet.show(
                childFragmentManager,
                PostPublishingBottomSheetFragment.TAG
            )
            alertDialog.dismiss()
        }
    }

    private fun showInvalidDataDialog() {
        showAlertDialog(
            AlertDialogData(
                title = getString(R.string.invalid_data),
                message = getString(R.string.invalid_data_description)
            )
        )
    }

    override fun onResume() {
        super.onResume()

        with(viewBinding) {
            val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            rvImages.startAnimation(fadeIn)
            rvImages.visibility = if (viewModel.selectedImages.isEmpty()) View.GONE else View.VISIBLE
        }
    }
}