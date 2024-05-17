package com.a1danz.feature_vk_api.data.remote.response

import com.google.gson.annotations.SerializedName

class CreatingPostResponse(
    @SerializedName("response")
    val postData: PostData
)

class PostData(
    @SerializedName("post_id")
    val postId: Long?
)