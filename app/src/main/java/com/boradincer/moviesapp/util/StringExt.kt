package com.boradincer.moviesapp.util

import android.icu.text.NumberFormat
import android.icu.util.Currency
import java.util.*

fun Int.formatAsMoney(): String {
    val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
    format.currency = Currency.getInstance("USD")
    return format.format(this.toDouble())
}