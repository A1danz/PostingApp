package com.a1danz.feature_user_configurer.data.exception.handler

import android.util.Log
import com.a1danz.common.exception.ExceptionHandlerDelegate
import com.a1danz.feature_user_configurer.domain.exception.UserConfigurerException
import javax.inject.Inject

class RemoteExceptionHandlerDelegate @Inject constructor() : ExceptionHandlerDelegate {
    override fun handleException(ex: Throwable): Throwable {
        Log.e("ERR", "Remote exception", ex)
        return UserConfigurerException.RemoteException("remote exception", ex)
    }
}