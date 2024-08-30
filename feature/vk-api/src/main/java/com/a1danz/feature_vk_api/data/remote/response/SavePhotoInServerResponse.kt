package com.a1danz.feature_vk_api.data.remote.response

import com.google.gson.annotations.SerializedName

class SavePhotoInServerResponse (
    @SerializedName("server")
    val server: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("hash")
    val hash: String?
)