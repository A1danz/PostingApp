package com.a1danz.feature_create_post.domain.di

import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.User
import dagger.Module
import dagger.Provides

@Module
class UserConfigModule {
    @Provides
    fun provideUserConfig(user: User): Config = user.config
}