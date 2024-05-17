package com.a1danz.vk_page_publisher.di

import com.a1danz.vk_page_publisher.data.remote.VkPagePublisherApi
import com.a1danz.vk_page_publisher.data.remote.di.DomainModule
import dagger.Component

@Component(modules = [
    DomainModule::class
])
interface VkPagePublisherComponent {
    fun vkApi(): VkPagePublisherApi
}