package com.a1danz.feature_authorization.presentation.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.domain.service.exceptions.AuthException
import com.a1danz.feature_authorization.domain.service.exceptions.InvalidCredentialsException
import com.a1danz.feature_authorization.domain.service.exceptions.UserAlreadyExistsException
import com.a1danz.feature_authorization.domain.service.exceptions.WeakPasswordException
import com.a1danz.feature_authorization.domain.service.impl.exceptionhandler.FirebaseExceptionHandlerDelegate
import com.a1danz.feature_authorization.domain.service.impl.exceptionhandler.runCatching
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authService : AuthorizationService
) : ViewModel() {
    val SUCCESS_SIGN_UP = "success"

    private val _signUpResultFlow = MutableStateFlow("")
    val signUpResultFlow : StateFlow<String> = _signUpResultFlow

    fun doSignUp(email : String, password : String) {
        viewModelScope.launch {
            runCatching(FirebaseExceptionHandlerDelegate()) {
                authService.signUp(email, password)
            }.onSuccess {
                _signUpResultFlow.value = SUCCESS_SIGN_UP
            }.onFailure { ex ->
                println("HANDLED EXCEPTION - $ex")
                if (ex !is AuthException) _signUpResultFlow.value = "error"
                _signUpResultFlow.value = when(ex) {
                    is InvalidCredentialsException -> "Неправильный формат e-mail"
                    is WeakPasswordException -> "Пароль слишком легкий, попробуйте его усложнить"
                    is UserAlreadyExistsException -> "Пользователь с данным e-mail уже зарегестрирован"
                    else -> "Не удалось создать аккаунт."
                }
            }
        }

    }

}