package com.a1danz.feature_create_post.presentation.model.event

sealed interface BottomSheetUiEvent {
    data object Dismiss : BottomSheetUiEvent
}