package com.boradincer.moviesapp.data.network

import com.boradincer.moviesapp.BuildConfig
import com.boradincer.moviesapp.data.model.MovieDetail
import com.boradincer.moviesapp.data.model.responseModel.GetGenresResponseModel
import com.boradincer.moviesapp.data.model.responseModel.GetMoviesResponseModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MoviesAppApi {

    @GET("/3/genre/movie/list")
    suspend fun getGenres(): Response<GetGenresResponseModel>

    @GET("/3/discover/movie")
    suspend fun getMovies(
        @Query("with_genres") genres: String? = null,
        @Query("with_keywords") keywords: String? = null,
        @Query("page") page: Int
    ): Response<GetMoviesResponseModel>

    @GET("/3/movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") movieId: Int? = null,
    ): Response<MovieDetail>

    companion object {
        operator fun invoke() : MoviesAppApi {
            val logging = HttpLoggingInterceptor()
            if(BuildConfig.DEBUG)
                logging.level = HttpLoggingInterceptor.Level.BODY
            else
                logging.level = HttpLoggingInterceptor.Level.NONE

            val authInterceptor = Interceptor { chain ->
                val originalRequest: Request = chain.request()
                val authenticatedRequest: Request = originalRequest.newBuilder()
                    // todo move token to gradle files
                    .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlZjE0NWMxMjNjNThjMDU3YjgwNGZkMzU5MmIxMjI1NiIsInN1YiI6IjY0OTE3YTFhMjYzNDYyMDE0ZTU5YTQyNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kqzGujvNgeZStjHp2fLK1eC6AUnsNVQDsWKReLlxx4w")
                    .build()
                chain.proceed(authenticatedRequest)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(authInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("https://api.themoviedb.org/")
                .build()
                .create(MoviesAppApi::class.java)
        }
    }
}