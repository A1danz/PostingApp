package com.a1danz.feature_settings.presentation.screens.social_media.base

import com.a1danz.common.presentation.base.BaseViewModel
import com.a1danz.feature_settings.presentation.model.state.PlatformScreenState
import com.a1danz.feature_settings.presentation.model.tg.TgUserInfoUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BasePlatformViewModel<T> : BaseViewModel() {
    protected val _screenState: MutableStateFlow<PlatformScreenState<T>?> = MutableStateFlow(null)
    val screenState: StateFlow<PlatformScreenState<T>?> = _screenState
}