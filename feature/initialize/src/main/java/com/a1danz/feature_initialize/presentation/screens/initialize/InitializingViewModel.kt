package com.a1danz.feature_initialize.presentation.screens.initialize

import androidx.lifecycle.ViewModel
import com.a1danz.feature_initialize.domain.interactor.InitializerInteractor
import com.a1danz.feature_initialize.presentation.navigation.InitializingRouter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InitializingViewModel @Inject constructor(
    private val initInteractor: InitializerInteractor,
    private val router: InitializingRouter
) : ViewModel() {
    fun checkUser(): Flow<Boolean> = flow {
        if (initInteractor.checkUser()) {
            initInteractor.initializeUser()
            emit(true)
        } else {
            router.navigateFromInitializingToAuthorization()
            emit(false)
        }
    }
}