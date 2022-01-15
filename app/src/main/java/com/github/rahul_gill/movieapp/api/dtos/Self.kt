package com.github.rahul_gill.movieapp.api.dtos


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Self(
    val href: String? = null
)