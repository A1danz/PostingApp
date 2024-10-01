package com.a1danz.feature_authorization.presentation.model.event

import com.a1danz.common.presentation.model.ReadableError

sealed interface SignInEvent {
    data class ShowError(val readableError: ReadableError) : SignInEvent
    data object NavigateToAuthorizedState : SignInEvent
    data object NavigateToSignUp : SignInEvent
}