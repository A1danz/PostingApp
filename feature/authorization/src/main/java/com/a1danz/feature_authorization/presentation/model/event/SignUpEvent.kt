package com.a1danz.feature_authorization.presentation.model.event

import com.a1danz.common.presentation.model.ReadableError

sealed interface SignUpEvent {
    data class ShowError(val readableError: ReadableError) : SignUpEvent
    data object NavigateToAuthorizedState : SignUpEvent
    data object NavigateToSignIn : SignUpEvent
}