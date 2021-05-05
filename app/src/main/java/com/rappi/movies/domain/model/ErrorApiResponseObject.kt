package com.rappi.movies.domain.model

import com.google.gson.annotations.SerializedName

data class ErrorApiResponseObject(@SerializedName(value = "code", alternate = ["status_code"]) private val _code: Int?,
                                  @SerializedName(value = "message", alternate = ["error_description"]) private val _message: String?) {

    val code: Int
        get() = this._code ?: -1

    val message: String
        get() = this._message ?: "Unknown exception"
}