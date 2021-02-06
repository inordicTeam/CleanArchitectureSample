package com.example.moviesfinder.data.api

import com.example.moviesfinder.domain.entities.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    companion object {
        const val BASE_URL = "https://api.reelgood.com/v3.0/content/"
    }

    @GET("random?sources=netflix")
    suspend fun getRandomMovie(
        @Query("content_kind") contentKind: String,
        @Query("minimum_imdb") minImdb: Int
    ): Movie
}