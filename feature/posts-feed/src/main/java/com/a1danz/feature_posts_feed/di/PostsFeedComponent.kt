package com.a1danz.feature_posts_feed.di

import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceStaticInfo
import com.a1danz.feature_posts_feed.data.di.DataModule
import com.a1danz.feature_posts_feed.domain.di.DomainModule
import com.a1danz.feature_posts_feed.presentation.di.PresentationModule
import com.a1danz.feature_posts_feed.presentation.screens.feed.PostsFeedFragment
import com.a1danz.feature_posts_feed_database.data.local.dao.PostDao
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

        @BindsInstance
        fun placesStaticInfo(places: HashMap<PostPlaceType, PostPlaceStaticInfo>): Builder

        fun build(): PostsFeedComponent
    }

    fun inject(fragment: PostsFeedFragment)
}