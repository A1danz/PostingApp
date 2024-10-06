package com.a1danz.feature_user_configurer.domain.exception

sealed class UserConfigurerException(msg: String, cause: Throwable) : Exception(msg, cause) {
    class LocalException(msg: String, cause: Throwable) : UserConfigurerException(msg, cause)
    class RemoteException(msg: String, cause: Throwable) : UserConfigurerException(msg, cause)
}