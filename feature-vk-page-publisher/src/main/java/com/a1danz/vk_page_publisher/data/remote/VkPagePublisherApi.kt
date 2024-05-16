package com.a1danz.vk_page_publisher.data.remote

import com.a1danz.vk_page_publisher.data.remote.pojo.response.WallUploadServerResponse
import retrofit2.http.GET

interface VkPagePublisherApi {
    @GET("photos.getWallUploadServer")
    fun getWallUploadServer(): WallUploadServerResponse


}