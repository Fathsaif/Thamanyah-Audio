package com.example.thmanyahaudiotask.ui.search.presenter

import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionUi

sealed class SearchUIState {
    data class Success(val data: List<SectionUi>) : SearchUIState()
    data class Error(val message: String) : SearchUIState()
    data class Searching(val query: String) : SearchUIState()
    object Init : SearchUIState()
    object Empty : SearchUIState()
}