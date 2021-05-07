package com.rappi.movies.data.remote.exceptions

import com.rappi.movies.data.remote.response.ErrorApiResponseObject
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

data class ApiResponseHandler @Inject constructor(private val retrofit: Retrofit) {

    fun <T> handle(response: Response<T>): Observable<T> {
        if (response.isSuccessful) {
            val body = response.body()
            return if (body != null) {
                Observable.just(body)
            } else {
                Observable.error(Throwable("Response body is null"))
            }
        } else {
            val errorBody = response.errorBody()
            return if (errorBody != null) {
                val converter: Converter<ResponseBody, ErrorApiResponseObject> =
                    this.retrofit.responseBodyConverter(
                        ErrorApiResponseObject::class.java,
                        emptyArray()
                    )
                val apiError = converter.convert(errorBody)
                if (apiError != null) {
                    Observable.error(
                        AppException(
                            AppException.Type.ERROR_GENERIC,
                            apiError.code,
                            apiError.message
                        )
                    )
                } else {
                    Observable.error(Throwable("Unable to parse error body"))
                }
            } else {
                Observable.error(Throwable("Error body is null"))
            }
        }
    }
}