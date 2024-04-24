package com.a1danz.feature_settings.data.remote.vk.interceptor

import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkAccessToken
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class VkInterceptor @Inject constructor(
    private val user: User
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val vkConfig = user.config.vkConfig ?: throw IllegalStateException("Token isn't initialized")
        val editedUrl = chain.request().url.newBuilder()
            .addQueryParameter(PARAM_ACCESS_TOKEN, vkConfig.accessToken)
            .addQueryParameter(PARAM_USER_ID, vkConfig.userId.toString())
            .addQueryParameter(PARAM_VERSION, PARAM_VERSION_VALUE)
            .build()

        val editedRequest = chain.request().newBuilder()
            .url(editedUrl)
            .build()

        return chain.proceed(editedRequest)
    }

    companion object {
        const val PARAM_ACCESS_TOKEN = "access_token"
        const val PARAM_USER_ID = "user_id"
        const val PARAM_VERSION = "v"
        const val PARAM_VERSION_VALUE = "5.131"

    }
}