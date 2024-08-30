package com.a1danz.feature_user_configurer.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.feature_user_configurer.UserConfigurer
import com.google.firebase.firestore.FirebaseFirestore
import dagger.BindsInstance
import dagger.Component

@Component(modules = [
    UserConfigurerModule::class
])
interface UserConfigurerComponent {
    fun getUserConfigurer(): UserConfigurer

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun userModelDelegate(userModelDelegate: UserModelDelegate): Builder
        @BindsInstance
        fun dataStore(dataStore: DataStore<Preferences>): Builder
        @BindsInstance
        fun firestore(firestore: FirebaseFirestore): Builder

        fun build(): UserConfigurerComponent

    }
}

