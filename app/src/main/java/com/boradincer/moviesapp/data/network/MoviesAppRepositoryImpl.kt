package com.boradincer.moviesapp.data.network

import com.boradincer.moviesapp.data.model.responseModel.GetGenresResponseModel
import com.boradincer.moviesapp.data.model.responseModel.GetMoviesResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesAppRepositoryImpl: MoviesAppRepository {
    override fun getMovies(genres: String?, keywords: String?): Flow<ApiResult<GetMoviesResponseModel>> {
        retried = false
        return flow {
            try {
                val response = api.getMovies(genres, keywords)
                if(response.isSuccessful) {
                    emit(ApiResult.Success(response.body()))
                } else {
                    emit(ApiResult.Error("An error occured while fetching the data. Try again later", 501))
                }
            } catch (e: Exception) {
                if(!retried) {
                    getMovies(genres, keywords)
                } else {
                    retried = true
                    emit(ApiResult.Error("An error occured while fetching the data. Try again later", 501))
                }
            }
        }
    }

    override fun getGenres(): Flow<ApiResult<GetGenresResponseModel>> {
        retried = false
        return flow {
            try {
                val response = api.getGenres()
                if(response.isSuccessful) {
                    emit(ApiResult.Success(response.body()))
                } else {
                    emit(ApiResult.Error("An error occured while fetching the data. Try again later", 501))
                }
            } catch (e: Exception) {
                if(!retried) {
                    getGenres()
                } else {
                    retried = true
                    emit(ApiResult.Error("An error occured while fetching the data. Try again later", 501))
                }
            }
        }
    }

    companion object {
        private val api: MoviesAppApi = MoviesAppApi.invoke()
        private var retried = false
    }

}