package com.example.thmanyahaudiotask.ui.home.presenter

import com.example.thmanyahaudiotask.mvi_base.ViewEvent

sealed interface HomeEvent : ViewEvent {
    data object LoadInitial : HomeEvent
    data object Refresh : HomeEvent
    data object LoadNextPage : HomeEvent
    data class Retry(val from: RetrySource) : HomeEvent

    enum class RetrySource { INITIAL, PAGING }
}
