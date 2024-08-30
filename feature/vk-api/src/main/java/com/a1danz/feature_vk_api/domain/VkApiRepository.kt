package com.a1danz.feature_vk_api.domain

import com.a1danz.feature_vk_api.domain.model.CreatingPostResultDomainModel
import com.a1danz.feature_vk_api.domain.model.SavePhotoResultDomainModel
import com.a1danz.feature_vk_api.domain.model.WallUploadServerDomainModel
import java.io.File

interface VkApiRepository {
    suspend fun getWallUploadServer(ownerId: Long, isGroup: Boolean): WallUploadServerDomainModel
    suspend fun createPost(ownerId: Long, message: String = "", photos: List<File> = listOf(), isGroup: Boolean = false): CreatingPostResultDomainModel
    suspend fun savePhoto(url: String, photo: File, ownerId: Long, isGroup: Boolean): SavePhotoResultDomainModel
    suspend fun savePhotos(url: String, photos: List<File>, ownerId: Long, isGroup: Boolean): List<SavePhotoResultDomainModel>
}