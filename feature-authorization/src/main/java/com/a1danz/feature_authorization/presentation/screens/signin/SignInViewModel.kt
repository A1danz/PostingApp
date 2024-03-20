package com.a1danz.feature_authorization.presentation.screens.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.domain.service.exceptions.AuthException
import com.a1danz.feature_authorization.domain.service.exceptions.InvalidCredentialsException
import com.a1danz.feature_authorization.domain.service.exceptions.InvalidUserException
import com.a1danz.feature_authorization.domain.service.impl.exceptionhandler.FirebaseExceptionHandlerDelegate
import com.a1danz.feature_authorization.domain.service.impl.exceptionhandler.runCatching
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val authService : AuthorizationService) : ViewModel() {
    val SUCCESS_SIGN_IN = "success"

    private val _signInResultFlow : MutableStateFlow<String> = MutableStateFlow("")
    val signInResultFlow : StateFlow<String> get() = _signInResultFlow
    fun doSignIn(email : String, password : String) {
        viewModelScope.launch {
            runCatching(FirebaseExceptionHandlerDelegate()) {
                authService.signIn(email, password)
            }.onSuccess {
                _signInResultFlow.value = SUCCESS_SIGN_IN
            }.onFailure { ex ->
                println("ERROR class - $ex")
                if (ex !is AuthException) {
                    _signInResultFlow.value = "error"
                    return@onFailure
                }

                _signInResultFlow.value = when(ex) {
                    is InvalidCredentialsException -> "Неверные данные"
                    is InvalidUserException -> "Такого пользователя не существует"
                    else -> "Ошибка авторизации"
                }
            }
        }
    }


}