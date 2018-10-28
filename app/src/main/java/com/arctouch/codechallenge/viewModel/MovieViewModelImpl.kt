package com.arctouch.codechallenge.viewModel

import android.arch.lifecycle.LiveData
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie


interface MovieViewModelImpl {
    fun onStart()
    fun onStop()
    fun fetchMovies() : LiveData<ArrayList<Movie>>
    fun fetchGenres() : LiveData<GenreResponse>
    fun fetchMovieDetails(movieId: Int) : LiveData<Movie>
}