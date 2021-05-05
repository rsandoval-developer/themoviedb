package com.rappi.movies.di

import com.rappi.movies.data.datasourceimplementations.MoviesApiDataSourceImp
import com.rappi.movies.data.datasourceimplementations.MoviesDatabaseDataSourceImpl
import com.rappi.movies.data.repositoryimplementations.MoviesRepositoryImp
import com.rappi.movies.domain.datasource.MoviesApiDataSource
import com.rappi.movies.domain.datasource.MoviesDatabaseDataSource
import com.rappi.movies.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    abstract fun provideMoviesDataSource(moviesDatabaseDataSourceImpl: MoviesDatabaseDataSourceImpl): MoviesDatabaseDataSource

    @Binds
    abstract fun provideMoviesApiDataSource(moviesApiDataSourceImp: MoviesApiDataSourceImp): MoviesApiDataSource

    @Binds
    abstract fun provideMoviesRepository(moviesRepositoryImp: MoviesRepositoryImp): MoviesRepository

}