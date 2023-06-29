package com.boradincer.moviesapp.ui.moviesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boradincer.moviesapp.MainActivity
import com.boradincer.moviesapp.R
import com.boradincer.moviesapp.databinding.FragmentMoviesListBinding
import com.boradincer.moviesapp.ui.movieDetail.MovieDetailFragment
import com.boradincer.moviesapp.util.BaseFragment
import com.boradincer.moviesapp.util.addActionBarButton
import com.boradincer.moviesapp.util.removeActionBarButtons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListFragment: BaseFragment() {
    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private val listViewModel: MoviesListFragmentViewModel by viewModels()

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
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        val view = binding.root

        setObservers()
        setViews()
        setListeners()
        initViewModel()

        return view
    }

    private fun initViewModel() {
        // getMovies is firstly called in getGenres service once
        // in order to assure genre list existence.
        listViewModel.getGenres()
    }

    private fun setListeners() {
        binding.rvMoviesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy != 0 && !listViewModel.isLastPage) {
                    listViewModel.page += 1
                    listViewModel.getMovies()
                }
            }
        })

        binding.fabSearch.setOnClickListener {
            val bottomSheetDialog = SearchBottomSheetFragment(listViewModel)
            bottomSheetDialog.show(childFragmentManager, "searchDialog")
        }
    }

    private fun setViews() {
        with(binding.rvMoviesList) {
            layoutManager = LinearLayoutManager(context)
            adapter = MoviesListAdapter(mutableListOf()) { movie ->
                val bundle = Bundle()
                bundle.putParcelable("movie", movie)
                val detailFragment = MovieDetailFragment()
                detailFragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.root_layout, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun setObservers() {
        with(listViewModel) {
            moviesList.observe(viewLifecycleOwner) { movies ->
                if(movies.isNotEmpty()) {
                    val moviesRecyclerView = binding.rvMoviesList
                    if(page == 1) {
                        (moviesRecyclerView.adapter as MoviesListAdapter).resetList(movies)
                        moviesRecyclerView.layoutManager?.scrollToPosition(0)
                        listViewModel.isLastPage = false
                    }
                    else
                        (moviesRecyclerView.adapter as MoviesListAdapter).submitList(movies)
                }
            }
            loading.observe(viewLifecycleOwner) { isLoading ->
                (activity as MainActivity).setLoading(isLoading)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}