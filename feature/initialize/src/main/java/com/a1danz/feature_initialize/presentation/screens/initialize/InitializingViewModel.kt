package com.a1danz.feature_initialize.presentation.screens.initialize

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1danz.feature_authorization.domain.model.AuthorizedUser
import com.a1danz.feature_initialize.domain.interactor.InitializerInteractor
import com.a1danz.feature_initialize.presentation.model.event.InitializingEvent
import com.a1danz.feature_initialize.presentation.navigation.InitializingRouter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InitializingViewModel @Inject constructor(
    private val interactor: InitializerInteractor,
    private val router: InitializingRouter
) : ViewModel() {

    private var authorizedUser: AuthorizedUser? = null

    private val _initializingEvent: MutableSharedFlow<InitializingEvent> = MutableSharedFlow()
    val initializingEvent: SharedFlow<InitializingEvent> = _initializingEvent

    private val _userIsAuthorizedState: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val userIsAuthorizedState: StateFlow<Boolean?> = _userIsAuthorizedState

    fun checkUserAuthorization() {
        viewModelScope.launch {
            runCatching {
                interactor.getAuthorizedUser().apply {
                    authorizedUser = this
                }
            }.onSuccess { user ->
                _userIsAuthorizedState.value = user != null
            }.onFailure {
                Log.e("ERR", "Error while initializing", it)
                _userIsAuthorizedState.value = false
            }
        }
    }

    fun initializeUser() {
        viewModelScope.launch {
            if (authorizedUser == null) {
                _initializingEvent.emit(InitializingEvent.NavigateToAuthorization)
                return@launch
            }

            runCatching {
                interactor.initializeUser(authorizedUser!!)
            }.onSuccess {
                _initializingEvent.emit(InitializingEvent.NavigateToMain)
            }.onFailure {
                Log.e("ERR", "Error while initializing", it)
                _initializingEvent.emit(InitializingEvent.NavigateToAuthorization)
            }
        }
    }

    fun navigateToAuthorizationScreen() {
        router.navigateFromInitializingToAuthorization()
    }

    fun navigateToMainScreen() {
        router.navigateFromInitializingToMain()
    }
}