package com.boradincer.moviesapp.util

import com.boradincer.moviesapp.data.model.Movie
import com.boradincer.moviesapp.data.model.MovieGenre

class GenresManager {
    companion object {
        val genresList = mutableListOf<MovieGenre>()

        fun getGenresOfMovie(movie: Movie): List<MovieGenre> {
            return genresList.filter { it.id in movie.genre_ids }
        }

        fun getSelectedGenres(): List<MovieGenre> {
            return genresList.filter { it.selected }
        }

        fun resetSelectedGenres() {
            genresList.forEach {
                it.selected = false
            }
        }
    }
}