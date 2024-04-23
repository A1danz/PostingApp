package com.a1danz.common.core.datastore.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.a1danz.common.core.datastore.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManagerImpl(
    private val context: Context
) : DataStoreManager {
    override suspend fun saveString(key: String, value: String) {
        context.dataStore.edit { config ->
            config[stringPreferencesKey(key)] = value
        }

    }

    override suspend fun getString(key: String, defValue: String): Flow<String> {
        return context.dataStore.data.map { config ->
            config[stringPreferencesKey(key)] ?: defValue
        }
    }

    override suspend fun getLong(key: String, defValue: Long): Flow<Long> {
        return context.dataStore.data.map { config ->
            config[longPreferencesKey(key)] ?: defValue
        }
    }

    override suspend fun saveLong(key: String, value: Long) {
        context.dataStore.edit { config ->
            config[longPreferencesKey(key)] = value
        }
    }
}


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "config")
