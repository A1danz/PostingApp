package com.a1danz.feature_authorization.presentation.screens.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.feature_authorization.R
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.domain.service.exceptions.AuthException
import com.a1danz.feature_authorization.domain.service.exceptions.InvalidCredentialsException
import com.a1danz.feature_authorization.domain.service.exceptions.InvalidUserException
import com.a1danz.feature_authorization.domain.service.impl.exceptionhandler.FirebaseExceptionHandlerDelegate
import com.a1danz.feature_authorization.domain.service.impl.exceptionhandler.runCatching
import com.a1danz.feature_authorization.utils.AuthorizationCodes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val authService : AuthorizationService,
    private val resManager : ResourceManager
    ) : ViewModel() {
    private val _signInResultFlow : MutableStateFlow<String> = MutableStateFlow("")
    val signInResultFlow : StateFlow<String> get() = _signInResultFlow
    fun doSignIn(email : String, password : String) {
        viewModelScope.launch {
            runCatching(FirebaseExceptionHandlerDelegate()) {
                authService.signIn(email, password)
            }.onSuccess {
                _signInResultFlow.value = AuthorizationCodes.SUCCESS_AUTH_CODE
            }.onFailure { ex ->
                println("ERROR class - $ex")
                if (ex !is AuthException) {
                    Log.e("AUTH", "Not auth exception during authorization. Ex: $ex")
                    _signInResultFlow.value = resManager.getString(R.string.authorization_error)
                    return@onFailure
                }

                _signInResultFlow.value = when(ex) {
                    is InvalidCredentialsException -> resManager.getString(R.string.invalid_data)
                    is InvalidUserException -> resManager.getString(R.string.user_doest_not_exist)
                    else -> resManager.getString(R.string.authorization_error)
                }
            }
        }
    }


}