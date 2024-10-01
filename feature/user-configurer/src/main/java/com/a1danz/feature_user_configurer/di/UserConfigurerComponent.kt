package com.a1danz.feature_user_configurer.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.feature_user_configurer.UserConfigurer
import com.a1danz.feature_user_configurer.di.scope.UserConfigurerScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        UserConfigurerModule::class
    ],
    dependencies = [
//        CoreDataComponent::class
    ]
)
@UserConfigurerScope
interface UserConfigurerComponent {
    fun userConfigurer(): UserConfigurer

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun userModelDelegate(userModelDelegate: UserModelDelegate): Builder

        @BindsInstance
        fun dataStore(dataStore: DataStore<Preferences>): Builder

        @BindsInstance
        fun firestore(firestore: FirebaseFirestore): Builder

//        fun coreDataComponent(coreDataComponent: CoreDataComponent): Builder

        fun build(): UserConfigurerComponent

    }
}

