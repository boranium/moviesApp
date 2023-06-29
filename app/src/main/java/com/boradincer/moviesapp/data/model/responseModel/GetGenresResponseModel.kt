package com.boradincer.moviesapp.data.model.responseModel

import com.boradincer.moviesapp.data.model.MovieGenre

data class GetGenresResponseModel(
    val genres: List<MovieGenre>
)