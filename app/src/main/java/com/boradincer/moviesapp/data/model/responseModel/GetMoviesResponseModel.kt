package com.boradincer.moviesapp.data.model.responseModel

import com.boradincer.moviesapp.data.model.Movie

data class GetMoviesResponseModel(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
) {
}