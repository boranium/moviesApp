package com.boradincer.moviesapp.di

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApp: Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: MoviesApp? = null

        // General purpose app context getter.
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}