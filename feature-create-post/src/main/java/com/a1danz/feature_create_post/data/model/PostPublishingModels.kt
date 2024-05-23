package com.a1danz.feature_create_post.data.model

import com.google.gson.annotations.SerializedName

data class PostPublishingModels(
    @SerializedName("publishing_models")
    val publishingModels: MutableList<PostPublishingModel>
)

data class PostPublishingModel(
    @SerializedName("place")
    val place: String,
    @SerializedName("u_id")
    val uId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("img")
    val img: String
)