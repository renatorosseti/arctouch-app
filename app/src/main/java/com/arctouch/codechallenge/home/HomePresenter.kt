package com.arctouch.codechallenge.home

import android.util.Log
import com.arctouch.codechallenge.model.Movie
import javax.inject.Inject

class HomePresenter(val view: HomeContracts.View, val interactor: HomeInteractor) : HomeContracts.Presenter, HomeContracts.InteractorOutput {

//    @Inject
//    lateinit var interactor: HomeInteractor

    override fun fetchMoviesList() = interactor.fetchMoviesData()

    override fun onMovieClicked() {
        Log.i("HomePresenter", "onMovieClicked!!!")
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMoviesFetchedSuccess(movies: List<Movie>) {
        Log.i("HomePresenter", "onMoviesFetchedSuccess")
        view.showMovies(movies)
    }

    override fun onMovieFetchedError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}