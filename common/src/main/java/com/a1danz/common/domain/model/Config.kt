package com.a1danz.common.domain.model

import com.google.gson.annotations.SerializedName

data class Config(
    @SerializedName("vk_config")
    var vkConfig: VkConfig? = null,
    @SerializedName("tg_config")
    var tgConfig: TgConfig? = null
)