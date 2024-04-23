package com.a1danz.feature_authorization.presentation.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.R
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.domain.service.exceptions.AuthException
import com.a1danz.feature_authorization.domain.service.exceptions.InvalidCredentialsException
import com.a1danz.feature_authorization.domain.service.exceptions.UserAlreadyExistsException
import com.a1danz.feature_authorization.domain.service.exceptions.WeakPasswordException
import com.a1danz.feature_authorization.domain.service.impl.exceptionhandler.FirebaseExceptionHandlerDelegate
import com.a1danz.feature_authorization.domain.service.impl.exceptionhandler.runCatching
import com.a1danz.feature_authorization.utils.AuthorizationCodes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val authService : AuthorizationService,
    private val resManager : ResourceManager,
    private val authorizationRouter: AuthorizationRouter
) : ViewModel() {
    private val _signUpResultFlow = MutableStateFlow("")
    val signUpResultFlow: StateFlow<String> = _signUpResultFlow

    fun doSignUp(email : String, password : String, name: String) {
        viewModelScope.launch {
            runCatching(FirebaseExceptionHandlerDelegate()) {
                authService.signUp(email, password, name)
            }.onSuccess {
                _signUpResultFlow.value = AuthorizationCodes.SUCCESS_AUTH_CODE
            }.onFailure { ex ->
                println("HANDLED EXCEPTION - $ex")
                if (ex !is AuthException) _signUpResultFlow.value = AuthorizationCodes.ERROR_CODE
                _signUpResultFlow.value = when(ex) {
                    is InvalidCredentialsException -> resManager.getString(R.string.invalid_email)
                    is WeakPasswordException -> resManager.getString(R.string.weak_password)
                    is UserAlreadyExistsException -> resManager.getString(R.string.user_already_exists)
                    else -> resManager.getString(R.string.failed_to_create_account)
                }
            }
        }
    }

    fun moveToSignIn() {
        authorizationRouter.openSignInScreen()
    }

    fun moveToAuthorizedState() {
        authorizationRouter.openMainScreen()
    }
}