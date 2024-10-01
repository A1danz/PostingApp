package com.a1danz.posting.di.featureprovide.module

import dagger.Module

@Module(
    includes = [
        ComponentHolderModule::class,
        CoreComponentsModule::class,
        FeatureManagerModule::class
    ]
)
class FeatureProvideModule