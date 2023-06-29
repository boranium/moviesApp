package com.boradincer.moviesapp.ui.customWidgets

import android.content.Context
import android.icu.text.DecimalFormat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.boradincer.moviesapp.R

class RatingWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {
    // Subviews in the widget
    private val textView: TextView

    init {
        // Inflate the layout for the custom view
        LayoutInflater.from(context).inflate(R.layout.widget_rating, this, true)

        // Get references to the ImageView and TextView views
        textView = findViewById(R.id.tvItemMovieRating)

        // Get the data from the parameters
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingWidget)
        val text = typedArray.getString(R.styleable.RatingWidget_ratingText)
        typedArray.recycle()

        // Set the text of the TextView
        textView.text = text
    }

    // Setter for the text
    fun setText(rating: Double) {
        val decimalFormat = DecimalFormat("0.0")
        val text = decimalFormat.format(rating)
        textView.text = "$text/10 IMDB"
    }

    // Getter for the text
    fun getText(): String {
        return textView.text.toString()
    }
}