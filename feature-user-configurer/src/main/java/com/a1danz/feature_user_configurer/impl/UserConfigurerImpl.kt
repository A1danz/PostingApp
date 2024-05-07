package com.a1danz.feature_user_configurer.impl

import android.util.Log
import com.a1danz.common.core.utils.Unsubscriber
import com.a1danz.common.domain.UserModelDelegate
import com.a1danz.common.domain.model.Config
import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.TgUserInfo
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.domain.model.VkGroupInfo
import com.a1danz.feature_user_configurer.UserConfigurer
import com.a1danz.feature_user_configurer.repo.UserRepository
import com.a1danz.feature_user_configurer.utils.UnsubscriberImpl
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.getField
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class UserConfigurerImpl @Inject constructor(
    private val userModelDelegate: UserModelDelegate,
    private val userRepository: UserRepository,
    private val firestore: FirebaseFirestore
) : UserConfigurer {
    private val user: User
        get() = userModelDelegate.user ?: throw IllegalStateException("User doesn't authorized")

    override suspend fun saveUser(user: User) {
        userRepository.saveUser(user)
    }

    override suspend fun initUser() {
        userModelDelegate.user = userRepository.getUser()
    }

    override suspend fun updateUserDelegate(userId: String) {
        var user: User? = userRepository.getUser()
        if (user == null) {
            val userDoc = firestore.collection("users").document(userId).get().await()
            val name = userDoc.getString("name") ?: "User"
            user = User(userId, name = name)
            saveUser(user)
        }
        userModelDelegate.user = user
    }

    override suspend fun hasUserVkToken(): Boolean {
        return user.config.vkConfig != null
    }

    override suspend fun updateUserConfig(update: (Config) -> Config) {
        userRepository.updateConfig(update)
        user.config = update(user.config)
    }

    override suspend fun updateVkConfig(update: (VkConfig) -> VkConfig) {
        userRepository.updateVkConfig(update)
        user.config.vkConfig?.let(update)
    }

    override suspend fun clearVkConfig() {
        user.config.vkConfig = null
        userRepository.clearVkConfig()
    }

    override suspend fun getSelectedGroups(): List<VkGroupInfo> {
        return user.config.vkConfig?.userGroups ?: emptyList()
    }

    override suspend fun hasUserTgConfig(): Boolean {
        return user.config.tgConfig != null
    }

    override suspend fun updateTgConfig(update: (TgConfig) -> TgConfig) {
        Log.d("UC UPDATING", "BEFORE - ${user.config.tgConfig}")
        userRepository.updateTgConfig(update)
        if (user.config.tgConfig != null) {
            user.config.tgConfig = update(user.config.tgConfig!!)
        }
//        user.config.tgConfig?.let(update) - why this not working?
        Log.d("UPDATED CONFIG", "AFTER - ${user.config.tgConfig}")
    }

    override suspend fun listenTgUpdate(listenFlow: MutableStateFlow<Boolean?>): Unsubscriber {
        val document = firestore.collection("users").document(user.uId)

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

    override suspend fun initTgToken() {
        val doc = firestore.collection("users").document(user.uId).get().await()
        val tgUsername = doc.getString("telegram.username")
            ?: throw IllegalStateException("tg username not found")
        val tgUserId = doc.getLong("telegram.tg_user_id")
            ?: throw IllegalStateException("tg userId not found")
        val tgUserPhoto = doc.getString("telegram.photo")
            ?: throw IllegalStateException("tg userPhoto not found")

        val tgUserInfo = TgUserInfo(
            tgUserId = tgUserId,
            tgUsername = tgUsername,
            tgUserPhoto = tgUserPhoto
        )

//        val chats = doc.data?.get("telegram.chats") as? Map<String, Any>
//
//        val chatsList: ArrayList<TgChatInfo> = ArrayList()
//
//        chats?.forEach { (key, value) ->
//            val chatData = value as? Map<String, Any>
//            chatData?.let {
//                chatsList.add(
//                    TgChatInfo(
//                        name = chatData["name"] as? String ?: return@let,
//                        id = key.toLong(),
//                        photo = chatData["photo"] as String
//                    )
//                )
//            }
//        }

        val config = TgConfig(tgUserInfo = tgUserInfo)
        userRepository.updateConfig {
            it.copy(tgConfig = config)
        }

        user.config.tgConfig = config
    }

    override suspend fun clearTgConfig() {
        user.config.tgConfig = null
        userRepository.clearTgConfig()
        val clearedTgData = HashMap<String, Any>()
        clearedTgData.put("telegram", HashMap<String, Any>().apply {
            put("chats", HashMap<String, Any>())
        })
        firestore.collection("users").document(user.uId).update(clearedTgData)
    }
}
