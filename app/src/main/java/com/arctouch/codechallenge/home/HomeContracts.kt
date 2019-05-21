package com.arctouch.codechallenge.home

import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.Single

class HomeContracts {
    interface View {
        fun showMovies(movies: List<Movie>)
        fun showSingleMovie()
        fun showError(message: String)
    }

    interface Presenter {
        fun onMovieClicked()
        fun fetchMoviesList(): Single<UpcomingMoviesResponse>
        fun onDestroy()
    }

    interface Interactor {
        fun fetchMoviesData(): Single<UpcomingMoviesResponse>
    }

}