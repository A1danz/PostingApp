package com.a1danz.feature_create_post.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.a1danz.feature_create_post.data.model.PostPublishingModel
import com.a1danz.feature_create_post.data.model.PostPublishingModels
import com.a1danz.feature_create_post.domain.repository.DataStoreRepository
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
) : DataStoreRepository {

    override suspend fun writeBoolean(key: String, value: Boolean) {
        dataStore.edit { prefs ->
            prefs[booleanPreferencesKey(key)] = value
        }
    }

    override suspend fun readBoolean(key: String): Boolean? {
        return dataStore.data.first()[booleanPreferencesKey(key)]
    }

    override suspend fun initPostPublishingModels(key: String) {
        val postPublishingModels = PostPublishingModels(mutableListOf())
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(key)] = gson.toJson(postPublishingModels)
        }
    }

    override suspend fun getPostPublishingModels(key: String): PostPublishingModels? {
        return try {
            val json = dataStore.data.first()[stringPreferencesKey(key)]
            gson.fromJson(json, PostPublishingModels::class.java)
        } catch (ex: Exception) {
            Log.e("ERROR", "Can not get postPublishingModels: $ex")
            ex.printStackTrace()
            null
        }
    }

    override suspend fun addPostPublishingModel(key: String, postPublishingModel: PostPublishingModel) {
        val publishingModels = getPostPublishingModels(key)
        if (publishingModels == null) {
            Log.e("ERROR", "POST PUBLISHING MODELS IS NULL, BUT NEED TO ADD MODEL")
            return
        }

        publishingModels.publishingModels.add(postPublishingModel)
        dataStore.updateData { prefs ->
            prefs.toMutablePreferences().apply {
                set(stringPreferencesKey(key), gson.toJson(publishingModels))
            }
        }
    }
}