package com.a1danz.feature_settings.domain.model

class VkUserGroupsDomainModel (
    val userGroups: List<VkGroupDomainModel>
)

class VkGroupDomainModel(
    val id: Long,
    val name: String,
    val imgUrl: String
) {
    override fun toString(): String {
        return "$id: $name, $imgUrl"
    }
}
