package com.github.rahul_gill.movieapp.models

import com.github.rahul_gill.movieapp.api.dtos.Show

data class MovieDetails (
    val id: Int = Movie.idObj.also { Movie.idObj-- },
    val name: String = "-",
    val imageUrl: String = "-",
    val summary: String = "-",
    val type: String = "-",
    val language: String = "-",
    val country: String = "-",
    val genres: String = "-",
    val status: String = "-",
    val premiered: String = "-",
    val network: String = "-",
    val ratings: String = "-",
    val betterImageUrl: String = "-"
){

    companion object {
        private var idObj = -1
        fun from(show: Show) = MovieDetails(
            id =  show.id ?: idObj.also { idObj-- },
            name = show.name ?: "-",
            imageUrl = show.image?.medium ?:  "-",
            summary = show.summary ?: "-",
            type = show.type ?: "-",
            language = show.language ?: "-",
            country = show.network?.country?.name ?: "-",
            genres = show.genres?.joinToString(", ") ?:"-",
            status = (show.status ?: "-").let { if(it == "Ended" && show.ended != null)  it + " on " + show.ended else it },
            premiered = show.premiered ?: "-",
            network = show.network?.name ?: "-",
            ratings = if(show.rating?.average == null) "-" else show.rating.average.toString(),
            betterImageUrl = show.image?.original ?: "-"
        )
    }
}