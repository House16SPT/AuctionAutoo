package com.example.auctionauto

import android.content.Context
import android.widget.Toast

// Useful/repeatedly-used-in-multiple-files functions go here, and we can call them in all files after importing from this file!

fun ensureNumeric(context: Context, input: String) : String {
    // ensure numeric input
    if (!input.matches(Regex("[^0-9]+"))) { // if only numeric chars are entered
        return input.replace(Regex("[^0-9]"), "")
    } else {
        Toast.makeText(context, "Duration must be a positive integer.", Toast.LENGTH_SHORT).show()
        return input.replace(Regex("[^0-9]"), "") // remove non-number chars from input
    }
}