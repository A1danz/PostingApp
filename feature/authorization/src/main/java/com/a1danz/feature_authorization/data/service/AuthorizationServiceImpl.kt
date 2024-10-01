package com.a1danz.feature_authorization.data.service

import com.a1danz.common.ext.doOrThrow
import com.a1danz.feature_authorization.data.exceptionhandler.FirebaseExceptionHandlerDelegate
import com.a1danz.feature_authorization.di.scope.AuthorizationScope
import com.a1danz.feature_authorization.domain.exceptions.AuthException
import com.a1danz.feature_authorization.domain.model.AuthorizedUser
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AuthorizationScope
class AuthorizationServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val dispatcher: CoroutineDispatcher,
    private val firebaseExceptionHandlerDelegate: FirebaseExceptionHandlerDelegate,
) : AuthorizationService {

    override suspend fun signIn(email: String, password: String) {
        withContext(dispatcher) {
            doOrThrow(firebaseExceptionHandlerDelegate) {
                firebaseAuth.signInWithEmailAndPassword(email, password).await() ?: throw AuthException.UnknownException()
            }
        }
    }

    override suspend fun signUp(email: String, password: String, name: String) {
        withContext(dispatcher) {
            doOrThrow(firebaseExceptionHandlerDelegate) {
                val signUpResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                    ?: throw AuthException.UnknownException()

                val firebaseUser = signUpResult.user ?: throw AuthException.UnknownException()
                firebaseFirestore.collection("users").document(firebaseUser.uid).set(
                    hashMapOf(
                        "name" to name,
                        "telegram" to hashMapOf(
                            "chats" to hashMapOf<String, Any>()
                        )
                    )
                ).await()
            }
        }

    }

    override suspend fun signOut() {
        withContext(dispatcher) {
            firebaseAuth.signOut()
        }
    }

    override suspend fun hasUser(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun getUser(): AuthorizedUser? {
        return firebaseAuth.currentUser?.let { firebaseUser ->
            AuthorizedUser(firebaseUser.uid)
        }
    }
}