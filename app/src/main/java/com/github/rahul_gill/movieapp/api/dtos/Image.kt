package com.github.rahul_gill.movieapp.api.dtos


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val medium: String? = null,
    val original: String? = null
)