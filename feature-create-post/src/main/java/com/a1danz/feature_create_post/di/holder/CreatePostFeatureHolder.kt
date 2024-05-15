package com.a1danz.feature_create_post.di.holder

import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.feature_create_post.di.DaggerCreatePostComponent
import javax.inject.Inject

@ApplicationScope
class CreatePostFeatureHolder @Inject constructor(
    private val resManager: ResourceManager,
    private val userModelDelegate: UserModelDelegate
) : FeatureApiHolder() {
    override fun getComponent(): Any {
        return DaggerCreatePostComponent.builder()
            .resManager(resManager)
            .user(userModelDelegate.user ?: throw IllegalStateException("[Create Post Screen] User not initialized"))
            .build()
    }
}