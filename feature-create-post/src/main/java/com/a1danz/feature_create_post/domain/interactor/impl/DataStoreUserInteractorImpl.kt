package com.a1danz.feature_create_post.domain.interactor.impl

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.a1danz.feature_create_post.domain.interactor.DatastoreUserInteractor
import com.a1danz.feature_create_post.domain.repository.DataStoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DataStoreUserInteractorImpl(
    private val dispatcher: CoroutineDispatcher,
    private val dataStoreRepository: DataStoreRepository
) : DatastoreUserInteractor {
    override suspend fun writePublishingInProcess(inProcess: Boolean) {
        withContext(dispatcher) {
            dataStoreRepository.writeBoolean(PUBLISHING_IN_PROCESS_KEY, inProcess)
        }
    }

    override suspend fun getPublishingInProcess(): Boolean {
        return withContext(dispatcher) {
            val result = dataStoreRepository.readBoolean(PUBLISHING_IN_PROCESS_KEY)
            if (result == null) Log.e("DATASTORE NULL", "PUBLISHING PROCESS IS NULL")
            result ?: false
        }
    }

    companion object {
        const val PUBLISHING_IN_PROCESS_KEY = "publishing_in_process"
    }
}