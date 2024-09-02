package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.model.event

sealed interface UiEvent {
    data object TimeoutExpired : UiEvent
}