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
    val userGroups: ArrayList<VkGroupInfo> = arrayListOf(),
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("user_info")
    val userInfo: VkUserInfo
)

data class VkUserInfo (
    @SerializedName("user_img")
    val userImg: String?,
    @SerializedName("full_name")
    val fullName: String
)

data class VkGroupInfo(
    @SerializedName("group_id")
    val groupId: Long,
    @SerializedName("group_name")
    var groupName: String
)
