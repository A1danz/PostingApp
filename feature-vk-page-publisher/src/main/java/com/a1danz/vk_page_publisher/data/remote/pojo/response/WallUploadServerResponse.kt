package com.a1danz.vk_page_publisher.data.remote.pojo.response

import com.google.gson.annotations.SerializedName

data class WallUploadServerResponse(
    @SerializedName("response")
    val uploadServerDataModel: UploadServerDataModel
)

class UploadServerDataModel(
    @SerializedName("album_id")
    val albumId: Long,
    @SerializedName("upload_url")
    val uploadUrl: String,
    @SerializedName("user_id")
    val userId: Long
)