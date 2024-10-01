package com.a1danz.posting.di

import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.posting.App
import com.a1danz.posting.di.featureprovide.module.ComponentHolderModule
import com.a1danz.posting.di.featureprovide.module.FeatureManagerModule
import com.a1danz.posting.di.featureprovide.module.FeatureProvideModule
import com.a1danz.posting.di.firebase.FirebaseModule
import com.a1danz.posting.di.navigation.NavigationModule
import com.a1danz.posting.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
        FeatureProvideModule::class,
        FirebaseModule::class,
    ]
)
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

