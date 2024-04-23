package com.a1danz.common.domain.model

data class VkAccessToken(
    val accessToken: String,
    val userId: Long
) {
    companion object {
        fun toFirebaseObject(accessToken: VkAccessToken): HashMap<String, Any> {
            val vkTokenData = hashMapOf(
                "access_token" to accessToken.accessToken,
                "vk_user_id" to accessToken.userId
            )

            return hashMapOf("vk_data" to vkTokenData)
        }

        fun fromFirebaseObject(vkData: Map<*, *>?): VkAccessToken? {
            if (vkData != null) {
                val tokenValue = vkData["access_token"] as? String
                val vkUserId = vkData["vk_user_id"] as? String
                if (tokenValue != null && vkUserId != null) {
                    return VkAccessToken(tokenValue, vkUserId.toLong())
                }
            }
            return null
        }
    }
}