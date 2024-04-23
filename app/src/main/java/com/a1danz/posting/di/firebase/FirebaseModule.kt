package com.a1danz.posting.di.firebase

import com.a1danz.common.di.scope.ApplicationScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @ApplicationScope
    @Provides
    fun provideFirestore() = Firebase.firestore

    @ApplicationScope
    @Provides
    fun provideAuth() = Firebase.auth

}