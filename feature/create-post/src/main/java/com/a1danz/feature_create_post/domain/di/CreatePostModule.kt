package com.a1danz.feature_create_post.domain.di

import com.a1danz.feature_create_post.domain.interactor.CreatePostInteractor
import com.a1danz.feature_create_post.domain.interactor.impl.CreatePostInteractorImpl
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        CreatePostInteractorBinder::class,
    ]
)
class CreatePostModule

@Module
interface CreatePostInteractorBinder {

    @Binds
    fun bindCreatePostInteractor_to_Impl(createPostInteractorImpl: CreatePostInteractorImpl): CreatePostInteractor
}