package com.a1danz.feature_posts_feed.di

import com.a1danz.common.resources.ResourceManager
import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.core_data.database.dao.PostDao
import com.a1danz.feature_posts_feed.data.di.DataModule
import com.a1danz.feature_posts_feed.domain.di.DomainModule
import com.a1danz.feature_posts_feed.presentation.di.PresentationModule
import com.a1danz.feature_posts_feed.presentation.screens.feed.PostsFeedFragment
import dagger.BindsInstance
import dagger.Component


@FeatureScope
@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class
    ]
)
interface PostsFeedComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun postsDao(postsDao: PostDao): Builder

        @BindsInstance
        fun resManager(resourceManager: ResourceManager): Builder

        fun build(): PostsFeedComponent
    }

    fun inject(fragment: PostsFeedFragment)
}