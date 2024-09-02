package com.a1danz.feature_create_post.data.di

import com.a1danz.feature_create_post.data.repository.PostsLocalRepositoryImpl
import com.a1danz.feature_create_post.domain.repository.PostsLocalRepository
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        RepositoryBinderModule::class
    ]
)
class DataModule {

    @Provides
    fun provideGson(): Gson = Gson()

}


@Module
interface RepositoryBinderModule {

    @Binds
    fun bindPostLocalRepository_to_Impl(postsLocalRepositoryImpl: PostsLocalRepositoryImpl): PostsLocalRepository
}