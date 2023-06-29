package com.boradincer.moviesapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
) : Parcelable {
    companion object {
        fun toGenresString(genres: List<Int>): String? {
            var genresString = ""
            genres.forEach { genresString += "$it|" }
            if (genresString.isNotEmpty() && genresString.last() == '|') {
                genresString = genresString.dropLast(1)
            }
            return if (genresString.isNotEmpty())
                genresString
            else
                null
        }
    }
}