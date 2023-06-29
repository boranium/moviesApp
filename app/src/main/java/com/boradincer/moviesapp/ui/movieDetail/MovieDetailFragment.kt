package com.boradincer.moviesapp.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.boradincer.moviesapp.MainActivity
import com.boradincer.moviesapp.R
import com.boradincer.moviesapp.data.model.Movie
import com.boradincer.moviesapp.databinding.FragmentMovieDetailBinding
import com.boradincer.moviesapp.ui.moviesList.MoviesListFragment
import com.boradincer.moviesapp.ui.moviesList.MoviesListFragmentViewModel
import com.boradincer.moviesapp.util.BaseFragment
import com.boradincer.moviesapp.util.formatAsMoney
import com.boradincer.moviesapp.util.removeActionBarButtons
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment: BaseFragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: MovieDetailFragmentViewModel by viewModels()

    companion object {
        fun newInstance(): MoviesListFragment {
            return MoviesListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        getParcelable<Movie>("movie")?.let { movie ->
            detailViewModel.selectedMovie = movie

            setObservers()
            setViews()
            setListeners()
            initViewModel()
        }

        return view
    }

    private fun initViewModel() {
        detailViewModel.getMovieDetail()
    }

    private fun setListeners() {

    }

    private fun setViews() {

    }

    private fun setObservers() {
        detailViewModel.movieDetail.observe(this) { movieDetail ->
            with(binding) {
                tvMovieDetailName.text = movieDetail.original_title
                tvMovieDetailOverview.text = movieDetail.overview
                with(movieDetail.tagline) {
                    if(isNotEmpty())
                        tvMovieDetailTagline.text = this
                    else
                        tvMovieDetailTagline.text = getString(R.string.no_tagline)
                }

                lyMovieDetailLang.tvMoviePropertyText.text = movieDetail.original_language.uppercase()
                lyMovieDetailLang.tvMoviePropertyTitle.text = getString(R.string.language)

                lyMovieDetailBudget.tvMoviePropertyText.text = movieDetail.budget.formatAsMoney()
                lyMovieDetailBudget.tvMoviePropertyTitle.text = getString(R.string.budget)

                lyMovieDetailRatingCount.tvMoviePropertyText.text = movieDetail.vote_count.toString()
                lyMovieDetailRatingCount.tvMoviePropertyTitle.text = getString(R.string.vote_count)

                rwMovieDetailRating.setText(movieDetail.vote_average)

                Glide.with(root.context)
                    .load("https://image.tmdb.org/t/p/original${movieDetail.backdrop_path}")
                    .into(ivMovieDetailPoster)
            }
        }

        detailViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            (activity as MainActivity).setLoading(isLoading)
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.removeActionBarButtons()
    }
}