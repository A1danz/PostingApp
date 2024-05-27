package com.a1danz.feature_posts_feed.presentation.screens.feed

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_posts_feed.R
import com.a1danz.feature_posts_feed.databinding.FragmentPostsFeedBinding
import com.a1danz.feature_posts_feed.di.PostsFeedComponent
import com.a1danz.feature_posts_feed.presentation.model.PostUiModel
import com.a1danz.feature_posts_feed.presentation.screens.feed.rv.PostsAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class PostsFeedFragment : BaseFragment(R.layout.fragment_posts_feed) {

    private val viewBinding: FragmentPostsFeedBinding by viewBinding(FragmentPostsFeedBinding::bind)
    private val viewModel: PostsFeedViewModel by viewModels { vmFactory }


    override fun inject() {
        (requireActivity().application as? FeatureContainer)?.getFeature(
            PostsFeedComponent::class.java
        )?.inject(this)
    }

    override fun subscribe() {
        with(viewBinding) {
            lifecycleScope.launch {
                viewModel.postsStateFlow.collect {
                    it?.let { postsList ->
                        setLoadingState(false)
                        addPostsToRecyclerView(postsList)
                    }
                }
            }

            lifecycleScope.launch {
                viewModel.errorFlow.collect {
                    it?.let { error ->
                        setLoadingState(false)
                        Log.d("ERROR", error)
                    }
                }
            }
        }
    }

    override fun initViews() {
        setLoadingState(true)
        viewModel.getPosts()
    }

    private fun setLoadingState(isLoading: Boolean) {
        viewBinding.layoutProgressBar.isVisible = isLoading
    }

    private fun addPostsToRecyclerView(posts: List<PostUiModel>) {
        showEmptyPostsText(posts.isEmpty())

        val adapter = PostsAdapter(posts.toMutableList(), ::onRemoveCallback)
        viewBinding.rvPosts.adapter = adapter
    }

    private fun onRemoveCallback(adapter: PostsAdapter, postUiModel: PostUiModel, itemPosition: Int,) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.post_removing))
            .setMessage(getString(R.string.post_removing_description))
            .setPositiveButton(getString(R.string.remove_post)) { dialog, which ->
                viewModel.removePost(postUiModel)
                adapter.removeItem(itemPosition)
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, which -> }
            .show()
    }

    private fun showEmptyPostsText(state: Boolean) {
        if (state) viewBinding.tvEmptyPosts.visibility = View.VISIBLE
        else viewBinding.tvEmptyPosts.visibility = View.GONE
    }


}