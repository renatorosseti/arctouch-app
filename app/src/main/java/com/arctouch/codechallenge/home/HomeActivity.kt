package com.arctouch.codechallenge.home

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.viewModel.HomeViewModel
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.home_activity.*
import javax.inject.Inject

class HomeActivity : HomeContracts.View, DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: HomeViewModel

    private val compositeDisposable = CompositeDisposable()

    private lateinit var listMovies: List<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchMovies().observe(this, Observer<List<Movie>> {
            if (it != null) {
                listMovies.addAll(it)
                recyclerView.adapter.notifyDataSetChanged()
            }
        })

    }

    override fun showMovies(movies: List<Movie>) {
        Log.i("HomeActivity", "showMovies")
        recyclerView.adapter = HomeAdapter(movies)
        progressBar.visibility = View.GONE
    }

    override fun showSingleMovie() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun addToCompositeDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}
