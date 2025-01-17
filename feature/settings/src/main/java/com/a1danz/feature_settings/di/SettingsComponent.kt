package com.a1danz.feature_settings.di

import com.a1danz.common.resources.ResourceManager
import com.a1danz.common.di.scope.FeatureScope
import com.a1danz.common.domain.model.User
import com.a1danz.common.intent.IntentManager
import com.a1danz.feature_settings.data.di.DataModule
import com.a1danz.feature_settings.data.di.VkNetworkModule
import com.a1danz.feature_settings.domain.di.DomainModule
import com.a1danz.feature_settings.presentation.di.PresentationModule
import com.a1danz.feature_settings.presentation.navigation.SettingsRouter
import com.a1danz.feature_settings.presentation.screens.main.SettingsFragment
import com.a1danz.feature_settings.presentation.screens.social_media.SocialMediaSettingsFragment
import com.a1danz.feature_settings.presentation.screens.social_media.tg.TgSettingsFragment
import com.a1danz.feature_settings.presentation.screens.social_media.vk.VkSettingsFragment
import com.a1danz.feature_user_configurer.UserConfigurer
import com.google.firebase.firestore.FirebaseFirestore
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
    ]
)
@FeatureScope
interface SettingsComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun router(router: SettingsRouter): Builder

        @BindsInstance
        fun resourceManager(resourceManager: ResourceManager): Builder

        @BindsInstance
        fun intentManager(intentManager: IntentManager): Builder

        @BindsInstance
        fun user(user: User): Builder

        @BindsInstance
        fun firebaseFirestore(firestore: FirebaseFirestore): Builder

        @BindsInstance
        fun userConfigurer(userConfigurer: UserConfigurer): Builder

        fun build(): SettingsComponent
    }

    fun inject(fragment: SocialMediaSettingsFragment)
    fun inject(fragment: SettingsFragment)
    fun inject(fragment: VkSettingsFragment)
    fun inject(fragment: TgSettingsFragment)
}