package com.a1danz.posting.di

import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.posting.App
import com.a1danz.posting.MainActivity
import com.a1danz.posting.di.featureprovide.ComponentHolderModule
import com.a1danz.posting.di.featureprovide.FeatureManagerModule
import com.a1danz.posting.di.presentation.PresentationModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [
    AppModule::class, NavigationModule::class,
    ComponentHolderModule::class, FeatureManagerModule::class,
    AuthorizationModule::class, PresentationModule::class
])
@ApplicationScope
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(app: App)
}

