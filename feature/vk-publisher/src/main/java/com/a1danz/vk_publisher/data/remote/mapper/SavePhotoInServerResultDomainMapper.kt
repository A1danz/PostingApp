package com.a1danz.vk_publisher.data.remote.mapper

import com.a1danz.feature_vk_api.data.remote.response.SavePhotoInServerResponse
import com.a1danz.feature_vk_api.domain.model.SavePhotoInServerResultDomainModel
import com.a1danz.vk_publisher.data.remote.exception.SavePhotoInServerException
import javax.inject.Inject

class SavePhotoInServerResultDomainMapper @Inject constructor() {
    fun mapToDomainModel(response: SavePhotoInServerResponse): SavePhotoInServerResultDomainModel {
        return SavePhotoInServerResultDomainModel(
            server = response.server ?: throw SavePhotoInServerException("server not found"),
            photo = response.photo ?: throw SavePhotoInServerException("photo not found"),
            hash = response.hash ?: throw SavePhotoInServerException("hash not found")
        )
    }
}