package com.a1danz.feature_settings.presentation.mapper

import com.a1danz.common.domain.model.TgConfig
import com.a1danz.feature_settings.presentation.model.tg.TgUserInfoUiModel
import javax.inject.Inject

class TgUserInfoUiMapper @Inject constructor() {
    fun mapToTgUserInfoUiModel(tgConfig: TgConfig): TgUserInfoUiModel {
        return tgConfig.run {
            TgUserInfoUiModel(
                username = tgUserInfo.tgUsername,
                chatNames = selectedChats.map { it.name },
                photo = tgUserInfo.tgUserPhoto
            )
        }
    }
}