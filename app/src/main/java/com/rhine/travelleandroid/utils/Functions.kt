package com.rhine.travelleandroid.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Patterns
import java.math.BigInteger
import java.security.MessageDigest
import java.util.regex.Pattern

fun String.sha256(): String {
    val md = MessageDigest.getInstance("SHA-256")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(64, '0')
}

fun String?.isValidEmail(): Boolean =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String?.isPasswordValid(): Boolean = Pattern.compile(
    "^" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[0-9])" +
            "(?=.*[!@#\\\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>?/`~])" +
            "(?=\\S+$)" +
            ".{4,}" +
            "$"
).matcher(this ?: "").matches()

fun String?.passwordLength(): Boolean = Pattern.compile(
    ".{6,}"
).matcher(this ?: "").matches()

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}