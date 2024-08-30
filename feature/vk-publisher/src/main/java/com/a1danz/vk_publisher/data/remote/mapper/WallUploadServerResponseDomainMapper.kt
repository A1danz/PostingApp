package com.a1danz.vk_publisher.data.remote.mapper

import com.a1danz.feature_vk_api.data.remote.response.WallUploadServerResponse
import com.a1danz.feature_vk_api.domain.model.WallUploadServerDomainModel
import com.a1danz.vk_publisher.data.remote.exception.VkUploadServerNotFoundException
import javax.inject.Inject

class WallUploadServerResponseDomainMapper @Inject constructor() {
    fun mapToDomainModel(response: WallUploadServerResponse): WallUploadServerDomainModel {
        return response.uploadServerDataModel.let {
            WallUploadServerDomainModel(
                uploadUrl = it.uploadUrl ?: throw VkUploadServerNotFoundException("Can not found upload Server")
            )
        }
    }
}