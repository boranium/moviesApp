package com.boradincer.moviesapp.util

import android.widget.Toast
import com.boradincer.moviesapp.di.MoviesApp

class ErrorManager {
    companion object {
        fun showError(message: String) {
            Toast.makeText(MoviesApp.applicationContext(), message, Toast.LENGTH_SHORT).show()
        }
        fun showError(messageRes: Int) {
            with(MoviesApp.applicationContext()) {
                Toast.makeText(this, this.getText(messageRes), Toast.LENGTH_SHORT).show()
            }
        }
    }
}