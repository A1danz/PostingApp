package com.a1danz.feature_settings.data.remote.vk.pojo

import com.google.gson.annotations.SerializedName

class UserGroupsResponse(
    @SerializedName("response")
    var response: ResponseData? = null
)

class ResponseData(
    @SerializedName("count")
    var count: Int? = null,
    @SerializedName("items")
    var groups: List<GroupData>? = null
)

class GroupData(
    @SerializedName("id")
    var groupId: Long? = null,
    @SerializedName("name")
    var groupName: String? = null,
    @SerializedName("photo_100")
    var groupImgUrl: String? = null
)