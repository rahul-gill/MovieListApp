package com.github.rahul_gill.movieapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.rahul_gill.movieapp.api.MoviesApi
import com.github.rahul_gill.movieapp.models.MovieDetails
import com.github.rahul_gill.movieapp.models.ResponseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(): ViewModel() {
    @Inject
    lateinit var moviesApi: MoviesApi
    private val TAG = "AppViewModel"


    private val _movieList: MutableLiveData<ResponseWrapper<List<MovieDetails>>> = MutableLiveData(
        ResponseWrapper.LoadingInProgress())
    val movieList: LiveData<ResponseWrapper<List<MovieDetails>>>
        get() = _movieList


    suspend fun getMovieList(){
        try {
            _movieList.value = ResponseWrapper.Result(
                moviesApi.getAllMovieList().map { if(it.show != null)  MovieDetails.from(it.show) else MovieDetails() }
            )
        }catch (e: HttpException){
            _movieList.value = ResponseWrapper.Error("Something went wrong. Try again later")
            e.printStackTrace()

        }catch (e: Exception){
                _movieList.value = ResponseWrapper.Error("${e.message}")
            e.printStackTrace()
        }
    }

    suspend fun getSearchedMovieList(searchString: String){
        try {
            _movieList.value = ResponseWrapper.Result(
                moviesApi.getSearchedItem(searchString).map { if(it.show != null)  MovieDetails.from(it.show) else MovieDetails() }
            )
        } catch (e: HttpException){
            _movieList.value = ResponseWrapper.Error("Something went wrong. Try again later.a")
        }catch (e: Exception){
            if("${e::class}" == "class kotlinx.coroutines.JobCancellationException (Kotlin reflection is not available)")
                _movieList.value = ResponseWrapper.LoadingInProgress()
            else
                _movieList.value = ResponseWrapper.Error("${e.message}")
        }
    }

    fun setMovieListToNoneType(){
        _movieList.value = ResponseWrapper.None()
    }
}