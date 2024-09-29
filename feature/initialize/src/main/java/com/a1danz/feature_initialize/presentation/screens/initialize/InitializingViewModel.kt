package com.a1danz.feature_initialize.presentation.screens.initialize

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1danz.feature_initialize.domain.interactor.InitializerInteractor
import com.a1danz.feature_initialize.presentation.navigation.InitializingRouter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InitializingViewModel @Inject constructor(
    private val interactor: InitializerInteractor,
    private val router: InitializingRouter
) : ViewModel() {

    private val _userAuthorizedState: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val userAuthorizedState: SharedFlow<Boolean> = _userAuthorizedState

    fun checkUserAuthorization() {
        viewModelScope.launch {
            val user = interactor.getAuthorizedUser()
            if (user != null) {
                interactor.initializeUser(user)
            }

            _userAuthorizedState.emit(user != null)
        }
    }

    fun goToAuthorizationScreen() {
        router.navigateFromInitializingToAuthorization()
    }

    fun goToMainScreen() {
        router.navigateFromInitializingToMain()
    }
}