package com.a1danz.feature_settings.data.di

import com.a1danz.common.core.cryptography.Cryptographer
import com.a1danz.feature_settings.data.repository.tg.TgRepositoryImpl
import com.a1danz.feature_settings.data.repository.vk.VkRepositoryImpl
import com.a1danz.feature_settings.domain.repository.TgRepository
import com.a1danz.feature_settings.domain.repository.VkRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module(
    includes = [
        VkNetworkModule::class,
        RepositoryBinderModule::class,
    ]
)
class DataModule {

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideCryptographer(): Cryptographer = Cryptographer()
}

@Module
interface RepositoryBinderModule {
    @Binds
    fun bindTgRepository_to_Impl(impl: TgRepositoryImpl): TgRepository

    @Binds
    fun bindVkRepository_to_Impl(repositoryImpl: VkRepositoryImpl): VkRepository
}