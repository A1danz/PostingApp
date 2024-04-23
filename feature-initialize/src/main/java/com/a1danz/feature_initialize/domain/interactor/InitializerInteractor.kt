package com.a1danz.feature_initialize.domain.interactor

import com.a1danz.common.domain.model.User

interface InitializerInteractor {
    suspend fun initializeUser()
    suspend fun checkUser(): Boolean
}