package com.boradincer.moviesapp.ui.customWidgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.boradincer.moviesapp.R

class DateWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {
    // Subviews in the widget
    private val imageView: ImageView
    private val textView: TextView

    init {
        // Inflate the layout for the custom view
        LayoutInflater.from(context).inflate(R.layout.widget_date, this, true)

        // Get references to the ImageView and TextView views
        imageView = findViewById(R.id.ivDateWidget)
        textView = findViewById(R.id.tvDateWidget)

        // Get the data from the parameters
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DateWidget)
        val text = typedArray.getString(R.styleable.DateWidget_dateText)
        typedArray.recycle()

        // Set the text of the TextView
        textView.text = text
    }

    // Setter for the text
    fun setText(text: String) {
        textView.text = text
    }

    // Getter for the text
    fun getText(): String {
        return textView.text.toString()
    }
}