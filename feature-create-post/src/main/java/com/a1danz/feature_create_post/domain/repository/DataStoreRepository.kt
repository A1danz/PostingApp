package com.a1danz.feature_create_post.domain.repository

interface DataStoreRepository {
    suspend fun writeBoolean(key: String, value: Boolean)
    suspend fun readBoolean(key: String): Boolean?
}