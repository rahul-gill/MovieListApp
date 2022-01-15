package com.github.rahul_gill.movieapp.api

import com.github.rahul_gill.movieapp.api.dtos.ApiResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("search/shows?q=all")
    suspend fun getAllMovieList(): List<ApiResponseItem>

    @GET("/search/shows")
    suspend fun getSearchedItem(@Query("q") searchString: String): List<ApiResponseItem>

}