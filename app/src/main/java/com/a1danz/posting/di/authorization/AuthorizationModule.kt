package com.a1danz.posting.di.authorization

import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.common.domain.model.User
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.domain.service.impl.AuthorizationServiceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
class AuthorizationModule {
    @ApplicationScope
    @Provides
    fun provideAuthorizationService(authorization: FirebaseAuth, firestore: FirebaseFirestore) : AuthorizationService
        = AuthorizationServiceImpl(authorization, firestore)
}