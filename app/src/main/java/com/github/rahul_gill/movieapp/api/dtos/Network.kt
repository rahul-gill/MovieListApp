package com.github.rahul_gill.movieapp.api.dtos


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Network(
    val country: Country? = null,
    val id: Int? = null,
    val name: String? = null
)