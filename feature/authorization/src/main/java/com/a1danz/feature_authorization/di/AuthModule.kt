package com.a1danz.feature_authorization.di

import com.a1danz.feature_authorization.di.presentation.PresentationModule
import dagger.Module

@Module(includes = [PresentationModule::class])
class AuthModule {
}