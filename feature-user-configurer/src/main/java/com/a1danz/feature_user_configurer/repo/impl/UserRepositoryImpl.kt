package com.a1danz.feature_user_configurer.repo.impl

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.feature_user_configurer.repo.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
) : UserRepository {

    override suspend fun getUser(): User? {
        return try {
            val json = dataStore.data.first()[USER_DATA_KEY] ?: return null
            gson.fromJson(json, User::class.java)
        } catch (ex: Exception) {
            Log.e("ERROR getUser", "Err: $ex")
            null
        }
    }

    override suspend fun saveUser(user: User) {
        val json = gson.toJson(user)
        dataStore.edit { prefs ->
            prefs[USER_DATA_KEY] = json
        }
//        dataStoreManager.saveString(USER_UID, user.uId)
//        dataStoreManager.saveString(USER_NAME, user.name)
    }

    override suspend fun updateConfig(update: (Config) -> Config) {
        dataStore.updateData { prefs ->
            prefs.toMutablePreferences().apply {
                val json = prefs[USER_DATA_KEY]
                if (json != null) {
                    val user = gson.fromJson(json, User::class.java)
                    user.config = update(user.config)
                    set(USER_DATA_KEY, gson.toJson(user))
                } else {
                    Log.e("PREFS EMPTY", "cant update UserConfig because it null")
                }
            }
        }
    }

    override suspend fun updateVkConfig(update: (VkConfig) -> VkConfig) {
        dataStore.updateData { prefs ->
            prefs.toMutablePreferences().apply {
                val json = prefs[USER_DATA_KEY]
                if (json != null) {
                    val user = gson.fromJson(json, User::class.java)
                    user.config.vkConfig = if (user.config.vkConfig != null) update(user.config.vkConfig!!) else null
                    set(USER_DATA_KEY, gson.toJson(user))
                } else {
                    Log.e("PREFS EMPTY", "cant update UserConfig because it null")
                }
            }
        }
    }

    override suspend fun clearVkConfig() {
        dataStore.updateData { prefs ->
            prefs.toMutablePreferences().apply {
                val json = prefs[USER_DATA_KEY]
                if (json != null) {
                    val user = gson.fromJson(json, User::class.java)
                    user.config.vkConfig = null
                    set(USER_DATA_KEY, gson.toJson(user))
                }
            }
        }
    }

    override suspend fun updateTgConfig(update: (TgConfig) -> TgConfig) {
        dataStore.updateData { prefs ->
            prefs.toMutablePreferences().apply {
                val json = prefs[USER_DATA_KEY]
                if (json != null) {
                    val user = gson.fromJson(json, User::class.java)
                    user.config.tgConfig = if (user.config.tgConfig != null) update(user.config.tgConfig!!) else null
                    set(USER_DATA_KEY, gson.toJson(user))
                } else {
                    Log.e("PREFS EMPTY", "cant update UserConfig because it null")
                }
            }

        }
    }

    override suspend fun clearTgConfig() {
        dataStore.updateData { prefs ->
            prefs.toMutablePreferences().apply {
                val json = prefs[USER_DATA_KEY]
                if (json != null) {
                    val user = gson.fromJson(json, User::class.java)
                    user.config.tgConfig = null
                    set(USER_DATA_KEY, gson.toJson(user))
                }
            }
        }
    }

    companion object {
        private val USER_DATA_KEY = stringPreferencesKey("user_data")
    }
}