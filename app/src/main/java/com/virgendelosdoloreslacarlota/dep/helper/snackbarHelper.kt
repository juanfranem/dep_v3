package com.virgendelosdoloreslacarlota.dep.helper

import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.virgendelosdoloreslacarlota.dep.R

fun View.showSnackBarErrorMessage(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(ResourcesCompat.getColor(resources, R.color.red, context.theme))
        .setTextColor(ResourcesCompat.getColor(resources, R.color.white, context.theme))
        .show()
}

fun View.showSnackBarWarningMessage(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(ResourcesCompat.getColor(resources, R.color.yellow, context.theme))
        .setTextColor(ResourcesCompat.getColor(resources, R.color.black, context.theme))
        .show()
}

fun View.showSnackBarSuccessMessage(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(ResourcesCompat.getColor(resources, R.color.green, context.theme))
        .setTextColor(ResourcesCompat.getColor(resources, R.color.white, context.theme))
        .show()
}