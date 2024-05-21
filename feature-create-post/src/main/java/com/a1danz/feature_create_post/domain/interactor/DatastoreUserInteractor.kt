package com.a1danz.feature_create_post.domain.interactor

interface DatastoreUserInteractor {
    suspend fun writePublishingInProcess(inProcess: Boolean)
    suspend fun getPublishingInProcess(): Boolean
}