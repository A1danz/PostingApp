package com.a1danz.vk_page_publisher.data.remote.di

import com.a1danz.vk_page_publisher.data.remote.VkPagePublisherApi
import com.a1danz.vk_page_publisher.data.remote.interceptor.VkApiInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DomainModule {

    @Provides
    fun provideVkApiHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(VkApiInterceptor(
                apiVersion = "5.199",
                accessToken = "vk1.a.pMUtl9kDxEbCL4BYvmnIy21XLKlSgbU1o8w4GZwIXae88Nia0crg2WnXGhg6yivyyKzAI6s2bFbikqrOMJKFDHvLWDFmjrU8rgF0kmfGDH4EAGGo0NxnpkyFPWOaqTHtnZZSruLALA0dPbD5JZSkw4sgkW-tEqPBFuCJbOUIaiUWqENO8ISN_uPBldqPe6pW6WjEsX12qGiL2AVapyVvSA"
            ))
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideVkPagePublisherApi(retrofit: Retrofit): VkPagePublisherApi {
        return retrofit.create(VkPagePublisherApi::class.java)
    }
}