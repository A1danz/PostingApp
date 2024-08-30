package com.a1danz.feature_vk_api.data.remote.response

import com.google.gson.annotations.SerializedName

data class WallUploadServerResponse(
    @SerializedName("response")
    val uploadServerDataModel: UploadServerDataModel
)

class UploadServerDataModel(
    @SerializedName("upload_url")
    val uploadUrl: String?
)