package com.a1danz.feature_settings.domain.interactor

import com.a1danz.feature_settings.domain.interactor.tg.TgUserInteractor
import com.a1danz.feature_settings.domain.interactor.vk.VkUserInteractor

interface UserInteractor : VkUserInteractor, TgUserInteractor {
}