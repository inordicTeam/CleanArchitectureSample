package com.example.moviesfinder.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.moviesfinder.MovieApp
import com.example.moviesfinder.R
import com.example.moviesfinder.domain.repositories.IMovieRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CoroutineScope {
    override val coroutineContext = Dispatchers.Main

    @Inject
    lateinit var movieRepository: IMovieRepository

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

            launch {
                movieLoader.visibility = View.VISIBLE

                try {

                    movieRepository.getRandomMovie(
                        minImdb = (imdbChooser.selectedItem as String).toInt(),
                        isMovie, isTVShow
                    ).let {
                        movieTitle.text = it.title
                        movieRating.text = "${it.rating} IMDB"
                        movieYear.text = Calendar.getInstance().let { calendar ->
                            calendar.time = it.date
                            calendar.get(Calendar.YEAR).toString()
                        }
                        Glide.with(this@MainActivity)
                            .load("https://img.reelgood.com/content/${if (it.contentType == 's') "show" else "movie"}/${it.id}/poster-780.webp")
                            .into(moviePoster)
                    }
                    movieInfo.visibility = View.VISIBLE

                } catch (e: Exception) {
                    Log.w(javaClass.simpleName, "Error", e)
                    Toast.makeText(this@MainActivity, "Error was occurred", Toast.LENGTH_SHORT).show()
                } finally {
                    movieLoader.visibility = View.GONE
                }
            }
        }
    }
}