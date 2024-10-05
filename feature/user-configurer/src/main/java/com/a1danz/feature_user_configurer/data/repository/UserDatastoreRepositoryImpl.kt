package com.a1danz.feature_user_configurer.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.a1danz.common.delegate.ExceptionHandlerDelegate
import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.ext.doOrLog
import com.a1danz.feature_user_configurer.domain.repository.UserLocalRepository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDatastoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson,
    private val dispatcher: CoroutineDispatcher,
) : UserLocalRepository {

    override suspend fun getUser(): User? {
        return withContext(dispatcher) {
            doOrLog("Cant get user") {
                dataStore.data.first()[USER_DATA_KEY]?.let { json ->
                    gson.fromJson(json, User::class.java)
                }
            }
        }
    }

    override suspend fun saveUser(user: User) {
        withContext(dispatcher) {
            doOrLog {
                dataStore.edit { prefs ->
                    prefs[USER_DATA_KEY] = gson.toJson(user)
                }
            }
        }
    }

    override suspend fun updateConfig(update: (Config) -> Config) {
        withContext(dispatcher) {
            doOrLog {
                dataStore.updateData { prefs ->
                    prefs.toMutablePreferences().apply {
                        val json = prefs[USER_DATA_KEY]
                        if (json != null) {
                            val user = gson.fromJson(json, User::class.java)
                            user.config = update(user.config)
                            set(USER_DATA_KEY, gson.toJson(user))
                        } else {
                            Log.e("PREFS EMPTY", "Cant update UserConfig because it null")
                        }
                    }
                }
            }
        }
    }

    override suspend fun updateVkConfig(update: (VkConfig) -> VkConfig) {
        updateConfig {
            it.copy(vkConfig = it.vkConfig?.let(update))
        }
    }

    override suspend fun clearVkConfig() {
        updateConfig {
            it.copy(vkConfig = null)
        }
    }

    override suspend fun updateTgConfig(update: (TgConfig) -> TgConfig) {
        updateConfig {
            it.copy(tgConfig = it.tgConfig?.let(update))
        }
    }

    override suspend fun clearTgConfig() {
        updateConfig {
            it.copy(tgConfig = null)
        }
    }

    companion object {
        private val USER_DATA_KEY = stringPreferencesKey("user_data")
    }
}