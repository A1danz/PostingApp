package com.a1danz.feature_settings.data.repository.tg

import android.util.Log
import com.a1danz.feature_settings.domain.model.TgChatDomainModel
import com.a1danz.feature_settings.domain.model.TgChatsDomainModel
import com.a1danz.feature_settings.domain.repository.TgRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TgRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userId: String
) : TgRepository {
    override suspend fun getChats(): TgChatsDomainModel {
        val doc = firestore.collection("users").document(userId).get().await()
        val tgData = doc.data?.get("telegram") as? Map<String, Any>
        val chats = tgData?.get("chats") as? Map<String, Any>
        Log.d("CHATS IN REPO", chats.toString())

        val chatList = arrayListOf<TgChatDomainModel>()
        chats?.forEach { (key, value) ->
            val chatData = value as? Map<String, Any>
            chatData?.let {
                chatList.add(
                    TgChatDomainModel(
                        chatName = chatData["name"] as? String ?: return@let,
                        chatId = key.toLong(),
                        chatPhoto = chatData["photo"] as String
                    )
                )
            }
        }

        return TgChatsDomainModel(chats = chatList)
    }
}