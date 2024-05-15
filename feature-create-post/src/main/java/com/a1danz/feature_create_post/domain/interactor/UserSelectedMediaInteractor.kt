package com.a1danz.feature_create_post.domain.interactor

import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.VkConfig

interface UserSelectedMediaInteractor {
    fun getTgConfig(): TgConfig?
    fun getVkConfig(): VkConfig?
}