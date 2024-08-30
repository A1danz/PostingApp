package com.a1danz.feature_posts_feed.data.di

import com.a1danz.feature_posts_feed.data.repository.PostsRepositoryImpl
import com.a1danz.feature_posts_feed.domain.repository.PostsRepository
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        PostsRepositoryBinderModule::class
    ]
)
class DataModule


@Module
interface PostsRepositoryBinderModule {

    @Binds
    fun bindRepository_to_Impl(postsRepositoryImpl: PostsRepositoryImpl): PostsRepository
}