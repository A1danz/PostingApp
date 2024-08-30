package com.a1danz.common.di.featureprovide

interface FeatureContainer {
    fun <T> getFeature(key: Class<T>) : T
}