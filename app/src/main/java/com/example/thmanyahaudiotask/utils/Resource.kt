package com.example.thmanyahaudiotask.utils

import com.example.thmanyahaudiotask.utils.enums.ErrorStates

sealed class Resource<T>(
    val status: Status,
    val data: T? = null,
    val errorMessage: String? = null,
    val errorCode: ErrorStates? = null
) {
    class Success<T>(data: T, errorMessage: String? = null) :
        Resource<T>(Status.SUCCESS, data, errorMessage)

    class Loading<T>(data: T? = null, errorMessage: String? = null) :
        Resource<T>(Status.LOADING, data, errorMessage)

    class Error<T>(data: T? = null, errorMessage: String? = null, errorCode: ErrorStates?) :
        Resource<T>(Status.ERROR, data, errorMessage, errorCode)
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
