package com.rappi.movies.data.remote.exceptions

class RappiException @JvmOverloads constructor(val code: Int,
                                               message: String,
                                               cause: Throwable? = null) : Throwable(message, cause)