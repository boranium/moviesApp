package com.boradincer.moviesapp.ui.moviesList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boradincer.moviesapp.R
import com.boradincer.moviesapp.data.model.Movie
import com.boradincer.moviesapp.data.network.ApiStatus
import com.boradincer.moviesapp.data.network.MoviesAppRepository
import com.boradincer.moviesapp.util.ErrorManager
import com.boradincer.moviesapp.util.GenresManager
import com.boradincer.moviesapp.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListFragmentViewModel @Inject constructor(
    private val moviesAppRepository: MoviesAppRepository
): ViewModel() {
    val moviesList = MutableLiveData<List<Movie>>()
    var selectedGenres = mutableListOf<Int>()
    var selectedKeyword: String? = null
    var isLastPage: Boolean = false
    var errorOccured: Boolean = false
    var page: Int = 1

    val loading = MutableLiveData<Boolean>()

    fun resetQuery() {
        selectedGenres = mutableListOf()
        selectedKeyword = ""
        page = 1
    }

    fun getMovies() {
        if(NetworkUtil.isNetworkAvailable()) {
            loading.value = true
            val genresString = Movie.toGenresString(selectedGenres)
            if(selectedKeyword == "")
                selectedKeyword = null

            viewModelScope.launch {
                moviesAppRepository.getMovies(genresString, selectedKeyword, page).collect { moviesResponse ->
                    when(moviesResponse.status) {
                        ApiStatus.SUCCESS -> {
                            moviesResponse.data?.let {
                                moviesList.value = it.results
                                if(it.page == it.total_pages)
                                    isLastPage = true
                            } ?: kotlin.run {
                                errorOccured = false
                                moviesList.value = mutableListOf()
                            }
                        }
                        ApiStatus.ERROR -> {
                            errorOccured = true
                            moviesList.value = mutableListOf()
                        }
                        else -> {

                        }
                    }
                    loading.value = false
                }
            }
        }
        else {
            ErrorManager.showError(R.string.check_internet)
            errorOccured = true
            moviesList.value = mutableListOf()
            loading.value = false
        }
    }

    fun getGenres() {
        if(NetworkUtil.isNetworkAvailable()) {
            loading.value = true
            if(selectedKeyword == "")
                selectedKeyword = null

            viewModelScope.launch {
                moviesAppRepository.getGenres().collect { genresResponse ->
                    when(genresResponse.status) {
                        ApiStatus.SUCCESS -> {
                            genresResponse.data?.let {
                                GenresManager.genresList.addAll(it.genres)
                                getMovies()
                            } ?: kotlin.run {
                                errorOccured = false
                                moviesList.value = mutableListOf()
                            }
                        }
                        ApiStatus.ERROR -> {
                            errorOccured = true
                            moviesList.value = mutableListOf()
                        }
                        else -> {

                        }
                    }
                    loading.value = false
                }
            }
        }
        else {
            ErrorManager.showError(R.string.check_internet)
            errorOccured = true
            moviesList.value = mutableListOf()
            loading.value = false
        }
    }
}