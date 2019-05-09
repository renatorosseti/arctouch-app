package com.arctouch.codechallenge.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.repository.Repository
import com.arctouch.codechallenge.view.home.HomeViewState

class HomeViewModel(val repository: Repository) : ViewModel() {



    fun fetchMovies() : LiveData<HomeViewState> {
        repository.retrieveReactiveMovies().value
    }
}