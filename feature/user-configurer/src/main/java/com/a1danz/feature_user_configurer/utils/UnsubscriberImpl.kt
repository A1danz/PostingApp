package com.a1danz.feature_user_configurer.utils

import com.a1danz.common.utils.Unsubscriber
import com.google.firebase.firestore.ListenerRegistration

class UnsubscriberImpl(
    private val listenerRegistration: ListenerRegistration
) : Unsubscriber {
    override fun unsubcribe() {
        listenerRegistration.remove()
    }
}