package com.jeff.covidtracker.android.base.extension

import android.app.Activity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jeff.covidtracker.R
import java.util.*

fun Activity.invokeSimpleDialog(title: String,
                                positiveButtonText: String,
                                message: String,
                                onApprove: () -> Unit) {
    AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, which ->
                onApprove.invoke()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
            }
            .show()
}

fun Activity.invokeSimpleDialog(title: String,
                                positiveButtonText: String,
                                negativeButtonText: String,
                                message: String,
                                onApprove: () -> Unit) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonText) { dialog, which ->
            onApprove.invoke()
        }
        .setNegativeButton(negativeButtonText) { dialog, which ->
            dialog.dismiss()
        }
        .show()
}

fun Activity.invokeSimpleDialog(title: String,
                                positiveButtonText: String,
                                message: String) {
    AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, which ->
                dialog.dismiss()
            }
            .show()
}

fun Activity.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun String.Companion.substringWithDots(s: String, maxLength: Int) : String {
    return when {
        s.length >= maxLength -> {
            String.format("${s.substring(0, maxLength)}…")
        }
        else -> {
            s
        }
    }
}


@OptIn(ExperimentalStdlibApi::class)
fun String.toTitleCase(): String = this.capitalize(Locale.ROOT)

fun View.visibility(isVisible: Boolean) {
    if (isVisible){
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun String.Companion.numbersWithComma(i: Int): String {
    return this.format("%,d", i)
}