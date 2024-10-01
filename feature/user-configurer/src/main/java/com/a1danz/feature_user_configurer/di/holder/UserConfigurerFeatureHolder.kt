package com.a1danz.feature_user_configurer.di.holder

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.feature_user_configurer.di.DaggerUserConfigurerComponent
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

@ApplicationScope
class UserConfigurerFeatureHolder @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userModelDelegate: UserModelDelegate,
    private val dataStore: DataStore<Preferences>,
//    private val coreDataComponent: CoreDataComponent,
) : FeatureApiHolder() {

    val userConfigurerComponent by lazy {
        DaggerUserConfigurerComponent.builder()
//            .coreDataComponent(coreDataComponent)
            .userModelDelegate(userModelDelegate)
            .dataStore(dataStore)
            .firestore(firestore)
            .build()
    }

    override fun getComponent(): Any {
        return userConfigurerComponent
    }
}