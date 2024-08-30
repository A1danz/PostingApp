package com.a1danz.feature_settings.di.module.social_media.tg

import com.a1danz.common.core.cryptography.Cryptographer
import com.a1danz.common.domain.model.User
import com.a1danz.feature_settings.data.repository.tg.TgRepositoryImpl
import com.a1danz.feature_settings.domain.interactor.UserInteractor
import com.a1danz.feature_settings.domain.interactor.tg.TgUserInteractor
import com.a1danz.feature_settings.domain.repository.TgRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [InteractorBinder::class])
class TgModule {

    @Provides
    fun provideCryptographer(): Cryptographer = Cryptographer()

    @Provides
    fun provideTgRepository(firestore: FirebaseFirestore, user: User) : TgRepository =
        TgRepositoryImpl(firestore, user.uId)
}

@Module
interface InteractorBinder {
    @Binds
    fun bindIteractor_to_TgInteractor(userInteractor: UserInteractor): TgUserInteractor
}