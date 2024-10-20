package com.a1danz.feature_create_post.di.holder

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.a1danz.common.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.core_data.database.dao.PostDao
import com.a1danz.feature_create_post.di.DaggerCreatePostComponent
import javax.inject.Inject

@ApplicationScope
class CreatePostFeatureHolder @Inject constructor(
    private val resManager: ResourceManager,
    private val userModelDelegate: UserModelDelegate,
    private val dataStore: DataStore<Preferences>,
    private val postDao: PostDao
) : FeatureApiHolder() {
    override fun getComponent(): Any {
        return DaggerCreatePostComponent.builder()
            .resManager(resManager)
            .user(userModelDelegate.user ?: throw IllegalStateException("[Create Post Screen] User not initialized"))
            .dataStore(dataStore)
            .postDao(postDao)
            .build()
    }
}