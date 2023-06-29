package com.boradincer.moviesapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class MovieDetail(
    val adult: Boolean,
    val backdrop_path: String,
    val id: Int,

    val budget: Int,
    val revenue: Int,

    val title: String,
    val original_title: String,
    val original_language: String,
    val genres: List<MovieGenre>,

    val overview: String,
    val tagline: String,

    val poster_path: String,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int
)