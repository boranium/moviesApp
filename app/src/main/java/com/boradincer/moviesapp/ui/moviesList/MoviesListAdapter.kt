package com.boradincer.moviesapp.ui.moviesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boradincer.moviesapp.data.model.Movie
import com.boradincer.moviesapp.databinding.ItemMovieBinding
import com.boradincer.moviesapp.util.DateUtils
import com.boradincer.moviesapp.util.GenresManager
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class MoviesListAdapter(
    private var movieList: MutableList<Movie>,
    private var onMovieClick: (Movie) -> Unit
): RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    fun submitList(newItems: List<Movie>) {
        movieList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun resetList(newItems: List<Movie>) {
        movieList.clear()
        movieList.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder.recyclerViewListItemBinding) {
            val movie = movieList[position]
            rootMovie.setOnClickListener {
                onMovieClick.invoke(movie)
            }
            tvItemMovieName.text = "${movieList[position].original_title}"

            with(rvItemMovieGenres) {
                val flexLayoutManager = FlexboxLayoutManager(context)
                flexLayoutManager.flexDirection = FlexDirection.ROW
                flexLayoutManager.justifyContent = JustifyContent.FLEX_START
                layoutManager = flexLayoutManager

                adapter = GenreListAdapter(genreList = GenresManager.getGenresOfMovie(movie).toMutableList())
            }

            val date = DateUtils.convertStringToDate(movie.release_date, DateUtils.DATE_FORMAT_YYYY_MM_DD)
            val dateStr = DateUtils.convertDateToString(date, DateUtils.DATE_FORMAT_DD_MM_YYYY)
            dwItemMovieDate.setText(dateStr)

            rwItemMovieRating.setText(movie.vote_average)

            ivItemMoviePoster.clipToOutline = true
            Glide.with(root.context)
                .load("https://image.tmdb.org/t/p/w200${movie.poster_path}")
                .into(ivItemMoviePoster)
        }
    }

    inner class MovieViewHolder(
        val recyclerViewListItemBinding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(recyclerViewListItemBinding.root)
}