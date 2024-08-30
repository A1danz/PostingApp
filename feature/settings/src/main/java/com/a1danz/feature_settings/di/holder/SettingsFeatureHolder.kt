package com.a1danz.feature_settings.di.holder

import android.util.Log
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.feature_settings.di.DaggerSettingsComponent
import com.a1danz.feature_settings.presentation.navigation.SettingsRouter
import com.a1danz.feature_user_configurer.di.UserConfigurerComponent
import com.a1danz.feature_user_configurer.di.holder.UserConfigurerFeatureHolder
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

@ApplicationScope
class SettingsFeatureHolder @Inject constructor(
    private val settingsRouter: SettingsRouter,
    private val resourceManager: ResourceManager,
    private val userModelDelegate: UserModelDelegate,
    private val firestore: FirebaseFirestore,
    private val userConfHolder: UserConfigurerFeatureHolder
) : FeatureApiHolder() {
    override fun getComponent(): Any {
        Log.d("SETTINGS HOLDER", "CREATED")
        return DaggerSettingsComponent.builder()
            .router(settingsRouter)
            .resourceManager(resourceManager)
            .user(userModelDelegate.user ?: throw IllegalStateException("User not initialized in SettingsComp"))
            .firebaseFirestore(firestore)
            .userConfigurer((userConfHolder.getComponent() as UserConfigurerComponent).getUserConfigurer())
            .build()
    }
}