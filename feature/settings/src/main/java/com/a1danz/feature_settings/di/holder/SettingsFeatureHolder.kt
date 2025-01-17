package com.a1danz.feature_settings.di.holder

import android.util.Log
import com.a1danz.common.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.common.intent.IntentManager
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
    private val intentManager: IntentManager,
    private val userModelDelegate: UserModelDelegate,
    private val firestore: FirebaseFirestore,
    private val userConfHolder: UserConfigurerFeatureHolder
) : FeatureApiHolder() {
    override fun getComponent(): Any {
        return DaggerSettingsComponent.builder()
            .router(settingsRouter)
            .resourceManager(resourceManager)
            .intentManager(intentManager)
            .user(userModelDelegate.user ?: throw IllegalStateException("User not initialized in SettingsComp"))
            .firebaseFirestore(firestore)
            .userConfigurer((userConfHolder.getComponent() as UserConfigurerComponent).userConfigurer())
            .build()
    }
}