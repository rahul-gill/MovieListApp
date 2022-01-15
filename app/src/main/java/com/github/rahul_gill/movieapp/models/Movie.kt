package com.github.rahul_gill.movieapp.models

import com.github.rahul_gill.movieapp.api.dtos.Show

data class Movie(
    val id: Int = idObj.also { idObj-- },
    val name: String = "No Name",
    val imageUrl: String = "",
    val summary: String = "No summary"
){
    companion object{
        var idObj = -1;
        fun fromShowDto(show: Show) = Movie(
            id = show.id ?: idObj.also { idObj-- },
            name = show.name ?: "",
            imageUrl = show.image?.medium ?: "",
            summary = show.summary ?: ""
        )
    }
}