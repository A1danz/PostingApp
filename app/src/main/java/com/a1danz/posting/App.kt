package com.a1danz.posting

import android.app.Application
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.posting.di.AppComponent
import com.a1danz.posting.di.DaggerAppComponent
import com.a1danz.posting.di.featureprovide.FeatureHolderManager
import javax.inject.Inject


class App : Application(), FeatureContainer {
    lateinit var appComponent : AppComponent
    @Inject lateinit var featureHolderManager: FeatureHolderManager
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        appComponent.inject(this)

    }

    override fun <T> getFeature(key: Class<T>): T {
        return featureHolderManager.getComponent(key)
    }
}
