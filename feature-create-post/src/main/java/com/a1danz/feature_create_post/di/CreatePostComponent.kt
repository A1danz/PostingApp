package com.a1danz.feature_create_post.di

import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.feature_create_post.di.presentation.PresentationModule
import com.a1danz.feature_create_post.presentation.CreatePostFragment
import dagger.BindsInstance
import dagger.Component
import com.a1danz.common.domain.model.User
import com.a1danz.feature_create_post.domain.di.DomainModule
import com.a1danz.feature_create_post.presentation.bottom_sheet.SelectedSocialMediaBottomSheetFragment

@Component(
    modules = [
        PresentationModule::class,
        DomainModule::class
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

        fun build(): CreatePostComponent
    }

    fun inject(fragment: CreatePostFragment)
    fun inject(fragment: SelectedSocialMediaBottomSheetFragment)
}