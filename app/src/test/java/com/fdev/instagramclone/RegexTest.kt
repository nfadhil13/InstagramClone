package com.fdev.instagramclone

import org.junit.jupiter.api.Test
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegexTest {

    @Test
    fun regexTest(){
        val b = Pattern.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\\\S+\$).{4,}\$","aaaab123@AA")
        val pattern = Pattern.compile("^[a-z]{1,}\$");
        val matcher = pattern.matcher("aaaaa")
        println("${matcher.matches()}")
    }

}