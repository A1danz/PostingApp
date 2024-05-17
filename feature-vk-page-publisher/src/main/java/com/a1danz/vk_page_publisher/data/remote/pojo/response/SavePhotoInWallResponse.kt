package com.a1danz.vk_page_publisher.data.remote.pojo.response

import com.google.gson.annotations.SerializedName

class SavePhotoInWallResponse (
    @SerializedName("response")
    val wallPhotosList: List<WallPhoto>
)

data class WallPhoto (
    @SerializedName("album_id")
    val albumId: Long,
    @SerializedName("date")
    val date: Long,
    @SerializedName("id")
    val id: Long
)