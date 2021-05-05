package com.rappi.movies.presentation.base

import android.app.Application
import androidx.lifecycle.ViewModel
import com.rappi.movies.R
import com.rappi.movies.data.remote.exceptions.AppException

abstract class ViewModelBase(
    private val applicationContext: Application
) : ViewModel() {

    abstract fun defaultError(error: Throwable)

    fun handleError(error: Throwable) {
        if (error is AppException) {
            when (error.validationType) {
                AppException.Type.ERROR_NETWORK -> {
                    this.defaultError(
                        AppException(
                            error.validationType,
                            this.applicationContext.resources.getString(R.string.connection_error_description)
                        )
                    )
                }
            }
        } else {
            this.defaultError(error)
        }
    }
}