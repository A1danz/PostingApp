package com.a1danz.feature_posts_feed.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.di.presentation.DaggerViewModelFactory
import com.a1danz.common.di.presentation.ViewModelFactoryBinder
import com.a1danz.common.di.presentation.ViewModelKey
import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.feature_posts_feed.presentation.screens.feed.PostsFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelFactoryBinder::class
    ]
)
class PresentationModule {

    @Provides
    @FeatureScope
    @[IntoMap ViewModelKey(PostsFeedViewModel::class)]
    fun providePostsFeedViewModel(viewModel: PostsFeedViewModel): ViewModel = viewModel
}