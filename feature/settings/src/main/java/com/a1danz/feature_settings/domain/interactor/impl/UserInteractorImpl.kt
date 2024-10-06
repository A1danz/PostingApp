package com.a1danz.feature_settings.domain.interactor.impl

import com.a1danz.common.core.cryptography.Cryptographer
import com.a1danz.common.core.utils.Unsubscriber
import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.domain.model.VkGroupInfo
import com.a1danz.common.domain.model.VkUserInfo
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.domain.repository.TgRepository
import com.a1danz.feature_settings.domain.repository.VkRepository
import com.a1danz.feature_user_configurer.UserConfigurer
import com.vk.id.AccessToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserInteractorImpl @Inject constructor(
    private val user: User,
    private val userConfigurer: UserConfigurer,
    private val dispatcher: CoroutineDispatcher,

    private val vkRepository: VkRepository,

    private val cryptographer: Cryptographer,
    private val tgRepository: TgRepository,
) : UserInteractor {

    override fun getUserVkConfig(): VkConfig? {
        return user.config.vkConfig
    }

    override suspend fun saveVkToken(accessToken: AccessToken): VkConfig {
        return withContext(dispatcher) {
            VkConfig(
                userId = accessToken.userID,
                accessToken = accessToken.token,
                userInfo = VkUserInfo(
                    userImg = accessToken.userData.run {
                        photo200 ?: photo100 ?: photo50
                    },
                    fullName = accessToken.userData.firstName + " " + accessToken.userData.lastName
                ),
            ).also { vkConfig ->
                userConfigurer.updateUserConfig {
                    it.copy(vkConfig = vkConfig)
                }
            }
        }
    }

    override suspend fun getAllEditorGroups(): List<VkGroupInfo> {
        return withContext(dispatcher) {
            vkRepository.getUserEditGroups()
        }
    }

    override suspend fun updateGroups(groups: List<VkGroupInfo>) {
        withContext(dispatcher) {
            userConfigurer.updateVkConfig { vkConfig ->
                vkConfig.copy(userGroups = groups)
            }
        }
    }

    override suspend fun clearVkToken() {
        withContext(dispatcher) {
            userConfigurer.clearVkConfig()
        }
    }

    override fun getTgUserConfig(): TgConfig? {
        return user.config.tgConfig
    }

    override fun getTgLinkedCode(): String {
        return cryptographer.encrypt(user.uId)
    }

    override fun listenTgUpdates(
        tgInitializedInRemote: MutableStateFlow<Boolean?>
    ): Unsubscriber {
        return userConfigurer.listenTgUpdate(tgInitializedInRemote)
    }

    override suspend fun initTgConfig(): TgConfig {
        return withContext(dispatcher) {
            userConfigurer.initTgConfig()
        }
    }

    override suspend fun getAllChatsWithBot(): List<TgChatInfo> {
        return withContext(dispatcher) {
            tgRepository.getChats(user.uId)
        }
    }

    override suspend fun updateSelectedChats(chats: List<TgChatInfo>) {
        withContext(dispatcher) {
            userConfigurer.updateTgConfig {
                it.copy(selectedChats = chats)
            }
        }
    }

    override suspend fun clearTgData() {
        withContext(dispatcher) {
            userConfigurer.clearTgConfig()
        }
    }
}