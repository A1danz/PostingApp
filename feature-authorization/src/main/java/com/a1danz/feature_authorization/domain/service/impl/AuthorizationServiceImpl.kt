package com.a1danz.feature_authorization.domain.service.impl

import android.util.Log
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_authorization.domain.service.exceptions.AuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthorizationServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : AuthorizationService {
    override suspend fun signIn(email: String, password: String) {

        firebaseAuth.signInWithEmailAndPassword(email, password).await() ?: throw AuthException()
    }

    override suspend fun signUp(email: String, password: String, name: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            ?: throw AuthException()
        val fbUser = getUser()
        firebaseFirestore.collection("users").document(fbUser.uid).set(
            hashMapOf(
                "name" to name,
                "telegram" to hashMapOf(
                    "chats" to hashMapOf<String, Any>()
                )
            )
        )
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun hasUser(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun getUser(): FirebaseUser {
        return firebaseAuth.currentUser ?: throw IllegalStateException("user not found")
    }
}