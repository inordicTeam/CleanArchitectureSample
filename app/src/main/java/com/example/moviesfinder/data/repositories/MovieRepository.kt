package com.example.moviesfinder.data.repositories

import com.example.moviesfinder.data.api.MovieApi
import com.example.moviesfinder.domain.entities.Movie
import com.example.moviesfinder.domain.repositories.IMovieRepository
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
) : IMovieRepository {

    override suspend fun getRandomMovie(
        minImdb: Int,
        isMovie: Boolean,
        isTVShow: Boolean
    ): Movie = withContext(Dispatchers.IO) {
        val contentKind = when {
            isMovie && isTVShow -> "both"
            isMovie -> "movie"
            isTVShow -> "show"
            else -> ""
        }
        movieApi.getRandomMovie(contentKind, minImdb)
    }
}