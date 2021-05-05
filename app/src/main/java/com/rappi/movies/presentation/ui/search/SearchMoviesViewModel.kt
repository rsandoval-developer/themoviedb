package com.rappi.movies.presentation.ui.search

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.rappi.movies.BuildConfig
import com.rappi.movies.domain.model.Movie
import com.rappi.movies.domain.usecases.ParamsSearchMovies
import com.rappi.movies.domain.usecases.SearchMoviesUseCase
import com.rappi.movies.presentation.base.Resource
import com.rappi.movies.presentation.base.ViewModelBase

class SearchMoviesViewModel @ViewModelInject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    application: Application
) :
    ViewModelBase(application) {

    val resource = MutableLiveData<Resource<List<Movie>>>()

    fun searchMovies(
        query: String
    ) {
        this.resource.value = Resource.Loading(true)
        this.searchMoviesUseCase.execute(
            params = ParamsSearchMovies(BuildConfig.API_KEY, query),
            onSuccess = ::handleMovies,
            onError = ::handleError
        )
    }

    private fun handleMovies(counters: List<Movie>) {
        this.resource.value = Resource.Loading(false)
        this.resource.value = Resource.Success(counters)
    }

    override fun defaultError(error: Throwable) {
        this.resource.value = Resource.Loading(false)
        this.resource.value = Resource.Failure(error)
    }

    override fun onCleared() {
        this.searchMoviesUseCase.dispose()
        super.onCleared()
    }
}