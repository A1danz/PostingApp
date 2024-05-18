package com.a1danz.vk_publisher.di

import com.a1danz.feature_vk_api.data.remote.VkApi
import com.a1danz.feature_vk_api.domain.VkApiRepository
import com.a1danz.vk_publisher.data.remote.di.NetworkModule
import com.a1danz.vk_publisher.data.remote.repository.VkApiRepositoryImpl
import com.a1danz.vk_publisher.di.qualifiers.VkAccessToken
import com.a1danz.vk_publisher.domain.VkPublisher
import dagger.BindsInstance
import dagger.Component

@Component(modules = [
    NetworkModule::class
])
interface VkPublisherComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun vkAccessToken(@VkAccessToken vkAccessToken: String): Builder

        fun build(): VkPublisherComponent
    }

    fun vkPublisherFactory(): VkPublisher.Factory
}