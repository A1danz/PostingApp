package com.a1danz.posting.di.featureprovide.module

import android.content.Context
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.core_data.di.CoreDataComponent
import com.a1danz.core_data.di.DaggerCoreDataComponent
import dagger.Module
import dagger.Provides

@Module
class CoreComponentsModule {

    @ApplicationScope
    @Provides
    fun provideCoreDataComponent(context: Context): CoreDataComponent {
        return DaggerCoreDataComponent.builder()
            .context(context)
            .build()
    }
}