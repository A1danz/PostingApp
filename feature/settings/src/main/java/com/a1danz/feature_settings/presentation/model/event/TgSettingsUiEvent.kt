package com.a1danz.feature_settings.presentation.model.event

sealed interface TgSettingsUiEvent {
    data class CopyTextToClipboard(val label: String, val text: String) : TgSettingsUiEvent
}