package com.a1danz.feature_vk_api.data.remote

import com.a1danz.feature_vk_api.data.remote.response.CreatingPostResponse
import com.a1danz.feature_vk_api.data.remote.response.SavePhotoInServerResponse
import com.a1danz.feature_vk_api.data.remote.response.SavePhotoInWallResponse
import com.a1danz.feature_vk_api.data.remote.response.WallUploadServerResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

interface VkApi {
    @GET("photos.getWallUploadServer")
    suspend fun getWallUploadServer(): WallUploadServerResponse

    @GET("photos.getWallUploadServer")
    suspend fun getWallUploadServer(@Query("group_id") groupId: Long): WallUploadServerResponse

    @Multipart
    @POST
    suspend fun savePhotoInServer(
        @Url url: String,
        @Part photo: MultipartBody.Part
    ): SavePhotoInServerResponse

    @POST("photos.saveWallPhoto")
    @FormUrlEncoded
    suspend fun savePhotoInWall(
        @Field("server") server: String,
        @Field("photo") photo: String,
        @Field("hash") hash: String,
        @Field("user_id") userId: Long? = null,
        @Field("group_id") groupId: Long? = null
    ): SavePhotoInWallResponse

    @POST("wall.post")
    @FormUrlEncoded
    suspend fun createPost(
        @Field("owner_id") ownerId: Long,
        @Field("message") message: String,
        @Field("attachments") attachments: String,
        @Field("from_group") fromGroup: Int? = null
    ): CreatingPostResponse
}