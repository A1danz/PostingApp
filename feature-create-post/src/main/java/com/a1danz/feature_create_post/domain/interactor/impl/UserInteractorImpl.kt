package com.a1danz.feature_create_post.domain.interactor.impl

import android.util.Log
import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_create_post.data.model.PostPublishingModels
import com.a1danz.feature_create_post.domain.factory.PostPublishingDomainModelsFactory
import com.a1danz.feature_create_post.domain.interactor.UserInteractor
import com.a1danz.feature_create_post.domain.model.PostPublishingDomainModel
import com.a1danz.feature_create_post.domain.model.PostPublishingItemDomainModel
import com.a1danz.feature_create_post.domain.model.PostPublishingItemInfoDomainModel
import com.a1danz.feature_places_info.data.mapper.PostPlaceTypeMapper
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceStaticInfo
import com.a1danz.feature_post_publisher_api.PostPublisher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UserInteractorImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val user: User,
    private val placesStaticInfo: HashMap<PostPlaceType, PostPlaceStaticInfo>,
    private val postPublishingFactory: PostPublishingDomainModelsFactory,
    private val postPlaceTypeMapper: PostPlaceTypeMapper
) : UserInteractor {
    override fun getTgChats(): List<TgChatInfo>? {
        return user.config.tgConfig?.selectedChats
    }

    override fun getVkUserInfo(): VkConfig? {
        return user.config.vkConfig
    }

    override fun getPostPlaceStaticInfo(postPlaceType: PostPlaceType): PostPlaceStaticInfo? {
        val postPlaceStaticInfo = placesStaticInfo[postPlaceType]
        if (postPlaceStaticInfo == null) {
            Log.e("STATIC INFO IS NULL", "STATIC INFO ABOUT $postPlaceType IS NULL!!!")
        }

        return postPlaceStaticInfo
    }

    override fun getPostPublishingModel(postPlaceType: PostPlaceType): PostPublishingDomainModel? {
        return postPublishingFactory.createPostPublishingModel(postPlaceType)
    }

    override fun getAlreadyCreatedPublishingModels(
        savedPostPublishingModels: PostPublishingModels?,
        postPublishers: HashMap<String, PostPublisher>
    ): List<PostPublishingDomainModel> {
        Log.e("SAVED", "$savedPostPublishingModels")
        Log.e("PUBLISHERS", "$postPublishers")
        val result: HashSet<PostPublishingDomainModel> = hashSetOf()
        savedPostPublishingModels?.publishingModels?.forEach {
            Log.e("ENTERED IN FOREACH", "ENTERED")
            val postPlaceType = postPlaceTypeMapper.mapDataToDomain(it.place) ?: return@forEach
            val postPlaceStaticInfo = getPostPlaceStaticInfo(postPlaceType) ?: return@forEach
            val publisher = postPublishers[it.uId]
            if (publisher == null) {
                Log.e("PUBLISHER NULL", "PUBLISHER IS NULL for ${it.place} - ${it.uId}")
                return@forEach
            }

            var postPublishingModel =
                result.find { postPublishingModel -> postPublishingModel.postPlaceStaticInfo == postPlaceStaticInfo }

            if (postPublishingModel == null) {
                postPublishingModel = PostPublishingDomainModel(
                    postPlaceStaticInfo,
                    mutableListOf()
                )
                result.add(postPublishingModel)
            }

            postPublishingModel.postPublishingItems.add(
                PostPublishingItemDomainModel(
                    publisher,
                    PostPublishingItemInfoDomainModel(
                        name = it.name,
                        img = it.img,
                        uId = it.uId,
                        postPlaceType = postPlaceTypeMapper.mapDataToDomain(it.place)
                    )
                )
            )
        }
        return result.toList()
    }
}