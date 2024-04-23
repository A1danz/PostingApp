package com.a1danz.feature_settings.data.remote.vk

import com.a1danz.feature_settings.data.remote.vk.pojo.UserGroupsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface VkApi {
    @GET("groups.get?filter=editor&extended=1")
    suspend fun getUserEditGroups() : UserGroupsResponse

}