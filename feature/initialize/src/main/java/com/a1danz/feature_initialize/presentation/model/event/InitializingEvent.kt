package com.a1danz.feature_initialize.presentation.model.event

sealed interface InitializingEvent {
    data object NavigateToMain : InitializingEvent
    data object NavigateToAuthorization : InitializingEvent
}