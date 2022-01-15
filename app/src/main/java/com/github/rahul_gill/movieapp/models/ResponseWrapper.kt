package com.github.rahul_gill.movieapp.models

sealed class ResponseWrapper<T> {
    class Result<T>(val data: T) : ResponseWrapper<T>()
    class Error<T>(val message: String) : ResponseWrapper<T>()
    class LoadingInProgress<T>: ResponseWrapper<T>()
    class None<T>: ResponseWrapper<T>()
}