package com.a1danz.feature_authorization.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a1danz.common.core.resources.ResourceManager
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
    @Component.Builder
    interface Builder {
        fun build() : AuthComponent
        @BindsInstance
        fun router(router : AuthorizationRouter) : Builder
        @BindsInstance
        fun resourceManager(resManager : ResourceManager) : Builder
        @BindsInstance
        fun viewModelFactory(viewModelFactory : ViewModelProvider.Factory) : Builder
    }

    fun inject(fragment : SignInFragment)
    fun inject(fragment : SignUpFragment)
}