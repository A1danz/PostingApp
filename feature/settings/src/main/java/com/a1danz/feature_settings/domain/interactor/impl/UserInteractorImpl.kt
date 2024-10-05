package com.a1danz.feature_settings.domain.interactor.impl

import android.util.Log
import com.a1danz.common.core.cryptography.Cryptographer
import com.a1danz.common.domain.model.TgChatInfo
import com.a1danz.common.domain.model.TgConfig
import com.a1danz.common.domain.model.TgUserInfo
import com.a1danz.common.domain.model.User
import com.a1danz.common.domain.model.VkConfig
import com.a1danz.common.domain.model.VkGroupInfo
import com.a1danz.common.domain.model.VkUserInfo
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.domain.mapper.TgChatsDomainMapper
import com.a1danz.feature_settings.domain.mapper.VkUserGroupsDomainMapper
import com.a1danz.feature_settings.domain.repository.TgRepository
import com.a1danz.feature_settings.domain.repository.VkRepository
import com.a1danz.feature_settings.presentation.model.TgChatUiModel
import com.a1danz.feature_settings.presentation.model.TgChatsUiModel
import com.a1danz.feature_settings.presentation.model.TgUserInfoUiModel
import com.a1danz.feature_settings.presentation.model.VkUserGroupUiModel
import com.a1danz.feature_settings.presentation.model.VkUserGroupsUiModel
import com.a1danz.feature_settings.presentation.model.VkUserInfoUiModel
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
    private val userGroupsDomainMapper: VkUserGroupsDomainMapper,

    private val cryptographer: Cryptographer,
    private val tgRepository: TgRepository,
    private val tgChatsDomainMapper: TgChatsDomainMapper
) : UserInteractor {

    override fun getUserVkConfig(): VkConfig? {
        return user.config.vkConfig
    }

    override fun getUserVkInfoUiModel(vkConfig: VkConfig): VkUserInfoUiModel {
        return VkUserInfoUiModel(
            vkConfig.userInfo.fullName,
            vkConfig.userGroups.map { group -> group.groupName },
            vkConfig.userInfo.userImg
        )
    }

    override fun hasUserVkToken(): Boolean {
        return user.config.vkConfig != null
    }


    override suspend fun saveVkToken(accessToken: AccessToken) {
        withContext(dispatcher) {
            userConfigurer.updateUserConfig {
                Log.d(
                    "PHOTOS",
                    "${accessToken.userData.photo50}, ${accessToken.userData.photo100}, ${accessToken.userData.photo200}"
                )
                var photo = accessToken.userData.photo200
                if (photo == null) photo = accessToken.userData.photo100
                if (photo == null) photo = accessToken.userData.photo50
                it.copy(
                    vkConfig = VkConfig(
                        userId = accessToken.userID,
                        accessToken = accessToken.token,
                        userInfo = VkUserInfo(
                            userImg = photo,
                            fullName = accessToken.userData.firstName + " " + accessToken.userData.lastName
                        ),
                    )
                )
            }
        }
    }

    override suspend fun getUserGroups(): VkUserGroupsUiModel {
        return withContext(dispatcher) {
            val groupsUiModel =
                userGroupsDomainMapper.mapToUiModel(vkRepository.getUserEditGroups())
            val groups = HashSet<VkUserGroupUiModel>(groupsUiModel.groups)
//            val selectedGroups = HashSet<VkGroupInfo>(userConfigurer.getSelectedGroups())
//            selectedGroups.forEach {selectedGroup ->
//                groups.find {
//                    selectedGroup.groupId == it.groupId
//                }?.isChosen = true
//            }

            VkUserGroupsUiModel(groups.toList().sortedBy { it.isChosen })

        }
    }

    override fun getVkUserInfo(): VkUserInfo {
        return user.config.vkConfig?.userInfo ?: throw IllegalStateException("User unauthorized")
    }

    override suspend fun addGroup(vkGroupInfo: VkGroupInfo) {
        withContext(dispatcher) {
            userConfigurer.updateVkConfig { vkConfig ->
                vkConfig.userGroups.add(vkGroupInfo)
                vkConfig.copy()
            }
        }
    }

    override suspend fun removeGroup(vkGroupInfo: VkGroupInfo) {
        withContext(dispatcher) {
            userConfigurer.updateVkConfig { vkConfig ->
                vkConfig.userGroups.remove(vkGroupInfo)
                vkConfig.copy()
            }
        }
    }

    override fun getUserSelectedVkGroups(): List<VkGroupInfo> {
        return user.config.vkConfig?.userGroups ?: listOf()
    }

    override suspend fun clearVkToken() {
        withContext(dispatcher) {
            userConfigurer.clearVkConfig()
        }
    }

    override fun hasUserTgConfig(): Boolean {
        return user.config.tgConfig != null
    }

    override fun getTgSelectedChats(): List<TgChatInfo> {
        return user.config.tgConfig?.selectedChats ?: listOf()
    }

    override suspend fun saveUserTgConfig(tgUserInfo: TgUserInfo) {
        withContext(dispatcher) {
            userConfigurer.updateUserConfig { config ->
                config.copy(tgConfig = TgConfig(tgUserInfo = tgUserInfo))
            }
        }
    }

    override fun getTgLinkedCode(): String {
        return cryptographer.encrypt(user.uId)
    }

    override suspend fun listenTgUpdates(
        listenFlow: MutableStateFlow<Boolean?>,
        tokenInitializedFlow: MutableStateFlow<Boolean?>
    ) {
        withContext(dispatcher) {
            val unsubscriber = userConfigurer.listenTgUpdate(listenFlow)
            listenFlow.collect {
                if (it == true) {
                    unsubscriber.unsubcribe()
                    initTgToken()
                    tokenInitializedFlow.emit(true)
                }
            }
        }
    }

    override fun getTgUserInfo(): TgUserInfo {
        return user.config.tgConfig?.tgUserInfo
            ?: throw IllegalStateException("Telegram user not initialized")
    }

    override suspend fun initTgToken() {
        withContext(dispatcher) {
            userConfigurer.initTgConfig()
        }
    }

    override suspend fun getChats(): TgChatsUiModel {
        return withContext(dispatcher) {
            val allChats = HashSet<TgChatUiModel>(
                tgChatsDomainMapper.mapToUiModel(tgRepository.getChats()).chats
            )

            Log.d("UI CHATS", allChats.toString())
            val selectedChats = HashSet<TgChatInfo>(user.config.tgConfig?.selectedChats ?: emptyList())


            Log.d("SELECTED UI CHATS", selectedChats.toString())
            selectedChats.forEach {
                allChats.find { chat ->
                    chat.chatId == it.id
                }?.isSelected = true
            }

            TgChatsUiModel(allChats.toList())
        }


    }

    override suspend fun addSelectedChat(chatModel: TgChatUiModel) {
        Log.d("ADDING CHAT USINT", "ADDING CHAT IN USERINTERACTOR - ${chatModel.chatId}")
        withContext(dispatcher) {
            userConfigurer.updateTgConfig {
                val selectedChats = it.selectedChats.toMutableList()
                Log.d("CHATS BEFORE UINT", "CHATS IN USERINTERACTOR BEFORE ADDING - $selectedChats")
                selectedChats.add(TgChatInfo(chatModel.chatName, chatModel.chatId, chatModel.chatPhoto))
                Log.d("CHATS AFTER UINT", "CHATS IN USERINTERACTOR AFTER ADDING - $selectedChats")
                it.copy(selectedChats = selectedChats)
            }
        }
    }

    override suspend fun removeSelectedChat(chatModel: TgChatUiModel) {
        Log.d("REMOVING USINT", "REMOVING CHAT IN USERINTERACTOR - ${chatModel.chatId}")
        withContext(dispatcher) {
            userConfigurer.updateTgConfig {
                val selectedChats = it.selectedChats.toMutableList()
                Log.d("CHATS BEFORE UINT", "CHATS IN USERINTERACTOR BEFORE REMOVING- $selectedChats")
                val removeResult = selectedChats.removeIf { chat ->
                    chat.id == chatModel.chatId
                }
                Log.d("CHATS AFTER UINT", "CHATS IN USERINTERACTOR AFTER REMOVING- $selectedChats")

                Log.d("REMOVE RESULT", selectedChats.toString())
                it.copy(selectedChats = selectedChats)
            }
        }

    }

    override fun getTgUserInfoUiModel(tgConfig: TgConfig): TgUserInfoUiModel {
        return TgUserInfoUiModel(
            username = tgConfig.tgUserInfo.tgUsername,
            chatNames = tgConfig.selectedChats.map { it.name },
            photo = tgConfig.tgUserInfo.tgUserPhoto
        )
    }

    override fun getTgUserConfig(): TgConfig? {
        return user.config.tgConfig
    }

    override suspend fun unlinkTg() {
        withContext(dispatcher) {
            userConfigurer.clearTgConfig()
        }
    }
}