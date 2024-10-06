package com.a1danz.feature_settings.data.remote.vk.api

import com.a1danz.feature_settings.data.remote.vk.config.VkRemoteConfig
import com.a1danz.feature_settings.data.remote.vk.pojo.UserGroupsResponse
import retrofit2.http.GET

interface VkApi {
    @GET(VkRemoteConfig.EDITOR_GROUPS_ENDPOINT)
    suspend fun getUserEditGroups(): UserGroupsResponse
}