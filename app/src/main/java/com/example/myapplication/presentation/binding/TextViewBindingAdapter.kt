package com.example.myapplication.presentation.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("listToTextView")
fun setListToTextView(textView: TextView?, languages: MutableList<String>?) {
    if (textView == null || languages.isNullOrEmpty()) return
    val text = StringBuilder()
    languages.forEachIndexed { index, item ->
        if (index == 0) text.append(item)
        else text.append("\n$item")
    }
    textView.text = text
}