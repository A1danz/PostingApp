package com.a1danz.feature_authorization.di

import com.a1danz.feature_authorization.data.di.DataModule
import com.a1danz.feature_authorization.presentation.di.PresentationModule
import com.a1danz.feature_authorization.domain.di.DomainModule
import dagger.Module

@Module(
    includes = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
    ]
)
class AuthModule