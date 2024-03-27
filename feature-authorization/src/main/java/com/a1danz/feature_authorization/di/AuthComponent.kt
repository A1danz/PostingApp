package com.a1danz.feature_authorization.di

import androidx.fragment.app.Fragment
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.presentation.screens.AuthorizationFragment
import com.a1danz.feature_authorization.presentation.screens.signin.SignInFragment
import com.a1danz.feature_authorization.presentation.screens.signup.SignUpFragment
import com.a1danz.feature_authorization.presentation.screens.vm_factory.SignInViewModelFactory
import com.a1danz.feature_authorization.presentation.screens.vm_factory.SignUpViewModelFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Component.Builder


@Component(modules = [AuthModule::class])
interface AuthComponent {
    companion object {
        fun init(router: AuthorizationRouter) = DaggerAuthComponent.builder()
            .router(router)
            .build()
    }
    @Component.Builder
    interface Builder {
        fun build() : AuthComponent
        @BindsInstance
        fun router(router : AuthorizationRouter) : Builder
    }
    fun getSignInFactory() : SignInViewModelFactory
    fun getSignUpFactory() : SignUpViewModelFactory

    fun inject(fragment : SignInFragment)
    fun inject(fragment : SignUpFragment)
}