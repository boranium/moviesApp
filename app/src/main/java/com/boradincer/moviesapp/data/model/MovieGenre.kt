package com.boradincer.moviesapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieGenre(
    val id: Int,
    val name: String,
    var selected: Boolean = false
) : Parcelable