package com.boradincer.moviesapp.ui.moviesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boradincer.moviesapp.databinding.FragmentSearchDialogBinding
import com.boradincer.moviesapp.util.GenresManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchBottomSheetFragment(
) : BottomSheetDialogFragment() {
    private var _binding: FragmentSearchDialogBinding? = null
    private val binding get() = _binding!!
    lateinit var moviesListFragmentViewModel: MoviesListFragmentViewModel
    fun newInstance(): SearchBottomSheetFragment {
        return SearchBottomSheetFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        with(binding) {
            with(rvSearchGenres) {
                val flexLayoutManager = FlexboxLayoutManager(context)
                flexLayoutManager.flexDirection = FlexDirection.ROW
                flexLayoutManager.justifyContent = JustifyContent.FLEX_START
                layoutManager = flexLayoutManager
                adapter = GenreListAdapter {
                    it.selected = !it.selected
                }
            }
            btnSearchPositive.setOnClickListener {
                with(moviesListFragmentViewModel) {
                    selectedGenres = GenresManager.getSelectedGenres().map { it.id }.toMutableList()
                    selectedKeyword = etSearchKeyword.text.trim().toString()
                    page = 1

                    getMovies()
                }
                dismiss()
            }

            // clean the query
            btnSearchNegative.setOnClickListener {
                with(moviesListFragmentViewModel) {
                    selectedGenres = mutableListOf()
                    selectedKeyword = ""
                }
                etSearchKeyword.setText("")
                GenresManager.resetSelectedGenres()
                rvSearchGenres.adapter?.notifyDataSetChanged()
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        if(!::moviesListFragmentViewModel.isInitialized)
            dismiss()
    }
}