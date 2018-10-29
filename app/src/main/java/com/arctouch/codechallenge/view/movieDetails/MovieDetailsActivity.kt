package com.arctouch.codechallenge.view.movieDetails

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arctouch.application.CodeChallengeApp
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.util.MovieUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.instance

class MovieDetailsActivity : AppCompatActivity(), KodeinAware {

    override lateinit var kodein: Kodein

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        kodein = (applicationContext as CodeChallengeApp).kodein
    }

    override fun onStart() {
        super.onStart()
        val movieImageUrlBuilder : MovieUrlBuilder = kodein.direct.instance()

        textViewMovieName.text = intent.getStringExtra("title")
        textViewMovieOverview.text = intent.getStringExtra("overview")
        textViewMovieGenres.text = intent.getStringExtra("genres")
        textViewReleaseDate.text = intent.getStringExtra("release_date")

        Glide.with(this)
                .load(intent.getStringExtra("backdropPath")?.let { movieImageUrlBuilder.buildBackdropUrl(it) })
                .apply(RequestOptions().placeholder(R.color.colorPrimary))
                .into(imageViewBackground)

        Glide.with(this)
                .load(intent.getStringExtra("posterPath")?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(posterDetailsImageView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movie_details, menu)
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
}
