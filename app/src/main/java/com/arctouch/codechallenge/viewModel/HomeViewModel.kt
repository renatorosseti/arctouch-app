package com.arctouch.codechallenge.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.arctouch.codechallenge.repository.HomeInteractor
import com.arctouch.codechallenge.view.home.HomeViewState

class HomeViewModel(val homeInteractor: HomeInteractor) : ViewModel() {

    fun fetchMovies(): LiveData<HomeViewState> {
        var data = MutableLiveData<HomeViewState>()
        data.value?.movies = homeInteractor.retrieveReactiveMovies().value?.results!!
        Log.i("HomeInteractor", "check null ${homeInteractor}")
        return data
    }
}