package com.a1danz.common.domain.model

import com.google.gson.annotations.SerializedName

data class Config(
    @SerializedName("vk_config")
    var vkConfig: VkConfig? = null,
    @SerializedName("inst_config")
    var instConfig: InstConfig? = null
)

data class InstConfig(
    @SerializedName("token")
    val token: String
)

data class VkConfig (
    @SerializedName("user_groups")
    val userGroups: ArrayList<Long> = arrayListOf(),
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("access_token")
    val accessToken: String
)
