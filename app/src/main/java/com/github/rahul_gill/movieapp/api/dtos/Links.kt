package com.github.rahul_gill.movieapp.api.dtos


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Links(
    val previousepisode: Previousepisode? = null,
    val self: Self? = null
)