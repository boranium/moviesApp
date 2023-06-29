package com.boradincer.moviesapp.ui.moviesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boradincer.moviesapp.data.model.MovieGenre
import com.boradincer.moviesapp.databinding.ItemGenreBinding
import com.boradincer.moviesapp.util.GenresManager

class GenreListAdapter(
    private var genreList: MutableList<MovieGenre> = GenresManager.genresList,
    private var onClick: ((MovieGenre) -> Unit)? = null,
): RecyclerView.Adapter<GenreListAdapter.GenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder =
        GenreViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return genreList.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        with(holder.recyclerViewListItemBinding) {
            tvWidgetGenreLabel.text = genreList[position].name
            root.isSelected = genreList[position].selected
            root.setOnClickListener {
                onClick?.let {
                    it.invoke(genreList[position])
                    root.isSelected = genreList[position].selected
                }
            }

        }
    }

    inner class GenreViewHolder(
        val recyclerViewListItemBinding: ItemGenreBinding
    ) : RecyclerView.ViewHolder(recyclerViewListItemBinding.root)
}