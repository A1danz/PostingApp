package com.a1danz.common.domain.model

data class User (
    val uId: String,
    var vkToken: VkAccessToken? = null,
    var instToken: String? = null,
    val name: String
)
