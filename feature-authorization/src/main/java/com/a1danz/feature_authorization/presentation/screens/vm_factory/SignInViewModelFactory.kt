package com.a1danz.feature_authorization.presentation.screens.vm_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.presentation.screens.signin.SignInViewModel

class SignInViewModelFactory(private val authService: AuthorizationService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignInViewModel(authService) as T
    }
}