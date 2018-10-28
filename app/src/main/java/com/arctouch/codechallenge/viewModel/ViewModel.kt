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

    fun fetchGenres() : LiveData<GenreResponse> {
        return genreRepository.fetchGenres()
    }

    fun fetchMovies() : LiveData<ArrayList<Movie>> {
        currentPage++
        return movieRepository.fetchMovies(currentPage)
    }

}
