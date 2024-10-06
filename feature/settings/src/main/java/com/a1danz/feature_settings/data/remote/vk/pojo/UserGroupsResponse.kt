package com.a1danz.feature_settings.data.remote.vk.pojo

import com.google.gson.annotations.SerializedName

class UserGroupsResponse(
    @SerializedName("response")
    var response: ResponseData
)

class ResponseData(
    @SerializedName("count")
    var count: Int,
    @SerializedName("items")
    var groups: List<GroupData>,
)

class GroupData(
    @SerializedName("id")
    var groupId: Long,
    @SerializedName("name")
    var groupName: String,
    @SerializedName("photo_100")
    var groupImgUrl: String
)