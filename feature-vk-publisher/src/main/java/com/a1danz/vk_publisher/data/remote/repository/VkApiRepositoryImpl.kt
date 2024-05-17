package com.a1danz.vk_publisher.data.remote.repository

import com.a1danz.feature_vk_api.data.remote.VkApi
import com.a1danz.feature_vk_api.domain.VkApiRepository
import com.a1danz.feature_vk_api.domain.model.CreatingPostResultDomainModel
import com.a1danz.feature_vk_api.domain.model.SavePhotoResultDomainModel
import com.a1danz.feature_vk_api.domain.model.WallUploadServerDomainModel
import com.a1danz.vk_publisher.data.remote.mapper.CreatingPostResultDomainMapper
import com.a1danz.vk_publisher.data.remote.mapper.SavePhotoInServerResultDomainMapper
import com.a1danz.vk_publisher.data.remote.mapper.SavePhotoResponseDomainMapper
import com.a1danz.vk_publisher.data.remote.mapper.WallUploadServerResponseDomainMapper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject
import kotlin.math.absoluteValue

class VkApiRepositoryImpl @Inject constructor(
    private val vkApi: VkApi,
    private val wallUploadServerResponseDomainMapper: WallUploadServerResponseDomainMapper,
    private val savePhotoResultDomainMapper: SavePhotoResponseDomainMapper,
    private val creatingPostResultDomainMapper: CreatingPostResultDomainMapper,
    private val savePhotoInServerResultDomainMapper: SavePhotoInServerResultDomainMapper
) : VkApiRepository {
    override suspend fun getWallUploadServer(
        ownerId: Long,
        isGroup: Boolean
    ): WallUploadServerDomainModel {

        return wallUploadServerResponseDomainMapper.mapToDomainModel(
            if (isGroup) vkApi.getWallUploadServer(ownerId) else vkApi.getWallUploadServer()
        )
    }

    override suspend fun savePhoto(
        url: String,
        photo: File,
        ownerId: Long,
        isGroup: Boolean
    ): SavePhotoResultDomainModel {
        val requestFile: RequestBody = RequestBody.create(MultipartBody.FORM, photo)
        val body = MultipartBody.Part.createFormData("photo", photo.name, requestFile)
        val serverPhotoSaveResult =
            savePhotoInServerResultDomainMapper.mapToDomainModel(vkApi.savePhotoInServer(url, body))
        val wallPhotoSaveResponse = serverPhotoSaveResult.let {
            vkApi.savePhotoInWall(
                userId = if (!isGroup) ownerId else null,
                groupId = if (isGroup) ownerId else null,
                server = it.server,
                photo = it.photo,
                hash = it.hash
            )

        }

        return savePhotoResultDomainMapper.mapToDomainModel(wallPhotoSaveResponse)
    }

    override suspend fun savePhotos(
        url: String,
        photos: List<File>,
        ownerId: Long,
        isGroup: Boolean
    ): List<SavePhotoResultDomainModel> {
        return photos.map {
            savePhoto(url, it, ownerId, isGroup)
        }
    }

    override suspend fun createPost(
        ownerId: Long,
        message: String,
        photos: List<File>,
        isGroup: Boolean
    ): CreatingPostResultDomainModel {
        val uploadServer = getWallUploadServer(ownerId.absoluteValue, isGroup)
        val uploadedPhotos = savePhotos(
            url = uploadServer.uploadUrl,
            photos = photos,
            ownerId = ownerId.absoluteValue,
            isGroup
        )

        val attachments =
            uploadedPhotos.map { it.id }.joinToString(separator = ", ", prefix = "photo${ownerId}_")
        return creatingPostResultDomainMapper.mapToDomainModel(
            vkApi.createPost(
                ownerId = ownerId,
                message = message,
                attachments = attachments,
                fromGroup = if (isGroup) 1 else null
            )
        )
    }


}