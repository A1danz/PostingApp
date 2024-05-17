package com.a1danz.vk_page_publisher.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class VkApiInterceptor(
    private val apiVersion: String,
    private val accessToken: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val editedUrl = chain.request().url().newBuilder()
            .addQueryParameter("v", apiVersion)
            .addQueryParameter("access_token", accessToken)
            .build()

        val editedRequest = chain.request().newBuilder()
            .url(editedUrl)
            .build()

        return chain.proceed(editedRequest)
    }
}