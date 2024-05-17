package com.a1danz.vk_page_publisher.data.remote.pojo.response

import com.google.gson.annotations.SerializedName

class CreatingPostResponse(
    @SerializedName("response")
    val postData: PostData
)

class PostData(
    @SerializedName("post_id")
    val postId: Long
)