package com.a1danz.feature_posts_feed.domain.interactor.impl

import com.a1danz.feature_posts_feed.domain.interactor.UserInteractor
import com.a1danz.feature_posts_feed.domain.mapper.PostModelUiMapper
import com.a1danz.feature_posts_feed.domain.repository.PostsRepository
import com.a1danz.feature_posts_feed.presentation.model.PostUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserInteractorImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val postsRepository: PostsRepository,
    private val postModelUiMapper: PostModelUiMapper
) : UserInteractor {

    override suspend fun getPosts(): List<PostUiModel> {
        return withContext(dispatcher) {
            postsRepository.getPosts().sortedByDescending { it.date }.map {
                postModelUiMapper.mapToUiModel(it)
            }
        }
    }

    override suspend fun removePost(post: PostUiModel) {
        withContext(dispatcher) {
            postsRepository.deletePostById(post.id)
        }
    }
}