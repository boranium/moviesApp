package com.boradincer.moviesapp.data.network

import com.boradincer.moviesapp.data.model.MovieDetail
import com.boradincer.moviesapp.data.model.responseModel.GetGenresResponseModel
import com.boradincer.moviesapp.data.model.responseModel.GetMoviesResponseModel
import kotlinx.coroutines.flow.Flow

interface MoviesAppRepository {
    fun getGenres(): Flow<ApiResult<GetGenresResponseModel>>
    fun getMovies(genres: String?, keywords: String?, page: Int): Flow<ApiResult<GetMoviesResponseModel>>
    fun getMovieDetail(id: Int): Flow<ApiResult<MovieDetail>>
}