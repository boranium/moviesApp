package com.boradincer.moviesapp.ui.movieDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boradincer.moviesapp.R
import com.boradincer.moviesapp.data.model.Movie
import com.boradincer.moviesapp.data.model.MovieDetail
import com.boradincer.moviesapp.data.model.MovieGenre
import com.boradincer.moviesapp.data.network.ApiStatus
import com.boradincer.moviesapp.data.network.MoviesAppRepository
import com.boradincer.moviesapp.util.ErrorManager
import com.boradincer.moviesapp.util.GenresManager
import com.boradincer.moviesapp.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailFragmentViewModel @Inject constructor(
    private val moviesAppRepository: MoviesAppRepository
): ViewModel() {
    var selectedMovie: Movie? = null
    val movieDetail = MutableLiveData<MovieDetail>()

    val loading = MutableLiveData<Boolean>()

    fun getMovieDetail() {
        selectedMovie?.let { selectedMovie ->
            if(NetworkUtil.isNetworkAvailable()) {
                loading.value = true
                viewModelScope.launch {
                    moviesAppRepository.getMovieDetail(selectedMovie.id).collect { movieDetailResponse ->
                        when(movieDetailResponse.status) {
                            ApiStatus.SUCCESS -> {
                                movieDetailResponse.data?.let {
                                    movieDetail.value = it
                                } ?: kotlin.run {
                                    ErrorManager.showError(R.string.no_data)
                                }
                            }
                            ApiStatus.ERROR -> {
                                ErrorManager.showError(R.string.check_internet)
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
                loading.value = false
            }
        }
    }
}