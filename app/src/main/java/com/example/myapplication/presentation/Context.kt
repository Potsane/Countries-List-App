package com.example.myapplication.presentation

import android.app.AlertDialog
import android.content.Context

fun Context.showNetworkErrorDialog(
    onPositiveButtonClick: () -> Unit
) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    builder
        .setTitle("You are not connected to the internet")
        .setPositiveButton("Enable Internet") { _, _ -> onPositiveButtonClick() }
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

fun Context.showGenericErrorDialog(
    onPositiveButtonClick: () -> Unit
){
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    builder
        .setTitle("Ooh no Something went wrong!")
        .setPositiveButton("Close app") { _, _ -> onPositiveButtonClick() }
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

