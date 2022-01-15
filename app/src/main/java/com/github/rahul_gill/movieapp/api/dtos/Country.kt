package com.github.rahul_gill.movieapp.api.dtos


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Country(
    val code: String? = null,
    val name: String? = null,
    val timezone: String? = null
)