package com.a1danz.common.domain.model

import com.google.gson.annotations.SerializedName

data class Config(
    @SerializedName("vk_config")
    val vkConfig: VkConfig?,
    @SerializedName("inst_config")
    val instConfig: InstConfig?
)

data class InstConfig(
    @SerializedName("token")
    val token: String
)

data class VkConfig (
    @SerializedName("user_groups")
    val userGroups: List<Long>?,
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("access_token")
    val accessToken: String
)
