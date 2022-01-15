package com.github.rahul_gill.movieapp.api

import android.content.Context
import com.github.rahul_gill.movieapp.util.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MovieApiProvider {
    @Singleton
    @Provides
    fun getMoshiInstance(): Moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .build()

    @Singleton
    @Provides
    fun getOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
//            .addInterceptor { chain ->
//
//                if(!isConnected()) throw NoInternetException()
//
//                chain.proceed(chain.request())
//            }
            .build()
    }
    @Singleton
    @Provides
    fun getMovieApi(moshi: Moshi, okHttpClient: OkHttpClient) : MoviesApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Constants.MOVIE_API_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(MoviesApi::class.java)
}
