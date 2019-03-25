package com.arctouch.codechallenge.viewModel

import android.arch.lifecycle.*
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.repository.GenreRepository
import com.arctouch.codechallenge.repository.MovieRepository

class ViewModel(val genreRepository: GenreRepository, val movieRepository: MovieRepository) : ViewModel(), MovieViewModelImpl {

    var currentPage: Long = 0

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun fetchGenres() : LiveData<GenreResponse> {
        return genreRepository.fetchGenres()
    }

    override fun fetchMovies() : LiveData<ArrayList<Movie>> {
        currentPage++
        return movieRepository.fetchMovies(currentPage)
    }

    override fun fetchMovieDetails(movieId: Int) : LiveData<Movie> {
        return movieRepository.fetchMovieDetails(movieId.toLong())
    }



}
