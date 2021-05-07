package com.rappi.movies.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorApiResponseObject(
    @SerializedName(value = "code", alternate = ["status_code"]) private val _code: Int?,
    @SerializedName(
        value = "message",
        alternate = ["error_description"]
    ) private val _message: String?
) {

    val code: Int = this._code ?: -1

    val message: String = this._message ?: "Unknown exception"
}