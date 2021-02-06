package com.example.moviesfinder.domain.entities

import com.google.gson.annotations.SerializedName
import java.util.*

data class Movie(
    val id: String,
    val title: String,
    @SerializedName("released_on") val date: Date,
    @SerializedName("imdb_rating") val rating: Float,
    @SerializedName("content_type") val contentType: Char
)