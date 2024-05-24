package com.a1danz.feature_create_post.di.holder

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.feature_create_post.di.DaggerCreatePostComponent
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.a1danz.feature_places_info.presentation.model.PostPlaceStaticInfo
import javax.inject.Inject

@ApplicationScope
class CreatePostFeatureHolder @Inject constructor(
    private val resManager: ResourceManager,
    private val userModelDelegate: UserModelDelegate,
    private val dataStore: DataStore<Preferences>,
    private val places: HashMap<PostPlaceType, PostPlaceStaticInfo>
) : FeatureApiHolder() {
    override fun getComponent(): Any {
        return DaggerCreatePostComponent.builder()
            .resManager(resManager)
            .user(userModelDelegate.user ?: throw IllegalStateException("[Create Post Screen] User not initialized"))
            .dataStore(dataStore)
            .placesStaticInfo(places)
            .build()
    }
}