package com.example.moviesfinder.domain.repositories

import com.example.moviesfinder.domain.entities.Movie

interface IMovieRepository {
    suspend fun getRandomMovie(minImdb: Int, isMovie: Boolean, isTVShow: Boolean): Movie
}