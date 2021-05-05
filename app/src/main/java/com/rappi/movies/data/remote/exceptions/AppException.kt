package com.rappi.movies.data.remote.exceptions

class AppException @JvmOverloads constructor(
    val validationType: Type,
    message: String = "Validation error.",
    cause: Throwable? = null
) : Throwable(message, cause) {

    enum class Type {
        ERROR_NETWORK
    }
}