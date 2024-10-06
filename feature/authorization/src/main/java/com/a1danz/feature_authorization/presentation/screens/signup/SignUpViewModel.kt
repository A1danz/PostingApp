package com.a1danz.feature_authorization.presentation.screens.signup

import androidx.lifecycle.viewModelScope
import com.a1danz.common.presentation.base.BaseViewModel
import com.a1danz.common.presentation.model.ReadableError
import com.a1danz.common.presentation.model.Text
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.presentation.model.event.SignUpEvent
import com.a1danz.feature_authorization.presentation.utils.AuthorizationExceptionsConverter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val authService : AuthorizationService,
    private val authorizationRouter: AuthorizationRouter,
    private val authorizationExceptionsConverter: AuthorizationExceptionsConverter,
) : BaseViewModel() {

    private val _signUpEvent: MutableSharedFlow<SignUpEvent> = MutableSharedFlow()
    val signUpEvent: SharedFlow<SignUpEvent> = _signUpEvent

    fun doSignUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            runCatching {
                authService.signUp(email, password, name)
            }.onSuccess {
                _signUpEvent.emit(SignUpEvent.NavigateToAuthorizedState)
            }.onFailure { ex ->
                _signUpEvent.emit(
                    SignUpEvent.ShowError(
                        authorizationExceptionsConverter.convertException(ex)
                    )
                )
            }
        }
    }

    fun onSignInBtnClicked() {
        viewModelScope.launch {
            _signUpEvent.emit(
                SignUpEvent.NavigateToSignIn
            )
        }
    }

    fun onError(msg: String) {
        viewModelScope.launch {
            _signUpEvent.emit(
                SignUpEvent.ShowError(
                    ReadableError.Custom(uiMessage = Text.Simple(msg))
                )
            )
        }
    }

    fun moveToSignIn() {
        authorizationRouter.openSignInScreen()
    }

    fun moveToAuthorizedState() {
        authorizationRouter.openMainScreen()
    }
}