package com.example.thmanyahaudiotask.ui.home.presenter

import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionUi

sealed class HomeUIStates {
    data class Success(val data: List<SectionUi>) : HomeUIStates()
    data class Error(val message: String) : HomeUIStates()
    object Loading : HomeUIStates()
    object Empty : HomeUIStates()
}
