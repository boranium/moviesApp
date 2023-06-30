package com.boradincer.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.boradincer.moviesapp.databinding.ActivityMainBinding
import com.boradincer.moviesapp.ui.moviesList.MoviesListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, MoviesListFragment.newInstance(), "listFragment")
                .commit()
        }

        val view = binding.root
        setContentView(view)
    }

    fun goBack() {
        supportFragmentManager.popBackStack()
    }

    fun setLoading(isLoading: Boolean) {
        with(binding) {
            if(isLoading) {
                rootLayout.isClickable = false
                pbLoading.visibility = View.VISIBLE
            }
            else {
                rootLayout.isClickable = true
                pbLoading.visibility = View.GONE
            }
        }
    }
}