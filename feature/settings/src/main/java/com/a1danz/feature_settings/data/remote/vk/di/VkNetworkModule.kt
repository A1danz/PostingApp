package com.a1danz.feature_settings.data.remote.vk.di

import com.a1danz.feature_settings.data.remote.vk.VkApi
import com.a1danz.feature_settings.data.remote.vk.interceptor.VkInterceptor
import com.a1danz.feature_settings.data.repository.vk.VkRepositoryImpl
import com.a1danz.feature_settings.domain.repository.VkRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [RepositoryBinder::class])
class VkNetworkModule {
    @Provides
    fun provideOkHttpClient(vkInterceptor: VkInterceptor) : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(vkInterceptor)
        .build()

    @Provides
    fun provideRetrofitInstance(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.vk.com/method/")
            .build()
    }

    @Provides
    fun provideVkApi(retrofit: Retrofit) : VkApi = retrofit.create(VkApi::class.java)
}

@Module
interface RepositoryBinder {
    @Binds
    fun bindRepository_to_Impl(repositoryImpl: VkRepositoryImpl): VkRepository
}