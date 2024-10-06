package com.a1danz.feature_settings.presentation.utils

import com.a1danz.common.exception.ExceptionConverter
import com.a1danz.common.presentation.model.ReadableError
import com.a1danz.common.presentation.model.Text
import com.a1danz.feature_settings.R
import com.a1danz.feature_user_configurer.domain.exception.UserConfigurerException
import javax.inject.Inject

class ExceptionConverter @Inject constructor() : ExceptionConverter {
    override fun convertException(ex: Throwable): ReadableError {
        return if (ex !is UserConfigurerException) ReadableError.Default()
        else {
            when(ex) {
                is UserConfigurerException.LocalException -> {
                    ReadableError.Custom(
                        Text.Resource(R.string.error_when_work_with_local_storage)
                    )
                }
                is UserConfigurerException.RemoteException -> {
                    ReadableError.Custom(
                        Text.Resource(R.string.error_when_work_with_remote_storage)
                    )
                }
            }
        }
    }
}