package com.a1danz.feature_initialize.di

import com.a1danz.feature_initialize.di.presentation.PresentationModule
import com.a1danz.feature_initialize.domain.interactor.InitializerInteractor
import com.a1danz.feature_initialize.domain.interactor.impl.InitializerInteractorImpl
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        PresentationModule::class
    ]
)
interface InitializingModule {

    @Binds
    fun bindInteractor_to_Impl(interactorImpl: InitializerInteractorImpl): InitializerInteractor
}