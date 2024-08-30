package com.a1danz.common.domain.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("u_id")
    val uId: String,
    @SerializedName("config")
    var config: Config = Config(),
    @SerializedName("name")
    val name: String
)
