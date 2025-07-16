package com.example.thmanyahaudiotask.utils

import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object NetworkHelper {
    fun <T> handleResponseException(
        exception: Exception,
        networkError: T,
        serverError: T,
        cancelled: T? = null
    ): T {
        return when (exception) {
            is TimeoutCancellationException -> {
                networkError
            }

            is SocketTimeoutException -> {
                networkError
            }

            is IOException -> {
                networkError
            }

            is HttpException -> {
                serverError
            }

            is JsonSyntaxException -> {
                serverError
            }

            is CancellationException -> {
                cancelled ?: serverError
            }

            else -> {
                serverError
            }
        }
    }
}
