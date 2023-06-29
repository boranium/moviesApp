package com.boradincer.moviesapp.data.network

import com.boradincer.moviesapp.data.model.responseModel.GetGenresResponseModel
import com.boradincer.moviesapp.data.model.responseModel.GetMoviesResponseModel
import kotlinx.coroutines.flow.Flow

interface MoviesAppRepository {
    fun getMovies(genres: String?, keywords: String?): Flow<ApiResult<GetMoviesResponseModel>>
    fun getGenres(): Flow<ApiResult<GetGenresResponseModel>>
}