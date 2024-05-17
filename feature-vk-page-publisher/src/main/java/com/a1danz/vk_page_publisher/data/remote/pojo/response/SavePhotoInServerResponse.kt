package com.a1danz.vk_page_publisher.data.remote.pojo.response

import com.google.gson.annotations.SerializedName

class SavePhotoInServerResponse (
    @SerializedName("server")
    val server: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("hash")
    val hash: String
)