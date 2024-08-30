package com.a1danz.feature_create_post.domain.di

import com.a1danz.feature_create_post.domain.interactor.UserSelectedMediaInteractor
import com.a1danz.feature_create_post.domain.interactor.impl.UserSelectedMediaInteractorImpl
import dagger.Binds
import dagger.Module

@Module(includes = [
    SelectedMediaInteractorBinder::class
])
class SelectedMediaModule

@Module
interface SelectedMediaInteractorBinder {
    @Binds
    fun bindSelectedMediaInteractor_to_Impl(interactor: UserSelectedMediaInteractorImpl): UserSelectedMediaInteractor
}