package com.a1danz.feature_telegram_bot.di

import com.a1danz.feature_telegram_bot.di.qualifiers.ApplicationUserId
import dagger.BindsInstance
import dagger.Component
import dagger.Provides

@Component
interface BotComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        @ApplicationUserId
        fun provideApplicationUserId(userId: String): Builder

        fun build(): BotComponent
    }

}