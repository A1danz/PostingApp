package com.a1danz.core_data.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun getString(key: String, defValue: String): Flow<String>
    suspend fun saveString(key: String, value: String)
    suspend fun getLong(key: String, defValue: Long): Flow<Long>
    suspend fun saveLong(key: String, value: Long)
}