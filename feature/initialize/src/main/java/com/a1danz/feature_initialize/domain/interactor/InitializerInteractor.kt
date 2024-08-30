package com.a1danz.feature_initialize.domain.interactor

interface InitializerInteractor {
    suspend fun initializeUser()
    suspend fun checkUser(): Boolean
}