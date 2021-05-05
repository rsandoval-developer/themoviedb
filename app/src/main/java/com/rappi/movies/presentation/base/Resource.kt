package com.rappi.movies.presentation.base

sealed class Resource<out T> {
    data class Loading(val visible: Boolean) : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val error: Throwable) : Resource<Nothing>()
}