package com.example.getitdone

import android.text.Editable
import com.example.getitdone.ui.MainActivity
import com.example.getitdone.util.InputValidator
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class inputValidationTest {

    @Test
    fun inputValidator_returnsFalseWhenEmpty(){

       val result =  InputValidator.validInput("")
        assertFalse(result)
    }

    @Test
    fun inputValidator_returnsFalseWhenNull(){

        val result =  InputValidator.validInput(null)
        assertFalse(result)
    }

    @Test
    fun inputValidator_returnsFalseWhenOnlyWhiteSpaces(){

        val result =  InputValidator.validInput("     ")
        assertFalse(result)
    }

    @Test
    fun inputValidator_returnsTrueWhenMoreThanOneNonWhiteSpaceCharacter(){

        val result =  InputValidator.validInput("      ab")
        assertTrue(result)
    }
}