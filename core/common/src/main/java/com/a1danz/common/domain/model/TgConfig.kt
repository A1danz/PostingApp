package com.a1danz.common.domain.model

import com.google.gson.annotations.SerializedName

data class TgConfig (
    @SerializedName("tg_user_info")
    val tgUserInfo: TgUserInfo,
    @SerializedName("selected_chats")
    val selectedChats: List<TgChatInfo> = listOf()
)

data class TgUserInfo(
    @SerializedName("tg_user_id")
    val tgUserId: Long,
    @SerializedName("username")
    val tgUsername: String,
    @SerializedName("user_photo")
    var tgUserPhoto: String
)

data class TgChatInfo(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("photo")
    var photo: String?
)