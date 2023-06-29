package com.boradincer.moviesapp.util

import android.app.Activity
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.boradincer.moviesapp.R
import com.boradincer.moviesapp.di.MoviesApp

fun Activity.addActionBarButton(image: Int, color: Int = R.color.white, action: () -> Unit) {
    val actionBar = (this as AppCompatActivity).supportActionBar
    actionBar?.setDisplayShowCustomEnabled(true)

    // Get the existing custom view or create a new LinearLayout
    val containerView = actionBar?.customView as? LinearLayout ?: LinearLayout(this).apply {
        layoutParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.WRAP_CONTENT,
            Gravity.END
        )
    }

    // Create a new button and add it to the container view
    val button = ImageView(this)
    val paddingDp = 2
    val density = MoviesApp.applicationContext().resources.displayMetrics.density
    button.setPadding((paddingDp * density).toInt())
    button.setImageDrawable(ContextCompat.getDrawable(this, image))
    button.setColorFilter(ContextCompat.getColor(this, color))
    button.setOnClickListener { action() }
    containerView.addView(button)

    // Set the container view as the new custom view of the action bar
    actionBar?.customView = containerView
}


fun Activity.removeActionBarButtons() {
    val actionBar = (this as AppCompatActivity).supportActionBar
    actionBar?.setDisplayShowCustomEnabled(false)
    actionBar?.customView = null
}