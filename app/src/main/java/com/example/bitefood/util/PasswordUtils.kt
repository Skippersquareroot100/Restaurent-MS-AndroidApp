package com.example.bitefood.util

import java.security.MessageDigest

object PasswordUtils {
    fun hash(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hashed = md.digest(password.toByteArray(Charsets.UTF_8))
        return hashed.joinToString("") { "%02x".format(it) }
    }
}
