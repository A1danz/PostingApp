package com.a1danz.feature_create_post.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.a1danz.common.resources.ResourceManager
import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.common.domain.model.User
import com.a1danz.core_data.database.dao.PostDao
import com.a1danz.feature_create_post.data.di.DataModule
import com.a1danz.feature_create_post.domain.di.DomainModule
import com.a1danz.feature_create_post.presentation.CreatePostFragment
import com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.PostPublishingBottomSheetFragment
import com.a1danz.feature_create_post.presentation.bottom_sheet.select_places.SelectedSocialMediaBottomSheetFragment
import com.a1danz.feature_create_post.presentation.di.PresentationModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        PresentationModule::class,
        DomainModule::class,
        DataModule::class
    ]
)
@FeatureScope
interface CreatePostComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun resManager(resourceManager: ResourceManager): Builder

        @BindsInstance
        fun user(user: User): Builder

        @BindsInstance
        fun dataStore(dataStore: DataStore<Preferences>): Builder

        @BindsInstance
        fun postDao(postDao: PostDao): Builder

        fun build(): CreatePostComponent
    }

    fun inject(fragment: CreatePostFragment)
    fun inject(fragment: SelectedSocialMediaBottomSheetFragment)
    fun inject(fragment: PostPublishingBottomSheetFragment)
}