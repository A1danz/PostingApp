package com.a1danz.feature_vk_api.data.remote.interceptor

import com.a1danz.feature_vk_api.data.remote.config.Config
import okhttp3.Interceptor
import okhttp3.Response

class VkApiInterceptor(
    private val accessToken: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val editedUrl = chain.request().url().newBuilder()
            .addQueryParameter("v", Config.API_VERSION)
            .addQueryParameter("access_token", accessToken)
            .build()

        val editedRequest = chain.request().newBuilder()
            .url(editedUrl)
            .build()

        return chain.proceed(editedRequest)
    }
}