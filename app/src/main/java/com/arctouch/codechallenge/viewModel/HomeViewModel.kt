package com.arctouch.codechallenge.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.repository.Repository
import com.arctouch.codechallenge.view.home.HomeViewState

class HomeViewModel(val repository: Repository) : ViewModel() {

    fun fetchMovies(): LiveData<HomeViewState> {
        var data = MutableLiveData<HomeViewState>()
        data.value?.movies = repository.retrieveReactiveMovies().value?.results!!
        return data
    }
}