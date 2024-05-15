package com.a1danz.feature_create_post.domain.interactor.impl

import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_create_post.domain.interactor.UserSelectedMediaInteractor
import javax.inject.Inject

class UserSelectedMediaInteractorImpl @Inject constructor(
    private val userConfig: Config
) : UserSelectedMediaInteractor {
    override fun getTgConfig(): TgConfig? {
        return userConfig.tgConfig
    }

    override fun getVkConfig(): VkConfig? {
        return userConfig.vkConfig
    }
}