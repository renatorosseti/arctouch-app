package com.arctouch.codechallenge.view.home

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.arctouch.application.CodeChallengeApp
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.repository.api.TmdbApi
import com.arctouch.codechallenge.util.InternetBuilder.Companion.isInternetAvailable
import com.arctouch.codechallenge.util.InternetUtil
import com.arctouch.codechallenge.view.ProgressDialog
import com.arctouch.codechallenge.view.movieDetails.MovieDetailsActivity
import com.arctouch.codechallenge.viewModel.ViewModel
import kotlinx.android.synthetic.main.home_activity.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    override lateinit var kodein: Kodein
    lateinit var api: TmdbApi
    lateinit var viewModel: ViewModel
    lateinit var linearManager: LinearLayoutManager
    var listMovies: ArrayList<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        setSupportActionBar(toolbar)
        kodein = (applicationContext as CodeChallengeApp).kodein
        api = kodein.direct.instance()
        viewModel = kodein.direct.instance()
    }

    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        listenMovieslist()
//        loadObservers()
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = HomeAdapter(listMovies, listMovies, this, kodein.direct.instance())
        linearManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    var visibleItemCount = linearManager.childCount
                    var totalItemCount = linearManager.itemCount
                    var pastVisibleItems = linearManager.findFirstCompletelyVisibleItemPosition()

                    if (pastVisibleItems + visibleItemCount > totalItemCount) {
                        fetchNewMovies()
                    }
                }
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        var searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        val searchManager = getSystemService(AppCompatActivity.SEARCH_SERVICE) as SearchManager
        searchView?.queryHint = getString(R.string.action_search)
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.onActionViewExpanded()
        searchView?.maxWidth = Integer.MAX_VALUE
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            var adapter = recyclerView.adapter as HomeAdapter

            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(text: String): Boolean {
                adapter.filter.filter(text)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fetchNewMovies() {
        if (InternetUtil(application).isInternetOn()) {
            ProgressDialog.show(this)
            viewModel.fetchMovies().observe(this, Observer<ArrayList<Movie>> {
                ProgressDialog.hide()
                if (it != null) {
                    listMovies.addAll(it)
                    recyclerView.adapter.notifyDataSetChanged()
                }
            })
        } else {
            toast(R.string.no_internet).show()
        }

    }

    private fun loadObservers() {
        if (isInternetAvailable(this)) {
            viewModel.fetchGenres().observe(this, Observer<GenreResponse> {
                fetchNewMovies()
            })
        } else {
            toast(R.string.no_internet).show()
        }
    }

    fun listenMovieDetails(movieId: Int) {
        if (InternetUtil(application).isInternetOn()) {
            ProgressDialog.show(this)
            viewModel.fetchMovieDetails(movieId).observe(this, Observer<Movie> {
                ProgressDialog.hide()
                if (it != null) {
                    startActivity<MovieDetailsActivity>(
                            "title" to it.title,
                            "overview" to it.overview,
                            "genres" to it.genres?.joinToString(separator = ", ") { it.name },
                            "backdropPath" to it.backdropPath,
                            "posterPath" to it.posterPath,
                            "release_date" to it.releaseDate
                    )
                }
            })
        } else {
            toast(R.string.no_internet).show()
        }
    }

    fun listenMovieslist() {
        InternetUtil(application).observe(this, Observer { status ->
            if (status!!) {
                viewModel.fetchGenres().observe(this, Observer<GenreResponse> {
                    fetchNewMovies()
                })
            } else {
                toast(R.string.no_internet).show()
            }
        })
    }


}
