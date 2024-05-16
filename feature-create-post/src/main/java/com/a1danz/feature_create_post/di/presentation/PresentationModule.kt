package com.a1danz.feature_create_post.di.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.di.presentation.DaggerViewModelFactory
import com.a1danz.common.di.presentation.ViewModelKey
import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.feature_create_post.presentation.CreatePostViewModel
import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.SelectedSocialMediaViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryBinder::class])
class PresentationModule {

    @Provides
    @FeatureScope
    @[IntoMap ViewModelKey(CreatePostViewModel::class)]
    fun provideCreatePostViewModel(viewModel: CreatePostViewModel): ViewModel = viewModel

    @Provides
    @FeatureScope
    @[IntoMap ViewModelKey(SelectedSocialMediaViewModel::class)]
    fun provideSelectedSocialMediaViewModel(viewModel: SelectedSocialMediaViewModel): ViewModel = viewModel
}

@Module
interface ViewModelFactoryBinder {
    @Binds
    @FeatureScope
    fun bindDaggerVMFactory_to_ViewModelFactory(daggerFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}