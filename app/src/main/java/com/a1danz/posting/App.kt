package com.a1danz.posting

import android.app.Application
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_authorization.di.AuthComponent
import com.a1danz.feature_authorization.di.DaggerAuthComponent

class App : Application() {
    lateinit var authComponent : AuthComponent
    override fun onCreate() {
        super.onCreate()
        authComponent = DaggerAuthComponent.create()
    }
}