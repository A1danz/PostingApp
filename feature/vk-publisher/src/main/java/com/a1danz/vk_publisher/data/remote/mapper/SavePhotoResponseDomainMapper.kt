package com.a1danz.vk_publisher.data.remote.mapper

import com.a1danz.feature_vk_api.data.remote.response.SavePhotoInWallResponse
import com.a1danz.feature_vk_api.domain.model.SavePhotoResultDomainModel
import com.a1danz.vk_publisher.data.remote.exception.SavedPhotoIdNotFoundException
import javax.inject.Inject

class SavePhotoResponseDomainMapper @Inject constructor() {
    fun mapToDomainModel(savePhotoInWallResponse: SavePhotoInWallResponse): SavePhotoResultDomainModel {
        return savePhotoInWallResponse.wallPhotosList[0].let {
            SavePhotoResultDomainModel(
                albumId = it.albumId ?: -1,
                date = it.date ?: -1,
                id = it.id ?: throw SavedPhotoIdNotFoundException("photo not found"),
                ownerId = it.ownerId ?: throw SavedPhotoIdNotFoundException("photo owner id not found")
            )
        }
    }
}