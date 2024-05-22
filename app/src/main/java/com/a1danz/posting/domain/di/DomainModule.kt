package com.a1danz.posting.domain.di

import com.a1danz.posting.domain.interactor.MainActivityUserInteractor
import com.a1danz.posting.domain.interactor.impl.MainActivityUserInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module(includes = [
    InteractorBinderModule::class
])
class DomainModule {

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}

@Module
interface InteractorBinderModule {

    @Binds
    fun bindMainActivityInteractor_toImpl(mainActivityUserInteractorImpl: MainActivityUserInteractorImpl): MainActivityUserInteractor
}