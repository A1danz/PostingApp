package com.a1danz.feature_authorization.presentation.screens.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1danz.common.presentation.base.BaseViewModel
import com.a1danz.common.presentation.model.ReadableError
import com.a1danz.common.presentation.model.Text
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.presentation.model.event.SignInEvent
import com.a1danz.feature_authorization.presentation.utils.AuthorizationExceptionsConverter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val authService : AuthorizationService,
    private val authorizationRouter: AuthorizationRouter,
    private val authorizationExceptionsConverter: AuthorizationExceptionsConverter,
) : BaseViewModel() {

    private val _signInEvent: MutableSharedFlow<SignInEvent> = MutableSharedFlow()
    val signInEvent: SharedFlow<SignInEvent> = _signInEvent

    fun doSignIn(email : String, password : String) {
        viewModelScope.launch {
            runCatching {
                authService.signIn(email, password)
            }.onSuccess {
                _signInEvent.emit(SignInEvent.NavigateToAuthorizedState)
            }.onFailure { ex ->
                _signInEvent.emit(
                    SignInEvent.ShowError(authorizationExceptionsConverter.convertException(ex))
                )
            }
        }
    }

    fun onSignUpBtnClicked() {
        viewModelScope.launch {
            _signInEvent.emit(SignInEvent.NavigateToSignUp)
        }
    }

    fun onError(msg: String) {
        viewModelScope.launch {
            _signInEvent.emit(
                SignInEvent.ShowError(
                    ReadableError.Custom(uiMessage = Text.Simple(msg))
                )
            )
        }
    }

    fun moveToSignUp() {
        authorizationRouter.openSignUpScreen()
    }

    fun moveToAuthorizedState() {
        authorizationRouter.openMainScreen()
    }
}
