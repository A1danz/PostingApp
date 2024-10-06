package com.a1danz.feature_settings.presentation.model.state

sealed interface PlatformScreenState<T> {
    data class Linked<T>(val platformUserUiInfo: T) : PlatformScreenState<T>
    class Unlinked<T> : PlatformScreenState<T>
}