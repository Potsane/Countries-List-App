package com.example.myapplication.presentation.binding

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("showOrHideError")
fun showOrHideError(view: ConstraintLayout?,  show: Boolean?) {
    if(view == null || show == null) return
    view.visibility = if(show) View.VISIBLE else View.GONE
}

@BindingAdapter("showOrHideList")
fun showOrHideList(view: SwipeRefreshLayout?, show: Boolean?) {
    if(view == null || show == null) return
    view.visibility = if(show)  View.GONE else View.VISIBLE
}