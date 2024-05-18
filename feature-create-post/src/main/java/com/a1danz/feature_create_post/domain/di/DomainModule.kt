package com.a1danz.feature_create_post.domain.di

import dagger.Module

@Module(includes = [
    SelectedMediaModule::class,
    PostPlacesInfoModule::class,
    PostPublishingModule::class,
    UserConfigModule::class
])
class DomainModule