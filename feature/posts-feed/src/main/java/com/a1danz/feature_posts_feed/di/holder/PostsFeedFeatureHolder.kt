package com.a1danz.feature_posts_feed.di.holder

import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceStaticInfo
import com.a1danz.feature_posts_feed.di.DaggerPostsFeedComponent
import com.a1danz.feature_posts_feed_database.data.local.dao.PostDao
import javax.inject.Inject

@ApplicationScope
class PostsFeedFeatureHolder @Inject constructor(
    private val resManager: ResourceManager,
    private val postsDao: PostDao,
    private val places: HashMap<PostPlaceType, PostPlaceStaticInfo>
) : FeatureApiHolder() {

    override fun getComponent(): Any {
        return DaggerPostsFeedComponent.builder()
            .resManager(resManager)
            .postsDao(postsDao)
            .placesStaticInfo(places)
            .build()
    }
}