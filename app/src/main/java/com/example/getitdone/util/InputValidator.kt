package com.example.getitdone.util

import android.text.Editable

object InputValidator {
    fun validInput(it: String?):Boolean{
        return !it?.trim().isNullOrEmpty() && it!!.trim().length>=2
    }
}