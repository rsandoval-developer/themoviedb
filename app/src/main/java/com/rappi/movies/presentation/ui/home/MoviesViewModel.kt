package com.rappi.movies.presentation.ui.home

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.rappi.movies.data.remote.request.GetMoviesRequestParams
import com.rappi.movies.domain.model.Movie
import com.rappi.movies.domain.usecases.GetMoviesUseCase
import com.rappi.movies.presentation.base.Resource
import com.rappi.movies.presentation.base.ViewModelBase

class MoviesViewModel @ViewModelInject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    application: Application
) :
    ViewModelBase(application) {

    val moviesViewModelState = MutableLiveData<Resource<List<Movie>>>()

    fun getMovies(
        getMoviesRequestParams: GetMoviesRequestParams
    ) {
        if (!getMoviesRequestParams.isPagination) {
            this.moviesViewModelState.value = Resource.Loading(true)
        }
        this.getMoviesUseCase.execute(
            params = getMoviesRequestParams,
            onSuccess = ::handleMovies,
            onError = ::handleError
        )
    }

    private fun handleMovies(counters: List<Movie>) {
        this.moviesViewModelState.value = Resource.Loading(false)
        this.moviesViewModelState.value = Resource.Success(counters)
    }

    override fun defaultError(error: Throwable) {
        this.moviesViewModelState.value = Resource.Loading(false)
        this.moviesViewModelState.value = Resource.Failure(error)
    }

    override fun onCleared() {
        this.getMoviesUseCase.dispose()
        super.onCleared()
    }
}