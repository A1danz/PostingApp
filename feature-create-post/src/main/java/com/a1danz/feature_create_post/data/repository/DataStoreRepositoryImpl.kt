package com.a1danz.feature_create_post.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.a1danz.feature_create_post.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {
    override suspend fun writeBoolean(key: String, value: Boolean) {
        dataStore.edit { prefs ->
            prefs[booleanPreferencesKey(key)] = value
        }
    }

    override suspend fun readBoolean(key: String): Boolean? {
        return dataStore.data.first()[booleanPreferencesKey(key)]

    }
}