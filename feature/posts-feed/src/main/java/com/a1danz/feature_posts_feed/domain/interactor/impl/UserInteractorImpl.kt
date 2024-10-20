package com.a1danz.feature_posts_feed.domain.interactor.impl

import androidx.paging.PagingData
import androidx.paging.map
import com.a1danz.feature_posts_feed.domain.interactor.UserInteractor
import com.a1danz.feature_posts_feed.domain.mapper.PostModelUiMapper
import com.a1danz.feature_posts_feed.domain.repository.PostsRepository
import com.a1danz.feature_posts_feed.presentation.model.PostUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserInteractorImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val postsRepository: PostsRepository,
    private val postModelUiMapper: PostModelUiMapper
) : UserInteractor {

    override fun getPosts(): Flow<PagingData<PostUiModel>> {
        return postsRepository.getPostsPagingFlow().map { pagingData ->
            pagingData.map { postModelUiMapper.mapToUiModel(it) }
        }
    }

    override suspend fun removePost(post: PostUiModel) {
        withContext(dispatcher) {
            postsRepository.deletePostById(post.id)
        }
    }
}