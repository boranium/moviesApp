package com.boradincer.moviesapp.ui.moviesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boradincer.moviesapp.MainActivity
import com.boradincer.moviesapp.R
import com.boradincer.moviesapp.databinding.FragmentMoviesListBinding
import com.boradincer.moviesapp.ui.movieDetail.MovieDetailFragment
import com.boradincer.moviesapp.util.BaseFragment
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
        listViewModel.resetQuery()
        listViewModel.getGenres()
    }

    private fun setListeners() {
        // lazy loading when scrolled to the end of recyclerview
        binding.rvMoviesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy != 0 && !listViewModel.isLastPage) {
                    listViewModel.page += 1
                    listViewModel.getMovies()
                }
            }
        })

        // search button action
        binding.fabSearch.setOnClickListener {
            val bottomSheetDialog = SearchBottomSheetFragment()
            bottomSheetDialog.moviesListFragmentViewModel = listViewModel
            bottomSheetDialog.show(childFragmentManager, "searchDialog")
        }

        // retry action of the empty state layout
        binding.tvEmptyStateAction.setOnClickListener {
            listViewModel.page = 1
            initViewModel()
        }
    }

    private fun setViews() {
        with(binding.rvMoviesList) {
            layoutManager = LinearLayoutManager(context)
            // the adapter takes its click listener at this scope
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
                val moviesRecyclerView = binding.rvMoviesList

                // data fetched successfully
                if(movies.isNotEmpty()) {
                    if(page == 1) {
                        (moviesRecyclerView.adapter as MoviesListAdapter).resetList(movies)
                        moviesRecyclerView.layoutManager?.scrollToPosition(0)
                        listViewModel.isLastPage = false
                    }
                    else
                        (moviesRecyclerView.adapter as MoviesListAdapter).submitList(movies)
                    binding.clEmptyState.visibility = View.GONE
                    binding.clMainContent.visibility = View.VISIBLE
                }
                // data not fetched correctly or no data available
                else if(moviesRecyclerView.adapter?.itemCount == 0) {
                    with(binding) {
                        clEmptyState.visibility = View.VISIBLE
                        clMainContent.visibility = View.GONE

                        // deciding if error occurred or not
                        if(!listViewModel.errorOccured) {
                            tvEmptyStateMsg.text = getString(R.string.no_data)
                        }
                    }
                }
            }

            loading.observe(viewLifecycleOwner) { isLoading ->
                (activity as MainActivity).setLoading(isLoading)
            }
        }
    }
}