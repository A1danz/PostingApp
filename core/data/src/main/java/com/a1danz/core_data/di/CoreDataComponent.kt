package com.a1danz.core_data.di

import android.content.Context
import com.a1danz.common.di.scope.ApplicationScope
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher

@Component(
    modules = [
        DataModule::class
    ]
)
@ApplicationScope
interface CoreDataComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): CoreDataComponent
    }

    fun gson(): Gson
    fun coroutineDispatcher(): CoroutineDispatcher
}