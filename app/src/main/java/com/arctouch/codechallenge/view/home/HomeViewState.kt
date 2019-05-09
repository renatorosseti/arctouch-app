package com.arctouch.codechallenge.view.home

import com.arctouch.codechallenge.model.Movie

data class HomeViewState (
        var data: ArrayList<Movie>,
        var loadingState: Boolean = false,
        var shouldWaitForInternet: Boolean  = false
)