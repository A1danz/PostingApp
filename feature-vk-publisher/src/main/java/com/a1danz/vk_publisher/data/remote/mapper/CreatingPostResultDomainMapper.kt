package com.a1danz.vk_publisher.data.remote.mapper

import com.a1danz.feature_vk_api.data.remote.response.CreatingPostResponse
import com.a1danz.feature_vk_api.domain.model.CreatingPostResultDomainModel
import com.a1danz.vk_publisher.data.remote.exception.CreatingPostIdNotFoundException
import javax.inject.Inject

class CreatingPostResultDomainMapper @Inject constructor() {
    fun mapToDomainModel(creatingPostResponse: CreatingPostResponse): CreatingPostResultDomainModel {
        return creatingPostResponse.postData.let {
            CreatingPostResultDomainModel(it.postId ?: throw CreatingPostIdNotFoundException("post id not found"))
        }
    }
}