package com.a1danz.feature_posts_feed.di.holder

import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.core_data.database.dao.PostDao
import com.a1danz.feature_posts_feed.di.DaggerPostsFeedComponent
import javax.inject.Inject

@ApplicationScope
class PostsFeedFeatureHolder @Inject constructor(
    private val resManager: ResourceManager,
    private val postsDao: PostDao
) : FeatureApiHolder() {

    override fun getComponent(): Any {
        return DaggerPostsFeedComponent.builder()
            .resManager(resManager)
            .postsDao(postsDao)
            .build()
    }
}