package com.github.rahul_gill.movieapp.api.dtos


import android.view.Gravity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Show(
    val averageRuntime: Int? = null,
    val dvdCountry: Any? = null,
    val ended: String? = null,
    val externals: Externals? = null,
    val genres: List<String>? = null,
    val id: Int? = null,
    val image: Image? = null,
    val language: String? = null,
    val links: Links? = null,
    val name: String? = null,
    val network: Network? = null,
    val officialSite: String? = null,
    val premiered: String? = null,
    val rating: Rating? = null,
    val runtime: Int? = null,
    val schedule: Schedule? = null,
    val status: String? = null,
    val summary: String? = null,
    val type: String? = null,
    val updated: Int? = null,
    val url: String? = null,
    val webChannel: Any? = null,
    val weight: Int? = null,
)