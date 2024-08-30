package com.a1danz.feature_user_configurer.repo.impl

import android.util.Log
import com.a1danz.common.core.utils.Unsubscriber
import com.a1danz.common.domain.model.TgUserInfo
import com.a1danz.common.domain.model.User
import com.a1danz.feature_user_configurer.repo.UserFirestoreRepository
import com.a1danz.feature_user_configurer.utils.UnsubscriberImpl
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserFirestoreRepository {
    override suspend fun getUser(userId: String): User {
        val userDoc = firestore.collection("users").document(userId).get().await()
        val name = userDoc.getString("name") ?: "User"
        return User(userId, name = name)
    }

    override suspend fun listenTgUpdate(listenFlow: MutableStateFlow<Boolean?>, userId: String): Unsubscriber {
        val document = firestore.collection("users").document(userId)

        val listener = document.addSnapshotListener { snapshot, error ->
            if (error != null) Log.e("ERROR", error.toString())
            if (snapshot != null && snapshot.exists()) {
                Log.d("RECEIVED SNAPSHOT", snapshot.data.toString())
                if (snapshot.data?.contains("telegram") == true) {
                    document.get().addOnSuccessListener {
                        if (it.getLong("telegram.tg_user_id") != null) {
                            listenFlow.value = true
                        }
                    }
                }
            }
        }

        return UnsubscriberImpl(listener)
    }

    override suspend fun getUserTgInfo(userId: String): TgUserInfo? {
        val doc = firestore.collection("users").document(userId).get().await()
        val tgUsername = doc.getString("telegram.username")
            ?: return null
        val tgUserId = doc.getLong("telegram.tg_user_id")
            ?: return null
        val tgUserPhoto = doc.getString("telegram.photo")
            ?: return null

        return TgUserInfo(
            tgUserId = tgUserId,
            tgUsername = tgUsername,
            tgUserPhoto = tgUserPhoto
        )
    }

    override suspend fun clearTgInformation(userId: String) {
        val clearedTgData = HashMap<String, Any>()
        clearedTgData.put("telegram", HashMap<String, Any>().apply {
            put("chats", HashMap<String, Any>())
        })
        firestore.collection("users").document(userId).update(clearedTgData)
    }
}