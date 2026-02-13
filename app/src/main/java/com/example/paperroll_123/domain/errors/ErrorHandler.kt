package com.example.paperroll_123.domain.errors

fun mapExceptionToError(e: Exception): ErrorType {
    return when (e) {
        is java.net.UnknownHostException,
        is java.net.ConnectException -> ErrorType.NoInternet
        is java.net.SocketTimeoutException -> ErrorType.Timeout
        else -> ErrorType.General
    }
}
