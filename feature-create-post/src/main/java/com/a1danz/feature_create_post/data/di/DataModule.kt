package com.a1danz.feature_create_post.data.di

import androidx.datastore.core.DataStore
import com.a1danz.feature_create_post.data.repository.DataStoreRepositoryImpl
import com.a1danz.feature_create_post.domain.repository.DataStoreRepository
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        DataStoreRepositoryBinderModule::class
    ]
)
class DataModule {

}


@Module
interface DataStoreRepositoryBinderModule {

    @Binds
    fun bindDataStore_to_Impl(dataStoreRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository
}