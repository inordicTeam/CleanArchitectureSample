package com.example.moviesfinder.presentation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.moviesfinder.MovieApp
import com.example.moviesfinder.R
import com.example.moviesfinder.domain.entities.Movie
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.*
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = mainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        MovieApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imdbRatings = listOf("5", "6", "7", "8", "9")
        imdbChooser.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            imdbRatings
        )

        buttonSpin.setOnClickListener {
            val isMovie = moviesCheckbox.isChecked
            val isTVShow = tvCheckbox.isChecked

            if (!(isMovie || isTVShow)) {
                Toast.makeText(this, "Check at least one checkbox", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mainPresenter.onSpinClicked(isMovie, isTVShow, (imdbChooser.selectedItem as String).toInt())
        }
    }

    override fun showError() {
        Toast.makeText(this@MainActivity, "Error was occurred", Toast.LENGTH_SHORT).show()
    }

    override fun showMovie(movie: Movie) = movie.let {
        movieTitle.text = it.title
        movieRating.text = "${it.rating} IMDB"
        movieYear.text = Calendar.getInstance().let { calendar ->
            calendar.time = it.date
            calendar.get(Calendar.YEAR).toString()
        }
        Glide.with(this@MainActivity)
            .load("https://img.reelgood.com/content/${if (it.contentType == 's') "show" else "movie"}/${it.id}/poster-780.webp")
            .into(moviePoster)
        return@let
    }

    override fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            movieInfo.visibility = View.GONE
            movieLoader.visibility = View.VISIBLE
        } else {
            movieLoader.visibility = View.GONE
            movieInfo.visibility = View.VISIBLE
        }
    }
}