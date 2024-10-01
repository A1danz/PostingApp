package com.a1danz.feature_authorization.di.holder

import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureApiHolder
import com.a1danz.common.di.scope.ApplicationScope
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.di.DaggerAuthComponent
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

@ApplicationScope
class AuthorizationFeatureHolder @Inject constructor(
    private val authorizationRouter: AuthorizationRouter,
    private val resourceManager : ResourceManager,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
) : FeatureApiHolder() {

    val authorizationComponent by lazy {
        DaggerAuthComponent.builder()
            .router(authorizationRouter)
            .resourceManager(resourceManager)
            .firebaseAuth(firebaseAuth)
            .firebaseFirestore(firebaseFirestore)
            .build()
    }

    override fun getComponent(): Any {
        return authorizationComponent
    }
}
