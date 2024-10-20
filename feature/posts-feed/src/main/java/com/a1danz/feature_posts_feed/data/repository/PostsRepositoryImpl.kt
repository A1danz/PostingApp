package com.a1danz.feature_posts_feed.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.a1danz.core_data.database.dao.PostDao
import com.a1danz.core_data.database.entites.PostEntity
import com.a1danz.feature_posts_feed.data.config.PostsPagingConfig
import com.a1danz.feature_posts_feed.data.mapper.PostEntityMapper
import com.a1danz.feature_posts_feed.domain.model.PostDomainModel
import com.a1danz.feature_posts_feed.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val dao: PostDao,
    private val postEntityMapper: PostEntityMapper
) : PostsRepository {
    override fun getPostsPagingFlow(): Flow<PagingData<PostDomainModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PostsPagingConfig.PAGE_SIZE,
                initialLoadSize = PostsPagingConfig.INITIAL_LOAD_SIZE,
            ),
            pagingSourceFactory = { dao.getPostsPagingSource() },
        ).flow.map {
            it.map { postEntityMapper.postToDomainModel(it) }
        }
    }

    override suspend fun deletePostById(id: Int) {
        dao.removeById(id)
    }
}