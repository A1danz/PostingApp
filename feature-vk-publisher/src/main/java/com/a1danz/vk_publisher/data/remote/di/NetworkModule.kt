package com.a1danz.vk_publisher.data.remote.di

import com.a1danz.feature_vk_api.data.remote.VkApi
import com.a1danz.feature_vk_api.data.remote.config.Config
import com.a1danz.feature_vk_api.data.remote.interceptor.VkApiInterceptor
import com.a1danz.feature_vk_api.domain.VkApiRepository
import com.a1danz.vk_publisher.data.remote.repository.VkApiRepositoryImpl
import com.a1danz.vk_publisher.di.qualifiers.VkAccessToken
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [
    RepositoryBinderModule::class
])
class NetworkModule {

    @Provides
    fun provideVkApiHttpClient(@VkAccessToken vkAccessToken: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(VkApiInterceptor(vkAccessToken))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Настройте уровень логирования
            })
            .build()
    }

    @Provides
    fun provideRetrofit(vkApiOkHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(vkApiOkHttpClient)
            .build()
    }

    @Provides
    fun provideVkApi(retrofit: Retrofit): VkApi {
        return retrofit.create(VkApi::class.java)
    }
}

@Module
interface RepositoryBinderModule {
    @Binds
    fun bindRepository_to_Impl(vkApiRepositoryImpl: VkApiRepositoryImpl): VkApiRepository
}