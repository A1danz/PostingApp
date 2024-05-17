package com.a1danz.vk_page_publisher.data.remote

import com.a1danz.vk_page_publisher.data.remote.pojo.response.CreatingPostResponse
import com.a1danz.vk_page_publisher.data.remote.pojo.response.SavePhotoInServerResponse
import com.a1danz.vk_page_publisher.data.remote.pojo.response.SavePhotoInWallResponse
import com.a1danz.vk_page_publisher.data.remote.pojo.response.WallUploadServerResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url

interface VkPagePublisherApi {
    @GET("photos.getWallUploadServer")
    suspend fun getWallUploadServer(): WallUploadServerResponse

    @Multipart
    @POST
    suspend fun savePhotoInServer(
        @Url url: String,
        @Part photo: MultipartBody.Part
    ): SavePhotoInServerResponse

    @POST("photos.saveWallPhoto")
    @FormUrlEncoded
    suspend fun savePhotoInWall(
        @Field("user_id") userId: Long,
        @Field("server") server: String,
        @Field("photo") photo: String,
        @Field("hash") hash: String,
    ): SavePhotoInWallResponse

    @POST("wall.post")
    @FormUrlEncoded
    suspend fun createPost(
        @Field("owner_id") ownerId: Long,
        @Field("message") message: String,
        @Field("attachments") attachments: String
    ): CreatingPostResponse
}