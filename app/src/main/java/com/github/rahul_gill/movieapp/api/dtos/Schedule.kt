package com.github.rahul_gill.movieapp.api.dtos


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Schedule(
    val days: List<String>? = null,
    val time: String? = null
)