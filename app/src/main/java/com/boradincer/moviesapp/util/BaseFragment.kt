package com.boradincer.moviesapp.util

import android.content.Context
import android.os.Parcelable
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    override fun onResume() {
        super.onResume()
    }

    inline fun <reified T> getParcelable(id: String): T? where T : Parcelable =
        arguments?.getParcelable<T>(id)
}