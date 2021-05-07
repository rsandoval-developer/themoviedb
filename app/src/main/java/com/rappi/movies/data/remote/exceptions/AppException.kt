package com.rappi.movies.data.remote.exceptions

class AppException @JvmOverloads constructor(
    val validationType: Type,
    val code: Int = 0,
    message: String = "Validation error.",
    cause: Throwable? = null
) : Throwable(message, cause) {

    enum class Type {
        ERROR_NETWORK,
        ERROR_GENERIC
    }
}