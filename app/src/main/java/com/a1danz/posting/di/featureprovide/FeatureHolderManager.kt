package com.a1danz.posting.di.featureprovide

import com.a1danz.common.di.featureprovide.FeatureApiHolder

class FeatureHolderManager(
    val featureHolders: Map<Class<*>, FeatureApiHolder>
) {
    fun <T> getComponent(key : Class<T>): T {
        val featureApiHolder = featureHolders[key]
            ?: throw IllegalStateException("Component holder for: {$key} not found")

        return featureApiHolder.getComponent() as T
    }
}
