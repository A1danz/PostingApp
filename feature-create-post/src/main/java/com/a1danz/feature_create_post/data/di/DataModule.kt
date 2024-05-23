package com.a1danz.feature_create_post.data.di

import androidx.datastore.core.DataStore
import com.a1danz.feature_create_post.data.repository.DataStoreRepositoryImpl
import com.a1danz.feature_create_post.domain.repository.DataStoreRepository
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        DataStoreRepositoryBinderModule::class
    ]
)
class DataModule {

    @Provides
    fun provideGson(): Gson = Gson()

}


@Module
interface DataStoreRepositoryBinderModule {

    @Binds
    fun bindDataStore_to_Impl(dataStoreRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository
}