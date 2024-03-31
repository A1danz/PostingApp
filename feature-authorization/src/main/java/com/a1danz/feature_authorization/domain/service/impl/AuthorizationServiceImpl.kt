package com.a1danz.feature_authorization.domain.service.impl

import android.app.Activity
import android.util.Log
import com.a1danz.feature_authorization.domain.model.User
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.domain.service.exceptions.AuthException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthorizationServiceImpl: AuthorizationService {
    override val currentUser: Flow<User?>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                println("TEST TAG - ${auth.currentUser}")
                this.trySend(auth.currentUser?.let { User(it.email.orEmpty(), it.uid) })
            }

            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }

    override suspend fun signIn(email: String, password: String){
        Firebase.auth.signInWithEmailAndPassword(email, password).await() ?: throw AuthException()
    }

    override suspend fun signUp(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await() ?: throw AuthException()
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun hasUser() : Boolean  {
        return Firebase.auth.currentUser != null
    }

}