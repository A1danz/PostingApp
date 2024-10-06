package com.a1danz.feature_settings.data.di

import com.a1danz.feature_settings.data.remote.vk.api.VkApi
import com.a1danz.feature_settings.data.remote.vk.config.VkRemoteConfig
import com.a1danz.feature_settings.data.remote.vk.interceptor.VkInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
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
            .baseUrl(VkRemoteConfig.BASE_URL)
            .build()
    }

    @Provides
    fun provideVkApi(retrofit: Retrofit) : VkApi = retrofit.create(VkApi::class.java)
}