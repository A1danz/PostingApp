package com.a1danz.feature_create_post.domain.di

import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.User
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.domain.interactor.UserSelectedMediaInteractor
import com.a1danz.feature_create_post.domain.interactor.impl.UserSelectedMediaInteractorImpl
import com.a1danz.feature_create_post.domain.model.PostPlaceStaticInfo
import com.a1danz.feature_create_post.domain.model.PostPlaceType
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [
    SelectedMediaInteractorBinder::class
])
class SelectedMediaModule

@Module
interface SelectedMediaInteractorBinder {
    @Binds
    fun bindSelectedMediaInteractor_to_Impl(interactor: UserSelectedMediaInteractorImpl): UserSelectedMediaInteractor
}