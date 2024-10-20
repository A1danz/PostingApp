package com.a1danz.feature_posts_feed.presentation.screens.feed

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_posts_feed.R
import com.a1danz.feature_posts_feed.databinding.FragmentPostsFeedBinding
import com.a1danz.feature_posts_feed.di.PostsFeedComponent
import com.a1danz.feature_posts_feed.presentation.model.PostUiModel
import com.a1danz.feature_posts_feed.presentation.rv.adapter.PostsAdapter
import com.a1danz.feature_posts_feed.presentation.rv.adapter.PostsLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostsFeedFragment : BaseFragment<PostsFeedViewModel>(R.layout.fragment_posts_feed) {

    private val viewBinding: FragmentPostsFeedBinding by viewBinding(FragmentPostsFeedBinding::bind)

    override val viewModel: PostsFeedViewModel by viewModels { vmFactory }

    private val pagingAdapter: PostsAdapter by lazy {
        PostsAdapter(onRemoveCallback = ::onRemoveBtnClicked)
    }

    private val concatAdapter: ConcatAdapter by lazy {
        pagingAdapter.withLoadStateFooter(
            footer = PostsLoadStateAdapter(pagingAdapter::retry)
        )
    }

    override fun inject() {
        (requireActivity().application as? FeatureContainer)?.getFeature(
            PostsFeedComponent::class.java
        )?.inject(this)
    }

    override fun subscribe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.postsPagingFlow.collectLatest(pagingAdapter::submitData)
            }
        }

        pagingAdapter.loadStateFlow.observe(::collectPagingLoadState)
    }

    override fun initViews() {
        with(viewBinding) {
            rvPosts.adapter = concatAdapter
        }
    }

    private fun onRemoveBtnClicked(postUiModel: PostUiModel) {
        viewModel.onPostRemovedClicked(postUiModel)
    }

    private fun collectPagingLoadState(loadStates: CombinedLoadStates) {
        with(viewBinding) {
            val refreshState = loadStates.refresh
            layoutProgressBar.isGone = refreshState !is LoadState.Loading

            if (refreshState is LoadState.NotLoading) {
                viewBinding.tvEmptyPosts.isGone = pagingAdapter.itemCount != 0
            }
        }
    }
}