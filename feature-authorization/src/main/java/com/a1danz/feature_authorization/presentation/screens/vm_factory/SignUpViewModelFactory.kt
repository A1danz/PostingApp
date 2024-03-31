package com.a1danz.feature_authorization.presentation.screens.vm_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.presentation.screens.signin.SignInViewModel
import com.a1danz.feature_authorization.presentation.screens.signup.SignUpViewModel
import javax.inject.Inject

class SignUpViewModelFactory @Inject constructor(
    private val authService: AuthorizationService,
    private val resManager : ResourceManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel(authService, resManager) as T
    }
}