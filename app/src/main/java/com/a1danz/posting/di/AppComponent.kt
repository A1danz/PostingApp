package com.a1danz.posting.di

import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_places_info.di.PostPlacesInfoModule
import com.a1danz.posting.App
import com.a1danz.posting.presentation.MainActivity
import com.a1danz.posting.di.authorization.AuthorizationModule
import com.a1danz.posting.di.featureprovide.ComponentHolderModule
import com.a1danz.posting.di.featureprovide.FeatureManagerModule
import com.a1danz.posting.di.firebase.FirebaseModule
import com.a1danz.posting.di.navigation.NavigationModule
import com.a1danz.posting.di.presentation.PresentationModule
import com.a1danz.posting.domain.di.DomainModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [
    AppModule::class, NavigationModule::class,
    ComponentHolderModule::class, FeatureManagerModule::class,
    AuthorizationModule::class, PresentationModule::class,
    FirebaseModule::class, PostPlacesInfoModule::class
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

