package com.a1danz.feature_settings.data.repository.tg

import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.feature_settings.domain.repository.TgRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TgRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : TgRepository {
    override suspend fun getChats(userId: String): List<TgChatInfo> {
        val doc = firestore.collection("users").document(userId).get().await()
        val tgData = doc.data?.get("telegram") as? Map<String, Any>
        val chats = tgData?.get("chats") as? Map<String, Any>

        val chatList = arrayListOf<TgChatInfo>()
        chats?.forEach { (key, value) ->
            val chatData = value as? Map<String, Any>
            chatData?.let {
                chatList.add(
                    TgChatInfo(
                        name = chatData["name"] as? String ?: return@let,
                        id = key.toLong(),
                        photo = chatData["photo"] as String
                    )
                )
            }
        }

        return chatList
    }
}