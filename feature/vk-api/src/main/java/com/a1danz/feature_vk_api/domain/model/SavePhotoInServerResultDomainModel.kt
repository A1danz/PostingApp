package com.a1danz.feature_vk_api.domain.model

data class SavePhotoInServerResultDomainModel(
    val server: String,
    val photo: String,
    val hash: String
)