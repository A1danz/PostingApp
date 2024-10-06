package com.a1danz.feature_user_configurer.data.repository

import android.util.Log
import com.a1danz.common.core.utils.Unsubscriber
import com.a1danz.common.domain.model.TgUserInfo
import com.a1danz.common.domain.model.User
import com.a1danz.common.ext.doOrLog
import com.a1danz.common.ext.doOrThrow
import com.a1danz.feature_user_configurer.data.exception.handler.RemoteExceptionHandlerDelegate
import com.a1danz.feature_user_configurer.domain.repository.UserRemoteRepository
import com.a1danz.feature_user_configurer.utils.UnsubscriberImpl
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserFirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dispatcher: CoroutineDispatcher,
    private val remoteExceptionHandlerDelegate: RemoteExceptionHandlerDelegate,
) : UserRemoteRepository {

    override suspend fun getUser(userId: String): User {
        return withContext(dispatcher) {
            doOrThrow(remoteExceptionHandlerDelegate) {
                firestore.collection("users")
                    .document(userId)
                    .get()
                    .await()
                    .let { userDoc ->
                        User(uId = userId, name = userDoc.getString("name") ?: "User")
                    }
            }
        }
    }

    override fun listenTgUpdate(telegramInitializedState: MutableStateFlow<Boolean?>, userId: String): Unsubscriber {
        val document = firestore.collection("users").document(userId)

        return UnsubscriberImpl(
            listenerRegistration = document.addSnapshotListener { snapshot, error ->
                if (error != null) Log.e("ERROR", error.toString())
                if (snapshot != null && snapshot.exists()) {
                    if (snapshot.data?.contains("telegram") == true) {
                        document.get().addOnSuccessListener {
                            if (it.getLong("telegram.tg_user_id") != null) {
                                telegramInitializedState.value = true
                            }
                        }
                    }
                }
            }
        )
    }

    override suspend fun getUserTgInfo(userId: String): TgUserInfo {
        return withContext(dispatcher) {
            doOrThrow(remoteExceptionHandlerDelegate) {
                val doc = firestore.collection("users").document(userId).get().await()
                val tgUsername = doc.getString("telegram.username")
                    ?: throw IllegalStateException("Field {telegram.username} not found for $userId")
                val tgUserId = doc.getLong("telegram.tg_user_id")
                    ?: throw IllegalStateException("Field {telegram.tg_user_id} not found for $userId")
                val tgUserPhoto = doc.getString("telegram.photo")
                    ?: throw IllegalStateException("Field {telegram.photo} not found for $userId")

                TgUserInfo(
                    tgUserId = tgUserId,
                    tgUsername = tgUsername,
                    tgUserPhoto = tgUserPhoto
                )
            }

        }
    }

    override suspend fun clearTgInformation(userId: String) {
        withContext(dispatcher) {
            doOrThrow(remoteExceptionHandlerDelegate) {
                firestore.collection("users").document(userId).update(
                    hashMapOf<String, Any>(
                        "telegram" to hashMapOf(
                            "chats" to hashMapOf<String, Any>()
                        )
                    )
                )
            }
        }
    }
}