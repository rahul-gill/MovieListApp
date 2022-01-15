package com.github.rahul_gill.movieapp.api.dtos


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Externals(
    val imdb: Any? = null,
    val thetvdb: Int? = null,
    val tvrage: Any? = null
)