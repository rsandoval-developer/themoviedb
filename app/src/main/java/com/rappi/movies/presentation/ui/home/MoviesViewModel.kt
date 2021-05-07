package com.rappi.movies.presentation.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rappi.movies.data.remote.request.GetMoviesRequestParams
import com.rappi.movies.domain.model.Movie
import com.rappi.movies.domain.usecases.GetMoviesUseCase
import com.rappi.movies.presentation.base.Resource

class MoviesViewModel @ViewModelInject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) :
    ViewModel() {

    val resource = MutableLiveData<Resource<List<Movie>>>()

    fun getMovies(
        getMoviesRequestParams: GetMoviesRequestParams
    ) {
        if (!getMoviesRequestParams.isPagination) {
            resource.value = Resource.Loading(true)
        }
        getMoviesUseCase.execute(
            params = getMoviesRequestParams,
            onSuccess = ::handleMovies,
            onError = ::handleError
        )
    }

    private fun handleMovies(counters: List<Movie>) {
        resource.value = Resource.Loading(false)
        resource.value = Resource.Success(counters)
    }

    private fun handleError(error: Throwable) {
        resource.value = Resource.Loading(false)
        resource.value = Resource.Failure(error)
    }

    override fun onCleared() {
        getMoviesUseCase.dispose()
        super.onCleared()
    }
}