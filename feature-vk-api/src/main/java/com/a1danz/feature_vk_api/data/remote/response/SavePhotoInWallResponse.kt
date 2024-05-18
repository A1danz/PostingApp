package com.a1danz.feature_vk_api.data.remote.response

import com.google.gson.annotations.SerializedName

class SavePhotoInWallResponse (
    @SerializedName("response")
    val wallPhotosList: List<WallPhoto>
)

data class WallPhoto (
    @SerializedName("album_id")
    val albumId: Long?,
    @SerializedName("date")
    val date: Long?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("owner_id")
    val ownerId: Long?
)