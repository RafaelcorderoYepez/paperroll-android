package com.example.paperroll_123.domain.errors

enum class ErrorType(val message: String) {
    NoInternet("There is no internet connection"),
    Timeout("The connection took too long"),
    SslError("SSL certificate error"),
    General("An unexpected error occurred")
}
